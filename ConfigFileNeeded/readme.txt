将DatabaseServersConfig.xml置于web service 服务器A 的 %tomcat%\bin\com\sjtu\ASE2008\bookstore目录下。

//将DatabaseServersConfig.xml中的<host>设为服务器A的IP地址。

将rmi_config.xml和webservice_config.xml置于RMI 服务器B 的com\sjtu\ASE2008\bookstore目录下。将webservice_config.xml中的<host>设为服务器A的IP地址,将rmi_config.xml中的<host>改为服务器B的IP地址。

同时将rmi_config.xml置于MVC服务器C的 %tomcat%\bin\com\sjtu\ASE2008\bookstore目录下。将rmi_config.xml中的<host>设为服务器B的IP地址

