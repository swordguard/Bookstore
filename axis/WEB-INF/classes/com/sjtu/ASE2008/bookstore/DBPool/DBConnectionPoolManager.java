/*
 *It is the class to manager connection pools
 *It creates connection pools first
 *then allocates each of the connection for the each client
 *It takes it back when the connection is free
 *The class uses singleton Design pattern
 *
 *@author steve sun
 *2008-7-14
 */
package com.sjtu.ASE2008.bookstore.DBPool;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;
import com.sjtu.ASE2008.bookstore.*;



public class DBConnectionPoolManager {
  static private DBConnectionPoolManager instance; //唯一数据库连接池管理实例类
  static private int clients; //客户连接数
  private Vector drivers = new Vector(); //驱动信息
  private Hashtable pools = new Hashtable(); //连接池
  private String poolname;

  /**
   * 得到唯一实例管理类，并且计算客户端数量。
    × singleton used here
   * @return
   */
  static synchronized public DBConnectionPoolManager getInstance() {
    if (instance == null) {
      instance = new DBConnectionPoolManager();
    }
    clients++;
    return instance;
  }

  /**
   * 建构函数私有以防止其它对象创建本类实例
   */
  private DBConnectionPoolManager() {

    this.init();
  }

  /**
   * 释放连接
   * @param name
   * @param con
   */
  public void freeConnection(String name, Connection con) {
    DBConnectionPool pool = (DBConnectionPool) pools.get(name); //根据关键名字得到连接池
    if (pool != null) {
      pool.freeConnection(con); //释放连接
    }
  }

  /**
   * 得到一个连接根据连接池的名字name
   * @param name
   * @return
   */
  public Connection getConnection(String name) {
    DBConnectionPool pool = null;
    Connection con = null;
    pool = (DBConnectionPool) pools.get(name); //从名字中获取连接池
    con = pool.getConnection(); //从选定的连接池中获得连接
    if (con != null) {
      System.out.println("从 getConnection(String) 得到连接。。。");
    }
    return con;
  }

  /**
   * 得到一个连接，根据连接池的名字和等待时间
   * @param name
   * @param time
   * @return
   */
  public Connection getConnection(String name, long timeout) {
    DBConnectionPool pool = null;
    Connection con = null;
    pool = (DBConnectionPool) pools.get(name); //从名字中获取连接池
    con = pool.getConnection(timeout); //从选定的连接池中获得连接
    if (con != null) {
      System.out.println("从 getConnection(String, long) 得到连接。。。");
    }
    return con;
  }

  /**
   * 释放所有连接
   */
  public synchronized void release() {
    // 等待直到最后一个客户程序调用
    if (--clients != 0) {
      return;
    }
    Enumeration allpools = pools.elements();
    while (allpools.hasMoreElements()) {
      DBConnectionPool pool = (DBConnectionPool) allpools.nextElement();
      if (pool != null) {
        pool.release();
      }
    }
    pools.clear();
  }

  /**
   * 创建连接池
   * @param props
   */
  private void createPools(DSConfigBean dsb) {
    DBConnectionPool dbpool = new DBConnectionPool();
    this.poolname = dsb.getName();
    dbpool.setName(dsb.getName());
    dbpool.setDriver(dsb.getDriver());
    dbpool.setUrl(dsb.getUrl());
    dbpool.setUser(dsb.getUsername());
    dbpool.setPassword(dsb.getPassword());
    dbpool.setMaxConn(dsb.getMaxconn());
    pools.put(dsb.getName(), dbpool);
    System.out.println("最大连接数是 : " + dsb.getMaxconn());
  }
  /**
   * 得到唯一连接池的名字
   *
   */
  public String getPoolname() {
    return this.poolname;
  }

  /**
   * 初始化连接池的参数
   */
  private void init() {
    //加载驱动程序
    this.loadDrivers();
    //创建连接池
    Iterator alldriver = drivers.iterator();
    while (alldriver.hasNext()) {
      this.createPools( (DSConfigBean) alldriver.next());
      System.out.println("正在创建连接池。。。");
    }
    System.out.println("创建连接池完毕。。。");
  }

  /**
   * 加载驱动程序
   * @param props
   */
  private void loadDrivers() {
    ParseDSConfig pd = new ParseDSConfig();
    //读取数据库配置文件
    drivers = pd.readConfigInfo("com/sjtu/ASE2008/bookstore/DatabaseServersConfig.xml");
    System.out.println("成功加载驱动程序");
  }

  /**
   * 返回所有在线的客户端的数量
   × @return clients
   */
  public int getClients() {
    return this.clients;
  }

}
