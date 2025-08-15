# Tiny Survivors Bot (Accesibilidad)

Proyecto Android (Kotlin) con un servicio de **Accesibilidad** para automatizar taps
en el juego. Incluye:
- App con interruptor para encender/apagar el bot.
- Servicio de accesibilidad con motor de **secuencias** (ej. "Reclamar" → "Atacar").
- Workflow de **GitHub Actions** para compilar y publicar el APK debug como artefacto.

## Uso rápido
1. Clona el repo y abre en **Android Studio** (JDK 17).
2. Ejecuta en un dispositivo real.
3. En la app, abre **Ajustes de Accesibilidad** y habilita *Tiny Survivors Bot*.

## CI (GitHub Actions)
- En cada push/PR a `main` o `master`, se compila con Gradle 8.7 + JDK 17.
- El APK `app-debug` queda disponible como artefacto del workflow.

## Personalización
- Ajusta `res/xml/accessibility_config.xml` → `android:packageNames` con el package del juego real.
- Añade/edita acciones en `TinySurvivorsService.kt` (lista `sequence`).