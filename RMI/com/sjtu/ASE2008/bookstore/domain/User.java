/**javabean
 *
 * @author
 * 2008-8-19
 */
package com.sjtu.ASE2008.bookstore.domain;

import java.io.Serializable;

public class User implements Serializable{
	
	//private static final long serialVersionUID = 1L;
	
  private String username;
  private String password;
  private String firstname;
  private String lastname;
  private String address;
  private String zipcode;
  private String email;
  private String telephone;
  private String badmin;
  private String registerdate;
  private String lastlogindate;
  private String lastlogintime;
  private String lastupdatedate;

  public User(){}

  public String getUsername(){
    return (this.username);
  }
  public String getPassword(){
    return (this.password);
  }
  public String getFirstname(){
    return (this.firstname);
  }
  public String getLastname(){
    return (this.lastname);
  }
  public String getAddress(){
    return (this.address);
  }
  public String getZipcode(){
    return (this.zipcode);
  }
  public String getEmail(){
    return (this.email);
  }
  public String getTelephone(){
    return (this.telephone);
  }
  public String getBadmin(){
    return (this.badmin);
  }
  public String getRegisterdate(){
    return (this.registerdate);
  }
  public String getLastlogindate(){
    return (this.lastlogindate);
  }
  public String getLastlogintime(){
    return (this.lastlogintime);
  }
  public String getLastupdatedate(){
    return (this.lastupdatedate);
  }

  public void setUsername(String username){
    this.username = username;
  }
  public void setPassword(String password){
    this.password = password;
  }
  public void setFirstname(String firstname){
    this.firstname = firstname;
  }
  public void setLastname(String lastname){
    this.lastname = lastname;
  }
  public void setAddress(String address){
    this.address = address;
  }
  public void setZipcode(String zipcode){
    this.zipcode = zipcode;
  }
  public void setEmail(String email){
    this.email = email;
  }
  public void setTelephone(String telephone){
    this.telephone = telephone;
  }
  public void setBadmin(String badmin){
    this.badmin = badmin;
  }
  public void setRegisterdate(String registerdate){
    this.registerdate = registerdate;
  }
  public void setLastlogindate(String lastlogindate){
    this.lastlogindate = lastlogindate;
  }
  public void setLastlogintime(String lastlogintime){
    this.lastlogintime = lastlogintime;
  }
  public void setLastupdatedate(String lastupdatedate){
    this.lastupdatedate = lastupdatedate;
  }
}
