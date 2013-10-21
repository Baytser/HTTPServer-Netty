package entitys;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2013-10-21T23:30:35")
@StaticMetamodel(Connection.class)
public class Connection_ { 

    public static volatile SingularAttribute<Connection, Integer> id;
    public static volatile SingularAttribute<Connection, String> timestamp;
    public static volatile SingularAttribute<Connection, Long> sentBytes;
    public static volatile SingularAttribute<Connection, Long> receivedBytes;
    public static volatile SingularAttribute<Connection, Long> speed;
    public static volatile SingularAttribute<Connection, String> uri;
    public static volatile SingularAttribute<Connection, String> srcIp;

}