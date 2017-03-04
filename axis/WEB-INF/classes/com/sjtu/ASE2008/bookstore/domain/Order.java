//Source file: K:\\GGGGGGGGGG\\2007-2008(2)\\ASE2008\\bookstore\\Root\\Shopping\\Order.java
/**domain class
 *
 * @author Steve Sun
 * 2008-8-6
 */

package com.sjtu.ASE2008.bookstore.domain;
import java.util.ArrayList;
import java.io.Serializable;

//import com.sjtu.ASE2008.bookstore.bookstoreFactory.IOrder;

/**
 It is created after current customer transforms all the items in his or her
 shoppingcart.
 It tells the customer that those books are ordered.
 It contains several order items.Each item was one kind of book with at least one
 copy.
 In order to serialize attributes of the javabean, its all attributes are set
 to type of String.
 */
public class Order implements Serializable{
  private ArrayList orderitems = null;
  /**
        Indicates which order it belongs to.
   */
  private String orderid;
  /**
     Tells whom it belongs to.
   */
  private String username;
  /**
      Tells the date that this order is made.
   */
  private String orderdate;
  /**
      Tells how many suborders contained in this order.
      Each sort of books ordered is considered as a single suborder.
   */
  private String linenumber;

  /**
      Tells the total sum of this order, considering all the suborders.
   */
  private String totalprice;

  /**
      Tells the physical ship location.
   */
  private String shipaddress;
  private String shipzipcode;

  /**
      The types of order status are as follows:
      1.to be processed
      2.processing
      3.on the way
      4.delivered
      -1.canceled
   */
  //private Integer orderstatus;
  /**
      @roseuid 487C77EF0167
   */
  public Order() {
    orderitems = new ArrayList();
  }

  /**
      Cancels the order before it's too late.
      It equals the functionality of setOrderStatus(orderid,-1).
      @param orderid
      @param statusid
      @roseuid 483CBA2D0167

  public void cancelTheOrder(String orderid, Integer statusid) {

  }

      Sets the status of this order.
      1.to be processed
      2.processing
      3.on the way
      4.delivered
      -1.canceled
      @param orderid
      @param statusid - Shows the detailed status of this order.
      The map relations between the number and the status is as follows:
      1.to be processed
      2.processing
      3.on the way
      4.delivered
      -1.canceled
      @roseuid 483CBA560232

     public void setOrderstatus(String orderid, Integer statusid)
     {

     }
   */

  public ArrayList getOrderitems(){
    return (this.orderitems);
  }
  public String getOrderid(){
    return (this.orderid);
  }
  /**
      Returns the username of current customer.
      @return String
      @roseuid 487C58490290
   */
  public String getUsername() {
    return (this.username);
  }
  /**
        Returns the date when current order is made.
        @return Date
        @roseuid 487C57F5030D
     */
    public String getOrderdate() {
      return (this.orderdate);
  }
  /**
      Returns the numbers of suborder item of current order.
      @return Integer
      @roseuid 487C58080213
   */
  public String getLinenumber() {
    return (this.linenumber);
  }

  /** Returns the total sum of this order.
      @return Double
      @roseuid 487C53EF02DE
   */
  public String getTotalprice() {
    return (this.totalprice);
  }
  /**
        Returns the address where the cargo is going to be shipped.
        @return String
        @roseuid 487C581F0148
     */
    public String getShipaddress() {
      return (this.shipaddress);
    }

    /**
        Returns the zipcode of destination.
        @return String
        @roseuid 487C582B03D8
     */
    public String getShipzipcode() {
      return (this.shipzipcode);
  }

  public void setOrderitems(ArrayList orderitems){
    this.orderitems = orderitems;
  }
  public void setOrderid(String orderid){
    this.orderid = orderid;
  }
  public void setUsername(String username){
    this.username = username;
  }
  public void setOrderdate(String orderdate){
    this.orderdate = orderdate;
  }
  public void setLinenumber(String linenumber){
    this.linenumber = linenumber;
  }
  public void setTotalprice(String totalprice){
    this.totalprice = totalprice;
  }
  public void setShipaddress(String shipaddress){
    this.shipaddress = shipaddress;
  }
  public void setShipzipcode(String shipzipcode){
    this.shipzipcode = shipzipcode;
  }






}
