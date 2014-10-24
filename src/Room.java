public class Room {
	private String
		_IP,
		_roomName;
	
	public Room(String IP, String roomName){
		_IP = IP;
		_roomName = roomName;
	}
	
	public String GetUsername(){
		return _roomName;
	}
	
	public String GetIP(){
		return _IP;
	}
}