/**This is the facade class for the invocation of web service
 *
 * @author Steve Sun
 * 2008-7-30
 * update 2008-7-31
 */

package com.sjtu.ASE2008.bookstore.Facade;

import com.sjtu.ASE2008.bookstore.*;
import com.sjtu.ASE2008.bookstore.Proxy.*;
import com.sjtu.ASE2008.bookstore.domain.*;
import com.sjtu.ASE2008.bookstore.bookstoreFactory.*;
import com.sjtu.ASE2008.bookstore.Composite.*;

import java.util.ArrayList;
import java.util.Iterator;

public class BookstoreFacadeService {
 
  public BookstoreFacadeService() {}
  



public String sayHello(String name){
	System.out.println("hello "+ name);
	return "hello " +name;
}


	public static void main(String args[]){
		
		System.out.println("=========web service==========");
	}
	
}
