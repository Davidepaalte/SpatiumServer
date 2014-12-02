import java.net.InetAddress;


public class Game {
	private InetAddress player1, player2;
	
	public Game(InetAddress Player1, InetAddress Player2){
		this.player1 = Player1;
		this.player2 = Player2;
	}
	
	public InetAddress getPlayer1(){
		return player1;
	}
	
	public InetAddress getPlayer2(){
		return player2;
	}
}
