/**
 * ���ݿ����ӳ�
 * It really deals with derby database whose driver and connection arguments are
 * specified in the DatabaseServersConfig.xml file.
 *
 * @author steve sun
 * 2008-7-14
 */
package com.sjtu.ASE2008.bookstore.DBPool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.*;

public class DBConnectionPool
    implements TimerListener {

  private Connection con = null;
  private int inUsed = 0; //
  private ArrayList freeConnections = new ArrayList(); 
  private int minConn; //
  private int maxConn; //
  private String name; //
  private String password; //
  private String url; //
  private String driver; //
  private String user; //
  public Timer timer; //

  public DBConnectionPool() {
    // TODO Auto-generated constructor stub
  }

  /**
   * �������ӳ�
   * @param driver
   * @param name
   * @param URL
   * @param user
   * @param password
   * @param maxConn
   */
  public DBConnectionPool(String name, String driver, String URL, String user,
                          String password, int maxConn) {
    this.name = name;
    this.driver = driver;
    this.url = URL;
    this.user = user;
    this.password = password;
    this.maxConn = maxConn;
  }

  /**
   * ���꣬�ͷ����ӣ���֪ͨ�����߳�
   * @param con
   */
  public synchronized void freeConnection(Connection con) {
    this.freeConnections.add(con); 
    this.inUsed--;
    notifyAll();
    System.out.println("�ͷ����ӣ������Ѿ�֪ͨ�����̡߳�");
    System.out.println("����" + inUsed + "��������ʹ����!");
  }

  /**
   * �����ӳػ��һ����������.��û�п��е������ҵ�ǰ������С��������� ������,�򴴽�������.
   * ��ԭ���Ǽ�Ϊ���õ����Ӳ�����Ч,�������ɾ��֮,
   * Ȼ��ݹ�����Լ��Գ����µĿ�������.
   */

  public synchronized Connection getConnection() {
    Connection con = null;
    if (this.freeConnections.size() > 0) { //freeConnections is ArrayList
      con = (Connection)this.freeConnections.get(0); // ��ȡ�����е�һ����������
      this.freeConnections.remove(0); //
      if (con == null) {
        con = getConnection(); //�����������
      }
    }
    else {
      con = newConnection(); //û�п��е��������½�����
    }
    if (this.maxConn == 0 || this.maxConn < this.inUsed) {
      con = null; //�ȴ� �����������ʱ
    }
    if (con != null) {
      this.inUsed++;
      System.out.println("�õ���" + this.name + "����һ�����ӣ�����" + inUsed + "��������ʹ��!");
    }
    return con;
  }

  /**
   * �����ӳػ�ȡ��������.����ָ���ͻ������ܹ��ȴ����ʱ�� �μ�ǰһ��getConnection()����.
   * @param timeout�Ժ���Ƶĵȴ�ʱ������
   */
  public synchronized Connection getConnection(long timeout) {
    long startTime = new Date().getTime();
    Connection con = null;
    while ( (con = getConnection()) == null) {
      try {
        wait(timeout);
      }
      catch (InterruptedException e) {
        e.printStackTrace();
      }
      if ( (new Date().getTime() - startTime) >= timeout) { // wait()���ص�ԭ���ǳ�ʱ
        System.out.println("��ȡ���ӳ�ʱ��");
        return null;
      }
    }

    if (con != null) {
      this.inUsed++;
      System.out.println("�õ���" + this.name + "����һ�����ӣ�����" + inUsed + "��������ʹ��!");
    }
    return con;
  }

  /*
      if(this.freeConnections.size()>0){
      con=(Connection)this.freeConnections.get(0);
      this.freeConnections.remove(0);//������ӷ����ȥ�ˣ��ʹӿ���������ɾ
      if(con==null){
      con=getConnection(timeout); //�����������
      }
      }else{
      con=newConnection(); //�½�����
      }
      if(this.maxConn==0||this.maxConn<this.inUsed){
      con = null;//�ﵽ�������������ʱ���ܻ�������ˡ�
      }
      if(con!=null){
      this.inUsed++;
      }
      return con;
      }
   */

  /**
   *�ͷ�ȫ������
   *
   */
  public synchronized void release() {
    Iterator allConns = this.freeConnections.iterator();
    while (allConns.hasNext()) {
      Connection con = (Connection) allConns.next();
      try {
        con.close();
        System.out.println("�ر����ӳ�" + name + "�е�һ������");
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
    this.freeConnections.clear();
  }

  /**
   * ����������
   * @return Connection
   */
  private Connection newConnection() {
    try {
      Class.forName(driver);
      con = DriverManager.getConnection(url, user, password);
      System.out.println("���ӳ�" + name + "����һ��������");
    }
    catch (ClassNotFoundException e) {
      e.printStackTrace();
      System.out.println("sorry can't find db driver!");
      return null;
    }
    catch (SQLException e1) {
      e1.printStackTrace();
      System.out.println("sorry can't create Connection!");
      return null;
    }
    return con;
  }

  /**
   * ��ʱ������
   */
  public synchronized void TimerEvent() {
    //��ʱ��û��ʵ���Ժ����ϵ�
  }

  /**
   * @return the driver
   */
  public String getDriver() {
    return driver;
  }

  /**
   * @param driver the driver to set
   */
  public void setDriver(String driver) {
    this.driver = driver;
  }

  /**
   * @return the maxConn
   */
  public int getMaxConn() {
    return maxConn;
  }

  /**
   * @param maxConn the maxConn to set
   */
  public void setMaxConn(int maxConn) {
    this.maxConn = maxConn;
  }

  /**
   * @return the minConn
   */
  public int getMinConn() {
    return minConn;
  }

  /**
   * @param minConn the minConn to set
   */
  public void setMinConn(int minConn) {
    this.minConn = minConn;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return the password
   */
  public String getPassword() {
    return password;
  }

  /**
   * @param password the password to set
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * @return the url
   */
  public String getUrl() {
    return url;
  }

  /**
   * @param url the url to set
   */
  public void setUrl(String url) {
    this.url = url;
  }

  /**
   * @return the user
   */
  public String getUser() {
    return user;
  }

  /**
   * @param user the user to set
   */
  public void setUser(String user) {
    this.user = user;
  }
}