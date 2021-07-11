spring.datasource.driverClassName=org.postgresql.Driver
spring.datasource.url= ${dbUrl}
spring.sql.init.mode=always
spring.sql.init.platform=${dbType}
spring.datasource.username=${dbUsername}
spring.datasource.password=${dbPassword}
spring.jpa.hibernate.ddl-auto = create-drop
spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.PostgreSQL95Dialect

server.port=${appPort}
server.servlet.context-path=/${appContextPath}
spring.application.name=${appName}