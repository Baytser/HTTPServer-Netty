/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package httpserverwithnetty;

import DBWorking.ManagerDB;

/**
 *
 * @author ike
 */
public class ServerStatus {
    private static int activeConnection = 0;
    private static long totalrequest = ManagerDB.getConnectionList().size();
    public static  void addConnection(){   
     activeConnection++;
   }
   synchronized public static  void subConnection(){   
   activeConnection--;
   }
   synchronized public static int getActiveConnection() {
        return activeConnection;
    }

    public static long getTotalrequest() {
        return totalrequest;
    }

    public static void setTotalrequest(long totalrequest) {
        ServerStatus.totalrequest = totalrequest;
    }
    public static  void addRequest(){
        totalrequest++;
    }
}
