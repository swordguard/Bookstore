# Bookstore
It is an online bookstre protytpe, only for demonstrating purpose. It has user registration component, shopping cart component, order and so on.

This project consists of FIVE parts
* Web service tier, like  http://localhost:8081/axis/
* Database (it uses Derby) tier, 
* RMI tier, 
* App tier, like http://localhost:8080/bookstore/homepage.jsp
* Config files

1. Axis
All files in axis/ folder are made to provide backend web service.
This folder needs to be put into %tomcat%\webapps\
If it is correctly configured and running, run http://localhost:8081/axis/servlet/AxisServlet, it should give some info like
And now... Some Services

BookstoreFacadeService (wsdl)
main
register
login
getLatestTitles
viewOrderByUser
getBookByTitle
.
.
. and so on

all source files are included.


2. Database
It is included in the Axis part, please put it into %tomcat%\webapps\axis\bin\, and it needs DatabaseServersConfig.xml to run the WS

3. RMI
all files and folder in RMI folder are needed
4. App
all files and folder in bookstore

5. Config files
DatabaseServersConfig.xml
rmi_config.xml
webservice_config.xml


This app is super complicated to config to run. It needs two web containers(Tomcat in this project), one for Web Service and one for the app, also RMI exists in between. Simple structure is as follows:

WS(including DB)  ------------>   RMI(Mid tier)  --------------> APP

Please make sure each part can run independently basically.
