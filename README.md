# BeanFramework

## Development Status

In progress, targeting at release 1.0.0.

## Why BeanFramework

BeanFramework is a project template with ready made modules such as user management, login function, cronjob and so on.

## Technologies

* Java: 1.8
* Spring Boot: 2.0.3.RELEASE
* AdminLTE 2.4.5

## Instruction

* Quick Startup

1. Copy all the directories under /beanframework/template/* and replace to /beanframework/*
2. Configure /beanframework/config/local.properties
3. Configure /beanframework/bin/pom.xml
4. Configure /beanframework/bin/platform/src/main/resources/application.properties for [app env]
5. Configure /beanframework/bin/platform/src/main/resources/config/ehcache-[Base on app env].xml
6. Configure /beanframework/bin/platform/src/main/resources/config/logback-[Base on app env].xml
7. Run "mvnw clean install" command in /beanframework/bin directory
8. Run server.bat or server.sh
9. Browse http://localhost:8080/console , navigate to Platform->Update to update all modules' data

* Console
http://localhost:8080/console

Default Account
Username: admin
Password: admin

Thre are two ways to change default admin's password:
Add/Modify "module.admin.default.id=admin" and "module.admin.default.password=admin" in properties file
or
Create admin record in console and change its password, this will allow console to validate admin credential from database instead of properties file

* Backoffice
http://localhost:8080/backoffice

Default Account
Username: employee
Password: employee

## License

MIT license. https://github.com/beanproject/beanframework/blob/master/LICENSE.txt

## Donation
If this project helped you reduce development time, you can always buy me a cup of coffee :) 

[![paypal](https://www.paypalobjects.com/en_US/i/btn/btn_donateCC_LG.gif)](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=QSJEVREPCXW72)