/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entitys;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ike
 */
@Entity
@Table(name = "requests")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Requests.findAll", query = "SELECT r FROM Requests r"),
    @NamedQuery(name = "Requests.findById", query = "SELECT r FROM Requests r WHERE r.id = :id"),
    @NamedQuery(name = "Requests.findByIp", query = "SELECT r FROM Requests r WHERE r.ip = :ip"),
    @NamedQuery(name = "Requests.findByRequestCount", query = "SELECT r FROM Requests r WHERE r.requestCount = :requestCount"),
    @NamedQuery(name = "Requests.findByTimeLastRequest", query = "SELECT r FROM Requests r WHERE r.timeLastRequest = :timeLastRequest")})
public class Requests implements Serializable {
    @Basic(optional = false)
    @Column(name = "time_last_request")
   
    private String timeLastRequest;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "ip")
    private String ip;
    @Basic(optional = false)
    @Column(name = "request_count")
    private int requestCount;

    public Requests() {
    }

    public Requests(Integer id) {
        this.id = id;
    }

    public Requests(String ip, int requestCount, String timeLastRequest) {
        this.ip = ip;
        this.requestCount = requestCount;
        this.timeLastRequest = timeLastRequest;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getRequestCount() {
        return requestCount;
    }

    public void setRequestCount(int requestCount) {
        this.requestCount = requestCount;
    }

    public String getTimeLastRequest() {
        return timeLastRequest;
    }

    public void setTimeLastRequest(String timeLastRequest) {
        this.timeLastRequest = timeLastRequest;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Requests)) {
            return false;
        }
        Requests other = (Requests) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entitys.Requests[ id=" + id + " ]";
    }
}
