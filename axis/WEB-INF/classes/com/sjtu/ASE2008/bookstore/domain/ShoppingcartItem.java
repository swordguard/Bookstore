//Source file: K:\\GGGGGGGGGG\\2007-2008(2)\\ASE2008\\bookstore\\Root\\Shopping\\ShoppingcartItem.java
/**domain class
 * javabean
 *
 * @author Steve Sun
 * 2008-8-6
 */

package com.sjtu.ASE2008.bookstore.domain;

import com.sjtu.ASE2008.bookstore.Composite.*;
//import com.sjtu.ASE2008.bookstore.DBPool.*;
import java.io.Serializable;
//import java.util.Calendar;
import java.util.ArrayList;
//import java.text.SimpleDateFormat;
/*
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 Each book item in the shoppingcart.
 It contains detailed attributes like the amount of certain book, the selling
 price and the total sum the customer has to pay.
 */
public class ShoppingcartItem
    implements Serializable, IItem {

  /**
      Represents which shoppingcart it belongs to.
   */
  //private String shoppingcartid;
  private String username;
  private String itemdate;

  /**
      the book title
   */
  private String title;

  /**
      numbers of the book that the customer want to buy
   */
  private String amount;

  /**
      the selling price of the book
   */
  private String sellingprice;

  /**
      @roseuid 487C77EE038A
   */
  public ShoppingcartItem() {

  }

  //an item can not add or remove item,it's the leaf node.
  public boolean addItem(IItem item) {
    return false;
  }

  public boolean removeItem(IItem item) {
    return false;
  }

  public int getNumber() {
    return (Integer.parseInt( (this.getAmount())));
  }

  public ArrayList<IItem> getChildren() {
    return null;
  }
/*
  public ArrayList makeOrder(IItem iItem) {
    ShoppingcartItem item = (ShoppingcartItem) iItem;
    ArrayList returnitem = new ArrayList();
    OrderItem orderItem = new OrderItem();

    orderItem.setUsername(item.getUsername());
    orderItem.setAmount(item.getAmount());
    orderItem.setItemdate("current_date");
    //format orderid
    //orderItem.setOrderid(s + this.getUsername());
    orderItem.setOrderstatusid("1");
    /**Sets the status of this order.
      1.to be processed
      2.processing
      3.on the way
      4.delivered
      -1.canceled

    orderItem.setSellingprice(item.getSellingprice());
    orderItem.setTitle(item.getTitle());
    orderItem.setTotalprice(String.valueOf(Double.parseDouble(item.getAmount())
                                           *
                                           Double.parseDouble(item.getSellingprice())));
    //when an new orderitem is created, it must be saved to DB first
    if((saveOrderItemToDB(orderItem)) != 1){
      return returnitem;
    }
    returnitem.add(orderItem);
    return returnitem;
  }

  public int saveOrderItemToDB(OrderItem orderItem) {
    int result = -1;
    DBConnectionPoolManager dbMan = DBConnectionPoolManager.getInstance();
    String poolname = dbMan.getPoolname();
    Connection conn = dbMan.getConnection(poolname);
    String sql = "insert into orderitems(orderid,username,itemdate,title,sellingprice,amount,totalprice,orderstatusid) values(?,?," + orderItem.getItemdate() + ",?,?,?,?,?)";
    PreparedStatement ps = null;
    //int line = -1;
    try{
      ps = conn.prepareStatement(sql);
      ps.setString(1,orderItem.getOrderid());
      ps.setString(2,orderItem.getUsername());
      //ps.setString(3,orderItem.getItemdate());
      //ps.setDate(3,java.sql.Date.valueOf(orderItem.getItemdate()));
      ps.setString(3,orderItem.getTitle());
      ps.setDouble(4,Double.valueOf(orderItem.getSellingprice()));
      ps.setInt(5,Integer.valueOf(orderItem.getAmount()));
      ps.setDouble(6,Double.valueOf(orderItem.getTotalprice()));
      ps.setInt(7,Integer.valueOf(orderItem.getOrderstatusid()));
      //line = ps.executeUpdate();
      ps.executeUpdate();
      /*
      if(line != 1){
        conn.rollback();
        return result;
      }

      result = 1;
    }catch(Exception e){
      System.out.println("Saving orderitem " + orderItem.getOrderid() + " to the database failed!");
      e.printStackTrace();
    }
    finally {
      dbMan.freeConnection(poolname, conn);
    }

    return result;
  }
*/
  /**
      Returns the identity number of the shoppingcart it belongs to.
      @return Integer
      @roseuid 487C59260177

     public String getShoppingcartid() {
    return (this.shoppingcartid);
     }
   */
  public String getUsername() {
    return (this.username);
  }

  public String getItemdate() {
    return (this.itemdate);
  }

  /**
      Returns the book title of this item.
      @return String
      @roseuid 487C5935030D
   */
  public String getTitle() {
    return (this.title);
  }

  /**
      Returns the amount of current book item.
      @return Integer
      @roseuid 487C593A02AF
   */
  public String getAmount() {
    return (this.amount);
  }

  /**
      Returns the selling price of current book item.
      @return Double
      @roseuid 487C593F033C
   */
  public String getSellingprice() {
    return (this.sellingprice);
  }

  /*
    public void setShoppingcartid(String shoppingcartid) {
      this.shoppingcartid = shoppingcartid;
    }
   */
  public void setUsername(String username) {
    this.username = username;
  }

  public void setItemdate(String itemdate) {
    this.itemdate = itemdate;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setAmount(String amount) {
    this.amount = amount;
  }

  public void setSellingprice(String sellingprice) {
    this.sellingprice = sellingprice;
  }
}
