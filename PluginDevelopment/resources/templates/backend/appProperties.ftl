# generated on ${.now?date} at ${.now?time} based on ${.current_template_name}
spring.datasource.driverClassName=${dbDriverClassName}
spring.datasource.url=${dbUrl}
spring.sql.init.mode=${dbInitMode}
spring.sql.init.platform=${dbType}
spring.datasource.username=${dbUsername}
spring.datasource.password=${dbPassword}
spring.jpa.hibernate.ddl-auto=${dbDDLAuto}
spring.jpa.properties.hibernate.dialect=${dbDialect}

server.port=${appPort}
server.servlet.context-path=/${appContextPath}
spring.application.name=${appName}