# $Id: Makefile 161 2007-03-01 14:06:01Z vingarzan $ Makefile  $Date: 2007-03-01 14:06:01 +0000 (Thu, 01 Mar 2007) $ $Author: vingarzan $ dvi
#
# Makefile for the Proxy-CSCF module
# \author Dragos Vingarzan vingarzan -at- fokus dot fraunhofer dot de
#
# WARNING: do not run this directly, it should be run by the master Makefile

include ../../Makefile.defs
auto_gen=
NAME=pcscf.so

include ../../Makefile.modules

DEFS += -I/usr/include/libxml2 -I../../lib -DSER -I../..
LIBS += -lxml2 -L../../lib/cds -l_ser_cds
