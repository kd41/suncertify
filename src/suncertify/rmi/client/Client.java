package suncertify.rmi.client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import suncertify.rmi.server.Hello;

public class Client {

  private Client() {
  }

  public static void main(String[] args) {
    try {
      Registry registry = LocateRegistry.getRegistry(1024);
      Hello stub = (Hello) registry.lookup("Hello");
      String response = stub.sayHello();
      System.out.println("response: " + response);
    } catch (Exception e) {
      System.err.println("Client exception: " + e.toString());
      e.printStackTrace();
    }
  }
}