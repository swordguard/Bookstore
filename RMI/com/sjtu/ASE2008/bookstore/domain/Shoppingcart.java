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
import java.util.*;
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
 If the customer registered in the website and he or she does not make the order
 the shoppingcart will appear next time when he or she logs in.
 */
public class Shoppingcart implements IItem, Serializable{
  private List<IItem> items = null;
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
  private String linenumber="0";

  /**
      total price of the entire items in this shopping cart
   */
  private String totalprice="0";

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
    return Integer.parseInt((this.getLinenumber()));
  }

  public List<IItem> getChildren() {
    return items;
  }
  

  public void setItems(List items) {
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

  public List getItems() {
    return (this.items);
  }

 

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

  

}
