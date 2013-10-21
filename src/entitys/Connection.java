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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "connection")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Connection.findAll", query = "SELECT c FROM Connection c"),
    @NamedQuery(name = "Connection.findById", query = "SELECT c FROM Connection c WHERE c.id = :id"),
    @NamedQuery(name = "Connection.findBySrcIp", query = "SELECT c FROM Connection c WHERE c.srcIp = :srcIp"),
    @NamedQuery(name = "Connection.findByUri", query = "SELECT c FROM Connection c WHERE c.uri = :uri"),
    @NamedQuery(name = "Connection.findByTimestamp", query = "SELECT c FROM Connection c WHERE c.timestamp = :timestamp"),
    @NamedQuery(name = "Connection.findBySentBytes", query = "SELECT c FROM Connection c WHERE c.sentBytes = :sentBytes"),
    @NamedQuery(name = "Connection.findByReceivedBytes", query = "SELECT c FROM Connection c WHERE c.receivedBytes = :receivedBytes"),
    @NamedQuery(name = "Connection.findBySpeed", query = "SELECT c FROM Connection c WHERE c.speed = :speed")})
public class Connection implements Serializable {
    @Basic(optional = false)
    @Column(name = "sent_bytes")
    private long sentBytes;
    @Basic(optional = false)
    @Column(name = "received_bytes")
    private long receivedBytes;
    @Basic(optional = false)
    @Column(name = "speed")
    private long speed;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "src_ip")
    private String srcIp;
    @Basic(optional = false)
    @Column(name = "uri")
    private String uri;
    @Basic(optional = false)
    @Column(name = "timestamp")
    
    private String timestamp;

    public Connection() {
    }

    public Connection(Integer id) {
        this.id = id;
    }

    public Connection(String srcIp, String uri, String timestamp, long sentBytes, long receivedBytes, long speed) {
        this.srcIp = srcIp;
        this.uri = uri;
        this.timestamp = timestamp;
        this.sentBytes = sentBytes;
        this.receivedBytes = receivedBytes;
        this.speed = speed;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSrcIp() {
        return srcIp;
    }

    public void setSrcIp(String srcIp) {
        this.srcIp = srcIp;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public long getSentBytes() {
        return sentBytes;
    }

    public void setSentBytes(int sentBytes) {
        this.sentBytes = sentBytes;
    }

    public long getReceivedBytes() {
        return receivedBytes;
    }

    public void setReceivedBytes(int receivedBytes) {
        this.receivedBytes = receivedBytes;
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
        if (!(object instanceof Connection)) {
            return false;
        }
        Connection other = (Connection) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entitys.Connection[ id=" + id + " ]";
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public long getSpeed() {
        return speed;
    }
    
}
