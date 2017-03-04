/** Represents the domain entity:book.
* It also carries all the necessary attributes of certain kind of book.
*
* @author Steve Sun
* 2008-7-17
*/

package com.sjtu.ASE2008.bookstore.domain;

import java.util.Date;
//import java.sql.Blob;
import java.io.Serializable;

public class Book implements Serializable{
	private String authors;
	private String catagoryname;
	private String description;
	private String ISBN;

	/**
	How many books of this kind left in the stock.
	*/
	private String leftover = "0";
        private String unitcost;
	private String sellingprice;
        private String discount= "1";
	private String stockdate;
	private String publisher;
	private String title;
        //private String picpath;
	/**
	Tells how many copies of this book the customer would like to buy
	*/
	//private String number = "0";

	/**
	@roseuid 487C77EF038A
	default Constructor
	*/
	public Book()
	{
          //System.out.println("Book default constructor is called");
	}

	//Constructor with parameters
	public Book(String title,String authors,String ISBN,String publisher,
				String description,String stockdate,String catagoryname,
				String unitcost, String sellingprice,
                                String discount,String leftover){
		this.title = title;
		this.authors = authors;
		this.ISBN = ISBN;
		this.publisher = publisher;
		this.description = description;
		this.stockdate = stockdate;
		this.catagoryname = catagoryname;
                //this.picpath = picpath;
                this.unitcost = unitcost;
		this.sellingprice = sellingprice;
                this.discount = discount;
		this.leftover = leftover;
	}

	public void setAuthors(String authors){
		this.authors = authors;
	}

	public void setCatagoryname(String catagoryname){
		this.catagoryname = catagoryname;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public void setISBN(String ISBN){
		this.ISBN = ISBN;
	}

	public void setPublisher(String publisher){
		this.publisher = publisher;
	}

       

	public void setTitle(String title){
		this.title = title;
	}

	public void setUnitcost(String unitcost){
		this.unitcost = unitcost;
	}

	public void setLeftover(String leftover){
		this.leftover = leftover;
	}

	public void setSellingprice(String sellingprice){
		this.sellingprice = sellingprice;
	}

	public void setDiscount(String discount){
		this.discount = discount;
	}

	public void setStockdate(String stockdate){
		this.stockdate = stockdate;
	}
        /**
        Taks book title and the number of this book as parameters.
        @param title
        @param number
        @roseuid 487C60CB0222
       
        public void setNumber(String number)
        {
                //this.title = title;
                this.number = number;
        }
 */

  /**
        Returns the unit cost of this kind of book.
        @return Double
        @roseuid 487C60A001E4
        */
        public String getUnitcost()
        {
                return this.unitcost;
        }
        /**
        Returns the selling price of this kind of book.
        @return Double
        @roseuid 487C60A001E4
        */
        public String getSellingprice()
        {
                return this.sellingprice;
        }


	/**
	Returns the selling price of this kind of book.
	@return Double
	@roseuid 487C60A001E4
	*/
	public String getDiscount()
	{
		return this.discount;
	}

	/**
	Returns the all leftover of this kind of book in the stock.
	@return Integer
	@roseuid 487C60B2037A
	*/
	public String getLeftover()
	{
		return this.leftover;
	}

	/**
	Returns the number of this kind of book that the customer would like to buy.
	@return Integer
	@roseuid 487C60BE02FD
	
	public String getNumber()
	{
		return this.number;
	}
*/


	/**
	Returns the authors of this kind of book.
	@return String
	@roseuid 487C6115001F
	*/
	public String getAuthors()
	{
		return this.authors;
	}

	/**
	Returns the catagory this book belongs to.
	@return String
	@roseuid 487C61660148
	*/
	public String getCatagoryname()
	{
		return this.catagoryname;
	}

	/**
	@return String
	@roseuid 487C616D0261
	*/
	public String getDescription()
	{
		return this.description;
	}

	/**
	Returns the ISBN this book owns
	@return String
	@roseuid 487C61740271
	*/
	public String getISBN()
	{
		return this.ISBN;
	}

	/**
	Returns the publisher of this book.
	@return String
	@roseuid 487C617A003E
	*/
	public String getPublisher()
	{
		return this.publisher;
	}
       

	/**
	Returns the book title, which is unique in the database and could be regarded as
	the actual primary key.
	@return String
	@roseuid 487C61800399
	*/
	public String getTitle()
	{
		return this.title;
	}

	public String getStockdate(){
		return this.stockdate;
	}
	
/*	
	public String getPicpath()
	{
		return this.picpath;
	}

	public void setPicpath(String picpath){
		this.picpath = picpath;
	}
*/


}
