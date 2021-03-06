/*
 * $Id: MARCommandListener.java 119 2007-02-06 16:48:35Z adp $
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
package de.fhg.fokus.hss.diam.cx;

import java.net.URI;
import java.util.Iterator;

import org.apache.log4j.Logger;

import de.fhg.fokus.cx.AuthenticationVector;
import de.fhg.fokus.cx.CxAuthDataResponse;
import de.fhg.fokus.cx.HSScxOperations;
import de.fhg.fokus.cx.datatypes.PublicIdentity;
import de.fhg.fokus.cx.exceptions.DiameterException;
import de.fhg.fokus.cx.exceptions.base.InvalidAvpValue;
import de.fhg.fokus.cx.exceptions.base.UnableToComply;
import de.fhg.fokus.diameter.DiameterPeer.DiameterPeer;
import de.fhg.fokus.diameter.DiameterPeer.data.AVP;
import de.fhg.fokus.diameter.DiameterPeer.data.AVPDecodeException;
import de.fhg.fokus.diameter.DiameterPeer.data.DiameterMessage;
import de.fhg.fokus.hss.diam.Constants;
import de.fhg.fokus.hss.diam.Constants.Vendor;


/**
 * Implementation of a Multimedia Authentication Request/Answer - Command (MAR/MAA).
 * For more imformations about MAR please refer to 3GPP TS 29.229. It contains a method 
 * recvMessage which processes the request after making sure that it is a MAR command 
 * and ultimately sends the  response to the communicating peer.
 *
 * @author Andre Charton (dev -at- open-ims dot org)
 */
public class MARCommandListener extends CxCommandListener{
    /** the counter to count calls */
    public static long counter = 0;
    /** the Logger */
    private static final Logger LOGGER =
        Logger.getLogger(MARCommandListener.class);
    /** An object representing Cx Operations of HSS */
    private HSScxOperations operations;

    /**
     * constructor
     * @param _operations HssCxOpeation
     * @param _diameterPeer diameterPeer
     */    
    public MARCommandListener(
        HSScxOperations _operations, DiameterPeer _diameterPeer){
        super(_diameterPeer);
        this.operations = _operations;
    }
    /**
     * This method recieves and processes the diameter message MAR
     * @param FQDN fully qualified domain name
     * @param requestMessage the diameter message
     * @see de.fhg.fokus.diameter.DiameterPeer.EventListener#recvMessage(java.lang.String, de.fhg.fokus.diameter.DiameterPeer.data.DiameterMessage)
     */
    public void recvMessage(String FQDN, DiameterMessage requestMessage){
    	
        if (requestMessage.commandCode == Constants.Command.MAR){
            counter++;
            LOGGER.debug("entering");
            LOGGER.debug(FQDN);

            try {
                AVP resultCode = null;
                PublicIdentity publicIdentity = loadPublicIdentity(requestMessage);
                    
                LOGGER.info("MAR of User: "+ publicIdentity.getIdentity() + " is being processed");      
                    
                URI privateUserIdentity = loadPrivateUserIdentity(requestMessage);
                Long numberOfAuthVectors = loadNumberOfAuthVectors(requestMessage);
                AuthenticationVector authenticationVector = loadAuthVector(requestMessage);

                String scscfName = new String(avpLookUp(requestMessage, Constants.AVPCode.CX_SERVER_NAME, true, Constants.Vendor.V3GPP).data);
                CxAuthDataResponse authDataResponse = null;
                authDataResponse = operations.cxAuthData(publicIdentity, privateUserIdentity, numberOfAuthVectors, authenticationVector, scscfName);
                
                if (authDataResponse == null){
                    throw new UnableToComply();
                }

                DiameterMessage responseMessage = diameterPeer.newResponse(requestMessage);
                
        		/* vendor-specific app id */
                AVP vendorSpecificApplicationID = new AVP(Constants.AVPCode.VENDOR_SPECIFIC_APPLICATION_ID, true, Constants.Vendor.DIAM);
                AVP vendorID = new AVP(Constants.AVPCode.VENDOR_ID, true, Constants.Vendor.DIAM);
                vendorID.setData(Constants.Vendor.V3GPP);
                vendorSpecificApplicationID.addChildAVP(vendorID);
                AVP applicationID = new AVP(Constants.AVPCode.AUTH_APPLICATION_ID, true,  Constants.Vendor.DIAM);
                applicationID.setData(Constants.Application.CX);
                vendorSpecificApplicationID.addChildAVP(applicationID);
                responseMessage.addAVP(vendorSpecificApplicationID);
        		
        		/* auth-session-state, no state maintained */
                AVP authenticationSessionState = new AVP(Constants.AVPCode.AUTH_SESSION_STATE, true, Constants.Vendor.DIAM);
                authenticationSessionState.setData(1);
                responseMessage.addAVP(authenticationSessionState);
                
                /* add authentication data items */
                saveAuthData(authDataResponse, responseMessage);

                /* Add the result code */
                resultCode = getResultCodeAVP(authDataResponse.getResultCode(), authDataResponse.resultCodeIsBase());
                responseMessage.addAVP(resultCode);
                
                diameterPeer.sendMessage(FQDN, responseMessage);
            }
            catch (DiameterException e){
                LOGGER.warn(this, e);
                sendDiameterException(FQDN, requestMessage, e);
            }
            catch (Exception e){
                LOGGER.error(this, e);
                sendUnableToComply(FQDN, requestMessage);
            }
        }
    }

