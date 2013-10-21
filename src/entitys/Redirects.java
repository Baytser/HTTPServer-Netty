/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entitys;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ike
 */
@Entity
@Table(name = "redirects")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Redirects.findAll", query = "SELECT r FROM Redirects r"),
    @NamedQuery(name = "Redirects.findByUrl", query = "SELECT r FROM Redirects r WHERE r.url = :url"),
    @NamedQuery(name = "Redirects.findByAmountRedir", query = "SELECT r FROM Redirects r WHERE r.amountRedir = :amountRedir")})
public class Redirects implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "url")
    private String url;
    @Basic(optional = false)
    @Column(name = "amount_redir")
    private int amountRedir;

    public Redirects() {
    }

    public Redirects(String url) {
        this.url = url;
    }

    public Redirects(String url, int amountRedir) {
        this.url = url;
        this.amountRedir = amountRedir;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getAmountRedir() {
        return amountRedir;
    }

    public void setAmountRedir(int amountRedir) {
        this.amountRedir = amountRedir;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (url != null ? url.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Redirects)) {
            return false;
        }
        Redirects other = (Redirects) object;
        if ((this.url == null && other.url != null) || (this.url != null && !this.url.equals(other.url))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entitys.Redirects[ url=" + url + " ]";
    }
    
}
