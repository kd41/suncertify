package suncertify.socket.server;

import static suncertify.constants.Variables.TERMINATOR;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import suncertify.db.Data;
import suncertify.db.RecordNotFoundException;
import suncertify.parser.PropertiesLoader;
import suncertify.socket.MessageType;
import suncertify.socket.SocketHelper;

public class Server {
  private static final Logger log = LoggerFactory.getLogger(Server.class);
  private ServerSocket serverSocket;
  private Data data = Data.getInstance();

  // TODO: replace for throw new MyIOException
  public Server(int port) throws IOException {
    serverSocket = new ServerSocket(port);
    log.info("Listening for clients on " + port + "....");
    int count = 0;
    while (true) {
      Socket clientSocket = serverSocket.accept();
      ServerThread thread = new ServerThread(clientSocket, count++);
      thread.start();
    }
  }

  // TODO: delete after
  public static void main(String[] args) throws IOException {
    new Server(Integer.parseInt(PropertiesLoader.getInstance().getDbPort()));
  }

  private class ServerThread extends Thread {
    private Socket clientSocket;
    private int count = -1;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private String message;

    private ServerThread(Socket clientSocket, int count) {
      this.clientSocket = clientSocket;
      this.count = count;
    }

    @Override
    public void run() {
      try {
        log.info("Connection received from " + clientSocket.getInetAddress().getHostName() + "; count: " + count);
        out = new ObjectOutputStream(clientSocket.getOutputStream());
        out.flush();
        in = new ObjectInputStream(clientSocket.getInputStream());
        do {
          try {
            // receive
            message = (String) in.readObject();
            log.info("client>" + message);
            if (message.startsWith(MessageType.READ.getName())) {
              long recNo = Long.parseLong(message.split(TERMINATOR)[1]);
              try {
                sendMessage(SocketHelper.getReadResponseMessage(data.readRecord(recNo)));
              } catch (RecordNotFoundException e) {
                sendMessage(MessageType.ERROR_NO_RECORD.getName());
              }
            }
          } catch (ClassNotFoundException e) {
            log.error("Data received in unknown format", e);
          }
        } while (true);
      } catch (EOFException e) {
      } catch (IOException e) {
        log.error(e.getMessage(), e);
      } finally {
        try {
          in.close();
        } catch (IOException e) {
          log.error(e.getMessage(), e);
        }
        try {
          out.close();
        } catch (IOException e) {
          log.error(e.getMessage(), e);
        }
      }
    }

    private void sendMessage(String msg) {
      try {
        out.writeObject(msg);
        out.flush();
        log.info("server>" + msg);
      } catch (IOException e) {
        log.error(e.getMessage(), e);
      }
    }
  }
}
