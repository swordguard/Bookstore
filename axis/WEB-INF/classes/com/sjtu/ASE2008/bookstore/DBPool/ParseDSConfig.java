/**
 * ���������ļ��� ��  д �޸� ɾ���Ȳ���
 */
package com.sjtu.ASE2008.bookstore.DBPool;

import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
import java.io.IOException;
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

  public Vector readConfigInfo(String path) {
    //String rpath=this.getClass().getResource("").getPath().substring(1)+path;
    Vector dsConfig = new Vector();
    String xmlpath = path;
    //FileInputStream fi = null;
    System.out.println("�����ļ���" + xmlpath);
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
      //System.out.println(" ��ȡ�����ļ���ϡ�");
    }
    catch (FileNotFoundException e) {
      //System.out.println("�����ļ�û�ҵ�");
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

  /**
   *�޸������ļ� ûʱ��д ����ʱ��������ȥ ��ʵһ����
   */
  /*
   public void modifyConfigInfo(String path,DSConfigBean dsb) throws Exception{
    String rpath=this.getClass().getResource("").getPath().substring(1)+path;
      FileInputStream fi=null; //����
      FileOutputStream fo=null; //д��
   }
   /**
    *���������ļ�
    *
    */
   /*
     public void addConfigInfo(String path,DSConfigBean dsb){
    String rpath=this.getClass().getResource("").getPath().substring(1)+path;
     FileInputStream fi=null;
     FileOutputStream fo=null;
     try{
       fi=new FileInputStream(rpath);//��ȡxml��
     SAXBuilder sb=new SAXBuilder();
     Document doc=sb.build(fi); //�õ�xml
       Element root=doc.getRootElement();
       List pools=root.getChildren();//�õ�xml����
     Element newpool=new Element("pool"); //���������ӳ�

       Element pooltype=new Element("type"); //�������ӳ�����
       pooltype.setText(dsb.getType());
       newpool.addContent(pooltype);

       Element poolname=new Element("name");//�������ӳ�����
       poolname.setText(dsb.getName());
       newpool.addContent(poolname);

       Element pooldriver=new Element("driver"); //�������ӳ�����
       pooldriver.addContent(dsb.getDriver());
       newpool.addContent(pooldriver);

       Element poolurl=new Element("url");//�������ӳ�url
       poolurl.setText(dsb.getUrl());
       newpool.addContent(poolurl);

       Element poolusername=new Element("username");//�������ӳ��û���
       poolusername.setText(dsb.getUsername());
       newpool.addContent(poolusername);

       Element poolpassword=new Element("password");//�������ӳ�����
       poolpassword.setText(dsb.getPassword());
       newpool.addContent(poolpassword);

       Element poolmaxconn=new Element("maxconn");//�������ӳ��������
     poolmaxconn.setText(String.valueOf(dsb.getMaxconn()));
     newpool.addContent(poolmaxconn);
       pools.add(newpool);//��child��ӵ�root
     Format format = Format.getPrettyFormat();
        format.setIndent("");
        format.setEncoding("utf-8");
        XMLOutputter outp = new XMLOutputter(format);
        fo = new FileOutputStream(rpath);
        outp.output(doc, fo);
    } catch (FileNotFoundException e) {
     // TODO Auto-generated catch block
       e.printStackTrace();
     } catch (JDOMException e) {
     // TODO Auto-generated catch block
      e.printStackTrace();
     } catch (IOException e) {
     // TODO Auto-generated catch block
      e.printStackTrace();
     }finally{

     }
     }
     /**
     *ɾ�������ļ�
     */

    /*
      public void delConfigInfo(String path,String name){
     String rpath=this.getClass().getResource("").getPath().substring(1)+path;
     FileInputStream fi = null;
     FileOutputStream fo=null;
     try {
       fi=new FileInputStream(rpath);//��ȡ·���ļ�
       SAXBuilder sb=new SAXBuilder();
       Document doc=sb.build(fi);
       Element root=doc.getRootElement();
       List pools=root.getChildren();
       Element pool=null;
       Iterator allPool=pools.iterator();
       while(allPool.hasNext()){
        pool=(Element)allPool.next();
        if(pool.getChild("name").getText().equals(name))  {
          pools.remove(pool);
          break;
       }
      }
       Format format = Format.getPrettyFormat();
       format.setIndent("");
       format.setEncoding("utf-8");
       XMLOutputter outp = new XMLOutputter(format);
       fo = new FileOutputStream(rpath);
       outp.output(doc, fo);

     } catch (FileNotFoundException e) {
     // TODO Auto-generated catch block
       e.printStackTrace();
     } catch (JDOMException e) {
     // TODO Auto-generated catch block
       e.printStackTrace();
     } catch (IOException e) {
     // TODO Auto-generated catch block
       e.printStackTrace();
     }finally{
       try {
        fi.close();
       } catch (IOException e) {
      // TODO Auto-generated catch block
        e.printStackTrace();
       }
     }
     }
     */


}
