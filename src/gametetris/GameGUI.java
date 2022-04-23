package gametetris;

import java.awt.*;

import javax.swing.*;

public class GameGUI {

	
	private JFrame frame;
	private JPanel mainPane;
	private JPanel[] playerPane = new JPanel[2];
	private JPanel infoPane;
	private Image[] image = new Image[2];
	
	private JLabel[] player = new JLabel[2];
	private JLabel[] renNum = new JLabel[2];
	private JLabel[] deleteNum = new JLabel[2];
	private JLabel[] hindNum = new JLabel[2];
	private JLabel[] point = new JLabel[2];
	
	/* フィールドの設定用 */
	private int MainW=1000;
	private int MainH=700;
	private int FieldW=360;
	private int FieldH=690;
	private int InfoW=200;
	private int InfoH=600;
	private int	WindowW = 1050;
	private int	WindowH = 700;
	private int	WindowX = 0;
	private int	WindowY = 0;
	private int	FieldRows = 23;
	private int	FieldCols = 12;
	private int BlockSize = 30;
	
	private Graphics[] fg = new Graphics[2]; //Field
	private Graphics[] bg = new Graphics[2]; //Block
	
	public GameGUI(GameKey key){
		frame = new JFrame("GameTetris");
		frame.setBounds(WindowX, WindowY, WindowW, WindowH);
		frame.setResizable(false);
		frame.setFocusable(true);
		frame.requestFocus();
		frame.addKeyListener(key);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container container = frame.getContentPane();
		
		mainPane = new JPanel();
		mainPane.setLayout(new FlowLayout());
		mainPane.setPreferredSize(new Dimension(MainW, MainH));
		
		playerPane[0] = new JPanel();
		playerPane[0].setPreferredSize(new Dimension(FieldW, FieldH));
		
		playerPane[1] = new JPanel();
		playerPane[1].setPreferredSize(new Dimension(FieldW, FieldH));
		
		infoPane = new JPanel();
		infoPane.setPreferredSize(new Dimension(InfoW, InfoH));
		infoPane.setLayout(new BoxLayout(infoPane, BoxLayout.Y_AXIS));
		
		player[0] = new JLabel("Me:");
		renNum[0] = new JLabel("Ren: ");
		deleteNum[0] = new JLabel("Delete: ");
		hindNum[0] = new JLabel("Hindrance: ");
		point[0] = new JLabel("Point: ");
		player[1] = new JLabel("Rival:");
		renNum[1] = new JLabel("Ren: ");
		deleteNum[1] = new JLabel("Delete: ");
		hindNum[1] = new JLabel("Hindrance: ");
		point[0] = new JLabel("Point: ");
		point[1] = new JLabel("Point: ");
		player[0].setFont(new Font("Arial", Font.PLAIN, 30));
		player[1].setFont(new Font("Arial", Font.PLAIN, 30));
		renNum[0].setFont(new Font("Arial", Font.PLAIN, 18));
		renNum[1].setFont(new Font("Arial", Font.PLAIN, 18));
		deleteNum[0].setFont(new Font("Arial", Font.PLAIN, 18));
		deleteNum[1].setFont(new Font("Arial", Font.PLAIN, 18));
		hindNum[0].setFont(new Font("Arial", Font.PLAIN, 18));
		hindNum[1].setFont(new Font("Arial", Font.PLAIN, 18));
		point[0].setFont(new Font("Arial", Font.PLAIN, 18));
		point[1].setFont(new Font("Arial", Font.PLAIN, 18));
		
		infoPane.add(player[0]);
		infoPane.add(renNum[0]);
		infoPane.add(deleteNum[0]);
		infoPane.add(hindNum[0]);
		infoPane.add(point[0]);
		infoPane.add(player[1]);
		infoPane.add(renNum[1]);
		infoPane.add(deleteNum[1]);
		infoPane.add(hindNum[1]);
		infoPane.add(point[1]);
		
		mainPane.add(playerPane[0]);
		mainPane.add(infoPane);
		mainPane.add(playerPane[1]);
		container.add(mainPane);
		frame.pack();
		
		image[0] = playerPane[0].createImage(FieldW, FieldH-1);
		image[1] = playerPane[1].createImage(FieldW, FieldH-1);
		
		fg[0] = playerPane[0].getGraphics();
		bg[0] = image[0].getGraphics();
		bg[0].fillRect(0, 0, FieldW, FieldH-1);
		fg[1] = playerPane[1].getGraphics();
		bg[1] = image[1].getGraphics();
		bg[1].fillRect(0, 0, FieldW, FieldH-1);
	}
	
