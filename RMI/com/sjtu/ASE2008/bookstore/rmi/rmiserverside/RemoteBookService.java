/** The implementation of interface IRemoteBookService
 * runs as RMI server side
 *
 * @author Steve Sun
 * 2008-7-18
 */

package com.sjtu.ASE2008.bookstore.rmi.rmiserverside;

import com.sjtu.ASE2008.bookstore.domain.*;
import com.sjtu.ASE2008.bookstore.rmi.*;
import com.sjtu.ASE2008.bookstore.Composite.*;
import com.sjtu.ASE2008.bookstore.Facade.*;

import java.rmi.RemoteException;
import java.rmi.Naming;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.LocateRegistry;
import java.rmi.*;
import java.net.*;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.apache.axis.encoding.ser.BeanSerializerFactory;
import org.apache.axis.encoding.ser.BeanDeserializerFactory;

import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.encoding.TypeMapping;
import javax.xml.rpc.encoding.TypeMappingRegistry;
import javax.xml.namespace.QName;

import java.util.*;
import java.util.Iterator;

public class RemoteBookService
    extends UnicastRemoteObject implements IRemoteBookService {

  //ArrayList booksArray = new ArrayList();
  //String wspath;
  //String rmipath;
  ParseHostConfig parseHostconfig = null;

  public RemoteBookService() throws RemoteException {
    super();
    parseHostconfig = new ParseHostConfig();
    setParseHostConfig(parseHostconfig);
    //parseHostconfig.getWSHostConfig();
    //parseHostconfig.getRMIHostConfig();
  }

  public ParseHostConfig getParseHostConfig() {
    return (this.parseHostconfig);
  }

  public void setParseHostConfig(ParseHostConfig parseHostConfig) {
    this.parseHostconfig = parseHostConfig;
  }

  /*
     public Book[] getLatestBooks(int number) throws RemoteException {
    Book books[] = new Book[number];
    //Book books [] = {};
    Integer n = new Integer(number);
    //String n = String.valueOf(number);
    String endpoint = "http://localhost:" + "8080" +
        "/axis/services/BooksService";
    // 创建service和call对象，这些对象是标准的JAX-RPC对象，这些对象用于存储服务调用的数据（metadata）。
    try {
      Service service = new Service();
      Call call = (Call) service.createCall();

      // Registers the complex object of Book


      TypeMappingRegistry registry = service.getTypeMappingRegistry();
      TypeMapping map = registry.getDefaultTypeMapping();
      QName qType = new QName(
          "http://localhost:8080/axis/services/BooksService", "Book");
      Class qClass = Class.forName("com.sjtu.ASE2008.bookstore.domain.Book");
      map.register(qClass, qType, new BeanSerializerFactory(qClass, qType),
                   new BeanDeserializerFactory(qClass, qType));
      System.out.println(
   "==Book is successfully registered for being serialized and deserialized.==");

      //////////访问定制发布的BooksService服务

      //设置访问点
      call.setTargetEndpointAddress(new java.net.URL(endpoint));
      //设置操作名
      call.setOperationName("getLatestBooks");
      //设置入口参数
      //call.addParameter("n", XMLType.XSD_STRING, ParameterMode.IN);
      call.addParameter("n", XMLType.XSD_INT, ParameterMode.IN);
      //设置返回参数类型
      call.setReturnType(qType, qClass);
      //System.out.println(call.invoke(new Object[] {n}).toString());

      books = (Book[]) call.invoke(new Object[] {2});
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return books;
     }
   */

  public List viewUsers() throws RemoteException{
    List users = new ArrayList();
    //String t = new String(title);
    String endpoint = parseHostconfig.getWsprotocol() + "://" +
        parseHostconfig.getWshost() + ":" +
        parseHostconfig.getWsportnumber()
        + "/axis/services/" + parseHostconfig.getWsservicename();

    //String endpoint = "http://localhost:8080/axis/services/BooksService";
    try {
      Service service = new Service();
      Call call = (Call) service.createCall();
      //注册JavaBean Book，注意和server-config.wsdd中的配置代码比较
      QName qn = new QName("urn:" + parseHostconfig.getWsservicename(), "User");
      //System.out.println("urn:" + parseHostconfig.getWsservicename());
      Class qClass = Class.forName("com.sjtu.ASE2008.bookstore.domain.User");

      call.registerTypeMapping(qClass, qn, new BeanSerializerFactory(qClass, qn),
                               new BeanDeserializerFactory(qClass, qn));

      call.setTargetEndpointAddress(new java.net.URL(endpoint));

      //调用的服务器端方法
      call.setOperationName(new QName(parseHostconfig.getWsservicename(),
                                      "viewUsers"));

      //设定传入的参数，
      //call.addParameter("title", XMLType.XSD_STRING, ParameterMode.IN);
      //设定返回的参数是Order.class
      call.setReturnClass(List.class);
      //booksArray = (ArrayList) call.invoke(new Object[] {});
      users = (List) call.invoke(new Object[] {});

    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return users;
  }

  public List getUserByName(String username) throws RemoteException{
    List user = new ArrayList();
    //String t = new String(title);
    String endpoint = parseHostconfig.getWsprotocol() + "://" +
        parseHostconfig.getWshost() + ":" +
        parseHostconfig.getWsportnumber()
        + "/axis/services/" + parseHostconfig.getWsservicename();

    //String endpoint = "http://localhost:8080/axis/services/BooksService";
    try {
      Service service = new Service();
      Call call = (Call) service.createCall();
      //注册JavaBean Book，注意和server-config.wsdd中的配置代码比较
      QName qn = new QName("urn:" + parseHostconfig.getWsservicename(), "User");
      //System.out.println("urn:" + parseHostconfig.getWsservicename());
      Class qClass = Class.forName("com.sjtu.ASE2008.bookstore.domain.User");

      call.registerTypeMapping(qClass, qn, new BeanSerializerFactory(qClass, qn),
                               new BeanDeserializerFactory(qClass, qn));

      call.setTargetEndpointAddress(new java.net.URL(endpoint));

      //调用的服务器端方法
      call.setOperationName(new QName(parseHostconfig.getWsservicename(),
                                      "getUserByName"));

      //设定传入的参数，
      call.addParameter("username", XMLType.XSD_STRING, ParameterMode.IN);
      //设定返回的参数是Order.class
      call.setReturnClass(List.class);
      //booksArray = (ArrayList) call.invoke(new Object[] {});
      user = (List) call.invoke(new Object[] {username});

    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return user;
  }

  public int register(User user) throws RemoteException{
    int result = -1;
    String endpoint = parseHostconfig.getWsprotocol() + "://" +
        parseHostconfig.getWshost() + ":" +
        parseHostconfig.getWsportnumber()
        + "/axis/services/" + parseHostconfig.getWsservicename();

    //String endpoint = "http://localhost:8080/axis/services/BooksService";
    try {
      Service service = new Service();
      Call call = (Call) service.createCall();
      //注册JavaBean Book，注意和server-config.wsdd中的配置代码比较
      QName qn = new QName("urn:" + parseHostconfig.getWsservicename(), "User");
      Class qClass = Class.forName("com.sjtu.ASE2008.bookstore.domain.User");

      call.registerTypeMapping(qClass, qn, new BeanSerializerFactory(qClass, qn),
                               new BeanDeserializerFactory(qClass, qn));

      call.setTargetEndpointAddress(new java.net.URL(endpoint));

      //调用的服务器端方法
      call.setOperationName(new QName(parseHostconfig.getWsservicename(),
                                      "register"));

      //设定传入的参数，
      call.addParameter("user", qn, ParameterMode.IN);
      //设定返回的参数是Order.class
     call.setReturnType(XMLType.XSD_INT);
      //booksArray = (ArrayList) call.invoke(new Object[] {});
      result = Integer.valueOf( (call.invoke(new Object[] {user})).toString());

    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }
  
  
  
  public int updateUserInfo(User user) throws RemoteException{
    int result = -1;
    String endpoint = parseHostconfig.getWsprotocol() + "://" +
        parseHostconfig.getWshost() + ":" +
        parseHostconfig.getWsportnumber()
        + "/axis/services/" + parseHostconfig.getWsservicename();

    try {
      Service service = new Service();
      Call call = (Call) service.createCall();
      //注册JavaBean Book，注意和server-config.wsdd中的配置代码比较
      QName qn = new QName("urn:" + parseHostconfig.getWsservicename(), "User");
      Class qClass = Class.forName("com.sjtu.ASE2008.bookstore.domain.User");

      call.registerTypeMapping(qClass, qn, new BeanSerializerFactory(qClass, qn),
                               new BeanDeserializerFactory(qClass, qn));

      call.setTargetEndpointAddress(new java.net.URL(endpoint));

      //调用的服务器端方法
      call.setOperationName(new QName(parseHostconfig.getWsservicename(),"updateUserInfo"));

      //设定传入的参数，
      call.addParameter("user", qn, ParameterMode.IN);
      //设定返回的参数是Order.class
     call.setReturnType(XMLType.XSD_INT);
      //booksArray = (ArrayList) call.invoke(new Object[] {});
      result = Integer.valueOf( (call.invoke(new Object[] {user})).toString());

    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }
  
  

  public int login(String username, String password) throws RemoteException{
    int result = -1;
    //String t = new String(title);
    String endpoint = parseHostconfig.getWsprotocol() + "://" +
        parseHostconfig.getWshost() + ":" +
        parseHostconfig.getWsportnumber()
        + "/axis/services/" + parseHostconfig.getWsservicename();

    //String endpoint = "http://localhost:8080/axis/services/BooksService";
    try {
      Service service = new Service();
      Call call = (Call) service.createCall();
      
      //注册JavaBean Book，注意和server-config.wsdd中的配置代码比较
      QName qn = new QName("urn:" + parseHostconfig.getWsservicename(), "User");
      //System.out.println("urn:" + parseHostconfig.getWsservicename());
      Class qClass = Class.forName("com.sjtu.ASE2008.bookstore.domain.User");

      call.registerTypeMapping(qClass, qn, new BeanSerializerFactory(qClass, qn),
                               new BeanDeserializerFactory(qClass, qn));
			
      call.setTargetEndpointAddress(new java.net.URL(endpoint));

      //调用的服务器端方法
      call.setOperationName(new QName(parseHostconfig.getWsservicename(),
                                      "login"));

      //设定传入的参数，
      call.addParameter("username", XMLType.XSD_STRING, ParameterMode.IN);
      call.addParameter("password", XMLType.XSD_STRING, ParameterMode.IN);
      //设定返回的参数是Order.class
      //call.setReturnClass(ArrayList.class);
      call.setReturnType(XMLType.XSD_INT);
      //booksArray = (ArrayList) call.invoke(new Object[] {});
      result = Integer.valueOf((call.invoke(new Object[] {username,password}).toString()));

    }
    catch (Exception e) {
      e.printStackTrace();
    }
    //System.out.println("From rmi server,result=" + result);
    return result;//return -1,1,2
  }
  
  public int updateDate(String column1, String column2,String table, String value) throws RemoteException{
    int result = -1;
    //String t = new String(title);
    String endpoint = parseHostconfig.getWsprotocol() + "://" +
        parseHostconfig.getWshost() + ":" +
        parseHostconfig.getWsportnumber()
        + "/axis/services/" + parseHostconfig.getWsservicename();

    //String endpoint = "http://localhost:8080/axis/services/BooksService";
    try {
      Service service = new Service();
      Call call = (Call) service.createCall();
      //注册JavaBean Book，注意和server-config.wsdd中的配置代码比较
      //QName qn = new QName("urn:" + parseHostconfig.getWsservicename(), "User");
      //System.out.println("urn:" + parseHostconfig.getWsservicename());
      //Class qClass = Class.forName("com.sjtu.ASE2008.bookstore.domain.User");

      //call.registerTypeMapping(qClass, qn, new BeanSerializerFactory(qClass, qn),
      //                         new BeanDeserializerFactory(qClass, qn));

      call.setTargetEndpointAddress(new java.net.URL(endpoint));

      //调用的服务器端方法
      call.setOperationName(new QName(parseHostconfig.getWsservicename(),
                                      "updateDate"));

      //设定传入的参数，
      call.addParameter("column1", XMLType.XSD_STRING, ParameterMode.IN);
      call.addParameter("column2", XMLType.XSD_STRING, ParameterMode.IN);
      call.addParameter("table", XMLType.XSD_STRING, ParameterMode.IN);
      call.addParameter("value", XMLType.XSD_STRING, ParameterMode.IN);
      //设定返回的参数是Order.class
      //call.setReturnClass(ArrayList.class);
      call.setReturnType(XMLType.XSD_INT);
      //booksArray = (ArrayList) call.invoke(new Object[] {});
      result = Integer.valueOf((call.invoke(new Object[] {column1,column2,table,value}).toString()));

    }
    catch (Exception e) {
      e.printStackTrace();
    }
    //System.out.println("From rmi server,result=" + result);
    return result;
  }

  public boolean doesItExist(String column, String table, String value) throws
      RemoteException {
    boolean y = false;
    String endpoint = parseHostconfig.getWsprotocol() + "://" +
        parseHostconfig.getWshost() + ":" +
        parseHostconfig.getWsportnumber()
        + "/axis/services/" + parseHostconfig.getWsservicename();

    //String endpoint = "http://localhost:8080/axis/services/BooksService";
    try {
      Service service = new Service();
      Call call = (Call) service.createCall();
      call.setTargetEndpointAddress(new java.net.URL(endpoint));
      call.setOperationName("doesItExist");
      //设定传入的参数，
      call.addParameter("column", XMLType.XSD_STRING, ParameterMode.IN);
      call.addParameter("table", XMLType.XSD_STRING, ParameterMode.IN);
      call.addParameter("value", XMLType.XSD_STRING, ParameterMode.IN);
      //设定返回的参数是Order.class
      call.setReturnType(XMLType.XSD_BOOLEAN);
      //booksArray = (ArrayList) call.invoke(new Object[] {});
      String b = (call.invoke(new Object[] {column, table, value})).toString();
      if (b.equals("true")) {
        y = true;
      }
      else {
        y = false;
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return y;

  }

  public List createShoppingcart() throws RemoteException {
    List cart = new ArrayList();

    String endpoint = parseHostconfig.getWsprotocol() + "://" +
        parseHostconfig.getWshost() + ":" +
        parseHostconfig.getWsportnumber()
        + "/axis/services/" + parseHostconfig.getWsservicename();

   
    try {
      Service service = new Service();
      Call call = (Call) service.createCall();
      //注册JavaBean Book，注意和server-config.wsdd中配置代码比较
      QName qn = new QName("urn:" + parseHostconfig.getWsservicename(),
                           "Shoppingcart");
      Class qClass = Class.forName(
          "com.sjtu.ASE2008.bookstore.domain.Shoppingcart");

      call.registerTypeMapping(qClass, qn, new BeanSerializerFactory(qClass, qn),
                               new BeanDeserializerFactory(qClass, qn));

      call.setTargetEndpointAddress(new java.net.URL(endpoint));

      //调用的服务器端方法
      call.setOperationName(new QName(parseHostconfig.getWsservicename(),
                                      "createShoppingcart"));

      
      call.setReturnClass(List.class);
     
      cart = (List) call.invoke(new Object[] {});

    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return cart;
  }
	
	
	
	public List getItems() throws RemoteException{
		List items = null;//new ArrayList();

    String endpoint = parseHostconfig.getWsprotocol() + "://" +
        parseHostconfig.getWshost() + ":" +
        parseHostconfig.getWsportnumber()
        + "/axis/services/" + parseHostconfig.getWsservicename();

   
    try {
      Service service = new Service();
      Call call = (Call) service.createCall();
      //注册JavaBean Book，注意和server-config.wsdd中配置代码比较
      QName qn = new QName("urn:" + parseHostconfig.getWsservicename(),"ShoppingcartItem");
      Class qClass = Class.forName("com.sjtu.ASE2008.bookstore.domain.ShoppingcartItem");

      call.registerTypeMapping(qClass, qn, new BeanSerializerFactory(qClass, qn),new BeanDeserializerFactory(qClass, qn));

      call.setTargetEndpointAddress(new java.net.URL(endpoint));

      //调用的服务器端方法
      call.setOperationName(new QName(parseHostconfig.getWsservicename(),"getItems"));
  
      call.setReturnClass(List.class);
     
      items = (List) call.invoke(new Object[] {});

    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return items;
		
	}
	
	
	
	
  public int PersistShoppingcart(IItem iItem) throws RemoteException {
    int result = -1;
    Shoppingcart cart = (Shoppingcart)iItem;
    String endpoint = parseHostconfig.getWsprotocol() + "://" +
        parseHostconfig.getWshost() + ":" +
        parseHostconfig.getWsportnumber()
        + "/axis/services/" + parseHostconfig.getWsservicename();

    //String endpoint = "http://localhost:8080/axis/services/BooksService";
    try {
      Service service = new Service();
      Call call = (Call) service.createCall();
      //注册JavaBean Book，注意和server-config.wsdd中的配置代码比较
      QName qn = new QName("urn:" + parseHostconfig.getWsservicename(),
                           "Shoppingcart");
      Class qClass = Class.forName(
          "com.sjtu.ASE2008.bookstore.domain.Shoppingcart");

      call.registerTypeMapping(qClass, qn, new BeanSerializerFactory(qClass, qn),
                               new BeanDeserializerFactory(qClass, qn));

      call.setTargetEndpointAddress(new java.net.URL(endpoint));

      //调用的服务器端方法
      call.setOperationName(new QName(parseHostconfig.getWsservicename(),
                                      "PersistShoppingcart"));

      //设定传入的参数，
      call.addParameter("cart", qn, ParameterMode.IN);
      //设定返回的参数是Order.class
      call.setReturnType(XMLType.XSD_INT);
      //booksArray = (ArrayList) call.invoke(new Object[] {});
      result = Integer.valueOf( (call.invoke(new Object[] {cart})).toString());

    }
    catch (Exception e) {
      e.printStackTrace();
    }

    return result;
  }

  public int PersistShoppingcartItem(ShoppingcartItem item) throws RemoteException {
    int result = -1;
    String endpoint = parseHostconfig.getWsprotocol() + "://" +
        parseHostconfig.getWshost() + ":" +
        parseHostconfig.getWsportnumber()
        + "/axis/services/" + parseHostconfig.getWsservicename();

    //String endpoint = "http://localhost:8080/axis/services/BooksService";
    try {
      Service service = new Service();
      Call call = (Call) service.createCall();
      //注册JavaBean Book，注意和server-config.wsdd中的配置代码比较
      QName qn = new QName("urn:" + parseHostconfig.getWsservicename(),
                           "ShoppingcartItem");
      Class qClass = Class.forName(
          "com.sjtu.ASE2008.bookstore.domain.ShoppingcartItem");

      call.registerTypeMapping(qClass, qn, new BeanSerializerFactory(qClass, qn),
                               new BeanDeserializerFactory(qClass, qn));

      call.setTargetEndpointAddress(new java.net.URL(endpoint));

      //调用的服务器端方法
      call.setOperationName(new QName(parseHostconfig.getWsservicename(),
                                      "PersistShoppingcartItem"));

      //设定传入的参数，
      call.addParameter("item", qn, ParameterMode.IN);
      //设定返回的参数是Order.class
      call.setReturnType(XMLType.XSD_INT);
      //booksArray = (ArrayList) call.invoke(new Object[] {});
      result = Integer.valueOf( (call.invoke(new Object[] {item})).toString());

    }
    catch (Exception e) {
      e.printStackTrace();
    }

    return result;
  }

  public int removeShopppingcartByUser(String username) {
    int result = -1;
    String endpoint = parseHostconfig.getWsprotocol() + "://" +
        parseHostconfig.getWshost() + ":" +
        parseHostconfig.getWsportnumber()
        + "/axis/services/" + parseHostconfig.getWsservicename();

    //String endpoint = "http://localhost:8080/axis/services/BooksService";
    try {
      Service service = new Service();
      Call call = (Call) service.createCall();
      
      //注册JavaBean Book，注意和server-config.wsdd中的配置代码比较
      QName qn = new QName("urn:" + parseHostconfig.getWsservicename(),
                           "hoppingcart");
      Class qClass = Class.forName(
          "com.sjtu.SE2008.bookstore.domain.Shoppingcart");

      call.registerTypeMapping(qClass, qn, new BeanSerializerFactory(qClass, qn),
                               new BeanDeserializerFactory(qClass, qn));
			
      call.setTargetEndpointAddress(new java.net.URL(endpoint));

      //调用的服务器端方法
      call.setOperationName(new QName(parseHostconfig.getWsservicename(),
                                      "removeShopppingcartByUser"));

      //设定传入的参数，
      call.addParameter("username", qn, ParameterMode.IN);
      //设定返回的参数是Order.class
      call.setReturnType(XMLType.XSD_INT);
      //booksArray = (ArrayList) call.invoke(new Object[] {});
      result = Integer.valueOf( (call.invoke(new Object[] {username})).toString());

    }
    catch (Exception e) {
      e.printStackTrace();
    }

    return result;

  }

  public int removeShopppingcartItemByUser(String username) throws RemoteException{
    int result = -1;
    String endpoint = parseHostconfig.getWsprotocol() + "://" +
        parseHostconfig.getWshost() + ":" +
        parseHostconfig.getWsportnumber()
        + "/axis/services/" + parseHostconfig.getWsservicename();

    //String endpoint = "http://localhost:8080/axis/services/BooksService";
    try {
      Service service = new Service();
      Call call = (Call) service.createCall();
      
      //注册JavaBean Book，注意和server-config.wsdd中的配置代码比较
      QName qn = new QName("urn:" + parseHostconfig.getWsservicename(),
                           "ShoppingcartItem");
      Class qClass = Class.forName(
          "com.sjtu.ASE2008.bookstore.domain.ShoppingcartItem");

      call.registerTypeMapping(qClass, qn, new BeanSerializerFactory(qClass, qn),
                               new BeanDeserializerFactory(qClass, qn));
			
      call.setTargetEndpointAddress(new java.net.URL(endpoint));

      //调用的服务器端方法
      call.setOperationName(new QName(parseHostconfig.getWsservicename(),
                                      "removeShopppingcartItemByUser"));

      //设定传入的参数，
      call.addParameter("username", qn, ParameterMode.IN);
      //设定返回的参数是Order.class
      call.setReturnType(XMLType.XSD_INT);
      //booksArray = (ArrayList) call.invoke(new Object[] {});
      result = Integer.valueOf( (call.invoke(new Object[] {username})).toString());

    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }
/*
  public ArrayList makeOrder(Shoppingcart cart) throws RemoteException {
    ArrayList order = new ArrayList();
    //Integer n = new Integer(number);

    String endpoint = parseHostconfig.getWsprotocol() + "://" +
        parseHostconfig.getWshost() + ":" +
        parseHostconfig.getWsportnumber()
        + "/axis/services/" + parseHostconfig.getWsservicename();

    //String endpoint = "http://localhost:8080/axis/services/BooksService";
    try {
      Service service = new Service();
      Call call = (Call) service.createCall();
      //注册JavaBean Book，注意和server-config.wsdd中的配置代码比较
      QName qn = new QName("urn:" + parseHostconfig.getWsservicename(),
                           "Shoppingcart");
      Class qClass = Class.forName(
          "com.sjtu.ASE2008.bookstore.domain.Shoppingcart");

      call.registerTypeMapping(qClass, qn, new BeanSerializerFactory(qClass, qn),
                               new BeanDeserializerFactory(qClass, qn));

      call.setTargetEndpointAddress(new java.net.URL(endpoint));

      //调用的服务器端方法
      call.setOperationName(new QName(parseHostconfig.getWsservicename(),
                                      "makeOrder"));

      //设定传入的参数，
      call.addParameter("cart", qn, ParameterMode.IN);
      //设定返回的参数是Order.class
      call.setReturnClass(ArrayList.class);
      //booksArray = (ArrayList) call.invoke(new Object[] {});
      order = (ArrayList) call.invoke(new Object[] {cart});

    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return order;

  }
*/

	public int saveOrderToDB(Order order) throws RemoteException{
		//System.out.println("begin to execute saveOrderToDB() 0 at RMI server side");
		int result = -1;
    String endpoint = parseHostconfig.getWsprotocol() + "://" +
        parseHostconfig.getWshost() + ":" +
        parseHostconfig.getWsportnumber()
        + "/axis/services/" + parseHostconfig.getWsservicename();

    //String endpoint = "http://localhost:8080/axis/services/BooksService";
    try {
      Service service = new Service();
      Call call = (Call) service.createCall();
      //注册JavaBean Bok，注意和server-config.wsdd中的配置代码比较
      QName qn = new QName("urn:" + parseHostconfig.getWsservicename(), "Order");
      Class qClass = Class.forName("com.sjtu.ASE2008.bookstore.domain.Order");

      call.registerTypeMapping(qClass, qn, new BeanSerializerFactory(qClass, qn),
                               new BeanDeserializerFactory(qClass, qn));

      call.setTargetEndpointAddress(new java.net.URL(endpoint));

      //调用的服务器端方法
      call.setOperationName(new QName(parseHostconfig.getWsservicename(),
                                      "saveOrderToDB"));

      //设定传入的参数，
      call.addParameter("order", qn, ParameterMode.IN);
      //设定返回的参数是Order.class
      call.setReturnType(XMLType.XSD_INT);
      //booksArray = (ArrayList) call.invoke(new Object[] {});
      //System.out.println("begin to execute saveOrderToDB() in RemoteBookService.class at RMI server side");
      result = Integer.valueOf( (call.invoke(new Object[] {order})).toString());
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return result;
	}

	public int saveOrderItemToDB(OrderItem orderItem) throws RemoteException{
		int result = -1;
    String endpoint = parseHostconfig.getWsprotocol() + "://" +
        parseHostconfig.getWshost() + ":" +
        parseHostconfig.getWsportnumber()
        + "/axis/services/" + parseHostconfig.getWsservicename();

    //String endpoint = "http://localhost:8080/axis/services/BooksService";
    try {
      Service service = new Service();
      Call call = (Call) service.createCall();
      //注册JavaBean Bok，注意和server-config.wsdd中的配置代码比较
      QName qn = new QName("urn:" + parseHostconfig.getWsservicename(), "OrderItem");
      Class qClass = Class.forName("com.sjtu.ASE2008.bookstore.domain.OrderItem");

      call.registerTypeMapping(qClass, qn, new BeanSerializerFactory(qClass, qn),
                               new BeanDeserializerFactory(qClass, qn));

      call.setTargetEndpointAddress(new java.net.URL(endpoint));

      //调用的服务器端方法
      call.setOperationName(new QName(parseHostconfig.getWsservicename(),
                                      "saveOrderItemToDB"));

      //设定传入的参数，
      call.addParameter("orderItem", qn, ParameterMode.IN);
      //设定返回的参数是Order.class
      call.setReturnType(XMLType.XSD_INT);
      //booksArray = (ArrayList) call.invoke(new Object[] {});
      result = Integer.valueOf( (call.invoke(new Object[] {orderItem})).toString());
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return result;
	}

  public List viewAllOrderItems() throws RemoteException {
    List orderitems = null;//new ArrayList();
    String endpoint = parseHostconfig.getWsprotocol() + "://" +
        parseHostconfig.getWshost() + ":" +
        parseHostconfig.getWsportnumber()
        + "/axis/services/" + parseHostconfig.getWsservicename();

    //String endpoint = "http://localhost:8080/axis/services/BooksService";
    try {
      Service service = new Service();
      Call call = (Call) service.createCall();
      //注册JavaBean Book，注意和server-config.wsdd中的配置代码比较
      QName qn = new QName("urn:" + parseHostconfig.getWsservicename(),"OrderItem");
      //System.out.println("urn:" + parseHostconfig.getWsservicename());
      Class qClass = Class.forName("com.sjtu.ASE2008.bookstore.domain.OrderItem");

      call.registerTypeMapping(qClass, qn, new BeanSerializerFactory(qClass, qn),new BeanDeserializerFactory(qClass, qn));

      call.setTargetEndpointAddress(new java.net.URL(endpoint));

      //调用的服务器端方法
      call.setOperationName(new QName(parseHostconfig.getWsservicename(), "viewAllOrderItems"));

      //设定传入的参数，
      //call.addParameter("cart", XMLType.XSD_STRING, ParameterMode.IN);
      //设定返回的参数是Order.class
      call.setReturnClass(List.class);
      //booksArray = (List) call.invoke(new Object[] {});
      orderitems = (List) call.invoke(new Object[] {});

    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return orderitems;
  }

  public List viewOrderItemsByUser(String username) throws RemoteException {
    List orderitems = new ArrayList();
    String endpoint = parseHostconfig.getWsprotocol() + "://" +
        parseHostconfig.getWshost() + ":" +
        parseHostconfig.getWsportnumber()
        + "/axis/services/" + parseHostconfig.getWsservicename();

    //String endpoint = "http://localhost:8080/axis/services/BooksService";
    try {
      Service service = new Service();
      Call call = (Call) service.createCall();
      //注册JavaBean Book，注意和server-config.wsdd中的配置代码比较
      QName qn = new QName("urn:" + parseHostconfig.getWsservicename(),
                           "OrderItem");
      //System.out.println("urn:" + parseHostconfig.getWsservicename());
      Class qClass = Class.forName(
          "com.sjtu.ASE2008.bookstore.domain.OrderItem");

      call.registerTypeMapping(qClass, qn, new BeanSerializerFactory(qClass, qn),
                               new BeanDeserializerFactory(qClass, qn));

      call.setTargetEndpointAddress(new java.net.URL(endpoint));

      //调用的服务器端方法
      call.setOperationName(new QName(parseHostconfig.getWsservicename(),
                                      "viewOrderItemsByUser"));

      //设定传入的参数，
      call.addParameter("username", XMLType.XSD_STRING, ParameterMode.IN);
      //设定返回的参数是Order.class
      call.setReturnClass(List.class);
      //booksArray = (List) call.invoke(new Object[] {});
      orderitems = (List) call.invoke(new Object[] {username});

    }
    catch (Exception e) {
      e.printStackTrace();
    }

    return orderitems;
  }
  
  /***
  public List viewOrders() throws RemoteException{
  	
  	Service service = new Service();
  	
  	
  }
  */

  public List viewOrderItemsByOrderid(String orderid) throws RemoteException {
    List orderitems = new ArrayList();
    String endpoint = parseHostconfig.getWsprotocol() + "://" +
        parseHostconfig.getWshost() + ":" +
        parseHostconfig.getWsportnumber()
        + "/axis/services/" + parseHostconfig.getWsservicename();

    //String endpoint = "http://localhost:8080/axis/services/BooksService";
    try {
      Service service = new Service();
      Call call = (Call) service.createCall();
      //注册JavaBean Book，注意和server-config.wsdd中的配置代码比较
      QName qn = new QName("urn:" + parseHostconfig.getWsservicename(),
                           "OrderItem");
      //System.out.println("urn:" + parseHostconfig.getWsservicename());
      Class qClass = Class.forName(
          "com.sjtu.ASE2008.bookstore.domain.OrderItem");

      call.registerTypeMapping(qClass, qn, new BeanSerializerFactory(qClass, qn),
                               new BeanDeserializerFactory(qClass, qn));

      call.setTargetEndpointAddress(new java.net.URL(endpoint));

      //调用的服务器端方法
      call.setOperationName(new QName(parseHostconfig.getWsservicename(),
                                      "viewOrderItemsByOrderid"));

      //设定传入的参数，
      call.addParameter("orderid", XMLType.XSD_STRING, ParameterMode.IN);
      //设定返回的参数是Order.class
      call.setReturnClass(List.class);
      //booksArray = (List) call.invoke(new Object[] {});
      orderitems = (List) call.invoke(new Object[] {orderid});

    }
    catch (Exception e) {
      e.printStackTrace();
    }

    return orderitems;
  }

  public List viewOrderByOrderid(String orderid) throws RemoteException{
    List theorder = new ArrayList();
    String endpoint = parseHostconfig.getWsprotocol() + "://" +
        parseHostconfig.getWshost() + ":" +
        parseHostconfig.getWsportnumber()
        + "/axis/services/" + parseHostconfig.getWsservicename();

    //String endpoint = "http://localhost:8080/axis/services/BooksService";
    try {
      Service service = new Service();
      Call call = (Call) service.createCall();
      //注册JavaBean Book，注意和server-config.wsdd中的配置代码比较
      QName qn = new QName("urn:" + parseHostconfig.getWsservicename(),
                           "Order");
      //System.out.println("urn:" + parseHostconfig.getWsservicename());
      Class qClass = Class.forName(
          "com.sjtu.ASE2008.bookstore.domain.Order");

      call.registerTypeMapping(qClass, qn, new BeanSerializerFactory(qClass, qn),
                               new BeanDeserializerFactory(qClass, qn));

      call.setTargetEndpointAddress(new java.net.URL(endpoint));

      //调用的服务器端方法
      call.setOperationName(new QName(parseHostconfig.getWsservicename(),
                                      "viewOrderByOrderid"));

      //设定传入的参数，
      call.addParameter("orderid", XMLType.XSD_STRING, ParameterMode.IN);
      //设定返回的参数是Order.class
      call.setReturnClass(List.class);
      //booksArray = (List) call.invoke(new Object[] {});
      theorder = (List) call.invoke(new Object[] {orderid});

    }
    catch (Exception e) {
      e.printStackTrace();
    }

    return theorder;

  }
  
  
  public List viewOrderByUser(String username) throws RemoteException{
  	List theorder = null;//new ArrayList();
    String endpoint = parseHostconfig.getWsprotocol() + "://" +
        parseHostconfig.getWshost() + ":" +
        parseHostconfig.getWsportnumber()
        + "/axis/services/" + parseHostconfig.getWsservicename();

    //String endpoint = "http://localhost:8080/axis/services/BooksService";
    try {
      Service service = new Service();
      Call call = (Call) service.createCall();
      //注册JavaBean Book，注意和server-config.wsdd中的配置代码比较
      QName qn = new QName("urn:" + parseHostconfig.getWsservicename(),
                           "Order");
      //System.out.println("urn:" + parseHostconfig.getWsservicename());
      Class qClass = Class.forName(
          "com.sjtu.ASE2008.bookstore.domain.Order");

      call.registerTypeMapping(qClass, qn, new BeanSerializerFactory(qClass, qn),
                               new BeanDeserializerFactory(qClass, qn));

      call.setTargetEndpointAddress(new java.net.URL(endpoint));

      //调用的服务器端方法
      call.setOperationName(new QName(parseHostconfig.getWsservicename(),
                                      "viewOrderByUser"));

      //设定传入的参数，
      call.addParameter("username", XMLType.XSD_STRING, ParameterMode.IN);
      //设定返回的参数是Order.class
      call.setReturnClass(List.class);
      //booksArray = (List) call.invoke(new Object[] {});
      theorder = (List) call.invoke(new Object[] {username});
  	}
    catch (Exception e) {
      e.printStackTrace();
    }

    return theorder;
  }
  
  
  public List searchByTitle(String title) throws RemoteException {
    List bookslist = new ArrayList();
    String endpoint = parseHostconfig.getWsprotocol() + "://" +
        parseHostconfig.getWshost() + ":" +
        parseHostconfig.getWsportnumber()
        + "/axis/services/" + parseHostconfig.getWsservicename();

    //String endpoint = "http://localhost:8080/axis/services/BooksService";
    try {
      Service service = new Service();
      Call call = (Call) service.createCall();
      
      //注册JavaBean Book，注意和server-config.wsdd中的配置代码比较
      QName qn = new QName("urn:" + parseHostconfig.getWsservicename(),
                           "Book");
      //System.out.println("urn:" + parseHostconfig.getWsservicename());
      Class qClass = Class.forName(
          "com.sjtu.ASE2008.bookstore.domain.Book");

      call.registerTypeMapping(qClass, qn, new BeanSerializerFactory(qClass, qn),
                               new BeanDeserializerFactory(qClass, qn));
			
      call.setTargetEndpointAddress(new java.net.URL(endpoint));

      //调用的服务器端方法
      call.setOperationName(new QName(parseHostconfig.getWsservicename(),
                                      "searchByTitle"));

      //设定传入的参数，
      call.addParameter("title", XMLType.XSD_STRING, ParameterMode.IN);
      //设定返回的参数是Order.class
      call.setReturnClass(List.class);
      //booksArray = (List) call.invoke(new Object[] {});
      bookslist = (List) call.invoke(new Object[] {title});

    }
    catch (Exception e) {
      e.printStackTrace();
    }

    return bookslist;
  }
  
  

  public List getLatestTitles(int number) throws RemoteException {
    List titles = new ArrayList();
    //int t = number;
    Integer n = new Integer(number);

    String endpoint = parseHostconfig.getWsprotocol() + "://" +
        parseHostconfig.getWshost() + ":" +
        parseHostconfig.getWsportnumber()
        + "/axis/services/" + parseHostconfig.getWsservicename();

    //String endpoint = "http://localhost:8080/axis/services/BooksService";
    try {
      Service service = new Service();
      Call call = (Call) service.createCall();
      
      //注册JavaBean Book，注意和server-config.wsdd中的配置代码比较
      QName qn = new QName("urn:" + parseHostconfig.getWsservicename(), "Book");
      Class qClass = Class.forName("com.sjtu.ASE2008.bookstore.domain.Book");

      call.registerTypeMapping(qClass, qn, new BeanSerializerFactory(qClass, qn),
                               new BeanDeserializerFactory(qClass, qn));
			
      call.setTargetEndpointAddress(new java.net.URL(endpoint));

      //调用的服务器端方法
      call.setOperationName(new QName(parseHostconfig.getWsservicename(),
                                      "getLatestTitles"));

      //设定传入的参数，
      call.addParameter("n", XMLType.XSD_INT, ParameterMode.IN);
      //设定返回的参数是Order.class
      call.setReturnClass(List.class);
      //booksArray = (ArrayList) call.invoke(new Object[] {});
      titles = (List) call.invoke(new Object[] {n});

    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return titles;
  }

  public List getBookByTitle(String title) throws RemoteException {
    List books = new ArrayList();
    //String t = new String(title);
    String endpoint = parseHostconfig.getWsprotocol() + "://" +
        parseHostconfig.getWshost() + ":" +
        parseHostconfig.getWsportnumber()
        + "/axis/services/" + parseHostconfig.getWsservicename();

    //String endpoint = "http://localhost:8080/axis/services/BooksService";
    try {
      Service service = new Service();
      Call call = (Call) service.createCall();
      //注册JavaBean Book，注意和server-config.wsdd中的配置代码比较
      QName qn = new QName("urn:" + parseHostconfig.getWsservicename(), "Book");
      //System.out.println("urn:" + parseHostconfig.getWsservicename());
      Class qClass = Class.forName("com.sjtu.ASE2008.bookstore.domain.Book");

      call.registerTypeMapping(qClass, qn, new BeanSerializerFactory(qClass, qn),
                               new BeanDeserializerFactory(qClass, qn));

      call.setTargetEndpointAddress(new java.net.URL(endpoint));

      //调用的服务器端方法
      call.setOperationName(new QName(parseHostconfig.getWsservicename(),
                                      "getBookByTitle"));

      //设定传入的参数，
      call.addParameter("title", XMLType.XSD_STRING, ParameterMode.IN);
      //设定返回的参数是Order.class
      call.setReturnClass(List.class);
      //booksArray = (List) call.invoke(new Object[] {});
      books = (List) call.invoke(new Object[] {title});

    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return books;
  }

  public List getAllCatagories() throws RemoteException {
    List catagories = new ArrayList();

    String endpoint = parseHostconfig.getWsprotocol() + "://" +
        parseHostconfig.getWshost() + ":" +
        parseHostconfig.getWsportnumber()
        + "/axis/services/" + parseHostconfig.getWsservicename();

    //String endpoint = "http://localhost:8080/axis/services/BooksService";
    try {
      Service service = new Service();
      Call call = (Call) service.createCall();
      
      //注册JavaBean Book，注意和server-config.wsdd中的配置代码比较
      QName qn = new QName("urn:" + parseHostconfig.getWsservicename(), "Book");
      Class qClass = Class.forName("com.sjtu.ASE2008.bookstore.domain.Book");

      call.registerTypeMapping(qClass, qn, new BeanSerializerFactory(qClass, qn),new BeanDeserializerFactory(qClass, qn));
			
      call.setTargetEndpointAddress(new java.net.URL(endpoint));

      //调用的服务器端方法
      call.setOperationName(new QName(parseHostconfig.getWsservicename(),
                                      "getAllCatagories"));

      //设定传入的参数，
      //call.addParameter("title", XMLType.XSD_STRING, ParameterMode.IN);
      //设定返回的参数是Order.class
      call.setReturnClass(List.class);
      //booksArray = (List) call.invoke(new Object[] {});
      catagories = (List) call.invoke(new Object[] {});
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return catagories;
  }

  public List getAllTitles() throws RemoteException {
    List alltitles = new ArrayList();

    String endpoint = parseHostconfig.getWsprotocol() + "://" +
        parseHostconfig.getWshost() + ":" +
        parseHostconfig.getWsportnumber()
        + "/axis/services/" + parseHostconfig.getWsservicename();

    //String endpoint = "http://localhost:8080/axis/services/BooksService";
    try {
      Service service = new Service();
      Call call = (Call) service.createCall();
      
      //注册JavaBean Book，注意和server-config.wsdd中配置代码比较
      QName qn = new QName("urn:" + parseHostconfig.getWsservicename(), "Book");
      Class qClass = Class.forName("com.sjtu.ASE2008.bookstore.domain.Book");

      call.registerTypeMapping(qClass, qn, new BeanSerializerFactory(qClass, qn),
                               new BeanDeserializerFactory(qClass, qn));
			
      call.setTargetEndpointAddress(new java.net.URL(endpoint));

      //调用的服务器端方法
      call.setOperationName(new QName(parseHostconfig.getWsservicename(),
                                      "getAllTitles"));

      //设定传入的参数，
      //call.addParameter("title", XMLType.XSD_STRING, ParameterMode.IN);
      //设定返回的参数是Order.class
      call.setReturnClass(List.class);
      //booksArray = (List) call.invoke(new Object[] {});
      alltitles = (List) call.invoke(new Object[] {});

    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return alltitles;

  }
  public int addBook(Book book) throws RemoteException {
    int result = -1;
    String endpoint = parseHostconfig.getWsprotocol() + "://" +
        parseHostconfig.getWshost() + ":" +
        parseHostconfig.getWsportnumber()
        + "/axis/services/" + parseHostconfig.getWsservicename();

    //String endpoint = "http://localhost:8080/axis/services/BooksService";
    try {
      Service service = new Service();
      Call call = (Call) service.createCall();
      //注册JavaBean Book，注意和server-config.wsdd中的配置代码比较
      QName qn = new QName("urn:" + parseHostconfig.getWsservicename(), "Book");
      Class qClass = Class.forName("com.sjtu.ASE2008.bookstore.domain.Book");

      call.registerTypeMapping(qClass, qn, new BeanSerializerFactory(qClass, qn),
                               new BeanDeserializerFactory(qClass, qn));

      call.setTargetEndpointAddress(new java.net.URL(endpoint));

      //调用的服务器端方法
      call.setOperationName(new QName(parseHostconfig.getWsservicename(),
                                      "addBook"));

      //设定传入的参数，
      call.addParameter("book", qn, ParameterMode.IN);
      //设定返回的参数是Order.class
     call.setReturnType(XMLType.XSD_INT);
      //booksArray = (ArrayList) call.invoke(new Object[] {});
      result = Integer.valueOf( (call.invoke(new Object[] {book})).toString());

    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }

  public int removeBookByTitle(String title) throws RemoteException {
    int result = -1;

    return result;
  }

  public int updateBookInfo(Book book) throws RemoteException {
    int result = -1;
    String endpoint = parseHostconfig.getWsprotocol() + "://" +
        parseHostconfig.getWshost() + ":" +
        parseHostconfig.getWsportnumber()
        + "/axis/services/" + parseHostconfig.getWsservicename();

    //String endpoint = "http://localhost:8080/axis/services/BooksService";
    try {
      Service service = new Service();
      Call call = (Call) service.createCall();
      //注册JavaBean Bok，注意和server-config.wsdd中的配置代码比较
      QName qn = new QName("urn:" + parseHostconfig.getWsservicename(), "Book");
      Class qClass = Class.forName("com.sjtu.ASE2008.bookstore.domain.Book");

      call.registerTypeMapping(qClass, qn, new BeanSerializerFactory(qClass, qn),
                               new BeanDeserializerFactory(qClass, qn));

      call.setTargetEndpointAddress(new java.net.URL(endpoint));

      //调用的服务器端方法
      call.setOperationName(new QName(parseHostconfig.getWsservicename(),
                                      "updateBookInfo"));

      //设定传入的参数，
      call.addParameter("book", qn, ParameterMode.IN);
      //设定返回的参数是Order.class
      call.setReturnType(XMLType.XSD_INT);
      //booksArray = (ArrayList) call.invoke(new Object[] {});
      result = Integer.valueOf( (call.invoke(new Object[] {book})).toString());
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }

  /*
    public ArrayList getLatestBooks(int number) throws RemoteException {
      ArrayList booksArray = new ArrayList();
      Integer n = new Integer(number);
      //parseHostconfig.getWSHostConfig(wspath);

      String endpoint = parseHostconfig.getWsprotocol() + "://" +
          parseHostconfig.getWshost() + ":" +
          parseHostconfig.getWsportnumber()
       + "/axis/services/" + parseHostconfig.getWsservicename();

      //String endpoint = "http://localhost:8080/axis/services/BooksService";
      try {
        Service service = new Service();
        Call call = (Call) service.createCall();
       //注册JavaBean Book，注意和server-config.wsdd中的配置代码比较
   QName qn = new QName("urn:" + parseHostconfig.getWsservicename(), "Book");
        Class qClass = Class.forName("com.sjtu.ASE2008.bookstore.domain.Book");

   call.registerTypeMapping(qClass, qn, new BeanSerializerFactory(qClass, qn),
                                 new BeanDeserializerFactory(qClass, qn));

        call.setTargetEndpointAddress(new java.net.URL(endpoint));

        //调用的服务器端方法
        call.setOperationName(new QName(parseHostconfig.getWsservicename(),
                                        "getLatestBooks"));

        //设定传入的参数，
        call.addParameter("n", XMLType.XSD_INT, ParameterMode.IN);
        //设定返回的参数是Order.class
        call.setReturnClass(ArrayList.class);
        //booksArray = (ArrayList) call.invoke(new Object[] {});
        booksArray = (ArrayList) call.invoke(new Object[] {});

      }
      catch (Exception e) {
        e.printStackTrace();
      }
      return booksArray;
    }
   */

  //*
   public static void main(String args[]) {
     try {
       
       String wspath = "com/sjtu/ASE2008/bookstore/webservice_config.xml";
       String rmipath = "com/sjtu/ASE2008/bookstore/rmi_config.xml";
       RemoteBookService remoteBookService = new RemoteBookService();
       remoteBookService.getParseHostConfig().getWSHostConfig(wspath);
       remoteBookService.getParseHostConfig().getRMIHostConfig(rmipath);
       //LocateRegistry.createRegistry(1099);
       LocateRegistry.createRegistry(remoteBookService.getParseHostConfig().getRmiportnumber());
       Naming.rebind(remoteBookService.getParseHostConfig().getRmiprotocol() +
                     "://" +remoteBookService.getParseHostConfig().getRmihost() + ":" +
                     remoteBookService.getParseHostConfig().getRmiportnumber() +"/"
                     + remoteBookService.getParseHostConfig().getRmiservicename(),remoteBookService);
				
				
				/*
				IRemoteBookService remoteBookService = new RemoteBookService();
				LocateRegistry.createRegistry(1099);
       Naming.rebind("rmi://localhost:1099/BookService_RMIServer" , remoteBookService);
       */
       System.out.println("*****************RMI is running*********************");
      
     } catch (RemoteException e) {

            System.out.println("创建远程对象发生异常！");   

            e.printStackTrace();

        } catch (MalformedURLException e) {

            System.out.println("重复绑定发生异常！");   

            e.printStackTrace();

        } 
     catch (Exception e) {
       e.printStackTrace();
     }
   }

   /*2008-8-1 test ok
    public static void main(String args[]) {

      String wspath = "com/sjtu/ASE2008/bookstore/webservice_config.xml";
     Srath = "com/sjtu/ASE2008/bookstore/rmi_config.xml";
      ArrayList bk = new ArrayList();
      try {
        RemoteBookService remoteBookService = new RemoteBookService();
        remoteBookService.parseHostconfig.getWSHostConfig(wspath);
        remoteBookService.parseHostconfig.getRMIHostConfig(rmipath);
        //BookstoreFacadeService singletonservice = BookstoreFacadeService.getInstance();
        //bk = singletonservice.getLatestTitles(0);
        bk = remoteBookService.getLatestTitles(0);//ok 2008-7-31
        //bk = remoteBookService.getAllCatagories();//ok 2008-7-31

//basic date type such as int, Date, double has to be wrapped as String to be transfered in the javabean object
        //bk = remoteBookService.getBookByTitle("Chicken Soup");//ok 2008-8-1
        //bk = remoteBookService.getLatestBooks(3);//ok 2008-8-1
        //bk = remoteBookService.getAllTitles();//ok 2008-7-31
      }
      catch (Exception e) {
        e.printStackTrace();
      }

                for (int i = 0; i < bk.size(); i++) {
            System.out.println(bk.get(i).toString());
                }

          Iterator ite = bk.iterator();

          while (ite.hasNext()) {
            Book b = (Book) ite.next();
            System.out.println(b.getTitle());
            System.out.println(b.getISBN());
            System.out.println(b.getLeftover());
            System.out.println(b.getSellingprice());
            System.out.println((b.getStockdate()));
            System.out.println(b.getStockdate().toString().length());
            System.out.println(b.getUnitcost());

          }
        }


    /* 2008-8-11 test ok with Shoppingcart
               public static void main(String args[]) {
         String wspath = "com/sjtu/ASE2008/bookstore/webservice_config.xml";
                 String rmipath = "com/sjtu/ASE2008/bookstore/rmi_config.xml";
                 ArrayList bk = new ArrayList();
                 try {
      RemoteBookService remoteBookService = new RemoteBookService();
                   remoteBookService.parseHostconfig.getWSHostConfig(wspath);
                   remoteBookService.parseHostconfig.getRMIHostConfig(rmipath);
                   bk = remoteBookService.getLatestTitles(1);
                   System.out.println(bk.toString())

                   //boolean b = remoteBookService.doesItExist("tile", "books", "a");


                          cartlist = facade.createShoppingcart();
                          Iterator ite = cartlist.iterator();
                          while (ite.hasNext()) {
                     Shoppingcart cart = (Shoppingcart) ite.next();
                     ShoppingcartItem item = new ShoppingcartItem();
                     item.setAmount("2");
                     cart.addItem(item);
                     System.out.println(item.getAmount());



                   //System.out.println(b);
                 }catch (Exception e) {
                  e.printStackTrace();
                }
     */

}
