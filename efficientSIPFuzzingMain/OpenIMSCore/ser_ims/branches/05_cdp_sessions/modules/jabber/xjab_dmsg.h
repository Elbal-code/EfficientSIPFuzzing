/*
 * $Id: xjab_dmsg.h 2 2006-11-14 22:37:20Z vingarzan $
 *
 * eXtended JABber module
 *
 *
 * Copyright (C) 2001-2003 FhG Fokus
 *
 * This file is part of ser, a free SIP server.
 *
 * ser is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version
 *
 * For a license to use the ser software under conditions
 * other than those described here, or to purchase support for this
 * software, please contact iptel.org by e-mail at the following addresses:
 *    info@iptel.org
 *
 * ser is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

/*** error and info messages ***/
#ifndef _XJAB_DMSG_H_
#define _XJAB_DMSG_H_

#define XJ_DMSG_INF_DISCONNECTED "INFO: Connection to Jabber server lost. You have to login to Jabber server again (join again the conferences that you were participating, too)."

#define XJ_DMSG_ERR_SENDIM "ERROR: Your message was not sent. Connection to IM network failed."

#define XJ_DMSG_ERR_NOTJCONF "ERROR: Your message was not sent. You are not joined in the conference. Please join the room before sending messages."

#define XJ_DMSG_INF_JCONFEXIT "INFO: You have just left the conference."

#define XJ_DMSG_ERR_JGWFORB "ERROR: Your message was not sent. You do not have permission to use the gateway."

#define XJ_DMSG_ERR_NOJSRV "ERROR: Your message was not sent. Cannot connect to Jabber server."

#define XJ_DMSG_ERR_JAUTH "ERROR: Your message was not sent. Authentication to Jabber server failed."

#define XJ_DMSG_ERR_JGWFULL "ERROR: Your message was not sent. SIP-2-JABBER gateway is full."

#define XJ_DMSG_ERR_JOINJCONF "ERROR: Cannot join the conference room."

#define XJ_DMSG_ERR_NEWJCONF "ERROR:Cannot create a new conference session."

#define XJ_DMSG_ERR_SENDJMSG "ERROR: Your message was not sent. Something wrong during transmitting to Jabber network."

#define XJ_DMSG_ERR_STOREJMSG "ERROR: Your message was not sent. Something wrong while trying to transmit it to Jabber network."

#define XJ_DMSG_ERR_NOREGIM "ERROR: Your message was not sent. You are not registered with this IM gateway."

#define XJ_DMSG_ERR_DISCONNECTED "ERROR: Connection to Jabber server lost. You have to login to Jabber server again (join again the conferences that you were participating, too)."

#define XJ_DMSG_INF_JOFFLINE "INFO: Your are now offline in Jabber network. Thank you for using SIP-Jabber gateway."

#define XJ_DMSG_ERR_JCONFNICK "ERROR: Your nickname already exists in the conference room. Please choose a new one."

#define XJ_DMSG_ERR_JCONFREFUSED "ERROR: Your participation to the conference room was refused."

#endif

