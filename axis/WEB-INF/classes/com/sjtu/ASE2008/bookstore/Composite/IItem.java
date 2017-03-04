/**interface
 * the composite pattern
 *
 * @author Steve Sun
 * 2008-8-8
 */
package com.sjtu.ASE2008.bookstore.Composite;

import java.util.ArrayList;
import com.sjtu.ASE2008.bookstore.domain.*;

public interface IItem{
  public boolean addItem(IItem item);
  public boolean removeItem(IItem item);
  //public int getNumber();
  public ArrayList<IItem> getChildren();
  //public ArrayList makeOrder(IItem iItem);
}
