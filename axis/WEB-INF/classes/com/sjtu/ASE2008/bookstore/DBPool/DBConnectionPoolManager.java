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
  static private DBConnectionPoolManager instance; //Ψһ���ݿ����ӳع���ʵ����
  static private int clients; //�ͻ�������
  private Vector drivers = new Vector(); //������Ϣ
  private Hashtable pools = new Hashtable(); //���ӳ�
  private String poolname;

  /**
   * �õ�Ψһʵ�������࣬���Ҽ���ͻ���������
    �� singleton used here
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
   * ��������˽���Է�ֹ�������󴴽�����ʵ��
   */
  private DBConnectionPoolManager() {

    this.init();
  }

  /**
   * �ͷ�����
   * @param name
   * @param con
   */
  public void freeConnection(String name, Connection con) {
    DBConnectionPool pool = (DBConnectionPool) pools.get(name); //���ݹؼ����ֵõ����ӳ�
    if (pool != null) {
      pool.freeConnection(con); //�ͷ�����
    }
  }

  /**
   * �õ�һ�����Ӹ������ӳص�����name
   * @param name
   * @return
   */
  public Connection getConnection(String name) {
    DBConnectionPool pool = null;
    Connection con = null;
    pool = (DBConnectionPool) pools.get(name); //�������л�ȡ���ӳ�
    con = pool.getConnection(); //��ѡ�������ӳ��л������
    if (con != null) {
      System.out.println("�� getConnection(String) �õ����ӡ�����");
    }
    return con;
  }

  /**
   * �õ�һ�����ӣ��������ӳص����ֺ͵ȴ�ʱ��
   * @param name
   * @param time
   * @return
   */
  public Connection getConnection(String name, long timeout) {
    DBConnectionPool pool = null;
    Connection con = null;
    pool = (DBConnectionPool) pools.get(name); //�������л�ȡ���ӳ�
    con = pool.getConnection(timeout); //��ѡ�������ӳ��л������
    if (con != null) {
      System.out.println("�� getConnection(String, long) �õ����ӡ�����");
    }
    return con;
  }

  /**
   * �ͷ���������
   */
  public synchronized void release() {
    // �ȴ�ֱ�����һ���ͻ��������
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
   * �������ӳ�
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
    System.out.println("����������� : " + dsb.getMaxconn());
  }
  /**
   * �õ�Ψһ���ӳص�����
   *
   */
  public String getPoolname() {
    return this.poolname;
  }

  /**
   * ��ʼ�����ӳصĲ���
   */
  private void init() {
    //������������
    this.loadDrivers();
    //�������ӳ�
    Iterator alldriver = drivers.iterator();
    while (alldriver.hasNext()) {
      this.createPools( (DSConfigBean) alldriver.next());
      System.out.println("���ڴ������ӳء�����");
    }
    System.out.println("�������ӳ���ϡ�����");
  }

  /**
   * ������������
   * @param props
   */
  private void loadDrivers() {
    ParseDSConfig pd = new ParseDSConfig();
    //��ȡ���ݿ������ļ�
    drivers = pd.readConfigInfo("com/sjtu/ASE2008/bookstore/DatabaseServersConfig.xml");
    System.out.println("�ɹ�������������");
  }

  /**
   * �����������ߵĿͻ��˵�����
   �� @return clients
   */
  public int getClients() {
    return this.clients;
  }

}
