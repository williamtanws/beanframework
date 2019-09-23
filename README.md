# Beanframework

## Development Status

Developing Beanframework 2.0.x

## Why Beanframework

Beanframework is a 100% open source project with ready made modules such as user management, login function, cronjob and so on. 
Beanframework is not a product and not build for any specific solution, it is a project framework with template that help you kick start your fresh project, with all the basic modules needed.

## Technologies

* Java: 1.8.x
* Spring Boot: 2.1.1.RELEASE
* AdminLTE 2.4.5

## Instruction

* Quick Startup

1. Duplicate all *.template files and remove .template suffix:
a. beanframework/bin/pom.xml.template
a. beanframework/bin/server.bat.template
a. beanframework/bin/server.sh.template
a. beanframework/bin/install/app.xml.template
a. beanframework/bin/platform/pom.xml.template
b. beanframework/config/pom.xml.template
c. beanframework/config/src/main/resources/*.template
2. Configure all the duplicated template files properly
3. Navigate to /beanframework/bin and run "mvnw clean install" command
4. Run server.bat or server.sh
5. Browse http://localhost:8080/console , navigate to Platform->Update to update all modules' data

* Install Application as Service
1. Navigate command line to beanframework/bin/install
2. Run "app install"
3. To remove, run "app uninstall"

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

MIT license. https://github.com/williamtanws/beanframework/blob/master/LICENSE

## Donation :)
If this open source project helped in your project, you could always back up this project hosting at https://beanframework.com by donation: 

[![paypal](https://www.paypalobjects.com/en_US/i/btn/btn_donateCC_LG.gif)](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=QSJEVREPCXW72)