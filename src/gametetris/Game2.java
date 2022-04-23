package gametetris;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Game2 implements Runnable, ActionListener{
	private GameClient client;
	private GameMain game;
	private String ip;
	private JFrame frame;
	private Container contentPane;
	private JLabel label;
	private JLabel label2;
	private JTextField ip_text;
	private JButton button;
	private boolean startFlag;
	
	public Game2(){
		client = new GameClient();
		frame = new JFrame("IP Address"); //以下、IP Address取得のためのGUIの生成
		frame.setBounds(30, 30, 500, 80);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = frame.getContentPane();
		label = new JLabel("IP Address: ");
		contentPane.add(label, BorderLayout.WEST);
		label2 = new JLabel("Input Server IP Address");
		contentPane.add(label2, BorderLayout.SOUTH);
		ip_text = new JTextField(10);
		contentPane.add(ip_text, BorderLayout.CENTER);
		button = new JButton("OK");
		button.addActionListener(this);
		contentPane.add(button, BorderLayout.EAST);
		frame.setVisible(true); //表示
		startFlag = false;
	}
	
	public void start(){
		while(!startFlag){ //Clientの準備完了を待つ．
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		label2.setText("OK");
		frame.setVisible(false);
		run(); //ゲームの開始
	}
	
	
	public void run() {
		try{
			while(game.run()){}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void actionPerformed(ActionEvent arg0) { //Buttonが押下された際
		ip = ip_text.getText();
		System.out.println(ip);
		if(ip.isEmpty()){ //TextFieldが空
			client = null;
			game = new GameMain();
			startFlag = true;
		}
		else if(client.setSocket(ip)){ //ソケット接続に成功
			game = new GameMain(client);
			startFlag = true;
		}else{ //ソケット接続に失敗
			label2.setText("Connection refused: Input Server IP Address again");
		}
	}
}



