/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DBWorking;

import entitys.Connection;
import entitys.Redirects;
import entitys.Requests;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import utils.JPUtil;

/**
 *
 * @author ike
 */
public class ManagerDB {
  
   synchronized public static void addOrUpdateRequestAmount(String ip, String timestamp){
        Requests req;
        EntityManager manager = JPUtil.getInstance().getManager();
        manager.getTransaction().begin();
        try {
              req  = manager.createNamedQuery("Requests.findByIp", Requests.class).setParameter("ip", ip).getSingleResult();
              req.setRequestCount(req.getRequestCount()+1);
              req.setTimeLastRequest(timestamp);
              manager.merge(req);
            } catch (NoResultException ex) {
                req = new Requests(ip, 1, timestamp);
                manager.persist(req);
            }
        manager.getTransaction().commit();
    }
    
   synchronized public static void insertConnection(String src_ip, String uri,String timestamp, long sentBytes, long receivedBytes, long speed){
           EntityManager manager = JPUtil.getInstance().getManager();
           manager.getTransaction().begin();
           manager.persist(new Connection(src_ip, uri, timestamp, sentBytes, receivedBytes, speed));
           manager.getTransaction().commit();
     }
     
     public static void insertOrUpdateURLRedirect(String url){
         Redirects redURL;
         EntityManager manager = JPUtil.getInstance().getManager();
        manager.getTransaction().begin();
        try {
              redURL  = manager.createNamedQuery("Redirects.findByUrl", Redirects.class).setParameter("url", url).getSingleResult();
              redURL.setAmountRedir(redURL.getAmountRedir()+1);
              manager.merge(redURL);
            } catch (NoResultException ex) {
                redURL = new Redirects(url, 1);
                manager.persist(redURL);
            }
        
        manager.getTransaction().commit();
     }
      public static  List<Connection> getConnectionList(){
         EntityManager manager = JPUtil.getInstance().getManager();
         List<Connection> connectList = manager.createNamedQuery("Connection.findAll", Connection.class).getResultList();
         return connectList;
      }
      public static  List<Requests> getUbiqueRequestList(){
         EntityManager manager = JPUtil.getInstance().getManager();
         List<Requests> uniqRequest = manager.createNamedQuery("Requests.findByRequestCount", Requests.class).setParameter("requestCount", 1).getResultList();
         return uniqRequest;
      }
       public static List<Redirects> getRedirectList(){
         EntityManager manager = JPUtil.getInstance().getManager();
         List<Redirects> allRedir = manager.createNamedQuery("Redirects.findAll", Redirects.class).getResultList();
         return allRedir;
       }
        public static  List<Requests> getIpRequestList(){
         EntityManager manager = JPUtil.getInstance().getManager();
         List<Requests> requestList = manager.createNamedQuery("Requests.findAll", Requests.class).getResultList();
         return requestList;
      }
}
