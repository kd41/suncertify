package suncertify.socket.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
  public static void main(String[] args) {
    Socket s = null;

    // Create the socket connection to the EchoServer.
    try {
      s = new Socket("localhost", 12111);
    } catch (UnknownHostException uhe) {
      // Host unreachable
      System.out.println("Unknown Host is localhost:");
      s = null;
    } catch (IOException ioe) {
      // Cannot connect to port on given host
      System.out.println("Cant connect to server at 12111. Make sure it is running.");
      s = null;
    }

    if (s == null)
      System.exit(-1);

    BufferedReader in = null;
    PrintWriter out = null;

    try {
      // Create the streams to send and receive information
      in = new BufferedReader(new InputStreamReader(s.getInputStream()));
      out = new PrintWriter(new OutputStreamWriter(s.getOutputStream()));

      // Since this is the client, we will initiate the talking.
      // Send a string.
      out.println("Hello");
      out.flush();
      // receive the reply.
      System.out.println("Server Says : " + in.readLine());

      // Send a string.
      out.println("This");
      out.flush();
      // receive a reply.
      System.out.println("Server Says : " + in.readLine());

      // Send a string.
      out.println("is");
      out.flush();
      // receive a reply.
      System.out.println("Server Says : " + in.readLine());

      // Send a string.
      out.println("a");
      out.flush();
      // receive a reply.
      System.out.println("Server Says : " + in.readLine());

      // Send a string.
      out.println("Test");
      out.flush();
      // receive a reply.
      System.out.println("Server Says : " + in.readLine());

      // Send the special string to tell server to quit.
      out.println("Quit");
      out.flush();
    } catch (IOException ioe) {
      System.out.println("Exception during communication. Server probably closed connection.");
    } finally {
      try {
        // Close the streams
        out.close();
        in.close();
        // Close the socket before quitting
        s.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
