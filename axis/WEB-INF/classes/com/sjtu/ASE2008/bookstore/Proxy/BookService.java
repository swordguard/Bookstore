//Source file: K:\\GGGGGGGGGG\\2007-2008(2)\\ASE2008\\bookstore\\Root\\Proxy\\com\\sjtu\\ASE2008\\bookstore\\Proxy\\BookService.java

package com.sjtu.ASE2008.bookstore.Proxy;

import com.sjtu.ASE2008.bookstore.DBPool.*;

import com.sjtu.ASE2008.bookstore.domain.*;
import java.util.ArrayList;
import java.util.Iterator;
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
    String poolname = dbMan.getPoolname();
    Connection conn = dbMan.getConnection(poolname);
    String sql1 = "insert into books(title,AuTHORS,ISBN,PUBLISHER, DESCRIPTION, stockdate, CataGORYname,pic) values('" +
        book.getTitle() + "','" + book.getAuthors() + "','" + book.getISBN() +
        "','" + book.getPublisher() + "','" + book.getDescription() +
        "','current_date','" + book.getCatagoryname() + "','" +
        book.getPicpath() + "')";
    String sql2 = "insert into bookitems(title,unitcost,sellingprice,costupdateddate,priceUpdatedDate,discount,discountUpdatedDate) values('" +
        book.getTitle() + "','" + book.getUnitcost() + "','" +
        book.getSellingprice() + "','current_date','current_date'" +
        book.getDiscount() + "','" + "'current_date')";
    String sql3 =
        "insert into bookinventory(title,quantity,udpatedDate) values('" +
        book.getTitle() + "','" + book.getLeftover() + "','current_date')";
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
      dbMan.freeConnection(poolname, conn);
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
    String poolname = dbMan.getPoolname();
    Connection conn = dbMan.getConnection(poolname);
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
      dbMan.freeConnection(poolname, conn);
    }
    return exist;
  }

  public ArrayList getAllCatagories() {
    ArrayList catagories = new ArrayList();
    DBConnectionPoolManager dbMan = DBConnectionPoolManager.getInstance();
    String poolname = dbMan.getPoolname();
    Connection conn = dbMan.getConnection(poolname);
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
      dbMan.freeConnection(poolname, conn);
    }
    return catagories;
  }

  /**
      @return java.util.ArrayList
      @roseuid 488EBC5501B5
   */
  public ArrayList getAllTitles() {
    return getLatestTitles(0);
  }

  /**
      @param number
      @return java.util.ArrayList
      @roseuid 488EBC550280
   */
  public ArrayList getLatestTitles(int number) {
    ArrayList titles = new ArrayList();
    int rowsnumber;
    //System.out.println("getLatestBooks() is being called!!!");
    DBConnectionPoolManager dbMan = DBConnectionPoolManager.getInstance();
    String poolname = dbMan.getPoolname();
    Connection conn = dbMan.getConnection(poolname);
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

      //if the parameter is out of range, then set the largest number
      if (number <= 0 || number > rowsnumber) {

        number = rowsnumber;
      }
      rowsqlrs.close();
      rowsqlps.close();
      for (int i = 0; i < number; i++) {
        if (titlesqlrs.next()) {
          titles.add(titlesqlrs.getString("title").trim());
        }
      }
      titlesqlrs.close();
      titlesqlps.close();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    finally {
      dbMan.freeConnection(poolname, conn);
    }
    return titles;
  }

  public ArrayList getLatestPicpath() {
    ArrayList titles = new ArrayList();
    int rowsnumber;
    //System.out.println("getLatestBooks() is being called!!!");
    DBConnectionPoolManager dbMan = DBConnectionPoolManager.getInstance();
    String poolname = dbMan.getPoolname();
    Connection conn = dbMan.getConnection(poolname);
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
      dbMan.freeConnection(poolname, conn);
    }
    return titles;
  }

  /**
      @param title
      @return java.util.ArrayList
      @roseuid 488EBC560119
   */
  public ArrayList getBookByTitle(String title) {
    ArrayList books = new ArrayList();

    DBConnectionPoolManager dbMan = DBConnectionPoolManager.getInstance();
    String poolname = dbMan.getPoolname();
    Connection conn = dbMan.getConnection(poolname);
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
        System.out.println("No such book with title " + title);
        return books;
      }

      Book theBook = new Book(booksqlrs.getString("title").trim(),
                              booksqlrs.getString("authors").trim(),
                              booksqlrs.getString("ISBN").trim(),
                              booksqlrs.getString("publisher").trim(),
                              booksqlrs.getString("description").trim(),
                              booksqlrs.getDate("stockdate").toString(),
                              booksqlrs.getString("catagoryname").trim(),
                              booksqlrs.getString("picpath").trim(),
                              String.valueOf(booksqlrs.getDouble("unitcost")),
                              String.valueOf(booksqlrs.getDouble("sellingprice")),
                              String.valueOf(booksqlrs.getDouble("discount")),
                              String.valueOf(booksqlrs.getInt("quantity"))
          );
      books.add(theBook);
      booksqlrs.close();
      booksqlps.close();
      /*
           String booksql = "select * from books where title=?";
           //String rstr = "select count(*) as row from books";
           String pricesql = "select * from bookitems where title=?";
           String leftoversql = "select * from bookinventory where title = ?";

           PreparedStatement booksqlps = null;

           //PreparedStatement rp = null;
           PreparedStatement pricesqlps = null;

           PreparedStatement leftoversqlps = null;


           ResultSet booksqlrs = null;
           //ResultSet rrs = null;
           ResultSet pricesqlrs = null;
           ResultSet leftoversqlrs = null;

           try {
        booksqlps = conn.prepareStatement(booksql);
        booksqlps.setString(1, title);
        booksqlrs = booksqlps.executeQuery();
        if (!booksqlrs.next()) {
          //System.out.println("No such book with title " + title);
          return books;
        }

        pricesqlps = conn.prepareStatement(pricesql);
        pricesqlps.setString(1, title);
        pricesqlrs = pricesqlps.executeQuery();
        pricesqlrs.next();

        leftoversqlps = conn.prepareStatement(leftoversql);
        leftoversqlps.setString(1, title);
        leftoversqlrs = leftoversqlps.executeQuery();
        leftoversqlrs.next();

        Book theBook = new Book(booksqlrs.getString("title"),
                                booksqlrs.getString("authors"),
                                booksqlrs.getString("ISBN"),
                                booksqlrs.getString("publisher"),
                                booksqlrs.getString("description"),
                                booksqlrs.getDate("stockdate"),
                                booksqlrs.getString("catagoryname"),
                                booksqlrs.getString("picpath").trim(),
                                pricesqlrs.getDouble("unitcost"),
                                pricesqlrs.getDouble("sellingprice"),
                                pricesqlrs.getDouble("discount"),
                                leftoversqlrs.getInt("quantity")
            );
        books.add(theBook);

        pricesqlrs.close();
        leftoversqlrs.close();
        booksqlrs.close();
        pricesqlps.close();
        leftoversqlps.close();
        booksqlps.close();

           }
       */
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
    finally {
      dbMan.freeConnection(poolname, conn);
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
