/**
 * UserService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package net.etfbl.mdp.czmdp.soap;

public interface UserService extends java.rmi.Remote {
    public boolean verify(net.etfbl.mdp.model.User user) throws java.rmi.RemoteException;
    public void registerLogin(net.etfbl.mdp.model.User user) throws java.rmi.RemoteException;
    public void registerLogout(net.etfbl.mdp.model.User user) throws java.rmi.RemoteException;
    public net.etfbl.mdp.model.User getActiveUser(java.lang.String city) throws java.rmi.RemoteException;
    public net.etfbl.mdp.model.User[] getOnlineUsers() throws java.rmi.RemoteException;
}
