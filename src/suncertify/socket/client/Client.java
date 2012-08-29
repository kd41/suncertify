package suncertify.socket.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import suncertify.parser.PropertiesLoader;

public class Client {
  private static final Logger log = LoggerFactory.getLogger(Client.class);
  private String host;
  private int port;
  private Socket socket;
  private ObjectOutputStream out;
  private ObjectInputStream in;

  private String message;
  private String response;

  protected Client(String host, int port) throws NotInizializedException {
    this.host = host;
    this.port = port;
  }

  protected void start() throws NotInizializedException {
    try {
      socket = new Socket(host, port);
      log.info("Connected to " + host + " in port " + port);
      out = new ObjectOutputStream(socket.getOutputStream());
      out.flush();
      in = new ObjectInputStream(socket.getInputStream());
      try {
        // send
        out.writeObject(message);
        out.flush();
        log.info("client>" + message);
        // receive
        response = (String) in.readObject();
        log.info("server>" + message);
      } catch (IOException e) {
        log.error(e.getMessage(), e);
      } catch (ClassNotFoundException e) {
        log.error(e.getMessage(), e);
      }
    } catch (UnknownHostException e) {
      log.error("You are trying to connect to an unknown host!", e);
      throw new NotInizializedException();
    } catch (IOException e) {
      log.error(e.getMessage(), e);
    } finally {
      try {
        in.close();
      } catch (IOException e) {
      }
      try {
        out.close();
      } catch (IOException e) {
      }
      try {
        socket.close();
      } catch (IOException e) {
      }

      log.info("Client stoped");
    }
  }

  protected String getResponse() {
    return response;
  }

  protected void setMessage(String message) {
    this.message = message;
  }

  public static void main(String args[]) throws NotInizializedException {
    new Client(PropertiesLoader.getInstance().getDbHost(), Integer.parseInt(PropertiesLoader.getInstance().getDbPort()));
  }

}