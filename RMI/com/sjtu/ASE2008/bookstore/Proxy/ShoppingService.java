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
import java.util.*;
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

  public int saveOrderToDB(Order order) {
    int result = -1;
    DBConnectionPoolManager dbMan = DBConnectionPoolManager.getInstance();
    //String poolname = dbMan.getPoolname();
    Connection conn = dbMan.getConnection();
    String sql = "insert into orders(orderid,username,orderdate,linenumber,totalprice,shipaddress,shipzip) values(?,?,?,?,?,?,?)";
    PreparedStatement ps = null;
    int line = -1;
    try {
      ps = conn.prepareStatement(sql);
      ps.setString(1, order.getOrderid());
      ps.setString(2, order.getUsername());
      ps.setString(3, order.getOrderdate());
      ps.setString(4, order.getLinenumber());
      ps.setString(5, order.getTotalprice());
      ps.setString(6, order.getShipaddress());
      ps.setString(7, order.getShipzipcode());
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
      //dbMan.freeConnection(poolname, conn);
    }

    return result;
  }

  public int removeShopppingcartAndItems(String username) {
    int result = -1;
    DBConnectionPoolManager dbMan = DBConnectionPoolManager.getInstance();
    //String poolname = dbMan.getPoolname();
    Connection conn = dbMan.getConnection();
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
      //dbMan.freeConnection(poolname, conn);
    }

    return result;
  }

  public int PersistShoppingcart(Shoppingcart cart){
    int result = -1;
    List children = cart.getChildren();
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
    //String poolname = dbMan.getPoolname();
    Connection conn = dbMan.getConnection();
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
      //dbMan.freeConnection(poolname, conn);
    }

    return result;
  }

  public int PersistShoppingcartItem(ShoppingcartItem item){
    int result = -1;
    DBConnectionPoolManager dbMan = DBConnectionPoolManager.getInstance();
    //String poolname = dbMan.getPoolname();
    Connection conn = dbMan.getConnection();
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
      //dbMan.freeConnection(poolname, conn);
    }

    return result;

  }



  public int saveOrderItemToDB(OrderItem orderItem) {
    int result = -1;
    DBConnectionPoolManager dbMan = DBConnectionPoolManager.getInstance();
    //String poolname = dbMan.getPoolname();
    Connection conn = dbMan.getConnection();
    String sql = "insert into orderitems(orderid,username,itemdate,title,sellingprice,amount,totalprice,orderstatusid) values(?,?,?,?,?,?,?,?)";
    PreparedStatement ps = null;
    int line = -1;
    try{
      ps = conn.prepareStatement(sql);
      ps.setString(1,orderItem.getOrderid());
      ps.setString(2,orderItem.getUsername());
      ps.setString(3,orderItem.getItemdate());
      //ps.setDate(3,java.sql.Date.valueOf(orderItem.getItemdate()));
      ps.setString(4,orderItem.getTitle());
      ps.setString(5,orderItem.getSellingprice());
      ps.setString(6,orderItem.getAmount());
      ps.setString(7,orderItem.getTotalprice());
      ps.setString(8,orderItem.getOrderstatusid());
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
      //dbMan.freeConnection(poolname, conn);
    }

    return result;
  }



	/////view all orders and its items, means all the records in orders and orderitmes table
  public List ViewAllOrderItems(){
     List list = new ArrayList();
    
    DBConnectionPoolManager dbMan = DBConnectionPoolManager.getInstance();
    //String poolname = dbMan.getPoolname();
    Connection conn = dbMan.getConnection();
    //String sql ="select t1.orderid,t1.username,t1.shipaddress,t1.shipzip,t2.itemdate,t2.title,t2.sellingprice,t2.amount,t2.totalprice,t2.orderstatusid from orders t1,orderitems t2 where t1.orderid = t2.orderid and t1.username = ?";
    String sql ="select * from orderitems";
    PreparedStatement ps = null;
    ResultSet rs = null;
    List orderitmes = null;
    try{
      ps = conn.prepareStatement(sql);
      //ps.setString(1,username);
      rs = ps.executeQuery();
      while(rs.next()){
      	OrderItem orderitem = new OrderItem();
        orderitem.setOrderid(rs.getString("orderid"));
        orderitem.setUsername(rs.getString("username"));
        orderitem.setItemdate(rs.getString("itemdate"));
        orderitem.setTitle(rs.getString("title"));
        
        orderitem.setSellingprice(rs.getString("sellingprice"));
        orderitem.setAmount(rs.getString("amount"));
        orderitem.setTotalprice(rs.getString("totalprice"));
        orderitem.setOrderstatusid(rs.getString("orderstatusid"));
        list.add(orderitem);
        
        list.add(orderitem);
      }
      rs.close();
      ps.close();
    }catch(Exception e){
      //System.out.println("Saving orderitem " + orderItem.getOrderid() + " to the database failed!");
      e.printStackTrace();
    }
    finally {
      //dbMan.freeConnection(poolname, conn);
    }
    return list;
  }

  public List viewOrderItemsByUser(String username){
    List orderitems = new ArrayList();
    //List orderitem = new ArrayList();
    DBConnectionPoolManager dbMan = DBConnectionPoolManager.getInstance();
    //String poolname = dbMan.getPoolname();
    Connection conn = dbMan.getConnection();
    String sql ="select t1.orderid,t1.username,t1.shipaddress,t1.shipzip,t2.itemdate,t2.title,t2.sellingprice,t2.amount,t2.totalprice,t2.orderstatusid from orders t1,orderitems t2 where t1.orderid = t2.orderid and t1.username = ?";
    PreparedStatement ps = null;
    ResultSet rs = null;
    try{
      ps = conn.prepareStatement(sql);
      ps.setString(1,username);
      rs = ps.executeQuery();
      while(rs.next()){
      	OrderItem orderitem = new OrderItem();
        orderitem.setOrderid(rs.getString("orderid"));
        orderitem.setUsername(rs.getString("username"));
        orderitem.setItemdate(rs.getString("itemdate"));
        orderitem.setTitle(rs.getString("title"));
        
        orderitem.setSellingprice(rs.getString("sellingprice"));
        orderitem.setAmount(rs.getString("amount"));
        orderitem.setTotalprice(rs.getString("totalprice"));
        orderitem.setOrderstatusid(rs.getString("orderstatusid"));
        orderitems.add(orderitem);
      }
      rs.close();
      ps.close();
    }catch(Exception e){
      //System.out.println("Saving orderitem " + orderItem.getOrderid() + " to the database failed!");
      e.printStackTrace();
    }
    finally {
      //dbMan.freeConnection(poolname, conn);
    }
    return orderitems;
  }
  
  
  public List viewOrderItemsByOrderid(String orderid){
    List orderitems = new ArrayList();
    
    DBConnectionPoolManager dbMan = DBConnectionPoolManager.getInstance();
    //String poolname = dbMan.getPoolname();
    Connection conn = dbMan.getConnection();
    //String sql ="select t1.orderid,t1.username,t1.shipaddress,t1.shipzip,t2.itemdate,t2.title,t2.sellingprice,t2.amount,t2.totalprice,t2.orderstatusid from orders t1,orderitems t2 where t1.orderid = t2.orderid and t1.username = ?";
    String sql ="select * from orderitems where orderid = ?";
    PreparedStatement ps = null;
    ResultSet rs = null;
    try{
      ps = conn.prepareStatement(sql);
      ps.setString(1,orderid);
      rs = ps.executeQuery();
      while(rs.next()){
      	OrderItem orderitem = new OrderItem();
        orderitem.setOrderid(rs.getString("orderid"));
        orderitem.setUsername(rs.getString("username"));
        orderitem.setItemdate(rs.getString("itemdate"));
        orderitem.setTitle(rs.getString("title"));
        
        orderitem.setSellingprice(rs.getString("sellingprice"));
        orderitem.setAmount(rs.getString("amount"));
        orderitem.setTotalprice(rs.getString("totalprice"));
        orderitem.setOrderstatusid(rs.getString("orderstatusid"));
        orderitems.add(orderitem);
      }
      rs.close();
      ps.close();
    }catch(Exception e){
      //System.out.println("Saving orderitem " + orderItem.getOrderid() + " to the database failed!");
      e.printStackTrace();
    }
    finally {
      //dbMan.freeConnection(poolname, conn);
    }
    return orderitems;
  }



	public List viewOrderByOrderid(String orderid){
    List list = new ArrayList();
    
    DBConnectionPoolManager dbMan = DBConnectionPoolManager.getInstance();
    //String poolname = dbMan.getPoolname();
    Connection conn = dbMan.getConnection();
    //String sql ="select t1.orderid,t1.username,t1.shipaddress,t1.shipzip,t2.itemdate,t2.title,t2.sellingprice,t2.amount,t2.totalprice,t2.orderstatusid from orders t1,orderitems t2 where t1.orderid = t2.orderid and t1.username = ?";
    String sql ="select * from orders where orderid = ?";
    PreparedStatement ps = null;
    ResultSet rs = null;
    try{
      ps = conn.prepareStatement(sql);
      ps.setString(1,orderid);
      rs = ps.executeQuery();
      while(rs.next()){
      	Order order = new Order();
        order.setOrderid(rs.getString("orderid"));
        order.setUsername(rs.getString("username"));
        order.setOrderdate(rs.getString("orderdate"));
        order.setLinenumber(rs.getString("linenumber"));
        order.setTotalprice(rs.getString("totalprice"));
        order.setShipaddress(rs.getString("shipaddress"));
        order.setShipzipcode(rs.getString("shipzip"));
        
        list.add(order);
      }
      rs.close();
      ps.close();
    }catch(Exception e){
      //System.out.println("Saving orderitem " + orderItem.getOrderid() + " to the database failed!");
      e.printStackTrace();
    }
    finally {
      //dbMan.freeConnection(poolname, conn);
    }
    return list;
  }
  
  
  public List viewOrderByUser(String username){
    List list = new ArrayList();
    
    DBConnectionPoolManager dbMan = DBConnectionPoolManager.getInstance();
    //String poolname = dbMan.getPoolname();
    Connection conn = dbMan.getConnection();
    //String sql ="select t1.orderid,t1.username,t1.shipaddress,t1.shipzip,t2.itemdate,t2.title,t2.sellingprice,t2.amount,t2.totalprice,t2.orderstatusid from orders t1,orderitems t2 where t1.orderid = t2.orderid and t1.username = ?";
    String sql ="select * from orders where username = ?";
    PreparedStatement ps = null;
    ResultSet rs = null;
    List orderitmes = null;
    try{
      ps = conn.prepareStatement(sql);
      ps.setString(1,username);
      rs = ps.executeQuery();
      while(rs.next()){
      	Order order = new Order();
      	orderitmes = this.viewOrderItemsByOrderid(rs.getString("orderid"));
      	order.setOrderitems(orderitmes);
        order.setOrderid(rs.getString("orderid"));
        order.setUsername(rs.getString("username"));
        order.setOrderdate(rs.getString("orderdate"));
        order.setLinenumber(rs.getString("linenumber"));
        order.setTotalprice(rs.getString("totalprice"));
        order.setShipaddress(rs.getString("shipaddress"));
        order.setShipzipcode(rs.getString("shipzip"));
        
        list.add(order);
      }
      rs.close();
      ps.close();
    }catch(Exception e){
      //System.out.println("Saving orderitem " + orderItem.getOrderid() + " to the database failed!");
      e.printStackTrace();
    }
    finally {
      //dbMan.freeConnection(poolname, conn);
    }
    return list;
  }
  
  
  
  public List searchByTitle(String title){
    List list = new ArrayList();
    
    DBConnectionPoolManager dbMan = DBConnectionPoolManager.getInstance();
    //String poolname = dbMan.getPoolname();
    Connection conn = dbMan.getConnection();
    //String sql ="select t1.orderid,t1.username,t1.shipaddress,t1.shipzip,t2.itemdate,t2.title,t2.sellingprice,t2.amount,t2.totalprice,t2.orderstatusid from orders t1,orderitems t2 where t1.orderid = t2.orderid and t1.username = ?";
    String sql ="select t1.title,t1.authors,t1.isbn,t1.publisher,t1.description,t1.stockdate,t1.catagoryname,"
    					+ "t2.unitcost, t2.sellingprice, t2.discount,"
    					+ "t3.quantity "
    					+ "from books t1, bookitems t2, bookinventory t3 "
    					+ "where t1.title like %?%";
    PreparedStatement ps = null;
    ResultSet booksqlrs = null;
    try{
      ps = conn.prepareStatement(sql);
      ps.setString(1,title);
      booksqlrs = ps.executeQuery();
      while(booksqlrs.next()){
      	Book theBook = new Book(booksqlrs.getString("title").trim(),
                              booksqlrs.getString("authors").trim(),
                              booksqlrs.getString("ISBN").trim(),
                              booksqlrs.getString("publisher").trim(),
                              booksqlrs.getString("description").trim(),
                              booksqlrs.getString("stockdate").toString(),
                              booksqlrs.getString("catagoryname").trim(),
                              //booksqlrs.getString("picpath").trim(),
                              booksqlrs.getString("unitcost"),
                              booksqlrs.getString("sellingprice"),
                              booksqlrs.getString("discount"),
                              booksqlrs.getString("quantity")
          );
        
        list.add(theBook);
      }
      booksqlrs.close();
      ps.close();
    }catch(Exception e){
      //System.out.println("Saving orderitem " + orderItem.getOrderid() + " to the database failed!");
      e.printStackTrace();
    }
    finally {
      //dbMan.freeConnection(poolname, conn);
    }
    return list;
  }
  
   public List getAllCatagories() {
    List list = new ArrayList();
    
    DBConnectionPoolManager dbMan = DBConnectionPoolManager.getInstance();
    //String poolname = dbMan.getPoolname();
    Connection conn = dbMan.getConnection();
    //String sql ="select t1.orderid,t1.username,t1.shipaddress,t1.shipzip,t2.itemdate,t2.title,t2.sellingprice,t2.amount,t2.totalprice,t2.orderstatusid from orders t1,orderitems t2 where t1.orderid = t2.orderid and t1.username = ?";
    String sql ="select * from catagory";
    PreparedStatement ps = null;
    ResultSet rs = null;
    try{
      ps = conn.prepareStatement(sql);
      rs = ps.executeQuery();
      while(rs.next()){
      	
        list.add(rs.getString("catagoryname"));
      }
      rs.close();
      ps.close();
    }catch(Exception e){
      //System.out.println("Saving orderitem " + orderItem.getOrderid() + " to the database failed!");
      e.printStackTrace();
    }
    finally {
      //dbMan.freeConnection(poolname, conn);
    }
    return list;
  }

}