	public void show(){
		frame.setVisible(true);
		frame.requestFocus(); 
	}

	public void fieldDraw(int p, int[][] field){
		for(int row = 1; row <= FieldRows-1; row++){
			for(int col = 0; col <= FieldCols-1; col++){
				if(field[row][col] == 9){
					bg[p].setColor(Color.black);
					bg[p].fillRect(BlockSize*col+1, BlockSize*row+1, BlockSize-2, BlockSize-2);
				}
				else if(field[row][col] == 0){
					bg[p].setColor(Color.white);
					bg[p].fillRect(BlockSize*col+1, BlockSize*row+1, BlockSize-2, BlockSize-2);
				}
				else if(field[row][col] == 1){
					bg[p].setColor(Color.magenta);
					bg[p].fillRect(BlockSize*col+1, BlockSize*row+1, BlockSize-2, BlockSize-2);
				}
				else if(field[row][col] == 2){
					bg[p].setColor(Color.green);
					bg[p].fillRect(BlockSize*col+1, BlockSize*row+1, BlockSize-2, BlockSize-2);
				}
				else if(field[row][col] == 3){
					bg[p].setColor(Color.red);
					bg[p].fillRect(BlockSize*col+1, BlockSize*row+1, BlockSize-2, BlockSize-2);
				}
				else if(field[row][col] == 4){
					bg[p].setColor(Color.blue);
					bg[p].fillRect(BlockSize*col+1, BlockSize*row+1, BlockSize-2, BlockSize-2);
				}
				else if(field[row][col] == 5){
					bg[p].setColor(Color.pink);
					bg[p].fillRect(BlockSize*col+1, BlockSize*row+1, BlockSize-2, BlockSize-2);
				}
				else if(field[row][col] == 6){
					bg[p].setColor(Color.cyan);
					bg[p].fillRect(BlockSize*col+1, BlockSize*row+1, BlockSize-2, BlockSize-2);
				}
				else if(field[row][col] == 7){
					bg[p].setColor(Color.darkGray);
					bg[p].fillRect(BlockSize*col+1, BlockSize*row+1, BlockSize-2, BlockSize-2);
				}
				else if(field[row][col] == 8){
					bg[p].setColor(Color.orange);
					bg[p].fillRect(BlockSize*col+1, BlockSize*row+1, BlockSize-2, BlockSize-2);
				}
				else if(field[row][col] == 10){
					bg[p].setColor(Color.gray);
					bg[p].fillRect(BlockSize*col+1, BlockSize*row+1, BlockSize-2, BlockSize-2);
				}
				else{
					bg[p].setColor(Color.white);
					bg[p].fillRect(BlockSize*col+1, BlockSize*row+1, BlockSize-2, BlockSize-2);
				}
			}
		}
	}
	
	public void inforDraw(int p, GameInfo info){
		renNum[p].setText("Ren: "+info.getRen());
		deleteNum[p].setText("Delete: "+info.getDelete());
		hindNum[p].setText("Hindrance: "+info.getHind());
		point[p].setText("Point: "+info.getPoint());
	}
	
	public void winDraw( boolean win){
		if(win == true){
			player[0].setText("Me: Winner");
			player[1].setText("Rival: Loser");
		}else{
			player[0].setText("Me: Loser");
			player[1].setText("Rival: Winner");
		}
	}
	
	public void display(){
		for(int p = 0; p < 2; p++){
			fg[p].drawImage(image[p], 0, 0, playerPane[p]);
		}
	}
}
