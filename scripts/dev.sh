#!/usr/bin/env bash
# Levanta el entorno local completo: Postgres (Docker) + back (:8080) + front (:5173).
# Uso: ./scripts/dev.sh        → Ctrl+C corta back y front (la base queda corriendo).
#      ./scripts/dev.sh --down → apaga la base (agregá -v a mano para borrar datos; ver README).
# Hace lo mismo que el paso a paso del README. Logs en .dev-logs/.
set -euo pipefail

ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
LOGS="$ROOT/.dev-logs"
mkdir -p "$LOGS"

if [ "${1:-}" = "--down" ]; then
  docker compose -f "$ROOT/server/docker-compose.yml" down
  exit 0
fi

wait_for() { # url, segundos
  for _ in $(seq 1 "$2"); do curl -sf -o /dev/null "$1" && return 0; sleep 1; done
  return 1
}

kill_tree() { # mata un proceso y todos sus descendientes (npm → sh → node, mvnw → java)
  local c; for c in $(pgrep -P "$1" 2>/dev/null); do kill_tree "$c"; done
  kill "$1" 2>/dev/null || true
}

PIDS=()
cleanup() {
  trap - EXIT INT TERM
  echo; echo "Cortando back y front..."
  for p in "${PIDS[@]}"; do kill_tree "$p"; done
  echo "La base sigue corriendo. Para apagarla: ./scripts/dev.sh --down"
}
trap cleanup EXIT INT TERM

# 1) Variables del back (sin editar nada: se copia el ejemplo si falta)
cd "$ROOT/server"
[ -f env.properties ] || cp env.properties.example env.properties
set -a; . ./env.properties; set +a

# 2) Base de datos
echo "→ Postgres (docker compose)..."
docker compose up -d --wait >"$LOGS/db.log" 2>&1 || { tail -n 20 "$LOGS/db.log"; exit 1; }

# 3) Back
echo "→ Back (./mvnw spring-boot:run), log: .dev-logs/server.log ..."
./mvnw -B -q spring-boot:run >"$LOGS/server.log" 2>&1 &
PIDS+=($!)
if ! wait_for http://localhost:8080/v1/api/render/health 180; then
  echo "❌ El back no respondió en 180 s. Últimas líneas:"; grep -v JAVA_TOOL_OPTIONS "$LOGS/server.log" | tail -n 30
  exit 1
fi

# 4) Front
cd "$ROOT/client"
if [ ! -f node_modules/.package-lock.json ] || [ package-lock.json -nt node_modules/.package-lock.json ]; then
  echo "→ npm ci ..."; npm ci --no-audit --no-fund >"$LOGS/client-install.log" 2>&1
fi
echo "→ Front (npm run dev), log: .dev-logs/client.log ..."
npm run dev -- --port 5173 --strictPort >"$LOGS/client.log" 2>&1 &
PIDS+=($!)
wait_for http://localhost:5173 60 || { echo "❌ El front no respondió."; tail -n 20 "$LOGS/client.log"; exit 1; }

cat <<'EOF'

✅ Entorno listo
   Front:   http://localhost:5173   (usuario demo: lavadero@munidigital.com / 12345Aa*)
   API:     http://localhost:8080/v1/api/render/health
   Swagger: http://localhost:8080/swagger-ui/index.html
   ⚠️ Hasta T-003 el front llama a la API de producción, no al back local.
   Ctrl+C para cortar back y front.
EOF
wait
