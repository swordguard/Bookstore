//Source file: K:\\GGGGGGGGGG\\2007-2008(2)\\ASE2008\\bookstore\\Root\\Shopping\\OrderItem.java
/**domain class
 *
 * @author Steve Sun
 * 2008-8-6
 */

package com.sjtu.ASE2008.bookstore.domain;

import java.io.Serializable;

/**
 Represents an item of certain book.
 Each kind of book in the shopping cart forms one order item.
 It has several attributes, such as title,amount,totalprice and so on.
 */
public class OrderItem implements Serializable {
  /**
        Indicates which order it belongs to.
   */
  private String orderid;
  /**
     Tells whom it belongs to.
   */
  private String username;

  private String itemdate;
  /**
      the book title
   */
  private String title;

  /**
      the selling price
   */
  private String sellingprice;

  /**
      the numbers of the book of this order item
   */
  private String amount;

  /**
      the total sum of this order item
   */
  private String totalprice;
  /**
      Tells the status of this order item.
      The status types are as follows:
      1.to be processed
      2.processing
      3.on the way
      4.delivered
      5.canceled
   */
  private String orderstatusid;

  /**
      @roseuid 487C77ED038A
   */
  public OrderItem() {

  }

  public String getOrderid() {
    return (this.orderid);
  }

  /**
     Returns the username of current customer of current bookitem.
     @return String
     @roseuid 487C58B9002E
   */
  public String getUsername() {
    return (this.username);
  }

  public String getItemdate() {
    return (this.itemdate);
  }

  /**
      Returns the book title of current item.
      @return String
      @roseuid 487C588001B5
   */
  public String getTitle() {
    return (this.title);
  }

  /**
      Returns the selling price of current book item.
      @return Double
      @roseuid 487C588B01B5
   */
  public String getSellingprice() {
    return (this.sellingprice);
  }

  /**
      Returns the amount of current book item.
      @return Integer
      @roseuid 487C5894003E
   */
  public String getAmount() {
    return (this.amount);
  }

  /**
      Returns the total sum of current book item.
      @return Double
      @roseuid 487C58A00196
   */
  public String getTotalprice() {
    return (this.totalprice);
  }

  /**
      Returns the value of order status of this order item.
      @return Integer
      @roseuid 487C58C1000F
   */
  public String getOrderstatusid() {
    return (this.orderstatusid);
  }

  public void setOrderid(String orderid) {
    this.orderid = orderid;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setItemdate(String itemdate) {
    this.itemdate = itemdate;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setSellingprice(String sellingprice) {
    this.sellingprice = sellingprice;
  }

  public void setAmount(String amount) {
    this.amount = amount;
  }

  public void setTotalprice(String totalprice) {
    this.totalprice = totalprice;
  }

  /**
     Sets the value of order status of current order item through its identity
        number.
        @param orderid
        @roseuid 487C58D701B5
   */
  public void setOrderstatusid(String orderstatusid) {
    this.orderstatusid = orderstatusid;
  }

}
