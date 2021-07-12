package net.etfbl.mdp.czmdp.soap;

public class UserAuthenticationProxy implements net.etfbl.mdp.czmdp.soap.UserAuthentication {
  private String _endpoint = null;
  private net.etfbl.mdp.czmdp.soap.UserAuthentication userAuthentication = null;
  
  public UserAuthenticationProxy() {
    _initUserAuthenticationProxy();
  }
  
  public UserAuthenticationProxy(String endpoint) {
    _endpoint = endpoint;
    _initUserAuthenticationProxy();
  }
  
  private void _initUserAuthenticationProxy() {
    try {
      userAuthentication = (new net.etfbl.mdp.czmdp.soap.UserAuthenticationServiceLocator()).getUserAuthentication();
      if (userAuthentication != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)userAuthentication)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)userAuthentication)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (userAuthentication != null)
      ((javax.xml.rpc.Stub)userAuthentication)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public net.etfbl.mdp.czmdp.soap.UserAuthentication getUserAuthentication() {
    if (userAuthentication == null)
      _initUserAuthenticationProxy();
    return userAuthentication;
  }
  
  public boolean verify(net.etfbl.mdp.model.User user) throws java.rmi.RemoteException{
    if (userAuthentication == null)
      _initUserAuthenticationProxy();
    return userAuthentication.verify(user);
  }
  
  
}