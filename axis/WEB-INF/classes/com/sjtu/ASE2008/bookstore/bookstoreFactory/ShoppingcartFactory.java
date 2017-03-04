//Source file: K:\\GGGGGGGGGG\\2007-2008(2)\\ASE2008\\bookstore\\Root\\com\\sjtu\\ASE2008\\bookstore\\bookstoreFactory\\ShoppingcartFactory.java
/**Factory
 * extends IBookstoreFactory
 *
 * @author Steve Sun
 * 2008-8-9
 */

package com.sjtu.ASE2008.bookstore.bookstoreFactory;

import com.sjtu.ASE2008.bookstore.domain.*;
import com.sjtu.ASE2008.bookstore.Composite.*;

public class ShoppingcartFactory
    extends IBookstoreFactory {
  /**
     * 建构函数私有以防止其它对象创建本类实例
     */

  //private ShoppingcartFactory() {}
  /**
      @return com.sjtu.ASE2008.bookstore.bookstoreFactory.IShoppingcart
      @roseuid 4897051800D8
   */
  public IItem createIItem() {
    return new Shoppingcart();
  }

  /* test ok 2008-8-6
  public static void main(String args[]){
    ShoppingcartFactory factory= (ShoppingcartFactory)IBookstoreFactory.getInstance();
    Shoppingcart cart = factory.getShoppingcart();
    cart.makeTheOrder();
  }
*/
}
