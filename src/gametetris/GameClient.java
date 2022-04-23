package gametetris;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class GameClient extends Thread{
	Socket socket;
	GameField field1;
	GameField field2;
	
	public GameClient(){
		
	}
	
	public boolean setSocket(String ipAddress){
		try {
			socket = new Socket(ipAddress, 26000);
			System.out.println(socket.getInetAddress());
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
			return false;
		} catch (IOException e1) {
			e1.printStackTrace();
			return false;
		}
		field1 = null;
		field2 = null;
		start();
		return true;
	}

	public void run(){
		while(true){
			try{
				// オブジェクト出力ストリーム
				ObjectOutputStream oos = new ObjectOutputStream( socket.getOutputStream() );
				// 書き出し
				oos.writeObject(field1);

				// オブジェクト入力ストリーム
				ObjectInputStream ois = new ObjectInputStream( socket.getInputStream() );
				field2 = (GameField)(ois.readObject());
			} catch(Exception e){
				//e.printStackTrace();
			}
		}
	}
	
	public void sendGameField(GameField field){
		field1 = field;
	}
	
	public GameField fetchGameField(){
		return field2;
	}
	
	public void close(){
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}