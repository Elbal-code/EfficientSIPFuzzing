/*
 * $Id: DiamServer.java 2 2006-11-14 22:37:20Z vingarzan $
 *
 * Copyright (C) 2004-2006 FhG Fokus
 *
 * This file is part of Open IMS Core - an open source IMS CSCFs & HSS
 * implementation
 *
 * Open IMS Core is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * For a license to use the Open IMS Core software under conditions
 * other than those described here, or to purchase support for this
 * software, please contact Fraunhofer FOKUS by e-mail at the following
 * addresses:
 *     info@open-ims.org
 *
 * Open IMS Core is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * It has to be noted that this Open Source IMS Core System is not
 * intended to become or act as a product in a commercial context! Its
 * sole purpose is to provide an IMS core reference implementation for
 * IMS technology testing and IMS application prototyping for research
 * purposes, typically performed in IMS test-beds.
 *
 * Users of the Open Source IMS Core System have to be aware that IMS
 * technology may be subject of patents and licence terms, as being
 * specified within the various IMS-related IETF, ITU-T, ETSI, and 3GPP
 * standards. Thus all Open IMS Core users have to take notice of this
 * fact and have to agree to check out carefully before installing,
 * using and extending the Open Source IMS Core System, if related
 * patents and licenses may become applicable to the intended usage
 * context. 
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA  
 * 
 */
package de.fhg.fokus.hss.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;


/** 
 * This class represents the diam_servers table in the database. Hibernate
 * uses it during transaction of diameter server specific data.
 * @author Hibernate CodeGenerator 
 */
public class DiamServer implements Serializable {

    /** identifier field */
    private String server;

    /** persistent field */
    private String host;

    /** full constructor 
     * @param server Name of server
     * @param host Name of Host
     */
    public DiamServer(String server, String host) {
        this.server = server;
        this.host = host;
    }

    /** default constructor */
    public DiamServer() {
    }

   /**
    * Get method for server
    * @return server name
    */
    public String getServer() {
        return this.server;
    }

   /**
    * Set method for server
    * @param server Server name
    */
    public void setServer(String server) {
        this.server = server;
    }

   /**
    * Get method for host
    * @return host name
    */
    public String getHost() {
        return this.host;
    }

   /**
    * Set method for host
    * @param host Host name
    */
    public void setHost(String host) {
        this.host = host;
    }

   /**
    * This method converts into string 
    * @return converted string 
    */
    public String toString() {
        return new ToStringBuilder(this)
            .append("server", getServer())
            .toString();
    }

}
