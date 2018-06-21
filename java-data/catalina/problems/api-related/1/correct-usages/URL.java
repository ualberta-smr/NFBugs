package org.apache.catalina.util;


import java.io.Serializable;
import java.net.MalformedURLException;

public final class URL implements Serializable {
        
        public String toString() {

                StringBuilder sb = new StringBuilder("URL[");
                sb.append("authority=");
                sb.append(authority);
                sb.append(", file=");
                sb.append(file);
                sb.append(", host=");
                sb.append(host);
                sb.append(", port=");
                sb.append(port);
                sb.append(", protocol=");
                sb.append(protocol);
                sb.append(", query=");
                sb.append(query);
                sb.append(", ref=");
                sb.append(ref);
                sb.append(", userInfo=");
                sb.append(userInfo);
                sb.append("]");
                return (sb.toString());
            }

}
