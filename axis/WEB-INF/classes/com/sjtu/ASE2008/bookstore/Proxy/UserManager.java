/**class
 * provides some service about users
 *
 * @author Steve Sun
 * 2008-8-19
 */
package com.sjtu.ASE2008.bookstore.Proxy;

import com.sjtu.ASE2008.bookstore.domain.*;
import com.sjtu.ASE2008.bookstore.DBPool.*;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserManager {
  //private User user;
  public UserManager() {}

  public ArrayList getUsers() {
    ArrayList users = new ArrayList();
    User user = null;

    DBConnectionPoolManager dbMan = DBConnectionPoolManager.getInstance();
    String poolname = dbMan.getPoolname();
    Connection conn = dbMan.getConnection(poolname);
    String usersql = "select * from users";

    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      ps = conn.prepareStatement(usersql);
      //System.out.println(booksql);
      rs = ps.executeQuery();
      while (rs.next()) {
        user = new User();
        user.setUsername(rs.getString("username").trim());
        user.setFirstname(rs.getString("firstname").trim());
        user.setLastname(rs.getString("lastname").trim());
        user.setAddress(rs.getString("address").trim());
        user.setZipcode(rs.getString("zipcode").trim());
        user.setEmail(rs.getString("email").trim());
        user.setTelephone(rs.getString("telephone").trim());
        user.setBadmin(String.valueOf(rs.getInt("badmin")));
        user.setRegisterdate(String.valueOf(rs.getDate("registerdate")));
        user.setLastlogindate(String.valueOf(rs.getDate("lastlogindate")));
        user.setLastlogintime(String.valueOf(rs.getTime("lastloginTime")));
        user.setLastupdatedate(String.valueOf(rs.getDate("lastupdatedate")));
        users.add(user);
      }
      rs.close();
      ps.close();
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
    finally {
      dbMan.freeConnection(poolname, conn);
    }
    return users;
  }

  public ArrayList getUserByName(String username) {
    ArrayList userlist = new ArrayList();
    User user = null;

    DBConnectionPoolManager dbMan = DBConnectionPoolManager.getInstance();
    String poolname = dbMan.getPoolname();
    Connection conn = dbMan.getConnection(poolname);
    String usersql = "select * from users where username = ?";

    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      ps = conn.prepareStatement(usersql);
      ps.setString(1, username);
      //System.out.println(booksql);
      rs = ps.executeQuery();
      while (rs.next()) {
        user = new User();
        user.setUsername(rs.getString("username").trim());
        user.setFirstname(rs.getString("firstname").trim());
        user.setLastname(rs.getString("lastname").trim());
        user.setAddress(rs.getString("address").trim());
        user.setZipcode(rs.getString("zipcode").trim());
        user.setEmail(rs.getString("email").trim());
        user.setTelephone(rs.getString("telephone").trim());
        user.setBadmin(String.valueOf(rs.getInt("badmin")));
        user.setRegisterdate(String.valueOf(rs.getDate("registerdate")));
        user.setLastlogindate(String.valueOf(rs.getDate("lastlogindate")));
        user.setLastlogintime(String.valueOf(rs.getTime("lastlogintime")));
        user.setLastupdatedate(String.valueOf(rs.getDate("lastupdatedate")));
        userlist.add(user);
      }
      rs.close();
      ps.close();
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
    finally {
      dbMan.freeConnection(poolname, conn);
    }
    return userlist;
  }

  public int register(User user) {
    int result = -1;
    DBConnectionPoolManager dbMan = DBConnectionPoolManager.getInstance();
    String poolname = dbMan.getPoolname();
    Connection conn = dbMan.getConnection(poolname);

    String sql = "insert into users(USERNAME, PASSWORD, FIRSTNAME, LASTNAME, ADDRESS, ZIPCODE, EMAIL, TELEPHONE,badmin,registerdate,lastlogindate,lastlogintime) values(?,?,?,?,?,?,?,?,?,current_date,current_date,current_time)";

    PreparedStatement ps = null;

    int line1 = -1;

    try {
      ps = conn.prepareStatement(sql);
      ps.setString(1,user.getUsername());
      ps.setString(2,user.getPassword());
      ps.setString(3,user.getFirstname());
      ps.setString(4,user.getLastname());
      ps.setString(5,user.getAddress());
      ps.setString(6,user.getZipcode());
      ps.setString(7,user.getEmail());
      ps.setString(8,user.getTelephone());
      ps.setString(9,user.getBadmin());

      line1 = ps.executeUpdate();
      if ( line1 != 1) {
        ps.close();
        conn.rollback();
        return result;
      }
      result = 1;
      System.out.println("The user " + user.getUsername() +
                         " has been successfully added to the database!");
      ps.close();
    }
    catch (Exception e) {
      System.out.println("Adding user to the database failed!");
      e.printStackTrace();
    }
    finally {
      dbMan.freeConnection(poolname, conn);
    }
    return result;
  }

  public int login(String username, String password) {
    int result = -1;
    DBConnectionPoolManager dbMan = DBConnectionPoolManager.getInstance();
    String poolname = dbMan.getPoolname();
    Connection conn = dbMan.getConnection(poolname);
    String sql = "select * from users where username=? and password=?";
    PreparedStatement ps = null;
    ResultSet rs =null;
    try{
      ps = conn.prepareStatement(sql);
      ps.setString(1,username);
      ps.setString(2,password);
      rs = ps.executeQuery();
      while(rs.next()){
        result = 1;
        if(rs.getString("badmin").equals("1")){
          result = 2;//return system admin
        }
      }
    }catch (Exception e) {
      //System.out.println("Adding user to the database failed!");
      e.printStackTrace();
    }
    finally {
      dbMan.freeConnection(poolname, conn);
    }
    //System.out.println("result=" + result);
    return result;
  }

	public int updateDate(String column1,String column2, String table,String value) {
    int result = -1;
    DBConnectionPoolManager dbMan = DBConnectionPoolManager.getInstance();
    String poolname = dbMan.getPoolname();
    Connection conn = dbMan.getConnection(poolname);
    String sql = "update " + table + " set " + column1 + "=current_date ," + column2 + "=current_time where username = ?";
    PreparedStatement ps = null;
    //ResultSet rs =null;
    try{
      ps = conn.prepareStatement(sql);
      ps.setString(1,value);
      if(ps.executeUpdate() != 1){
      	conn.rollback();
      	return result;
      }
      result = 1;
    }catch (Exception e) {
      //System.out.println("Adding user to the database failed!");
      e.printStackTrace();
    }
    finally {
      dbMan.freeConnection(poolname, conn);
    }
    //System.out.println("result=" + result);
    return result;
  }

}
