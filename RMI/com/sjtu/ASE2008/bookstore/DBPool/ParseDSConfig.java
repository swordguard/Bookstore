/**
 * ���������ļ��� ��  д �޸� ɾ���Ȳ���
 */
package com.sjtu.ASE2008.bookstore.DBPool;

import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
import java.io.*;
//import java.io.InputStream;
import java.util.List;
import java.util.Vector;
import java.util.Iterator;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

//import org.jdom.output.Format;
//import org.jdom.output.XMLOutputter;

/**
 * ��ȡxml�����ļ�
 * @param path
 * @return
 */
public class ParseDSConfig {


	public ParseDSConfig(){
		super();
		System.out.println("ParseDSConfig() is being called!!!");
	}
	
	
	
  public Vector readConfigInfo(String path) {
  	File dot = new File(".");
		String filename = null;
		
		try {
			filename = dot.getCanonicalFile() + File.separator + path;
			//System.out.println(
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  	//System.out.println(" readConfigInfo().");
    //String rpath=this.getClass().getResource("").getPath().substring(1)+path;
    Vector dsConfig = new Vector();
    String xmlpath = path;
    //FileInputStream fi = null;
    System.out.println("----------------------------�����ļ���" + filename);
    xmlpath = filename;
    SAXBuilder sb = new SAXBuilder(false);
    try {
      //fi=new FileInputStream(rpath);//��ȡ·���ļ�
      Document doc = sb.build(xmlpath);

      Element root = doc.getRootElement();

      List pools = root.getChildren();
      Element pool = null;
      Iterator allPool = pools.iterator();
      //System.out.println(" ��ʼ��ȡ�����ļ�...");
      while (allPool.hasNext()) {
        pool = (Element) allPool.next();
        DSConfigBean dscBean = new DSConfigBean();
        dscBean.setType(pool.getChildTextTrim("type"));
        dscBean.setName(pool.getChildTextTrim("name"));
        System.out.println(" ���ӳ����� : " + dscBean.getName());
        dscBean.setDriver(pool.getChildTextTrim("driver"));
        dscBean.setUrl(pool.getChildTextTrim("url"));
        dscBean.setUsername(pool.getChildTextTrim("username"));
        dscBean.setPassword(pool.getChildTextTrim("password"));
        dscBean.setMaxconn(Integer.parseInt(pool.getChildTextTrim("maxconn")));
        dsConfig.add(dscBean);
      }
      System.out.println(" ��ȡ�����ļ���ϡ�");
    }
    catch (FileNotFoundException e) {
      System.out.println("�����ļ�û�ҵ�");
      e.printStackTrace();
    }
    catch (JDOMException e) {
      e.printStackTrace();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    return dsConfig;
  }


}
