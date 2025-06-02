# Control Luz Pro

**Control Luz Pro** es una aplicación móvil desarrollada en Android Studio con Kotlin y Jetpack Compose, que simula el control y monitoreo en tiempo real de un dispositivo IoT (Internet de las Cosas), como una lámpara inteligente.

## 🚀 Características principales

- **Registro e inicio de sesión de usuarios** usando Firebase Authentication.
- **Visualización del estado** de una luz virtual (encendida o apagada) en tiempo real.
- **Control remoto:** cualquier usuario puede encender o apagar la luz desde la app y el cambio se refleja al instante para todos.
- **Panel visual:** ícono de bombilla, estado actual, y correo del usuario que realizó el último cambio.
- **Sincronización en tiempo real** utilizando Firebase Realtime Database.
- **Opción para mostrar u ocultar la contraseña** al escribirla.

## 🛠️ Tecnologías utilizadas

- Android Studio
- Kotlin + Jetpack Compose
- Firebase Authentication
- Firebase Realtime Database

## 🏠 Simulación IoT y conexión con hardware real

La app está pensada como la interfaz de usuario para un sistema IoT:  
- Un microcontrolador (por ejemplo, Arduino o ESP32) podría conectarse a la misma base de datos Firebase.
- Así, el microcontrolador puede **leer el estado** de la luz y controlar un dispositivo físico real (como un relay, LED, lámpara, etc.).
- Cualquier cambio realizado desde la app se reflejaría en el hardware y viceversa, todo en tiempo real.

## 🖼️ Capturas de pantalla

### Pantalla de Login
![Login](login.png)

### Panel de control
![Panel de control](panel.png)

### Luz apagada y encendida
![Apagada](apagada.png)
![Encendida](encendida.png)

### Vista de la base de datos en Firebase
![Firebase](firebase-apagada.png)
![Firebase](firebase-encendida.png)

## 📦 Instalación y ejecución

1. Clona este repositorio.
2. Agrega el archivo `google-services.json` configurado para tu proyecto Firebase en la carpeta `/app`.
3. Ejecuta en un emulador o dispositivo físico.

## 📝 Observaciones

- Para la defensa, la app se ejecutó en un emulador debido a problemas de compatibilidad de Google Play Services en el dispositivo físico.
- El funcionamiento es exactamente igual en un celular real.

---

**Autor:** Yonathan Andrade  
**Proyecto U2 - Analista Programador INACAP**
