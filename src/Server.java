import java.io.*;
import java.net.*;
import java.util.*;

import org.omg.CORBA.DataOutputStream;

public class TCPclient {
	public static ArrayList<Client> clientList;
	public final static int defaultPort = 80;
	public static Boolean newRoomSwitch = true;
	
	 public static void main(String argv[]){
		 clientList = new ArrayList<Client>();
		 
	 }
	 
	 public static void NewRoom(){
		
		 //Connection
		 ServerSocket server;
		 Socket connection;
		 Client newClient;
		 
		 //Message input and output
		 char[] roomName = new char[50];
		 
		 try {
			 server = new ServerSocket(defaultPort);
			 System.out.print("ServerSocket initialized\n");
			 
			 
			 while (newRoomSwitch) {
				 connection = server.accept();
				 
				 // Room name is recieved in the first 20 charaters - char[50]
			 }
			 
			 server.close();
			 
		 } catch (IOException e1) {
			 System.out.print("ERROR: failiure while trying to create a new room.\n" + e.getMessage() + "\nLook at NewRoom() inside try structure.\n\n");
		 }
	 }
}