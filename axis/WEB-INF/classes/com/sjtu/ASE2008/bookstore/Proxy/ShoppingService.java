/**class
 * provides shopping services like shoppingcart ,make order and so on
 * @author Steve Sun
 * 2008-8-12
 */
package com.sjtu.ASE2008.bookstore.Proxy;

import com.sjtu.ASE2008.bookstore.domain.*;
import com.sjtu.ASE2008.bookstore.DBPool.*;
import com.sjtu.ASE2008.bookstore.Composite.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

public class ShoppingService {
  private Shoppingcart shoppingcart= null;

  public ShoppingService(){
  }

  public void setShoppingcart(Shoppingcart shoppingcart){
    this.shoppingcart = shoppingcart;
  }
  public Shoppingcart getShoppingcart(){
    return (this.shoppingcart);
  }
/*
  public ArrayList makeOrder(Shoppingcart cart) {
    //Shoppingcart cart = (Shoppingcart) iItem;
    Order theorder = new Order();
    ArrayList children = new ArrayList();
    OrderItem orderItem = new OrderItem();
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
      orderItem = this.makeOrderItem(item);//execute saveOrderItemToDB()
      //set orderid of each item of the order same with the order itself
      //Iterator it = orderItem.iterator();
      //OrderItem oi = (OrderItem) it.next();
      orderItem.setOrderid(s + cart.getUsername());
      theorder.getOrderitems().add(orderItem);//form the order
      cart.removeItem(item);//clear the shoppingcart
    }
    theorder.setUsername(cart.getUsername());
    //make sure the order has the same orderid with ite suborders
    theorder.setOrderid(s + cart.getUsername());
    theorder.setOrderdate("current_date");
    theorder.setLinenumber(String.valueOf(children.size()));
    theorder.setTotalprice(String.valueOf(total));
    theorder.setShipaddress("");
    theorder.setShipzipcode("");
    order.add(theorder);//put the order into arraylist
    //when an order is created with several order items, it must be saved to DB.
    if((saveOrderToDB(theorder)) != 1){
      return null;
    }
    //clear shoppingcart and delete from table shoppingcart and shoppingcartitems
    if((removeShopppingcartAndItems(theorder.getUsername()) != 1)){
      System.out.println(cart.getUsername() + "'s shoppingcart and items still exists in DB");
    }
    //clear the shoppingcarts

    return order;
  }
*/
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
      ps.close();
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

