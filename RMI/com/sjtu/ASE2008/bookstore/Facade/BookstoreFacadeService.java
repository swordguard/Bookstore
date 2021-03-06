/**This is the facade class for the invocation of web service
 *
 * @author Steve Sun
 * 2008-7-30
 * update 2008-7-31
 */

package com.sjtu.ASE2008.bookstore.Facade;

import com.sjtu.ASE2008.bookstore.*;
import com.sjtu.ASE2008.bookstore.Proxy.*;
import com.sjtu.ASE2008.bookstore.domain.*;
import com.sjtu.ASE2008.bookstore.bookstoreFactory.*;
import com.sjtu.ASE2008.bookstore.Composite.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.sql.*;

public class BookstoreFacadeService {
  private BookServiceProxy bookServiceProxy = null;
  private ShoppingcartFactory shoppingcartFactory = null;
  private ShoppingService shoppingService = null;
  private UserManager userManager = null;
  //private UserManager userManager = null;
  /*
  private static BookstoreFacadeService instance = null;

  public static synchronized BookstoreFacadeService getInstance(){
    if(instance == null){
      instance = new BookstoreFacadeService();
      return instance;
    }
    return instance;
  }
  */
  public BookstoreFacadeService() {
  //private BookstoreFacadeService() {
    this.bookServiceProxy = new BookServiceProxy();
    this.shoppingcartFactory = new ShoppingcartFactory();
    this.shoppingService = new ShoppingService();
    this.userManager = new UserManager();
  }
/*
  public void setBookServiceProxy(BookServiceProxy bookServiceProxy) {
    this.bookServiceProxy = bookServiceProxy;
  }
  public void setShoppingcartFactory(ShoppingcartFactory shoppingcartFactory) {
      this.shoppingcartFactory = shoppingcartFactory;
  }
  public void setShoppingService(ShoppingService shoppingService) {
   this.shoppingService = shoppingService;
 }
*/
  public BookServiceProxy getBookServiceProxy() {
    return (this.bookServiceProxy);
  }

  public ShoppingcartFactory getShoppingcartFactory(){
    return (this.shoppingcartFactory);
  }

 public ShoppingService getShoppingService() {
   return (this.shoppingService);
 }
 public UserManager getUserManager() {
   return (this.userManager);
 }


  /*
  public void setUserManager(UserManager userManager) {
    this.userManager = userManager;
  }
  */
  //some methods from bookstoreFactory

  public List createShoppingcart(){
  	List arr = new ArrayList();

  	Shoppingcart cart = (Shoppingcart)this.getShoppingcartFactory().getInstance().createIItem();
  	arr.add(cart);
    return (arr);
  }
	

  
  
  //some methods from shoppingservice
/*
  public ArrayList makeOrder(IItem iItem){
    ArrayList arr = new ArrayList();
    Shoppingcart cart = (Shoppingcart) iItem;
    arr = this.getShoppingService().makeOrder(cart);
    return arr;
  }
*/

public int saveOrderToDB(Order order){
  return (this.getShoppingService().saveOrderToDB(order));
}
public int saveOrderItemToDB(OrderItem orderItem){
  return (this.getShoppingService().saveOrderItemToDB(orderItem));
}

  public int PersistShoppingcart(Shoppingcart cart){
    return (this.getShoppingService().PersistShoppingcart(cart));
  }
  public int PersistShoppingcartItem(ShoppingcartItem item){
      return (this.getShoppingService().PersistShoppingcartItem(item));
  }
  /*
  public int removeShopppingcartByUser(String username) {
    return (this.getShoppingService().removeShopppingcartByUser(username));
  }
  public int removeShopppingcartItemByUser(String username) {
    return (this.getShoppingService().removeShopppingcartItemByUser(username));
  }
*/
  public List viewAllOrderItems(){
    return shoppingService.ViewAllOrderItems();
  }


  public List viewOrderItemsByUser(String username){
    return shoppingService.viewOrderItemsByUser(username);
  }

  public List viewOrderItemsByOrderid(String orderid){
    return shoppingService.viewOrderItemsByOrderid(orderid);
  }



  public List viewOrderByOrderid(String orderid){
    return shoppingService.viewOrderByOrderid(orderid);
  }
  
  public List viewOrderByUser(String username){
    return shoppingService.viewOrderByUser(username);
  }
  
  
	public List searchByTitle(String title){
    return (shoppingService.searchByTitle(title));
  }
  
