package entitys;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2013-10-21T23:30:35")
@StaticMetamodel(Requests.class)
public class Requests_ { 

    public static volatile SingularAttribute<Requests, Integer> id;
    public static volatile SingularAttribute<Requests, String> timeLastRequest;
    public static volatile SingularAttribute<Requests, Integer> requestCount;
    public static volatile SingularAttribute<Requests, String> ip;

}