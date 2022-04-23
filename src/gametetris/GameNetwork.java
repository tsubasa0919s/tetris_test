package gametetris;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.awt.BorderLayout;
import java.awt.Container;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class GameNetwork extends Thread{

	JFrame frame;
	Container contentPane;
	JLabel label;
	JLabel label2;
	JLabel label3;
	JLabel label4;
	
	ServerSocket serverSocket1;
	ServerSocket serverSocket2;

	public static void main(String[] args){
		GameNetwork gmn = new GameNetwork();
		gmn.start();
	}

	public GameNetwork(){
		try {
			serverSocket1 = new ServerSocket(26000);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		InetAddress addr = null;
		try {
			addr = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		frame = new JFrame("GameTetrisServer");
		frame.setBounds(30, 30, 500, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = frame.getContentPane();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		label = new JLabel("Server Running");
		contentPane.add(label);
		label2 = new JLabel("IP Address: "+addr.getHostAddress());
		contentPane.add(label2);
		label3 = new JLabel("Player1:None");
		contentPane.add(label3);
		label4 = new JLabel("Player2:None");
		contentPane.add(label4);
		frame.setVisible(true);
		//		try {
		//			serverSocket2 = new ServerSocket(27000);
		//		} catch (IOException e) {
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		}
	}

	public void run() {
		try {

			while(true){
				System.out.println("クライアントからの接続を待ちます");
				Socket s1 = serverSocket1.accept();
				System.out.println("プレイヤー１OK!");
				label3.setText("Player1:OK");
				Socket s2 = serverSocket1.accept();
				System.out.println("プレイヤー２OK!");
				label4.setText("Player2:OK");
				
				while(true) {
					try {
						// 受信
						ObjectInputStream ois1 = new ObjectInputStream(s1.getInputStream());
						GameField data1 = (GameField)(ois1.readObject());
						ObjectInputStream ois2 = new ObjectInputStream(s2.getInputStream());
						GameField data2 = (GameField)(ois2.readObject());

						// 返信
						ObjectOutputStream oos1 = new ObjectOutputStream(s1.getOutputStream());
						oos1.writeObject(data2);
						ObjectOutputStream oos2 = new ObjectOutputStream(s2.getOutputStream());
						oos2.writeObject(data1);
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} catch (Exception ex) {
						System.out.println("切断されました");
						label3.setText("Player1:None");
						label4.setText("Player2:None");
						ex.printStackTrace();
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}