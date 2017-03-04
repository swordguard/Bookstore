//Source file: K:\\GGGGGGGGGG\\2007-2008(2)\\ASE2008\\bookstore\\Root\\com\\sjtu\\ASE2008\\bookstore\\domain\\Shoppingcart.java
/**domain class
 * javabean
 *
 * @author Steve Sun
 * 2008-8-6
 */

package com.sjtu.ASE2008.bookstore.domain;

//import com.sjtu.ASE2008.bookstore.bookstoreFactory.IShoppingcart;
import com.sjtu.ASE2008.bookstore.Composite.*;
import java.io.Serializable;
import java.util.ArrayList;
/*
import com.sjtu.ASE2008.bookstore.DBPool.*;

import java.util.Iterator;

import java.util.Calendar;
import java.text.SimpleDateFormat;

import java.sql.*;
*/
/**
 Every customer must have one and only one of it before he or she can continue to
 buy.
 The shoppingcart can have several items in it.
 The customer can put certain number of various kind of books into it,or remove
 some items from it even remove all items.Also he or she can modify the number of


 certain books in the shoppingcart.
 If the customer registered in the website and he or she does not make the order,


 the shoppingcart will appear next time when he or she logs in.
 */
public class Shoppingcart
    //implements Serializable, IItem {
    extends IItem,implements Serializable{
  private ArrayList<IItem> items = null;
  //private ShoppingcartItem shoppingcartItem = null;
  //private IItem iItem;


  /**
      tells whoes shopping cart it is
   */
  private String username;
  private String cartdate;
  /**
      tells how many item lines the shoppingcart has
   */
  private String linenumber;

  /**
      total price of the entire items in this shopping cart
   */
  private String totalprice;

  /**
      @roseuid 487C77F00242
   */
  public Shoppingcart() {
    items = new ArrayList();
    //System.out.println("A new shoppingcart was born!");
  }

  public boolean addItem(IItem item) {
    return (items.add(item));
  }

  public boolean removeItem(IItem item) {
    return (items.remove(item)); //items.remove(item) returns false or true
  }

  public int getNumber() {
    return (Integer.parseInt( (this.getLinenumber())));
  }

  public ArrayList<IItem> getChildren() {
    return items;
  }

/*
  public ArrayList makeOrder(IItem iItem) {
    Shoppingcart cart = (Shoppingcart) iItem;
    Order theorder = new Order();
    ArrayList children = new ArrayList();
    ArrayList orderItem = new ArrayList();
    ArrayList order = new ArrayList();
    children = cart.getChildren();//get its sub items
    Iterator ite = children.iterator();
    //set orderid
    Calendar now = Calendar.getInstance();
    SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddhhmmss");
    String s = fmt.format(now.getTime());
    double total = 0;

    while (ite.hasNext()) {
      ShoppingcartItem item = (ShoppingcartItem) ite.next();
      //count the total price of all order items
      total = Double.parseDouble(item.getAmount()) *
          Double.parseDouble(item.getSellingprice()) + total;
      orderItem = item.makeOrder(item);//execute saveOrderItemToDB()
      //set orderid of each item of the order same with the order itself
      Iterator it = orderItem.iterator();
      OrderItem oi = (OrderItem) it.next();
      oi.setOrderid(s + cart.getUsername());
      theorder.getOrderitems().add(orderItem);//form the order
    }
    theorder.setUsername(cart.getUsername());
    //make sure the order has the same orderid with ite suborders
    theorder.setOrderid(s + cart.getUsername());
    theorder.setOrderdate("current_date");
    theorder.setLinenumber(String.valueOf(children.size()));
    theorder.setTotalprice(String.valueOf(total));
    theorder.setShipaddress("");
    theorder.setShipzipcode("");
    //when an order is created with several order items, it must be saved to DB.
    if((saveOrderToDB(theorder)) != 1){
      return null;
    }
    //clear shoppingcart and delete from table shoppingcart and shoppingcartitems
    if((removeShopppingcartAndItems() != 1)){
      System.out.println(cart.getUsername() + "'s shoppingcart and items still exists in DB");
    }
    items.clear();
    return order;
  }

  public int saveOrderToDB(Order order) {
    int result = -1;
    DBConnectionPoolManager dbMan = DBConnectionPoolManager.getInstance();
    String poolname = dbMan.getPoolname();
    Connection conn = dbMan.getConnection(poolname);
    String sql = "insert into orders(orderid,username,orderdate,linenumber,totalprice,shipaddress,shipzip) values(?,?,"+order.getOrderdate()+",?,?,?,?)";
    PreparedStatement ps = null;
    int line = -1;
    try {
      ps = conn.prepareStatement(sql);
      ps.setString(1, order.getOrderid());
      ps.setString(2, order.getUsername());
      //ps.setString(3, order.getOrderdate());
      ps.setInt(3, Integer.valueOf(order.getLinenumber()));
      ps.setDouble(4, Double.valueOf(order.getTotalprice()));
      ps.setString(5, order.getShipaddress());
      ps.setString(6, order.getShipzipcode());
      line = ps.executeUpdate();
      if (line != 1) {
        conn.rollback();
        return result;
      }
      result = 1;
    }
    catch (Exception e) {
      System.out.println("Saving orders " + order.getOrderid() +
                         " to the database failed!");
      e.printStackTrace();
    }
    finally {
      dbMan.freeConnection(poolname, conn);
    }

    return result;
  }

  public int removeShopppingcartAndItems() {
    int result = -1;
    DBConnectionPoolManager dbMan = DBConnectionPoolManager.getInstance();
    String poolname = dbMan.getPoolname();
    Connection conn = dbMan.getConnection(poolname);
    String sql1 = "delete from shoppingcarts where username = ?";
    String sql2 = "delete from shoppingcartitems where username = ?";
    PreparedStatement ps1 = null;
    PreparedStatement ps2 = null;
    //int line1 = -1;
    //int line2 = -1;
    try {
      ps1 = conn.prepareStatement(sql1);
       ps2 = conn.prepareStatement(sql2);
      ps1.setString(1, this.getUsername());
      ps2.setString(1, this.getUsername());
      ps1.executeUpdate();
      ps2.executeUpdate();
      result = 1;
    }
    catch (Exception e) {
      System.out.println("Removing shoppingcart and its items failed!");
      e.printStackTrace();
    }
    finally {
      dbMan.freeConnection(poolname, conn);
    }

    return result;
  }
*/

  public void setItems(ArrayList items) {
    this.items = items;
  }

  /*
     public void setShoppingcartid(String shoppingcartid){
    this.shoppingcartid = shoppingcartid;
     }
   */
  public void setUsername(String username) {
    this.username = username;
  }

  public void setCartdate(String cartdate) {
    this.cartdate = cartdate;
  }

  public void setLinenumber(String linenumber) {
    this.linenumber = linenumber;
  }

  public void setTotalprice(String totalprice) {
    this.totalprice = totalprice;
  }

  public ArrayList getItems() {
    return (this.items);
  }

  /*
     public String getShoppingcartid(){
    return (this.shoppingcartid);
     }
   */
  /**
      Returns the username of current customer.
      @return String
      @roseuid 487C597A0290
   */

  public String getUsername() {
    return (this.username);
  }

  public String getCartdate() {
    return (this.cartdate);
  }

  /**
      Returns the numbers of the book items contained in this shoppingcart.
      @return Integer
      @roseuid 487C5981007D
   */

  public String getLinenumber() {
    return (this.linenumber);
  }

  /**
      Returns the total sum of all the book items in this shoppingcart.
      @return Double
      @roseuid 487C5988002E
   */

  public String getTotalprice() {
    return (this.totalprice);
  }

  /**
      Returns the username of current customer.
      @return String
      @roseuid 487C597A0290
   */
  /*
     public String getUsername() {
    return null;
     }
   */
  /**
      Returns the numbers of the book items contained in this shoppingcart.
      @return Integer
      @roseuid 487C5981007D
   */
  /*
     public Integer getLinenumber() {
    return null
     }
   */
  /**
      Returns the total sum of all the book items in this shoppingcart.
      @return Double
      @roseuid 487C5988002E
   */
  /*
     public Double getTotalprice() {
    return null;
     }
   */
  /**
      Returns one object of ShoppingcartItem in this shoppingcart.
      @param shoppingcartid
      @param title
      @return Shopping.ShoppingcartItem
      @roseuid 487C599F0177
   */

}
