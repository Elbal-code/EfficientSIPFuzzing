
/*
 * $Id: UpdateCxOperation.java 154 2007-02-26 22:19:10Z adp $
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
package de.fhg.fokus.hss.server.cx.op;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.log4j.Logger;

import de.fhg.fokus.cx.CSCFOperations;
import de.fhg.fokus.cx.exceptions.DiameterException;
import de.fhg.fokus.hss.model.CxUserProfil;
import de.fhg.fokus.hss.server.cx.CSCFOperationsImpl;

/**
 * It represents cx specific update operation triggered by PPR.
 * 
 * @author Andre Charton (dev -at- open-ims dot org)
 */
public class UpdateCxOperation extends CxOperation {

	/** logger */
	private static final Logger LOGGER = Logger.getLogger(UpdateCxOperation.class);
	/** name of serving call session control function */
	private String scscfName;
	private String publicUserIdentity;
    /**
     * constructor
     * @param _userProfil user profile
     * @param _privateUserIdentity private user identity
     */    	
	public UpdateCxOperation(CxUserProfil _userProfil, String privateIdentity, String publicUserIdentity){
		LOGGER.debug("entering");
		this.userProfil = _userProfil;
		try {
			this.privateUserIdentity = new URI(privateIdentity);
			this.publicUserIdentity = publicUserIdentity;
			
		} 
		catch (URISyntaxException e) {
			e.printStackTrace();
		}
		this.scscfName = userProfil.getImpi().getScscfName();
		LOGGER.debug("exiting");
	}
	

    /**
     * it performs the update opeartion
     * @return null after performing update 
     * @throws DiameterException
     */  
	public Object execute() throws DiameterException {
		LOGGER.debug("entering");
		CSCFOperations operations = new CSCFOperationsImpl();
		operations.cxUpdateSubscriberData(privateUserIdentity, userProfil.getIMSSubscription(publicUserIdentity),
				userProfil.getChargingInfoSet(), scscfName);
		LOGGER.debug("exiting");
		return null;
	}

}
