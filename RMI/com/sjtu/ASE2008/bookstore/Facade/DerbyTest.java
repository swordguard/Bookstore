package com.sjtu.ASE2008.bookstore.Facade;


import java.util.*;
import com.sjtu.ASE2008.bookstore.DBPool.*;

public class DerbyTest{
	
	
	public static void main(String args[]){
		
		ParseDSConfig ds = new ParseDSConfig();
		
		List title = new ArrayList();
		BookstoreFacadeService facade = new BookstoreFacadeService();
		title = facade.viewUsers();
		///int r = facade.login(username,password);
		System.out.println(title.size());
	}
	
}