#!/usr/bin/env bash
# Gate rápido del proyecto: build + lint + tests de server/ y client/.
# Uso: ./scripts/verify.sh [--server | --client]
# Sale con código != 0 si algo falla. Salida breve; el detalle queda en .verify-logs/<paso>.log
set -uo pipefail

ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
LOGS="$ROOT/.verify-logs"
mkdir -p "$LOGS"

RUN_SERVER=1; RUN_CLIENT=1
case "${1:-}" in
  --server) RUN_CLIENT=0 ;;
  --client) RUN_SERVER=0 ;;
  "") ;;
  *) echo "uso: $0 [--server | --client]"; exit 2 ;;
esac

FAILED=0
SUMMARY=()

# Muestra las últimas líneas útiles de un log fallido.
show_tail() {
  echo "---- $1 (últimas líneas; completo en ${1#"$ROOT"/}) ----"
  grep -v 'JAVA_TOOL_OPTIONS' "$1" | grep -vE '^\s*$' | tail -n "${2:-25}"
  echo "----"
}

port_open() { (exec 3<>"/dev/tcp/127.0.0.1/$1") 2>/dev/null; }

verify_server() {
  local s="server:" log="$LOGS/server.log"
  cd "$ROOT/server" || return

  # Variables: env.properties si existe; si no, los valores locales de ejemplo.
  local envfile="env.properties"
  [ -f "$envfile" ] || envfile="env.properties.example"
  set -a; # shellcheck disable=SC1090
  . "./$envfile"; set +a

  # Base de datos: la necesita el test de contexto (hasta T-006 / Testcontainers).
  if ! port_open 5432; then
    if command -v docker >/dev/null 2>&1; then
      docker compose up -d --wait >"$LOGS/db.log" 2>&1 || { show_tail "$LOGS/db.log" 10; }
    fi
  fi
  if ! port_open 5432; then
    SUMMARY+=("$s ❌ db (Postgres no responde en :5432; ver docs/SETUP.md)"); FAILED=1; return
  fi

  local jv
  jv="$(java -version 2>&1 | grep -m1 'version' | sed -E 's/.*version "([0-9]+).*/\1/')"
  if ./mvnw -B clean verify >"$log" 2>&1; then
    local tests
    tests="$(grep -E '^\[INFO\] Tests run:' "$log" | tail -1 | sed -E 's/.*Tests run: ([0-9]+).*/\1/')"
    SUMMARY+=("$s ✅ build ✅ tests (${tests:-0}) [JDK $jv]")
  else
    FAILED=1
    if grep -q 'Tests run:.*\(Failures: [1-9]\|Errors: [1-9]\)' "$log"; then
      SUMMARY+=("$s ✅ build ❌ tests [JDK $jv]")
      grep -E '<<< (FAILURE|ERROR)!|^\[ERROR\] Tests run:' "$log" | head -10
    else
      SUMMARY+=("$s ❌ build [JDK $jv]")
      grep -E '^\[ERROR\] (Failed to execute|/.*\.java)' "$log" | head -8 | cut -c1-300
      [ "$jv" != "17" ] && echo "Aviso: el build requiere JDK 17 hasta T-002 (java en PATH/JAVA_HOME es JDK $jv)."
    fi
    echo "Log completo: .verify-logs/server.log"
  fi
}

verify_client() {
  local s="client:" parts=()
  cd "$ROOT/client" || return

  # npm ci solo si faltan dependencias o cambió el lockfile.
  if [ ! -f node_modules/.package-lock.json ] || [ package-lock.json -nt node_modules/.package-lock.json ]; then
    if ! npm ci --no-audit --no-fund >"$LOGS/client-install.log" 2>&1; then
      SUMMARY+=("$s ❌ npm ci"); FAILED=1; show_tail "$LOGS/client-install.log"; return
    fi
  fi

  if npm run -s lint >"$LOGS/client-lint.log" 2>&1; then
    parts+=("✅ lint")
  else
    local n; n="$(grep -oE '[0-9]+ problems?' "$LOGS/client-lint.log" | tail -1)"
    parts+=("❌ lint (${n:-ver log})"); FAILED=1; show_tail "$LOGS/client-lint.log" 15
  fi

  if npm run -s build >"$LOGS/client-build.log" 2>&1; then
    parts+=("✅ build")
  else
    parts+=("❌ build"); FAILED=1; show_tail "$LOGS/client-build.log"
  fi

  if npm pkg get scripts.test | grep -q '"'; then
    if npm test --silent >"$LOGS/client-test.log" 2>&1; then
      parts+=("✅ tests")
    else
      parts+=("❌ tests"); FAILED=1; show_tail "$LOGS/client-test.log"
    fi
  else
    parts+=("⚪ sin tests (T-007)")
  fi
  SUMMARY+=("$s ${parts[*]}")
}

START=$SECONDS
[ $RUN_SERVER -eq 1 ] && verify_server
[ $RUN_CLIENT -eq 1 ] && verify_client

echo
OUT=""; for x in "${SUMMARY[@]}"; do OUT+="${OUT:+ · }$x"; done; echo "$OUT"
echo "($((SECONDS - START)) s) $([ $FAILED -eq 0 ] && echo 'VERIFY OK' || echo 'VERIFY FAILED')"
exit $FAILED