  public int removeShopppingcartAndItems(String username) {
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
      ps1.setString(1, username);
      ps2.setString(1, username);
      ps1.executeUpdate();
      ps2.executeUpdate();
      result = 1;
      ps1.close();
      ps2.close();
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

  public int PersistShoppingcart(Shoppingcart cart){
    int result = -1;
    ArrayList children = cart.getChildren();
    Iterator ite = children.iterator();
    //persist each cart items
    while(ite.hasNext()){
      ShoppingcartItem item = (ShoppingcartItem) ite.next();
      if(this.PersistShoppingcartItem(item) != 1){
        return result;
      }
    }
    //persist cart
    DBConnectionPoolManager dbMan = DBConnectionPoolManager.getInstance();
    String poolname = dbMan.getPoolname();
    Connection conn = dbMan.getConnection(poolname);
    String sql = "insert into shoppingcarts(username,cartdate,linenumber,totalprice) values(?," + cart.getCartdate() + ",?,?)";
    PreparedStatement ps = null;
    int line = -1;
    try{
      ps = conn.prepareStatement(sql);
      ps.setString(1,cart.getUsername());
      ps.setInt(3,Integer.valueOf(cart.getNumber()));
      ps.setDouble(4,Double.valueOf(cart.getTotalprice()));
      line = ps.executeUpdate();
      if(line != 1){
        conn.rollback();
        return result;
      }
      ps.close();
      result = 1;
    }catch(Exception e){
      System.out.println("Persisting shoppingcart " + cart.getUsername() + " to the database failed!");
      e.printStackTrace();
    }
    finally {
      dbMan.freeConnection(poolname, conn);
    }

    return result;
  }

  public int PersistShoppingcartItem(ShoppingcartItem item){
    int result = -1;
    DBConnectionPoolManager dbMan = DBConnectionPoolManager.getInstance();
    String poolname = dbMan.getPoolname();
    Connection conn = dbMan.getConnection(poolname);
    String sql = "insert into shoppingcartitems(username,itemdate,title,amount,sellingprice) values(?," + item.getItemdate() + ",?,?,?)";
    PreparedStatement ps = null;
    int line = -1;
    try{
      ps = conn.prepareStatement(sql);
      ps.setString(1,item.getUsername());
      ps.setString(2,item.getTitle());
      //ps.setString(3,orderItem.getItemdate());
      //ps.setDate(3,java.sql.Date.valueOf(orderItem.getItemdate()));
      ps.setInt(3,Integer.valueOf(item.getAmount()));
      ps.setDouble(4,Double.valueOf(item.getSellingprice()));

      line = ps.executeUpdate();
      //ps.executeUpdate();

      if(line != 1){
        conn.rollback();
        return result;
      }
      ps.close();
      result = 1;
    }catch(Exception e){
      System.out.println("Persisting shoppingcart item " + item.getUsername() + " to the database failed!");
      e.printStackTrace();
    }
    finally {
      dbMan.freeConnection(poolname, conn);
    }

    return result;

  }
/*
  public OrderItem makeOrderItem(ShoppingcartItem item) {
    //ShoppingcartItem item = (ShoppingcartItem) iItem;
    //ArrayList returnitem = new ArrayList();
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
      return null;
    }
    //returnitem.add(orderItem);
    return orderItem;
  }
*/
  public int saveOrderItemToDB(OrderItem orderItem) {
    int result = -1;
    DBConnectionPoolManager dbMan = DBConnectionPoolManager.getInstance();
    String poolname = dbMan.getPoolname();
    Connection conn = dbMan.getConnection(poolname);
    String sql = "insert into orderitems(orderid,username,itemdate,title,sellingprice,amount,totalprice,orderstatusid) values(?,?," + orderItem.getItemdate() + ",?,?,?,?,?)";
    PreparedStatement ps = null;
    int line = -1;
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
      line = ps.executeUpdate();
      //ps.executeUpdate();

      if(line != 1){
        conn.rollback();
        return result;
      }
      ps.close();
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

  public ArrayList ViewAllOrderItems(){
    ArrayList orderitems = new ArrayList();
    ArrayList orderitem = new ArrayList();
    DBConnectionPoolManager dbMan = DBConnectionPoolManager.getInstance();
    String poolname = dbMan.getPoolname();
    Connection conn = dbMan.getConnection(poolname);
    String sql ="select t1.orderid,t1.username,t1.shipaddress,t1.shipzip,t2.itemdate,t2.title,t2.sellingprice,t2.amount,t2.totalprice,t2.orderstatusid from orders t1,orderitems t2 where t1.orderid = t2.orderid";
    PreparedStatement ps = null;
    ResultSet rs = null;
    try{
      ps = conn.prepareStatement(sql);
      rs = ps.executeQuery();
      while(rs.next()){
        orderitem.add(rs.getString("orderid"));
        orderitem.add(rs.getString("username"));
        orderitem.add(rs.getString("shipaddress"));
        orderitem.add(rs.getString("shipzip"));
        orderitem.add(rs.getString("itemdate"));
        orderitem.add(rs.getString("title"));
        orderitem.add(rs.getString("sellingprice"));
        orderitem.add(rs.getString("amount"));
        orderitem.add(rs.getString("totalprice"));
        orderitem.add(rs.getString("orderstatusid"));
        orderitems.add(orderitem);
      }
      rs.close();
      ps.close();
    }catch(Exception e){
      //System.out.println("Saving orderitem " + orderItem.getOrderid() + " to the database failed!");
      e.printStackTrace();
    }
    finally {
      dbMan.freeConnection(poolname, conn);
    }
    return orderitems;
  }

  public ArrayList ViewOrderItemsByUser(String username){
    ArrayList orderitems = new ArrayList();
    ArrayList orderitem = new ArrayList();
    DBConnectionPoolManager dbMan = DBConnectionPoolManager.getInstance();
    String poolname = dbMan.getPoolname();
    Connection conn = dbMan.getConnection(poolname);
    String sql ="select t1.orderid,t1.username,t1.shipaddress,t1.shipzip,t2.itemdate,t2.title,t2.sellingprice,t2.amount,t2.totalprice,t2.orderstatusid from orders t1,orderitems t2 where t1.orderid = t2.orderid and t1.username = ?";
    PreparedStatement ps = null;
    ResultSet rs = null;
    try{
      ps = conn.prepareStatement(sql);
      ps.setString(1,username);
      rs = ps.executeQuery();
      while(rs.next()){
        orderitem.add(rs.getString("orderid"));
        orderitem.add(rs.getString("username"));
        orderitem.add(rs.getString("shipaddress"));
        orderitem.add(rs.getString("shipzip"));
        orderitem.add(rs.getString("itemdate"));
        orderitem.add(rs.getString("title"));
        orderitem.add(rs.getString("sellingprice"));
        orderitem.add(rs.getString("amount"));
        orderitem.add(rs.getString("totalprice"));
        orderitem.add(rs.getString("orderstatusid"));
        orderitems.add(orderitem);
      }
      rs.close();
      ps.close();
    }catch(Exception e){
      //System.out.println("Saving orderitem " + orderItem.getOrderid() + " to the database failed!");
      e.printStackTrace();
    }
    finally {
      dbMan.freeConnection(poolname, conn);
    }
    return orderitems;
  }


/*
  public void addItem(String title,String number,String price) {
    ShoppingcartItem item = new ShoppingcartItem();
    item.setTitle(title);
    item.setAmount(number);
    item.setSellingprice(price);
    //item.setItemdate();
    item.setUsername("");
    this.getShoppingcart().getItems().add(item);
  }

  public void removeItem(ShoppingcartItem item) {
    if (items.contains(item)) {
      System.out.println("items contains item");
      items.remove(item);
    }
    else {
      System.out.println("items do not contain item");
    }
  }

  public void removeAllItems() {
    items.clear();
  }

  public void updateNumberOfCertainItem(int number, ShoppingcartItem item) {
    item.setAmount(String.valueOf(number));
  }

     public OrderItem transformOrderItem() {
    Calendar now = Calendar.getInstance();
    SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddhhmmss");
    String orderid = fmt.format(now.getTime()) + this.username;

    OrderItem orderItem = new OrderItem();

    orderItem.setOrderid(orderid);
    orderItem.setUsername(this.username);
    orderItem.setItemdate(this.itemdate);
    orderItem.setTitle(this.title);
    orderItem.setAmount(this.amount);
    orderItem.setSellingprice(this.sellingprice);
     }
   */
}
