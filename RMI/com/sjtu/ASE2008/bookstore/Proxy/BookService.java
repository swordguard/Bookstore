//Source file: K:\\GGGGGGGGGG\\2007-2008(2)\\ASE2008\\bookstore\\Root\\Proxy\\com\\sjtu\\ASE2008\\bookstore\\Proxy\\BookService.java

package com.sjtu.ASE2008.bookstore.Proxy;

import com.sjtu.ASE2008.bookstore.DBPool.*;

import com.sjtu.ASE2008.bookstore.domain.*;
import java.util.*;
import java.util.Iterator;
import java.util.List;
import java.text.*;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 Inplementation of interface IBookService.

 @author Steve Sun
 @version 2008-7-29
 */
public class BookService
    implements IBookService {
  private Book theBook = null;

  /**
      @roseuid 488EBC5401B5
   */
  public BookService() {

  }

  public Book getBook() {
    return (this.theBook);
  }

  /**
      @param book
      @roseuid 488EBC540242
   */
  public int addBook(Book book) {
    int result = -1;
    DBConnectionPoolManager dbMan = DBConnectionPoolManager.getInstance();
    //String poolname = dbMan.getPoolname();
    
    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date now = new Date();
		String current_date = format.format(now);
    
    Connection conn = dbMan.getConnection();
    String sql1 = "insert into books(title,AuTHORS,ISBN,PUBLISHER, DESCRIPTION, stockdate, CataGORYname) values('" +
        book.getTitle() + "','" + book.getAuthors() + "','" + book.getISBN() +
        "','" + book.getPublisher() + "','" + book.getDescription() +
        "','"+current_date+"','" + book.getCatagoryname() + "')";
        //book.getPicpath() + 
    String sql2 = "insert into bookitems(title,unitcost,sellingprice,costupdateddate,priceUpdatedDate,discount,discountUpdatedDate) values('" +
        book.getTitle() + "','" + book.getUnitcost() + "','" +
        book.getSellingprice() + "','"+current_date+"','"+current_date+"','" +
        book.getDiscount() + "','" +current_date+"')";
    String sql3 =
        "insert into bookinventory(title,quantity,updatedDate) values('" +
        book.getTitle() + "','" + book.getLeftover() + "','"+current_date+"')";
    PreparedStatement ps1 = null;
    PreparedStatement ps2 = null;
    PreparedStatement ps3 = null;
    int line1 = -1;
    int line2 = -1;
    int line3 = -1;
    /*
        if (checkTitle(book.getTitle())) {
          System.out.println("The book <" + book.getTitle() +
                             "> already exits in the database");
          System.out.println(
     "It can't be added it to the database.But you can increase the stock number");
          return;
        }
     */
    System.out.println("The book <" + book.getTitle() +
                       "> does not exist in the database, so it could be added into the database");
    try {
      ps1 = conn.prepareStatement(sql1);
      ps2 = conn.prepareStatement(sql2);
      ps3 = conn.prepareStatement(sql3);
      line1 = ps1.executeUpdate();
      line2 = ps2.executeUpdate();
      line3 = ps3.executeUpdate();
      if ( (line1 + line2 + line3) != 3) {
        ps1.close();
        ps2.close();
        ps3.close();

        return result;
      }
      result = 1;
      System.out.println("The book <" + book.getTitle() +
                         "> has been successfully added to the database!");
      ps1.close();
      ps2.close();
      ps3.close();
    }
    catch (Exception e) {
      System.out.println("Adding book to the database failed!");
      e.printStackTrace();
    }
    finally {
      //dbMan.freeConnection(poolname, conn);
    }

    /*
         ps.setString(1,book.getTitle());
         ps.setString(2,book.getAuthors());
         ps.setString(3,book.getISBN());
         ps.setString(4,book.getPublisher());
         ps.setDate(5,book.getStockdate());
         ps.setString(6,book.getCatagoryname());
         ps.setBinaryStream(7,book.getTitle());
         ps.setString(1,book.getTitle());
         ps.setString(1,book.getTitle());
         ps.setString(1,book.getTitle());
     */
    return result;
  }

  /**
      @param title
      @roseuid 488EBC55001F
   */
  public int removeBookByTitle(String title) {
    int result = -1;

    return result;
  }

  public boolean checkTitle(String title) {
    boolean exist = false;
    DBConnectionPoolManager dbMan = DBConnectionPoolManager.getInstance();
   // String poolname = dbMan.getPoolname();
    Connection conn = dbMan.getConnection();
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = "select * from books where title = '" + title + "'";
    System.out.println("Checking the title to see if it could be added!");
    try {
      ps = conn.prepareStatement(sql);
      rs = ps.executeQuery();
      if (rs.next()) {
        exist = true;
      }
      rs.close();
      ps.close();
    }
    catch (Exception e) {
      //System.out.println("Adding book to the database failed!");
      e.printStackTrace();
    }
    finally {
      //dbMan.freeConnection(poolname, conn);
    }
    return exist;
  }

  public List getAllCatagories() {
    List catagories = new ArrayList();
    DBConnectionPoolManager dbMan = DBConnectionPoolManager.getInstance();
    //String poolname = dbMan.getPoolname();
    Connection conn = dbMan.getConnection();
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = "select catagoryname from bookcatagories";
    try {
      ps = conn.prepareStatement(sql);
      rs = ps.executeQuery();
      while (rs.next()) {
        catagories.add(rs.getString("catagoryname").trim());
      }
      rs.close();
      ps.close();
    }
    catch (Exception e) {
      //System.out.println("Adding book to the database failed!");
      e.printStackTrace();
    }
    finally {
      //dbMan.freeConnection(poolname, conn);
    }
    return catagories;
  }

  /**
      @return java.util.ArrayList
      @roseuid 488EBC5501B5
   */
  public List getAllTitles() {
  	//System.out.println("In BookService.java===========getAllTitles()");
    return getLatestTitles(0);
  }

  /**
      @param number
      @return java.util.ArrayList
      @roseuid 488EBC550280
   */
  public List getLatestTitles(int number) {
  	
    List titles = new ArrayList();
    int rowsnumber;
    
    DBConnectionPoolManager dbMan = DBConnectionPoolManager.getInstance();
    
    
    //String poolname = dbMan.getPoolname();
    Connection conn = dbMan.getConnection();
    //System.out.println("In BookService.java===========dbMan" + conn);
    String titlesql = "select title from books order by stockdate desc";
    String rowsql = "select count(*) as row from books";

    PreparedStatement titlesqlps = null;
    PreparedStatement rowsqlps = null;

    ResultSet titlesqlrs = null;
    ResultSet rowsqlrs = null;

    try {
      titlesqlps = conn.prepareStatement(titlesql);
      titlesqlrs = titlesqlps.executeQuery();
      rowsqlps = conn.prepareStatement(rowsql);
        
      rowsqlrs = rowsqlps.executeQuery();
    
      rowsqlrs.next();
      rowsnumber = rowsqlrs.getInt("row");
			//System.out.println("row================" + rowsnumber);
      //if the parameter is out of range, then set the largest number
      if (number <= 0 || number > rowsnumber) {

        number = rowsnumber;
      }
      rowsqlrs.close();
      rowsqlps.close();
      for (int i = 0; i < number; i++) {
        if (titlesqlrs.next()) {
          titles.add(titlesqlrs.getString("title").trim());
          //System.out.println("title================" + getString("title").trim());
        }
      }
      titlesqlrs.close();
      titlesqlps.close();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    finally {
      //dbMan.freeConnection(poolname, conn);
    }
     
    return titles;
  }

  public List getLatestPicpath() {
    List titles = new ArrayList();
    int rowsnumber;
    //System.out.println("getLatestBooks() is being called!!!");
    DBConnectionPoolManager dbMan = DBConnectionPoolManager.getInstance();
    //String poolname = dbMan.getPoolname();
    Connection conn = dbMan.getConnection();
    String titlesql =
        "select attr1 as picpath from books";

    //PreparedStatement titlesqlps = null;
    Statement stmt = null;

    ResultSet titlesqlrs = null;

    try {
      //titlesqlps = conn.prepareStatement(titlesql);
      stmt = conn.createStatement();
     titlesqlrs = stmt.executeQuery(titlesql);
      //titlesqlrs = titlesqlps.executeQuery();

      while (titlesqlrs.next()) {
        System.out.println("======");
        titles.add(titlesqlrs.getString("picpath").trim());
      }

      titlesqlrs.close();
      //titlesqlps.close();
      stmt.close();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    finally {
      //dbMan.freeConnection(poolname, conn);
    }
    return titles;
  }
  
  
  
  
  
  public boolean doesItExist(String value){
	boolean exist = false;
    DBConnectionPoolManager dbMan = DBConnectionPoolManager.getInstance();
    //String poolname = dbMan.getPoolname();
    Connection conn = dbMan.getConnection();
    String sql = "select * from books where title="+"'" + value+ "'";
    PreparedStatement ps = null;
    ResultSet rs =null;
    try{
      ps = conn.prepareStatement(sql);
      //ps.setString(1,username);
      //ps.setString(2,password);
      rs = ps.executeQuery();
			if(rs.next()) return true;
    }catch (Exception e) {
      //System.out.println("Adding user to the database failed!");
      e.printStackTrace();
    }
    finally {
      //dbMan.closeConnection();
      //dbMan.freeConnection(poolname, conn);
    }
    //System.out.println("result=" + result);
    return exist;
	
	
}
  
  
  
  
  
  
  

  /**
      @param title
      @return java.util.ArrayList
      @roseuid 488EBC560119
   */
  public List getBookByTitle(String title) {
    List books = new ArrayList();

    DBConnectionPoolManager dbMan = DBConnectionPoolManager.getInstance();
    //String poolname = dbMan.getPoolname();
    Connection conn = dbMan.getConnection();
    String booksql = "select t1.title,t1.authors,t1.isbn,t1.publisher,t1.description, t1.stockdate, t1.catagoryname, t1.attr1 as picpath, t2.unitcost,t2.sellingprice, t2.discount, t3.quantity from books t1, bookitems t2,bookinventory t3 where t1.title = t2.title and t2.title = t3.title and t1.title = ? ";

    PreparedStatement booksqlps = null;
    ResultSet booksqlrs = null;
    
    try {
      booksqlps = conn.prepareStatement(booksql);
      booksqlps.setString(1, title);
      //System.out.println(booksql);
    
      booksqlrs = booksqlps.executeQuery();
  
      //booksqlrs.next();
      //System.out.println(booksqlrs.getString("title").trim());

      if (!booksqlrs.next()) {
        System.out.println("No such book with title [" + title+"]");
        return books;
      }

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
      books.add(theBook);
      booksqlrs.close();
      booksqlps.close();
      
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
    finally {
      //dbMan.freeConnection(poolname, conn);
    }
    
   
    return books;
  }

  /**
      @param title
      @roseuid 488EBC5603A9
   */
  public int updateBookInfoByTitle(String title) {
    int result = -1;
		
		
    return result;

  }
  
  
  
  public int updateBookInfo(Book book) {
    int result = -1;
    DBConnectionPoolManager dbMan = DBConnectionPoolManager.getInstance();
    
    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date now = new Date();
		String current_date = format.format(now);
    
    Connection conn = dbMan.getConnection();
    String sql1 = "update books set authors='" + book.getAuthors() + "',ISBN='" + book.getISBN() +
        "', publisher='" + book.getPublisher() + "', description='" + book.getDescription() +
        "',stockdate='"+current_date+"', catagoryname='" + book.getCatagoryname() + "' where title='" +book.getTitle() + "'";
    
   String sql2 = "update bookitems set unitcost='" +book.getUnitcost() + "', sellingprice='" +
        book.getSellingprice() + "', costupdateddate='"+current_date+"', priceupdateddate='"+current_date+"', discount='" +
        book.getDiscount() + "', discountupdateddate='" +current_date+"' where title='" +book.getTitle() + "'";
    /*
    String sql3 =
        "insert into bookinventory(title,quantity,udpatedDate) values('" +
        book.getTitle() + "','" + book.getLeftover() + "','"+current_date+"' where title='" +book.getTitle() + "'";
      */  
    PreparedStatement ps1 = null;
   PreparedStatement ps2 = null;
    int line1 = -1;
		int line2 = -1;
    try {
      ps1 = conn.prepareStatement(sql1);
      ps2 = conn.prepareStatement(sql2);
      //ps3 = conn.prepareStatement(sql3);
      line1 = ps1.executeUpdate();
      line2 = ps2.executeUpdate();
      //line3 = ps3.executeUpdate();
      if ( (line1 + line2 ) != 2) {
        ps1.close();
        ps2.close();
        //ps3.close();

        return result;
      }
      result = 1;
      System.out.println("The book <" + book.getTitle() +
                         "> has been successfully updated to the database!");
      ps1.close();
      ps2.close();
     // ps3.close();
    }
    catch (Exception e) {
      System.out.println("Adding book to the database failed!");
      e.printStackTrace();
    }
    finally {
      //dbMan.freeConnection(poolname, conn);
    }

    /*
         ps.setString(1,book.getTitle());
         ps.setString(2,book.getAuthors());
         ps.setString(3,book.getISBN());
         ps.setString(4,book.getPublisher());
         ps.setDate(5,book.getStockdate());
         ps.setString(6,book.getCatagoryname());
         ps.setBinaryStream(7,book.getTitle());
         ps.setString(1,book.getTitle());
         ps.setString(1,book.getTitle());
         ps.setString(1,book.getTitle());
     */
    return result;
  }
  

/* test ok with the column attr1 in table books as the attribute picpath of javabean
  public static void main(String args[]) {
    ArrayList book = new ArrayList();
    BookService service = new BookService();
    book = service.getBookByTitle("Body Building");
    //book = service.getLatestPicpath();
    /*
        for(int i=0;i<book.size();i++){
     System.out.println(book.get(i).toString());
        }


    Iterator ite = book.iterator();
    while (ite.hasNext()) {
      Book b = (Book) ite.next();
      System.out.println(b.getTitle());
      System.out.println(b.getPicpath());
      System.out.println(b.getSellingprice());
      System.out.println(b.getLeftover());
    }

  }
*/
}
