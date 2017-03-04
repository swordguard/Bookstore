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

  public ArrayList createShoppingcart(){
  	ArrayList arr = new ArrayList();

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
  public ArrayList viewAllOrderItems(){
    return (this.getShoppingService().viewAllOrderItems());
  }

  public ArrayList viewOrderItemsByUser(String username){
    return (this.getShoppingService().viewOrderItemsByUser(username));
  }

  public ArrayList viewOrderItemsByOrderid(String orderid){
    return (this.getShoppingService().viewOrderItemsByOrderid(orderid));
  }

  public ArrayList viewOrderByOrderid(String orderid){
    return (this.getShoppingService().viewOrderByOrderid(orderid));
  }
	public ArrayList searchByTitle(String title){
    return (this.getShoppingService().searchByTitle(title));
  }

  //The following methods are from class BookServiceProxy.class
  public ArrayList getAllCatagories() {
    return (this.getBookServiceProxy().getAllCatagories());
  }

  public boolean doesItExist(String column, String table, String value) {
    return (this.getBookServiceProxy().doesItExist(column, table, value));
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

  public ArrayList getAllTitles() {
    return (this.getBookServiceProxy().getAllTitles());
  }

  public ArrayList getLatestTitles(int number) {
    return (this.getBookServiceProxy().getLatestTitles(number));
  }

  public ArrayList getBookByTitle(String title) {
    return (this.getBookServiceProxy().getBookByTitle(title));
  }

  public int updateBookInfo(Book book) {
    int result = -1;
    if ( (this.getBookServiceProxy().updateBookInfo(book)) == 1) {
      result = 1;
    }
    return result;
  }
  //some methods from UserManager.java
  public ArrayList viewUsers(){
    //ArrayList arr = new ArrayList();
    return (this.getUserManager().getUsers());
  }
  public ArrayList getUserByName(String username){
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
  /**test ok 2008-8-1
   public static void main(String args[]) {
     BookstoreFacadeService facade = new BookstoreFacadeService();
     BookServiceProxy bookServiceProxy = new BookServiceProxy();
     facade.setBookServiceProxy(bookServiceProxy);
     ArrayList c = new ArrayList();
     c = facade.getLatestTitles(1);//test ok 2008-7-31
     //c = facade.getAllCatagories();//test ok 2008-7-31
     //c = facade.getAllTitles();//test ok 2008-7-31
     //c = facade.getBookByTitle("a");//test ok 2008-7-31
     for (int i = 0; i < c.size(); i++) {
       System.out.println(c.get(i).toString());
     }

   }
*/

	public static void main(String args[]){
		ArrayList title = new ArrayList();
		BookstoreFacadeService facade = new BookstoreFacadeService();
		title = facade.viewAllOrderItems();
		///int r = facade.login(username,password);
		System.out.println(title.size());
	}
	
}
