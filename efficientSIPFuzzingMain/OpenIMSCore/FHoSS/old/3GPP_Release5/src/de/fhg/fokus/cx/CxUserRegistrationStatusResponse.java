/*
 * $Id: CxUserRegistrationStatusResponse.java 2 2006-11-14 22:37:20Z vingarzan $
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


package de.fhg.fokus.cx;

import java.util.ArrayList;

/**
 * This class represents the response message conataining the cx specific 
 * registration staus information. If UAR is called upon HSS it processes the 
 * request and saves the result in this response for further processing.
 * 
 * @author Peter Weik (pwe at fokus dot fraunhofer dot de)
 *
 */
public class CxUserRegistrationStatusResponse {
	
	/**
	 * The result code
	 */
	private int resultCode;

	/**
	 * true indicates the result code is Diameter Base Protocol Result Code
	 */
	private boolean baseResultCode;
	
	/**
	 * an object containing the capabilities of server
	 */		
	private ServerCapabilities serverCapabilities;
	
	/**
	 * the name of serving call session control function
	 */
	private String assignedSCSCFName;	
	 
	/**
	 * constructor
	 * @param resultCode
	 * @param baseResultCode 	"True" indicates a Base Protocol Result Code
	 */
	public CxUserRegistrationStatusResponse(int resultCode, boolean baseResultCode){
		this.resultCode = resultCode;
		this.baseResultCode = baseResultCode;
		serverCapabilities= new ServerCapabilities();
	}
	
	/**
	 * constructor
	 * @param resultCode the result code
	 */
	public CxUserRegistrationStatusResponse(int resultCode){
		this.resultCode = resultCode;
		this.baseResultCode = false;
		serverCapabilities= new ServerCapabilities();
	}
	
	/**
	 * Getter for result code
	 * @return Returns the resultCode.
	 */
	public int getResultCode() {
		return resultCode;
	}

	/**
     * returns boolean value which indicates whether the
     * result code is Diameter Base Protocol Code or not. If true then it is else not.
	 * @return	Whether the Result Code of the CxUserRegistrationStatusResponse is of the
	 * 			Diameter Base Protocol type. 
	 */
	public boolean resultCodeIsBase () {
		return baseResultCode;
	}

	/**
	 * Setter for the serving call session control function name
	 * @param fname The s_CSCFname to set.
	 */
	public void setAssignedSCSCFName(String fname) {
		assignedSCSCFName = fname;
	}	

	/**
	 * Getter for the serving call session control function name
	 * @return Returns the s_CSCFname.
	 */
	public String getAssignedSCSCFName() {
		return assignedSCSCFName;
	}

	/**
	 * Setter for server capabilities
	 * @param serverCapabilities the servercapabilities to be set
	 */
	public void setServerCapabilities(ServerCapabilities serverCapabilities){
		this.serverCapabilities = serverCapabilities;
	}
	/**
	 * Getter for server capabilities
	 * @return the s_CSCF capabilities.
	 */
	public ServerCapabilities getServerCapabilities() {
		return serverCapabilities;
	}
	/**
	 * It returns the amount of Information items for the Grouped AVP "Server Capabilities"
	 * @return	The amount of Information items for the Grouped AVP "Server Capabilities".
	 */
	public int getNumberOfCapabilityItems(){
		return (this.serverCapabilities.getMandatoryCapabilities().size() + this.serverCapabilities.getOptionalCapabilities().size() + this.serverCapabilities.getServerNames().size());
	}
	
	/**
	 * It returns the names of servers the user may be assigned to 
	 * @return	the server names the user may be assgined to.
	 */
	public ArrayList getServerNames(){
		return serverCapabilities.getServerNames();
	}
	
}