   public List getAllCatagories() {
    return (shoppingService.getAllCatagories());
  }

  //The following methods are from class BookServiceProxy.class


  public boolean doesItExist(String column, String table, String value) {
    return (this.getBookServiceProxy().doesItExist(column, table, value));
  }
  
  
   public int updateBookInfo(Book book) {
    int result = -1;
    if ( (this.getBookServiceProxy().updateBookInfo(book)) == 1) {
      result = 1;
    }
    return result;
  }
  

  public boolean checkTitle(String title) {
    return (this.getBookServiceProxy().checkTitle(title));
  }

  public int addBook(Book book) {
    int result = -1;
    if ( (this.getBookServiceProxy().addBook(book)) == 1) {
      result = 1;
    }
    return result;
  }

  public int removeBookByTitle(String title) {
    int result = -1;
    if ( (this.getBookServiceProxy().removeBookByTitle(title)) == 1) {
      result = 1;
    }
    return result;
  }

  public List getAllTitles() {
  	System.out.println("In BookFacadeService.java");
    return (this.getBookServiceProxy().getAllTitles());
  }
  
  
   public String getAllTitleString() {
  	System.out.println("In getAllTitleString.java");
    return this.getBookServiceProxy().getAllTitles().toString();
  }

  public List getLatestTitles(int number) {
    return (this.getBookServiceProxy().getLatestTitles(number));
  }

  public List getBookByTitle(String title) {
    return (this.getBookServiceProxy().getBookByTitle(title));
  }

 
  //some methods from UserManager.java
  public List viewUsers(){
    //ArrayList arr = new ArrayList();
    //System.out.println("================ viewUsers() in Facade is called");
    return (this.getUserManager().getUsers());
  }
  
  
  
  public List viewUsers2(){
    List users = new ArrayList();
    System.out.println("================ viewUsers2() in Facade is called");
    
    String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    String url = "jdbc:derby:bookstoreDB;create=true";
    String sql = "select count(*) from sys.systables";
   
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    
    try{
    	 Class.forName(driver);
    	    conn = DriverManager.getConnection(url, "", "");
    	    ps = conn.prepareStatement(sql);
    	    rs = ps.executeQuery();
    	while(rs.next()){
    		System.out.println("==========="+rs.getString(1));
        	users.add(rs.getString(1));
        }
    }catch(ClassNotFoundException e){
    	e.printStackTrace();
    }
    catch(SQLException e){
    	e.printStackTrace();
    } finally{
    	
    	try{
    	 rs.close();
    	    ps.close();
    	   conn.close();
    	}catch(SQLException e){
    		e.printStackTrace();
    	}
    }
   
    
    return users;
  }
  
  
  
  public List getUserByName(String username){
    return (this.getUserManager().getUserByName(username));
  }
  public int register(User user) {
    return (this.getUserManager().register(user));
  }
  public int login(String username, String password) {
    return (this.getUserManager().login(username,password));
  }
  public int updateDate(String column1, String column2,String table, String value) {
    return (this.getUserManager().updateDate(column1,column2,table,value));
  }
  
	public int updateUserInfo(User user) {
		return this.getUserManager().updateUserInfo(user);
	}


public String sayHello(String name){
	System.out.println("hello 22222222222222222222222"+ name);
	return "hello " +name;
}


public List<String> sayHello(){
	System.out.println("hello sayHello() is called.");
	List<String> list = new ArrayList<>();
	list.add("hello1111111111111111111111111111111111111111111222===================");
	//return viewUsers();
	return list;
}



public String derbyTest(){
	
		BookstoreFacadeService facade = new BookstoreFacadeService();
		List title = viewUsers();
		///int r = facade.login(username,password);
		System.out.println("size:" + title.size() + "  "+title.toString());
	
	return title.toString();
}


public String getNumberOfUsers(){
	
		BookstoreFacadeService facade = new BookstoreFacadeService();
		List title = this.getUserManager().getUsers();
		///int r = facade.login(username,password);
		System.out.println("size:" + title.toString());
	
	return title.toString();
}



	public static void main(String args[]){
		List title = new ArrayList();
		BookstoreFacadeService facade = new BookstoreFacadeService();
		
		//System.out.println("derbyTest: "+facade.getUsers());
		//System.out.println("derbyTest: "+facade.derbyTest());
		List users = facade.viewUsers();
		for(Object o : users){
			User user = (User)o;
			System.out.println(user.getUsername());
		}
	}
	
}
