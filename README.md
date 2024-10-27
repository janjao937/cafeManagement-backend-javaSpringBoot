# cafeManagement-backend-javaspringboots-mysql

## Don't forget to Create file at ./src/main/resources/application.properties

- spring.application.name=cafe-management-backend


- server.port=8080
- spring.security.user.name="set spring security username"
- spring.security.user.password="set spring security password"

### database setting
- spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
- spring.datasource.url=jdbc:mysql://localhost:3306/cafe_database
- spring.datasource.username=
- spring.datasource.password=
- spring.jpa.show-sql=true
-  spring.jpa.hibernate.ddl-auto=update
-  spring.jpa.properties.hibernate.transaction.jta.platform=org.hibernate.dialect.MySQLDialect
-  spring.jpa.properties.hibernate.format_sql=true

### mail SMTP setting

- spring.mail.host=smtp.gmail.com   (host mail name)
- spring.mail.username=xxx@gmail.com   (my email)
- spring.mail.port=587   (port 587=gmail)
- spring.mail.password=xxxx aaaa xxxx xxxx  (generate password from "https://myaccount.google.com/apppasswords" (in 2024)|SMTP password login server)
- spring.mail.properties.mail.smtp.auth=true   (SMTP auth)
- spring.mail.properties.mail.smtp.starttls.enable=true   (Upgrades an insecure SMTP connection)