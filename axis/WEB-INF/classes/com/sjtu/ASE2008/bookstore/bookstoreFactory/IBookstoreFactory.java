//Source file: K:\\GGGGGGGGGG\\2007-2008(2)\\ASE2008\\bookstore\\Root\\com\\sjtu\\ASE2008\\bookstore\\bookstoreFactory\\IBookstoreFactory.java
/**abstract class
 * abstract Factory
 * produce shoppingcarts and orders
 * @author Steve Sun
 * 2008-8-6
 */

package com.sjtu.ASE2008.bookstore.bookstoreFactory;

import com.sjtu.ASE2008.bookstore.Composite.*;

public abstract class IBookstoreFactory {
  //��̬ת����

  private static String className =
      "com.sjtu.ASE2008.bookstore.bookstoreFactory.ShoppingcartFactory";

  private static IBookstoreFactory instance = null;
  /**
   * �õ�Ψһʵ����
   * singleton
   * @return
   */

  public static synchronized IBookstoreFactory getInstance() {
    if (instance == null) {
      //��̬ת����

      try {
        Class c = Class.forName(className);
        instance = (IBookstoreFactory) c.newInstance();
      }
      catch (Exception e) {
        e.printStackTrace();
        return null;
      }

      //instance = new IBookstoreFactory();
    }
    return instance;
  }
  /**
      @roseuid 488D68610109
   */
  public abstract IItem createIItem();

}
