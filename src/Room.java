import java.net.InetAddress;
import java.net.Socket;

public class Room {
	private String _roomName;
	private Socket _connection;
	
	public Room(Socket connection, String roomName){
		_connection = connection;
		_roomName = roomName;
	}
	
	public String GetUsername(){
		return _roomName;
	}
	
	public InetAddress GetAddress(){
		return _connection.getInetAddress();
	}
}