# T-015 · Activar Dependabot
- Estado: Bloqueada (por T-011) · Prioridad: P0 · Esfuerzo: S · Agente: 🤖 ligero
- Fase: P0 Estabilizar
- Rama: `ci/T-015-enable-dependabot` · Depende de: T-011 (CI en verde) · Spec/ADR: —
- Revisa: frontera (toca CI)

## Por qué
No hay actualización automática de dependencias (auditoría §4.8) y el front tiene 40 vulnerabilidades. Se activa recién con CI en verde para que cada PR de Dependabot se valide solo.

## Objetivo
`.github/dependabot.yml` con Maven, npm y GitHub Actions, semanal, minor/patch agrupados, títulos compatibles con `pr-title.yml`.

## Archivos previstos
- crear: `.github/dependabot.yml` · modificar: `docs/STATUS.md`

## YAML propuesto

```yaml
version: 2
updates:
  - package-ecosystem: maven
    directory: /server
    schedule: { interval: weekly }
    commit-message: { prefix: build, include: scope }
    groups:
      maven-minor-patch: { update-types: [minor, patch] }
  - package-ecosystem: npm
    directory: /client
    schedule: { interval: weekly }
    commit-message: { prefix: build, include: scope }
    groups:
      npm-minor-patch: { update-types: [minor, patch] }
  - package-ecosystem: github-actions
    directory: /
    schedule: { interval: weekly }
    commit-message: { prefix: ci }
```

## Criterios de aceptación
- [ ] `python3 -c "import yaml;yaml.safe_load(open('.github/dependabot.yml'))"` sin errores
- [ ] En GitHub → Insights → Dependency graph → Dependabot aparecen los 3 ecosistemas sin errores (captura en el PR después del merge)
- [ ] El primer PR de Dependabot tiene título `build(deps): …` y pasa `pr-title`

## Notas de implementación
- Hecho: … · Pendiente: … · Próximo paso: …

## Resultado
- PR: #… · Qué cambió: … · Tests agregados: — · Pendientes → nuevas tareas: …
