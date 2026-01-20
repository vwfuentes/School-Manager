# School-Manager

Este proyecto es una aplicación de escritorio moderna para la gestión de estudiantes en una escuela.

## Requisitos previos

- Java JDK 11 o superior
- Maven 3.6+ instalado

## Descargar dependencias

Antes de compilar, ejecuta el siguiente comando para descargar todas las dependencias necesarias:

```
mvn dependency:resolve
```

Este comando descargará automáticamente todas las dependencias (JavaFX, SQLite JDBC, PostgreSQL JDBC) definidas en el archivo `pom.xml`.  
Si el comando finaliza sin errores, las dependencias están listas para usar.

## Compilar y ejecutar con Maven

1. **Instala Java y Maven**  
   Verifica que tienes Java 11+ y Maven instalados:
   ```
   java -version
   mvn -version
   ```

2. **Compila el proyecto**
   ```
   mvn clean install
   ```

3. **Ejecuta la aplicación**
   ```
   mvn javafx:run
   ```

## Ejecutar desde un IDE (IntelliJ IDEA, Eclipse)

1. Importa el proyecto como proyecto Maven.
2. Asegúrate de que el JDK configurado sea 11 o superior.
3. Ejecuta la clase `com.schoolmanager.Main` como aplicación Java.

## Configuración de JavaFX

Maven descarga automáticamente las dependencias de JavaFX.  
Si ejecutas manualmente (sin Maven), debes descargar el JavaFX SDK y agregarlo al módulo-path:

1. Descarga JavaFX SDK desde [https://gluonhq.com/products/javafx/](https://gluonhq.com/products/javafx/)
2. Extrae el SDK y localiza la carpeta `lib`.
3. Ejecuta con:
   ```
   java --module-path /ruta/a/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml -cp target/classes com.schoolmanager.Main
   ```

## Uso de la aplicación

1. Haz clic en "Subir archivo CSV" para cargar los datos de estudiantes.
2. Espera el mensaje "loading data..." mientras se procesan los datos.
3. Los estudiantes se mostrarán en la tabla.
4. Haz doble clic en una fila para editar los datos del estudiante en un modal.
5. Los cambios se guardan automáticamente en la base de datos SQLite (`school.db`).

## Estructura del archivo CSV

El archivo debe tener las siguientes columnas (en este orden):

```
nombre,apellidoMaterno,apellidoPaterno,edad,curso,celular,nombrePadre,apellidoPadre,nombreMadre,apellidoMadre,celularPadre,celularMadre
```

## Solución de problemas

- Si ves el error sobre JavaFX runtime, asegúrate de usar Maven o configura el módulo-path correctamente.
- Si tienes problemas con SQLite, verifica que tienes permisos de escritura en la carpeta del proyecto.
- **Si obtienes `command not found` al ejecutar comandos Maven:**  
  Debes instalar Maven en tu laptop.  
  - En macOS puedes instalar Maven fácilmente con Homebrew:
    ```
    brew install maven
    ```
    Si después de instalar sigue mostrando `command not found`, puede que el directorio de Maven no esté en tu PATH.  
    1. Verifica la ruta donde Homebrew instaló Maven:
       ```
       brew --prefix maven
       ```
       Esto mostrará una ruta como `/opt/homebrew/opt/maven` o `/usr/local/opt/maven`.
    2. Agrega el directorio `bin` de esa ruta a tu PATH. Por ejemplo:
       ```
       echo 'export PATH="$(brew --prefix maven)/bin:$PATH"' >> ~/.zshrc
       source ~/.zshrc
       ```
    3. Verifica la instalación:
       ```
       mvn -version
       ```
    Si sigue sin funcionar, asegúrate de que no tienes conflictos con otras versiones de Java/Maven y que tu terminal está usando el archivo de configuración correcto (`~/.zshrc` para zsh, `~/.bash_profile` para bash).
  - En Linux puedes instalar Maven con el gestor de paquetes de tu distribución:
    - **Debian/Ubuntu:**
      ```
      sudo apt update
      sudo apt install maven
      ```
    - **Fedora:**
      ```
      sudo dnf install maven
      ```
    - **Arch Linux:**
      ```
      sudo pacman -S maven
      ```
  - O descarga Maven desde [https://maven.apache.org/download.cgi](https://maven.apache.org/download.cgi)
  - Sigue la guía de instalación: [https://maven.apache.org/install.html](https://maven.apache.org/install.html)
  - Verifica la instalación ejecutando en la terminal:
    ```
    mvn -version
    ```
  Si el comando muestra la versión de Maven, ya está instalado correctamente.

## Créditos

Desarrollado con JavaFX y SQLite JDBC.
