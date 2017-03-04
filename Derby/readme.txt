put Derby database bookstoreDB into webservice %tomcat_home%\bin\ 
put DatabaseServersConfig.xml and rmi_config.xml into %tomcat_home%\bin\ 
config them accordingly

you may also need the following:
set DERBY_INSTALL=C:\Apache\db-derby-10.1.2.1-bin
set CLASSPATH=%DERBY_INSTALL%\lib\derby.jar
set CLASSPATH=%CLASSPATH%;%DERBY_INSTALL%\lib\derbytools.jar;
You may also download Derby app from internet.
