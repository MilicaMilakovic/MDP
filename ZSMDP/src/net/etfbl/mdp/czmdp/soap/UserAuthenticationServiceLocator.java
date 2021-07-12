/**
 * UserAuthenticationServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package net.etfbl.mdp.czmdp.soap;

public class UserAuthenticationServiceLocator extends org.apache.axis.client.Service implements net.etfbl.mdp.czmdp.soap.UserAuthenticationService {

    public UserAuthenticationServiceLocator() {
    }


    public UserAuthenticationServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public UserAuthenticationServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for UserAuthentication
    private java.lang.String UserAuthentication_address = "http://localhost:8080/CZMDP/services/UserAuthentication";

    public java.lang.String getUserAuthenticationAddress() {
        return UserAuthentication_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String UserAuthenticationWSDDServiceName = "UserAuthentication";

    public java.lang.String getUserAuthenticationWSDDServiceName() {
        return UserAuthenticationWSDDServiceName;
    }

    public void setUserAuthenticationWSDDServiceName(java.lang.String name) {
        UserAuthenticationWSDDServiceName = name;
    }

    public net.etfbl.mdp.czmdp.soap.UserAuthentication getUserAuthentication() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(UserAuthentication_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getUserAuthentication(endpoint);
    }

    public net.etfbl.mdp.czmdp.soap.UserAuthentication getUserAuthentication(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            net.etfbl.mdp.czmdp.soap.UserAuthenticationSoapBindingStub _stub = new net.etfbl.mdp.czmdp.soap.UserAuthenticationSoapBindingStub(portAddress, this);
            _stub.setPortName(getUserAuthenticationWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setUserAuthenticationEndpointAddress(java.lang.String address) {
        UserAuthentication_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (net.etfbl.mdp.czmdp.soap.UserAuthentication.class.isAssignableFrom(serviceEndpointInterface)) {
                net.etfbl.mdp.czmdp.soap.UserAuthenticationSoapBindingStub _stub = new net.etfbl.mdp.czmdp.soap.UserAuthenticationSoapBindingStub(new java.net.URL(UserAuthentication_address), this);
                _stub.setPortName(getUserAuthenticationWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("UserAuthentication".equals(inputPortName)) {
            return getUserAuthentication();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://soap.czmdp.mdp.etfbl.net", "UserAuthenticationService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://soap.czmdp.mdp.etfbl.net", "UserAuthentication"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("UserAuthentication".equals(portName)) {
            setUserAuthenticationEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