    /**
     * Help method to save authentication data extracted from 
     * authDataResponse in responseMessage
     * @param authDataResponse
     * @param responseMessage
     */
    private void saveAuthData(CxAuthDataResponse authDataResponse, DiameterMessage responseMessage){
        // Iterete and add vectors to response message
        Iterator it = authDataResponse.getAuthenticationVectors().iterator();
        int ix = 0;

        while (it.hasNext()){
            // store one vector in message as Auth Data Item
            AuthenticationVector vector = (AuthenticationVector) it.next();

            AVP authDataItem = new AVP(Constants.AVPCode.SIP_AUTH_DATA_ITEM, true, Constants.Vendor.V3GPP);
            AVP itemNumber = new AVP(Constants.AVPCode.SIP_ITEM_NUMBER, true, Constants.Vendor.V3GPP);
            itemNumber.setData(ix);
            authDataItem.addChildAVP(itemNumber);

            AVP authScheme = new AVP(Constants.AVPCode.SIP_AUTHENTICATION_SCHEME, true, Constants.Vendor.V3GPP);
            authScheme.setData(vector.authenticationScheme);
            authDataItem.addChildAVP(authScheme);
            
            //in the case of Early IMS
            if((vector.authenticationScheme).compareTo("Early-IMS-Security") == 0){
                AVP ip = new AVP(Constants.AVPCode.FRAMED_IP_ADDRESS, true, Constants.Vendor.V3GPP);
                ip.setData((vector.ip).getAddress());
                authDataItem.addChildAVP(ip);
            }

            AVP authenticate = new AVP(Constants.AVPCode.SIP_AUTHENTICATE, true, Constants.Vendor.V3GPP);
            authenticate.setData(vector.sipAuthenticate);
            authDataItem.addChildAVP(authenticate);

            AVP authorization = new AVP(Constants.AVPCode.SIP_AUTHORIZATION, true, Constants.Vendor.V3GPP);
            authorization.setData(vector.sipAuthorization);
            authDataItem.addChildAVP(authorization);

            AVP confidentialityKey = new AVP(Constants.AVPCode.CONFIDENTIALITY_KEY, true, Constants.Vendor.V3GPP);
            confidentialityKey.setData(vector.confidentialityityKey);
            authDataItem.addChildAVP(confidentialityKey);

            AVP integrityKey = new AVP(Constants.AVPCode.INTEGRITY_KEY, true, Constants.Vendor.V3GPP);
            integrityKey.setData(vector.integrityKey);
            authDataItem.addChildAVP(integrityKey);

            responseMessage.addAVP(authDataItem);
            ix++;
        }

        // Save the number of items.
        AVP numberOfItems = new AVP(Constants.AVPCode.SIP_NUMBER_AUTH_ITEMS, true, Constants.Vendor.V3GPP);
        numberOfItems.setData(ix);
        responseMessage.addAVP(numberOfItems);
    }

    /**
     * Loads the number of authentication vectors
     * @param requestMessage
     * @return number of authentication vectors
     * @throws DiameterException
     */
    private Long loadNumberOfAuthVectors(DiameterMessage requestMessage)
        throws DiameterException{
    	
        Long numberOfAuthVectors = null;
        try{
            numberOfAuthVectors = Long.valueOf(avpLookUp(requestMessage, Constants.AVPCode.SIP_NUMBER_AUTH_ITEMS, true,
                        Constants.Vendor.V3GPP).int_data);
        }
        catch (NumberFormatException e){
            LOGGER.error(this, e);
            throw new InvalidAvpValue();
        }

        return numberOfAuthVectors;
    }

    /**
     * Loads authentication vector
     * @param requestMessage the message
     * @return the authentication vector
     * @throws DiameterException
     */
    private AuthenticationVector loadAuthVector(DiameterMessage requestMessage)
        throws DiameterException{
    	
    	AuthenticationVector authenticationVector = null;
        AVP authVectorAVP = avpLookUp(requestMessage, Constants.AVPCode.SIP_AUTH_DATA_ITEM, true, Vendor.V3GPP);

        try {
			authVectorAVP.ungroup();
		} 
        catch (AVPDecodeException e) {
        	LOGGER.error("Ungrouping exception!!!");
			e.printStackTrace();
		}

        AVP sipAuthorization = authVectorAVP.findChildAVP(Constants.AVPCode.SIP_AUTHORIZATION, true, Vendor.V3GPP);
        AVP sipAuthScheme = authVectorAVP.findChildAVP(Constants.AVPCode.SIP_AUTHENTICATION_SCHEME, true, Vendor.V3GPP);

        authenticationVector = new AuthenticationVector();
        if (sipAuthorization != null){
            authenticationVector.sipAuthorization = sipAuthorization.data;
        }
        if (sipAuthScheme != null){
        	authenticationVector.authenticationScheme = new String(sipAuthScheme.getData());
        }
        	
        return authenticationVector;
    }
}
