/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author ike
 */
public class JPUtil {
     private static JPUtil instance;

    public static JPUtil getInstance() {
        if (instance == null) {
            instance = new JPUtil();
        }
        return instance;
    }
    
    private EntityManagerFactory emf;

    private JPUtil() {
        emf = Persistence.createEntityManagerFactory("HttpServerPU");
    }
    
    public EntityManager getManager() {
        return emf.createEntityManager();
    }
    
    public void closeFactory() {
        emf.close();
    }
} 

