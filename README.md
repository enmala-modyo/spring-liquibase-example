# Ejemplo modificación de esquema de base de datos y carga previa con Liquibase

Este proyecto presenta un ejemplo sencillo en el que se utilizan las capacidades nativas
de Spring para inicializar y realizar la carga previa de datos en el esquema de bases de datos.

Este mecanismo es muy básico y requiere de especial cuidado por parte del desarrollador para evitar errores.

Cuando se utiliza este mecanismo se ejecutan dos scripts (podrían ser más):
- schema.sql: Para modificar el esquema de la base de datos
- data.sql: Para cargar datos

En la configuración utilizada en el proyecto, estos scripts se ejecutan **después** de aplicar los cambios
definidos en las entidades JPA, es decir, se usan como complemento a JPA.

## Configuración
Para habilitar esta funcionalidad debemos modificar el archivo donde definimos el datasource principal.
En este caso `application-certification.yml`, `application-staging.yml` y `application-production.yml`, esto
asumiendo que los cambios los queremos ejecutar en los tres ambientes.

En todos los archivos debemos agregar:
```yaml
spring:
  jpa:
    defer-datasource-initialization: true
  sql:
    init:
      continue-on-error: true
      mode: always
      schema-locations: classpath:sql/schema.sql
      data-locations: classpath:sql/data.sql
```
- `defer-datasource-initialization` se utiliza para indicar que los scripts se ejecutarán después
de la configuración JPA (esto es importante para evitar errores).
- `continue-on-error` se usa para indicar que si hay algún error en nuestros scripts el proceso continúe.
Se puede cambiar a **false** para forzar que los scripts se ejecuten sin errores, sin embargo, mantenerlo en true
puede ser útil (como veremos más adelante).
- `mode` el modo **always** se usa para indicar que los scripts se deben ejecutar siempre, independiente
del motor de bases de datos en uso. En condición normal estos scripts solamente se ejecutan para bases de datos
en memoria (por ejemplo H2).
- `schema-locations` y `data-locations` se usan para indicar donde están los scripts que utilizaremos para
la inicialización de la base de datos. Si se definen no pueden estar vacíos, por lo que solamente se utilizará uno
ese es el único que debe definirse.

## Consideraciones para schema.sql
El script schema.sql se ejecutará inmediatamente terminado el proceso de inicialización JPA y
tiene por objeto realizar ajustes a la estructura creada en forma automática.
Es importante recordar que este script **se ejecuta cada vez que el servicio se inicia** por lo que
si tenemos el servicio corriendo en contenedores cada vez que un nuevo contenedor se levante el script se ejecutará.
Esto implica que debemos ser cuidadosos al momento de escribir nuestras sentencias SQL para no afectar a
las instancias en ejecución.

¿Qué precauciones tomar?
- Usar `if exists` en las sentencias que lo permitan.
- Verificar el comportamiento de nuestro script varias veces en nuestro ambiente local antes de desplegar algún cambio.

Como en nuestra configuración marcamos `continue-on-error: true`, si se produce algún error durante
la aplicación de los cambios, el proceso de inicialización del servicio continuará.

¿Para que podemos usar este script?
- Para eliminar objetos (tablas, columnas) que ya no necesitamos, por ejemplo debido a un cambio en la
configuración de nuestras entidades JPA. Hay que recordar que JPA solamente realiza cambios incrementales (agrega cosas),
nunca borra, por lo que si cambiamos el nombre de una columna, lo que sucederá realmente es que se creará una columna
nueva sin datos. Entonces podría ser útil ejecutar un script que verifique si la columna antigua existe,
copie los datos a la nueva y luego la elimine.
- Para eliminar restricciones. Supongamos que inicialmente teníamos una columna marcada como NOT NULL, pero
durante el desarrollo aparece un caso en el que es válido que la columna no tenga datos. En la
entidad podemos eliminar la restricción, pero ese cambio no se aplicará a la BD. Para esto podemos utilizar este script.

## Consideraciones para data.sql
El comportamiento de data.sql es similar al de schema.sql, pero está pensado para la carga de datos,
por lo tanto, se ejecuta después de schema.sql (cuando el esquema esta listo).

Al igual que en el caso anterior, este script se ejecuta cada vez que una instancia de nuestro servicio
se inicia, por lo que hay que tener especial cuidado de evitar insertar datos duplicados.

Aquí, la configuración `continue-on-error: true` nos puede ser útil debido a que podemos confiar
el control de la base de datos para evitar la inserción de datos duplicados para tablas de códigos
(la clave la conocemos). Distinto es el caso de las tablas que usan identity para la clave primaria.
En esos casos somos nosotros los responsables de tomar las medidas necesarias.

¿Para que podemos usar este script?
- Carga inicial de tablas de códigos (países, códigos telefónicos, estado civil, etc.)
- Corregir/actualizar códigos cargados previamente.


