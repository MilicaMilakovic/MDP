/**
 * UserServiceService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package net.etfbl.mdp.czmdp.soap;

public interface UserServiceService extends javax.xml.rpc.Service {
    public java.lang.String getUserServiceAddress();

    public net.etfbl.mdp.czmdp.soap.UserService getUserService() throws javax.xml.rpc.ServiceException;

    public net.etfbl.mdp.czmdp.soap.UserService getUserService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
