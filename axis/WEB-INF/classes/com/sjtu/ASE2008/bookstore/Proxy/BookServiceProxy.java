//Source file: K:\\GGGGGGGGGG\\2007-2008(2)\\ASE2008\\bookstore\\Root\\Proxy\\com\\sjtu\\ASE2008\\bookstore\\Proxy\\BookProxy.java

package com.sjtu.ASE2008.bookstore.Proxy;

import com.sjtu.ASE2008.bookstore.domain.Book;
import java.util.ArrayList;

public class BookServiceProxy
    implements IBookService {
  private BookService bookService = null;

  /**
      @roseuid 488EBC4502AF
   */
  public BookServiceProxy() {
    bookService = new BookService();
  }

  public BookService getBookService() {
    return (this.bookService);
  }

  public void setBookService(BookService bookService) {
    this.bookService = bookService;
  }

  /**
      @param book
      @roseuid 488EBC45035B
   */
  public int addBook(Book book) {
    int result = -1;
    if((this.bookService.addBook(book)) == 1){
      result = 1;
    }
    return result;
  }
  public boolean checkTitle(String title){
    return (this.bookService.checkTitle(title));
  }
  /**
      @param title
      @roseuid 488EBC460138
   */
  public int removeBookByTitle(String title) {
    int result =-1;
    if((this.bookService.removeBookByTitle(title)) == 1){
      result = 1;
    }
    return result;
  }

  public ArrayList getAllCatagories() {
      return (this.getBookService().getAllCatagories());
  }
  /**
      @return java.util.ArrayList
      @roseuid 488EBC46034B
   */
  public ArrayList getAllTitles() {
    return (this.bookService.getAllTitles());
  }

  /**
      @param number
      @return java.util.ArrayList
      @roseuid 488EBC47004E
   */
  public ArrayList getLatestTitles(int number) {
    return (this.bookService.getLatestTitles(number));
  }

  /**
      @param title
      @return java.util.ArrayList
      @roseuid 488EBC4702AF
   */
  public ArrayList getBookByTitle(String title) {
    return (this.bookService.getBookByTitle(title));
  }

  /**
      @param title
      @roseuid 488EBC4801E4
   */
  public int updateBookInfoByTitle(String title) {
    int result =-1;
    if((this.bookService.updateBookInfoByTitle(title)) == 1){
      result = 1;
    }
    return result;
  }
}
