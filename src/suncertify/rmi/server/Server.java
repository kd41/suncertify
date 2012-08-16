package suncertify.rmi.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server implements Hello {

  private static final Logger log = LoggerFactory.getLogger(Server.class);

  public Server() {
  }

  @Override
  public String sayHello() {
    return "Hello, world!!!dv";
  }

  public static void main(String args[]) {

    try {
      Server obj = new Server();
      Hello stub = (Hello) UnicastRemoteObject.exportObject(obj, 0);

      // Bind the remote object's stub in the registry
      Registry registry = LocateRegistry.createRegistry(1024);
      registry.rebind("Hello", stub);

      System.err.println("Server ready");
    } catch (Exception e) {
      System.err.println("Server exception: " + e.toString());
      e.printStackTrace();
      log.error(e.getMessage(), e);
    }
  }
}