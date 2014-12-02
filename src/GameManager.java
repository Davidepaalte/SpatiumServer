import java.io.*;
import java.net.*;
import java.util.*;

public class GameManager{
	public final int porta = 1000;
	
	private DatagramPacket packet;
	private  DatagramSocket sRecieve = null, sSend = null;
	private Thread thread;
	private InetAddress recievedAddress;

	public static ArrayList<Game> clientList;
	
	public GameManager(){
		try{
		sRecieve = new DatagramSocket(porta);
		sSend = new DatagramSocket(porta);
		}
		catch (Exception e){
			e.printStackTrace();
		}

		thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					sRecieve.receive(packet);
					recievedAddress = packet.getAddress();
					
					for (int i = 0; i < clientList.size(); i++){
						if(clientList.get(i).getPlayer1().equals(recievedAddress)){
							packet.setAddress(clientList.get(i).getPlayer2());
							sSend.send(packet);
						}
						else if(clientList.get(i).getPlayer2().equals(recievedAddress)){
							packet.setAddress(clientList.get(i).getPlayer1());
							sSend.send(packet);
						}
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		thread.start();
	}
	
	public boolean openGameRoom(InetAddress player1, InetAddress player2){
		Game newGame = new Game(player1, player2);
		if (!clientList.contains(newGame))
		{
			clientList.add(newGame);
			return true;
		}
		else return false;
	}
}
