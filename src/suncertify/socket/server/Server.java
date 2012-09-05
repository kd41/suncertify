package suncertify.socket.server;

import static suncertify.constants.Variables.TERMINATOR;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import suncertify.db.Data;
import suncertify.db.DuplicateKeyException;
import suncertify.db.RecordNotFoundException;
import suncertify.socket.MessageHelper;
import suncertify.socket.MessageType;

/**
 * The Class Server.
 */
public class Server {
  private ServerSocket serverSocket;
  private Data data = Data.getInstance();
  protected boolean isRunning = true;

  // TODO: replace for throw new MyIOException
  /**
   * Instantiates a new server.
   * 
   * @param port the port
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public Server(int port) throws IOException {
    serverSocket = new ServerSocket(port);
    int count = 0;
    while (isRunning) {
      Socket clientSocket = serverSocket.accept();
      ServerThread thread = new ServerThread(clientSocket, count++);
      thread.run();
    }
  }

  protected void stop() {
    this.isRunning = false;
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
        System.out.println("Connection received from " + clientSocket.getInetAddress().getHostName() + "; count: "
                           + count);
        out = new ObjectOutputStream(clientSocket.getOutputStream());
        out.flush();
        in = new ObjectInputStream(clientSocket.getInputStream());
        do {
          try {
            // receive
            message = (String) in.readObject();
            if (message.startsWith(MessageType.READ.getName())) {
              long recNo = Long.parseLong(message.split(TERMINATOR)[1]);
              try {
                sendMessage(MessageHelper.getReadResponseMessage(data.readRecord(recNo)));
              } catch (RecordNotFoundException e) {
                sendMessage(MessageHelper.getNoRecordError());
              }
            } else if (message.startsWith(MessageType.FIND.getName())) {
              String[] temp = message.split(TERMINATOR, -1);
              String[] criteria = { temp[1], temp[2], temp[3], temp[4], temp[5], temp[6] };
              sendMessage(MessageHelper.getFindByCriteriaResponseMessage(data.findByCriteria(criteria)));
            } else if (message.startsWith(MessageType.UPDATE.getName())) {
              String[] temp = message.split(TERMINATOR, -1);
              long recNo = Long.parseLong(temp[1]);
              String[] u_data = { temp[2], temp[3], temp[4], temp[5], temp[6], temp[7] };
              long lockCookie = Long.parseLong(temp[8]);
              try {
                data.updateRecord(recNo, u_data, lockCookie);
                sendMessage(MessageHelper.getUpdateResponseMessage());
              } catch (SecurityException e) {
                sendMessage(MessageHelper.getSecurityError());
              } catch (RecordNotFoundException e) {
                sendMessage(MessageHelper.getNoRecordError());
              }
            } else if (message.startsWith(MessageType.DELETE.getName())) {
              String[] temp = message.split(TERMINATOR, -1);
              long recNo = Long.parseLong(temp[1]);
              long lockCookie = Long.parseLong(temp[2]);
              try {
                data.deleteRecord(recNo, lockCookie);
                sendMessage(MessageHelper.getDeleteResponseMessage());
              } catch (SecurityException e) {
                sendMessage(MessageHelper.getSecurityError());
              } catch (RecordNotFoundException e) {
                sendMessage(MessageHelper.getNoRecordError());
              }
            } else if (message.startsWith(MessageType.CREATE.getName())) {
              String[] temp = message.split(TERMINATOR, -1);
              String[] c_data = { temp[1], temp[2], temp[3], temp[4], temp[5], temp[6] };
              try {
                long recNo = data.createRecord(c_data);
                sendMessage(MessageHelper.getCreateResponseMessage(recNo));
              } catch (DuplicateKeyException e) {
                sendMessage(MessageHelper.getDuplicateError());
              }
            } else if (message.startsWith(MessageType.LOCK.getName())) {
              String[] temp = message.split(TERMINATOR, -1);
              long recNo = Long.parseLong(temp[1]);
              try {
                long cookie = data.lockRecord(recNo);
                sendMessage(MessageHelper.getLockResponseMessage(cookie));
              } catch (RecordNotFoundException e) {
                sendMessage(MessageHelper.getNoRecordError());
              }
            } else if (message.startsWith(MessageType.UNLOCK.getName())) {
              String[] temp = message.split(TERMINATOR, -1);
              long recNo = Long.parseLong(temp[1]);
              long cookie = Long.parseLong(temp[2]);
              try {
                data.unlock(recNo, cookie);
                sendMessage(MessageHelper.getUnlockResponseMessage());
              } catch (SecurityException e) {
                sendMessage(MessageHelper.getSecurityError());
              }
            }
          } catch (ClassNotFoundException e) {
            System.out.println("Data received in unknown format: " + e);
          }
        } while (true);
      } catch (EOFException e) {
      } catch (IOException e) {
        System.out.println(e);
      } finally {
        try {
          in.close();
        } catch (IOException e) {
          System.out.println(e);
        }
        try {
          out.close();
        } catch (IOException e) {
          System.out.println(e);
        }
      }
    }

    private void sendMessage(String msg) {
      try {
        out.writeObject(msg);
        out.flush();
      } catch (IOException e) {
        System.out.println(e);
      }
    }

  }
}
