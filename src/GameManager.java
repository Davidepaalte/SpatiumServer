import java.io.*;
import java.net.*;
import java.sql.Connection;
import java.util.*;
import java.util.concurrent.ThreadFactory;

public class GameManager{
	public final int porta = 1000;
	
	private DatagramPacket packetP1toP2, packetP2toP1;
	private DatagramSocket socketP1 = null, socketP2 = null;
	private Thread threadP1toP2, threadP2toP1;
	
	//Dava para receber apenas o Address e usar a mesma porta que estava a ser usada
	public GameManager(InetAddress player1, InetAddress player2) throws Exception{
		socketP1 = new DatagramSocket(porta, player1);
		socketP2 = new DatagramSocket(porta, player2);

		threadP1toP2 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					//os Packets não são inicializados na classe GameManager
					socketP1.receive(packetP1toP2);
					socketP2.send(packetP1toP2);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		
		threadP2toP1 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					socketP2.receive(packetP2toP1);
					socketP1.send(packetP2toP1);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void Start(){
		threadP1toP2.start();
		threadP2toP1.start();
	}
}
