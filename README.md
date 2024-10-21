# cafeManagement-backend-javaspringboots-mysql

# Don't forget to Create file at /src/main/resources/application.properties

- spring.application.name=cafe-management-backend
- server.port=8080

#database setting
- spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
- spring.datasource.url=jdbc:mysql://localhost:3306/cafe-database
- spring.datasource.username=
- spring.datasource.password=
- spring.jpa.show-sql=true
- spring.jpa.hibernate.ddl-auto=update
- spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
- spring.jpa.properties.hibernate.format_sql=true