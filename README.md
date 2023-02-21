# Ejemplo modificación de esquema de base de datos y carga previa con Liquibase

Este proyecto presenta un ejemplo sencillo en el que se utiliza Liquibase para
inicializar, modificar y realizar la carga de datos en el esquema de bases de datos.

Este mecanismo es relativamente simple y solamente se requiere tener cuidado de marcar adecuadamente los
changesets para que Liquibase pueda reconocer el orden correcto de aplicación.

En este proyecto se ha organizado la configuración de la siguiente forma:
1. El archivo resources/db/changelog/db.changelog-master.yaml contiene la configuración base para Liquibase y
referencia a los archivos en resources/db/changelog/changes.
2. En resources/db/changelog/changes se han dispuesto varios archivos con los changesets.
3. En los archivos application-certification.yml, application-staging.yml y application-production.yml
se ha configurado el uso de Liquibase.

En esta configuración cada vez que el servicio se inicia Liquibase revisa en la base de datos
cuál es el último changeset aplicado y aplica todos los siguientes (por eso es importante numerarlos correctamente).
A continuación Hibernate verifica que el esquema de la base de datos sea consistente con lo definido en
las clases de entidades JPA. Con esto, si existe alguna inconsistencia el servicio no se levantará y
en el log se obtendrá el detalle del problema a corregir.

## Configuración
Para habilitar esta funcionalidad debemos modificar el archivo donde definimos el datasource principal.
En este caso `application-certification.yml`, `application-staging.yml` y `application-production.yml`, esto
asumiendo que los cambios los queremos ejecutar en los tres ambientes.

En todos los archivos debemos agregar:
```yaml
spring:
  liquibase:
    enabled: true
  jpa:
    properties:
      hibernate:
        dialect: ${PERSISTENCE_DIALECT:org.hibernate.dialect.MySQL5InnoDBDialect}
    show-sql: ${PERSISTENCE_SHOW_SQL:true}
  datasource:
    modyo:
      url: ${PERSISTENCE_URL:jdbc:h2:mem:modyo_ms_schema;MODE=MYSQL}
      username: ${PERSISTENCE_USER:root}
      password: ${PERSISTENCE_PASSWORD:}
      ddl-auto: validate
      hikari:
        connectionTimeout: ${PERSISTENCE_CONEC_TIMEOUT:30000}
        idleTimeout: ${PERSISTENCE_IDLE_TIMEOUT:600000}
        maxLifetime: ${PERSISTENCE_MAX_LIFETIME:1800000}
        maximumPoolSize: ${PERSISTENCE_MAX_POOL_SIZE:7}
```
Aquí hemos configurado ddl-auto en `validate` y hemos habilitado Liquibase (esta habilitado en forma predeterminada,
pero no perdemos nada teniendo el interruptor a la vista).

## Consideraciones para db.changelog-master.yaml
El nombre del archivo utilizado es el predeterminado y si queremos podemos modificarlo en la configuración
anterior.

Para mantener nuestra configuración ordenada hemos dejado todos los changesets en la carpeta changes, de otra forma
tendríamos que agregarlos en el mismo archivo.

Como vemos en los ejemplos dentro de resources/db/changelog/changes podemos utilizar Liquibase para
configurar la estructura de la base de datos, modificar objetos ya creados y para cargar o corregir
datos en las tablas.

