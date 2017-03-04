# Bookstore
It is an online bookstre protytpe, only for demonstrating purpose. It has user registration component, shopping cart component, order and so on.

This project consists of FIVE parts
* Web service tie, like  http://localhost:8081/axis/
* Database (it uses Derby) tie, 
* RMI tier, 
* App tier, like http://localhost:8080/bookstore/homepage.jsp
* Config file

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


