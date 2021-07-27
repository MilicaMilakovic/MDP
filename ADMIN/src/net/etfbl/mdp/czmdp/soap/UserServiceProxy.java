package net.etfbl.mdp.czmdp.soap;

public class UserServiceProxy implements net.etfbl.mdp.czmdp.soap.UserService {
  private String _endpoint = null;
  private net.etfbl.mdp.czmdp.soap.UserService userService = null;
  
  public UserServiceProxy() {
    _initUserServiceProxy();
  }
  
  public UserServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initUserServiceProxy();
  }
  
  private void _initUserServiceProxy() {
    try {
      userService = (new net.etfbl.mdp.czmdp.soap.UserServiceServiceLocator()).getUserService();
      if (userService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)userService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)userService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (userService != null)
      ((javax.xml.rpc.Stub)userService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public net.etfbl.mdp.czmdp.soap.UserService getUserService() {
    if (userService == null)
      _initUserServiceProxy();
    return userService;
  }
  
  public int getPort(java.lang.String username) throws java.rmi.RemoteException{
    if (userService == null)
      _initUserServiceProxy();
    return userService.getPort(username);
  }
  
  public boolean verify(net.etfbl.mdp.model.User user) throws java.rmi.RemoteException{
    if (userService == null)
      _initUserServiceProxy();
    return userService.verify(user);
  }
  
  public net.etfbl.mdp.model.User getActiveUser(java.lang.String city) throws java.rmi.RemoteException{
    if (userService == null)
      _initUserServiceProxy();
    return userService.getActiveUser(city);
  }
  
  public net.etfbl.mdp.model.User[] getOnlineUsers() throws java.rmi.RemoteException{
    if (userService == null)
      _initUserServiceProxy();
    return userService.getOnlineUsers();
  }
  
  public void registerLogin(net.etfbl.mdp.model.User user) throws java.rmi.RemoteException{
    if (userService == null)
      _initUserServiceProxy();
    userService.registerLogin(user);
  }
  
  public int assignPort() throws java.rmi.RemoteException{
    if (userService == null)
      _initUserServiceProxy();
    return userService.assignPort();
  }
  
  public void addUser(net.etfbl.mdp.model.User user) throws java.rmi.RemoteException{
    if (userService == null)
      _initUserServiceProxy();
    userService.addUser(user);
  }
  
  public boolean deleteUser(net.etfbl.mdp.model.User user) throws java.rmi.RemoteException{
    if (userService == null)
      _initUserServiceProxy();
    return userService.deleteUser(user);
  }
  
  public void registerLogout(net.etfbl.mdp.model.User user) throws java.rmi.RemoteException{
    if (userService == null)
      _initUserServiceProxy();
    userService.registerLogout(user);
  }
  
  public net.etfbl.mdp.model.User[] getAllUsers() throws java.rmi.RemoteException{
    if (userService == null)
      _initUserServiceProxy();
    return userService.getAllUsers();
  }
  
  
}