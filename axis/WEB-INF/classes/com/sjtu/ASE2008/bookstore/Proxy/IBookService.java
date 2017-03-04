//Source file: K:\\GGGGGGGGGG\\2007-2008(2)\\ASE2008\\bookstore\\Root\\Proxy\\com\\sjtu\\ASE2008\\bookstore\\Proxy\\IBookService.java

package com.sjtu.ASE2008.bookstore.Proxy;

import com.sjtu.ASE2008.bookstore.domain.Book;
import java.util.ArrayList;

/**
 This is an interface.
 It deals with all the request operations about books management.

 @author Steve Sun
 @version 2008-7-29
 */
public interface IBookService {

  /**
      Adds a new book to the database.

      @param book
      @roseuid 488E8D3C00FA
   */
  public int addBook(Book book);

  /**
      removes a book from the database by its title.

      @param title
      @roseuid 488E8D4E02DE
   */
  public int removeBookByTitle(String title);

  /**
     gets all the Catagories of books in the bookstore.
     @return java.util.ArrayList
     @roseuid 488E8D5A038A
   */
  public ArrayList getAllCatagories();

  /**
      gets the titles of all the books in the bookstore.
      @return java.util.ArrayList
      @roseuid 488E8D5A038A
   */
  public ArrayList getAllTitles();

  /**
      gets serveral book titles from database if required

      @param number
      @return java.util.ArrayList
      @roseuid 488E8D6D0251
   */
  public ArrayList getLatestTitles(int number);

  /**
      gets books by their titles
      @param title
      @return java.util.ArrayList
      @roseuid 488E8D7C007D
   */
  public ArrayList getBookByTitle(String title);

  /**
   updates the specified book's attributes, such as price,stock, description,
      discount and so on, according to its title

      @param title
      @roseuid 488E8E4D0213
   */
  public int updateBookInfoByTitle(String title);
}
