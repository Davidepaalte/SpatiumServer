import java.io.*;
import java.net.*;
import java.util.*;

import org.omg.CORBA.DataOutputStream;

public class Server{
	public static ArrayList<Room> clientList;
	public static Boolean newRoomSwitch = true;
	public static Boolean searchRoomSwitch = true;
	public final static int
		defaultCreatePort = 777,
		defaultSearchPort = 778; //Dados partilhados com o cliente
	public final static int roomNameLimit = 25;
	
	 public static void main(String argv[]){
		 clientList = new ArrayList<Room>();

		 clientList.add(new Room(null, "room1"));
		 clientList.add(new Room(null, "room2"));
		 clientList.add(new Room(null, "test3"));
		 clientList.add(new Room(null, "coiso4"));
		 clientList.add(new Room(null, "room1outravez"));
		 
		 Thread searchRooms = new Thread(new Runnable() {
				@Override
				public void run() {SearchRooms();}
			});
		 Thread newRoom = new Thread(new Runnable() {
				@Override
				public void run() {NewRoom();}
			});
		 
		 newRoom.start();
		 searchRooms.start();
	 }
	 
	 public static void NewRoom(){
		 //Connection
	 ServerSocket server;
	 Socket connection;
	 SocketAddress clientAdress;
	 Room newClient;
	 
	 //Message input and output
	 byte[] roomBytes = new byte[roomNameLimit];
	 String roomName;
	 InputStream reader;
	 OutputStream writer;
	 
	 try {
		 System.out.print("Trying to inicialize ServerSocket...    ");
		 server = new ServerSocket(defaultCreatePort);
		 System.out.print("ServerSocket initialized\n");
		 
		 
		 while (newRoomSwitch) {
			 System.out.print("Awaiting for new connection...    ");
			 connection = server.accept();
			 reader = connection.getInputStream();
			 System.out.print("Connection accepted\n");
	
			 System.out.print("Reading room name...    ");
			 // Room name is recieved in the first <roomNameLimit> charaters - char[roomNameLimit] or until it reaches a \n
			 for (int i = 0; i < roomNameLimit; i++){
				 int v = reader.read();
				 if (v == -1 || v == '\n')
					 i = roomNameLimit;
				 else
					 roomBytes[i] = (byte) v;
				 
			 }
			 roomName = new String(roomBytes);
			 System.out.print("Room name readed\n");
	
			 System.out.print("Getting remote socket address...    ");
			 newClient = new Room(connection, roomName);
			 clientList.add(newClient);
			 
			 //Printing results
			 System.out.print("Remote socket address added\n\tCreated new room:\n\tRoomName = " + newClient.GetUsername() + "\n\tAddress = " + newClient.GetAddress() + "\n\n");
			 
			 //Tells the client that the room was created
			 writer = connection.getOutputStream();
			 writer.write(1);
		 }
		 
		 server.close();
		 
	 } catch (IOException e) {
		 System.out.print("ERROR: failiure while trying to create a new room.\n" + e.getMessage() + "\nLook at NewRoom() inside try structure.\n\n");
	 }
}

	 public static boolean VerifyConnection(Socket connection){
		 int sleepTime = 500;
		 boolean isConnected;
		 
		 try {
			 Thread.sleep(sleepTime);
			 isConnected = connection.isConnected();
		 }
		 catch (InterruptedException e) {
			 e.printStackTrace();
		 }
		 
		 return false;
	 }
	 
	 public static void SearchRooms(){
		 //Connection
		 ServerSocket server;
		 Socket connection;
	 
		 //Message input and output
		 byte[] roomBytes = new byte[roomNameLimit];
		 InputStream reader;
		 OutputStream writer;
		 
		 try {
			 server = new ServerSocket(defaultSearchPort);
			 
			 while (searchRoomSwitch) {
				 System.out.print("Awaiting for new connection...    \n");
				 connection = server.accept();
				 reader = connection.getInputStream();
				 writer = connection.getOutputStream();
				 
				 //Write all room names separated by \n and, in the end, \n\n
				 for(int i = 0; i < clientList.size(); i++){
					 System.out.print(clientList.get(i).GetUsername() + "\n");
					 writer.write(clientList.get(i).GetUsername().getBytes());
					 writer.write("\n".getBytes());
				 }
				 writer.write("\n".getBytes());
				 System.out.print("\n");
				 
				 connection.close();
			 }
			 
			 server.close();
			 
		 } catch (IOException e) {
			 
		 }
	 }

}