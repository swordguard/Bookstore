set classpath=%classpath%;F:\Au\Tomcat7\RMI\lib\axis.jar;F:\Au\Tomcat7\RMI\lib\axis-ant.jar;F:\Au\Tomcat7\RMI\lib\commons-discovery-0.2.jar;F:\Au\Tomcat7\RMI\lib\commons-logging-1.0.4.jar;F:\Au\Tomcat7\RMI\lib\jaxrpc.jar;F:\Au\Tomcat7\RMI\lib\log4j-1.2.8.jar;F:\Au\Tomcat7\RMI\lib\mail-1.4.jar;F:\Au\Tomcat7\RMI\lib\saaj.jar;F:\Au\Tomcat7\RMI\lib\wsdl4j-1.5.1.jar;F:\Au\Tomcat7\RMI\lib\xercesImpl-2.8.1.jar;F:\Au\Tomcat7\RMI\lib\xml-apis-1.3.03.jar;F:\Au\Tomcat7\RMI\lib\jdom.jar;F:\Au\Dom4j\dom4j-1.6.1.jar;

javac com/sjtu/ASE2008/bookstore/rmi/rmiserverside/*.java

rmic com.sjtu.ASE2008.bookstore.rmi.rmiserverside.RemoteBookService
rem start rmiregistry
java com/sjtu/ASE2008/bookstore/rmi/rmiserverside/RemoteBookService
pause


