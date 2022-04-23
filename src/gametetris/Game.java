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

public class Game implements Runnable{ //runメソッドのスレッド化にRunnableをimplementsする
	private GameClient client;
	private GameMain game;
	
	public Game(){
		game = new GameMain(); //ソケット接続が完了したclientを引数としてGameMainを生成
	}
	
	public void start(){
		run(); //スタート
	}
	
	public void run() {
		try{ //エラーの監視
			while(game.run()){} //ゲーム実行
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}



