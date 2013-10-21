/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Pages;

import DBWorking.ManagerDB;
import entitys.Connection;
import entitys.Redirects;
import entitys.Requests;
import httpserverwithnetty.ServerStatus;
import java.util.List;

/**
 *
 * @author ike
 */
public class PageStatus extends Page {
 String page = createContent();
    public String createContent() {
        StringBuilder HTTPResponse = new StringBuilder();
        HTTPResponse.append(" <h2> <p>Number of all requset: " + ServerStatus.getTotalrequest() + " </p> </h2>");
        HTTPResponse.append(" <h2> <p>Number of active connections: " + ServerStatus.getActiveConnection() + " </p> </h2>");
        HTTPResponse.append(" <h2> <p>Number of unique request : " + ManagerDB.getUbiqueRequestList().size() + " </p> </h2>");
        HTTPResponse.append(" <table border=\"3\" cellpadding=\"5\">");
        HTTPResponse.append(" <tr> <th>IP</th>  <th>Count of request</th>  <th>Time lsat request</th> </tr>");
        List<Requests> allRequest = ManagerDB.getIpRequestList();
        for (Requests request : allRequest) {
            HTTPResponse.append(" <tr> <td>" + request.getIp() + "</td>  <td>" + request.getRequestCount() + "</td>  <td>" + request.getTimeLastRequest() + "</td> </tr>");
        }
        HTTPResponse.append(" </table>");
        HTTPResponse.append("<br></br>");
        HTTPResponse.append("<br></br>");

        HTTPResponse.append(" <table border=\"3\" cellpadding=\"5\">");
        HTTPResponse.append(" <tr> <th>URL</th>  <th>Count of redirect</th>  </tr>");
        List<Redirects> allRedirect = ManagerDB.getRedirectList();
        for (Redirects request : allRedirect) {
            HTTPResponse.append(" <tr> <td>" + request.getUrl() + "</td>  <td>" + request.getAmountRedir() + "</td>  </tr>");
        }
        HTTPResponse.append(" </table>");


        HTTPResponse.append("<br></br>");
        HTTPResponse.append("<br></br>");

        HTTPResponse.append(" <table border=\"3\" cellpadding=\"5\">");
        HTTPResponse.append(" <tr> <th>src_ip</th>  <th>uri</th>  <th>timestamp</th>  <th>sent_bytes</th>  <th>received_bytes</th>   <th>speed</th>  </tr>");
        List<Connection> allConnectRequest = ManagerDB.getConnectionList();
        int first = allConnectRequest.size();
        if (first < 16) {
            first = 0;
        } else {
            first -=16;
        }
        for (int i = first; i < allConnectRequest.size(); i++) {

            Connection request = allConnectRequest.get(i);
            HTTPResponse.append(" <tr> <td>" + request.getSrcIp() + "</td>  <td>" + request.getUri() + "</td>   <td>" + request.getTimestamp() + "</td>   "
                    + "  <td>" + request.getSentBytes() + "</td>  <td>" + request.getReceivedBytes() + "</td>  <td>" + request.getSpeed() + "</td> </tr>");
        }
        HTTPResponse.append(" </table>");
        return HTTPResponse.toString();
    }

    @Override
    public String getPage() {
        return this.page;
    }
}
