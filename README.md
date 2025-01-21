# Proyecto de Gestión de Cursos y Asistencia

Este proyecto es una aplicación web diseñada para gestionar cursos, asistencia y calificaciones, proporcionando una solución práctica para docentes y estudiantes.

## Tabla de Contenidos
- [Características Principales](#características-principales)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Estructura de la Base de Datos](#estructura-de-la-base-de-datos)
- [Requisitos del Sistema](#requisitos-del-sistema)
- [Instrucciones de Instalación](#instrucciones-de-instalación)
- [Uso de la Aplicación](#uso-de-la-aplicación)
- [Pruebas](#pruebas)
- [Contribución](#contribución)

## Características Principales
- Gestión de usuarios: estudiantes, docentes y administradores.
- Registro y consulta de asistencia por curso.
- Calificación y revisión de insumos por parte de los docentes.
- Paneles de control personalizados para cada tipo de usuario.
- Registro de usuarios con roles asignados (docente o estudiante).

## Estructura del Proyecto

### Carpeta `controlador`
Contiene las clases que gestionan la interacción con la base de datos:
- **Conexion.java**: Gestiona la conexión a la base de datos MySQL utilizando JDBC.
- **DocenteDAO.java**: Maneja operaciones relacionadas con los docentes, como gestión de asistencia y calificaciones.
- **EstudianteDAO.java**: Permite consultar la asistencia y notas de los estudiantes.
- **UsuarioDAO.java**: Administra el registro, login y obtención de usuarios.

### Carpeta `modelo`
Define las entidades principales del sistema:
- **Asistencia.java**: Representa los registros de asistencia, incluyendo atributos como curso, fecha y estado.
- **Notas.java**: Gestiona las notas de los estudiantes con atributos para insumos y cálculo del promedio.
- **Usuario.java**: Define los datos de los usuarios, como nombre, correo y rol.

### Carpeta `webapp`
Incluye los recursos web divididos por roles:
- **docente/**: Páginas JSP específicas para la gestión de asistencia y calificaciones por parte de los docentes.
  - **asistencia.jsp**: Registro de asistencia.
  - **calificar.jsp**: Gestión de calificaciones.
  - **dashboard_docente.jsp**: Panel principal del docente.
- **estudiante/**: Páginas JSP para estudiantes.
  - **asistencia.jsp**: Consulta de asistencia.
  - **calificar.jsp**: Visualización de calificaciones asignadas.
  - **dashboard_estudiante.jsp**: Panel principal del estudiante.
- **WEB-INF/**: Archivos de configuración y páginas principales como `index.jsp`, `logout.jsp` y `registro.jsp`.

### Archivo `pom.xml`
El archivo central de configuración de Maven incluye:
- Dependencias: `junit` para pruebas y `mysql-connector-java` para la base de datos.
- Plugins: Configuración para Tomcat y gestión del ciclo de vida de Maven.

## Estructura de la Base de Datos

La base de datos está compuesta por las siguientes tablas principales:

1. **usuarios**: Contiene información de los usuarios registrados.
   - Campos: `id_usuario`, `nombre`, `apellido`, `email`, `cedula`, `telefono`, `id_role`.

2. **roles**: Define los roles de los usuarios (por ejemplo, docente o estudiante).
   - Campos: `id_role`, `nombre_role`.

3. **asistencias**: Registra la asistencia de los estudiantes a los cursos.
   - Campos: `id_asistencia`, `id_usuario`, `id_curso`, `fecha`, `estado` (valores: 'Presente', 'Ausente', 'Tarde').

4. **notas**: Almacena las calificaciones de los estudiantes.
   - Campos: `id_nota`, `id_usuario`, `id_curso`, `insumo1`, `insumo2`, `insumo3`, `total` (calculado automáticamente).

5. **cursos**: Describe los cursos disponibles.
   - Campos: `id_curso`, `nombre_curso`, `descripcion`.

6. **estudiantes_cursos**: Relaciona a los estudiantes con los cursos.
   - Campos: `id`, `id_usuario`, `id_curso`.

### Relaciones entre tablas
- `asistencias.id_usuario` -> `usuarios.id_usuario`
- `asistencias.id_curso` -> `cursos.id_curso`
- `notas.id_usuario` -> `usuarios.id_usuario`
- `notas.id_curso` -> `cursos.id_curso`
- `estudiantes_cursos.id_usuario` -> `usuarios.id_usuario`
- `estudiantes_cursos.id_curso` -> `cursos.id_curso`
- `usuarios.id_role` -> `roles.id_role`

## Requisitos del Sistema
- **Java 8+**
- **Apache Tomcat 9.0** o superior
- **MySQL 8.0**
- Navegador web moderno

## Instrucciones de Instalación
1. Clonar el repositorio:
   ```bash
   git clone https://github.com/JSuarezSE/Proyecto_Gestion.git
   ```
2. Configurar la base de datos:
   - Crear una base de datos en MySQL.
   - Importar el archivo `proyecto_gestion.sql` incluido en el proyecto.
3. Configurar la conexión:
   - Modificar `Conexion.java` con las credenciales de tu base de datos.
4. Desplegar en Apache Tomcat:
   - Compilar el proyecto y generar el archivo WAR.
   - Desplegar el WAR en el servidor Tomcat.
5. Acceder a la aplicación:
   - URL predeterminada: `http://localhost:8080/Proyecto_Gestion`

## Uso de la Aplicación
1. **Usuarios Docentes:**
   - Registrar asistencias seleccionando estudiantes y el estado correspondiente.
   - Registrar calificaciones de estudiantes directamente desde el sistema.
2. **Usuarios Estudiantes:**
   - Consultar su historial de asistencias.
   - Revisar sus calificaciones asignadas por los docentes.
3. **Navegación General:**
   - Los roles definen las funcionalidades accesibles desde el dashboard personalizado.

## Pruebas
Se realizaron pruebas unitarias e integradas para garantizar el correcto funcionamiento del sistema:
- **ConexionTest.java**: Verifica la conexión con la base de datos.
- **DocenteDAOTest.java**: Prueba las operaciones relacionadas con docentes, como gestión de asistencia y calificaciones.
- **EstudianteDAOTest.java**: Evalúa la obtención de datos de asistencia y calificaciones de los estudiantes.
- **UsuarioDAOTest.java**: Valida las operaciones de registro, login y consulta de usuarios.

## Contribución
Si deseas contribuir:
1. Haz un fork del repositorio.
2. Crea una rama para tu funcionalidad (`git checkout -b feature/nueva-funcionalidad`).
3. Realiza un pull request describiendo tus cambios.
