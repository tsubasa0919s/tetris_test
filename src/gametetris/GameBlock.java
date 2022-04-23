package gametetris;

import java.util.*;

public class GameBlock implements Cloneable{

	private int[][] block;
	private int x;
	private int y;
	private int	FieldRows= 23;
	private int	FieldCols = 12;
	private int blockType;
	private int blockNum = 8;
	private int[] blockRand = new int[blockNum];
	private boolean[] blockMovePermission;
	private boolean hardDrop;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GameBlock block = new GameBlock();
		for(int i = 0; i < 20; i++){
			block.newBlock();
		}
	}

	public GameBlock clone(){
		GameBlock object;
		try {
			int[][] tmp = new int[block.length][block.length]; 
			object = (GameBlock) super.clone();
			for(int i = 0; i < block.length; i++){
				for(int j = 0; j < block.length; j++){
					tmp[i][j] = block[i][j];
				}
				object.block = tmp;
			}
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
		return object;
	}

	public GameBlock(){
		blockMovePermission = new boolean[6];
		for(int i = 0; i < 6; i++){
			blockMovePermission[i] = false;
		}
		blockType = 0;
	}

	private int newBlockType(){
		if(blockType > blockNum-2){
			blockType = 0;
		}
		if(blockType == 0){
			Random rnd = new Random();
			for(int i = 0; i < blockNum-1; i++){
				blockRand[i] = rnd.nextInt(blockNum-1)+1;
				int a = blockRand[i];
				for( i = 0; i < blockNum -1; i++){
					if(blockRand[i] == a){
						break;
					}
				}
			}
		}
		return blockRand[blockType++];
	}

	public void newBlock(){
		int n = newBlockType();
		switch(n){
		case 1 :
			block = new int[][] { {0, n, 0},
					{n, n, n},
					{0, 0, 0} };
			x=FieldCols/2-2;
			y=0;
			break;

		case 2 :
			block = new int[][] { {n, n, 0},
					{0, n, n},
					{0, 0, 0} };
			x=FieldCols/2-2;
			y=0;
			break;

		case 3 :
			block = new int[][] { {0, n, n},
					{n, n, 0},
					{0, 0, 0} };
			x=FieldCols/2-2;
			y=0;
			break;

		case 4 :
			block = new int[][] { {0, 0, n},
					{n, n, n},
					{0, 0, 0} };
			x=FieldCols/2-2;
			y=0;
			break;

		case 5 :
			block = new int[][] { {n, 0, 0},
					{n, n, n},
					{0, 0, 0} };
			x=FieldCols/2-2;
			y=0;
			break;

		case 6 :
			block = new int[][] { {n, n},
					{n, n} };
			x=FieldCols/2-2;
			y=0;
			break;

		case 7 :
			block = new int[][] { {0, 0, 0, 0},
					{n, n, n, n},
					{0, 0, 0, 0},
					{0, 0, 0, 0} };
			x=FieldCols/2-2;
			y=0;
			break;

		}
	}
	
	public boolean setBlockPermission(GameField field){
		movePermissionUpdate(field);
		if(!blockMovePermission[5]){
			return false;
		}
		return true;
	}

	public boolean newBlockPermission(GameField field){
		movePermissionUpdate(field);
		if(y == 0 && !blockMovePermission[5]){
			return false;
		}
		return true;
	}
	
	public void moveUP(GameField field){
		while(blockMovePermission[0]){
			y++;
			movePermissionUpdate(field);
		}
		hardDrop = true;
	}
	
	public boolean get_HardDrop(){
		if(hardDrop){
			hardDrop = false;
			return true;
		}
		return false;
	}

	//下移動
	public void moveDown(){
		if(blockMovePermission[0]){
			y++;
		}
	}
	//下移動（ダミーのみ使用可能）
	private void moveDown(boolean permission){
		if(permission){
			y++;
		}
	}

	//右移動
	public void moveRight(){
		if(blockMovePermission[1]){
			x++;
		}
	}

	//右移動 （ダミーのみ使用可能）
	private void moveRight(boolean permisson){
		if(permisson){
			x++;
		}
	}

	//左移動
	public void moveLeft(){
		if(blockMovePermission[2]){
			x--;
		}
	}

	//左移動 （ダミーのみ使用可能）
	private void moveLeft(boolean permisson){
		if(permisson){
			x--;
		}
	}

	//右回転
	public void turnRight(){
		if(blockMovePermission[3]){
			int[][] tmp = new int[block.length][block.length];

			for(int row = 0; row < block.length; row++){
				for(int col = 0; col < block.length; col++){
					tmp[row][col] = block[row][col];
				}
			}

			for(int row = 0; row < block.length; row++){
				for(int col = 0; col < block.length; col++){
					block[col][block.length - 1 - row] = tmp[row][col];
				}
			}
		}
	}

	//右回転（ダミーのみ使用可能）
	private void turnRight(boolean permission){
		if(permission){
			int[][] tmp = new int[block.length][block.length];

			for(int row = 0; row < block.length; row++){
				for(int col = 0; col < block.length; col++){
					tmp[row][col] = block[row][col];
				}
			}

			for(int row = 0; row < block.length; row++){
				for(int col = 0; col < block.length; col++){
					block[col][block.length - 1 - row] = tmp[row][col];
				}
			}
		}
	}

	//左回転
	public void turnLeft(){
		if(blockMovePermission[4]){
			int[][] tmp = new int[block.length][block.length];

			for(int row = 0; row < block.length; row++){
				for(int col = 0; col < block.length; col++){
					tmp[row][col] = block[row][col];
				}
			}

			for(int row = 0; row < block.length; row++){
				for(int col = 0; col < block.length; col++){
					block[block.length - 1 - col][row] = tmp[row][col];
				}
			}
		}
	}

	//左回転（ダミーのみ使用可能）
	private void turnLeft(boolean permission){
		if(permission){
			int[][] tmp = new int[block.length][block.length];

			for(int row = 0; row < block.length; row++){
				for(int col = 0; col < block.length; col++){
					tmp[row][col] = block[row][col];
				}
			}

			for(int row = 0; row < block.length; row++){
				for(int col = 0; col < block.length; col++){
					block[block.length - 1 - col][row] = tmp[row][col];
				}
			}
		}
	}

	public void movePermissionUpdate(GameField field){
		GameBlock[] dummy = new GameBlock[6];
		for(int i = 0; i < 6; i++){
			dummy[i] = clone();
		}
		dummy[0].moveDown(true);
		dummy[1].moveRight(true);
		dummy[2].moveLeft(true);
		dummy[3].turnRight(true);
		dummy[4].turnLeft(true);

		for(int i = 0; i < 6; i++){
			blockMovePermission[i] = true;
			for(int row=0; row < dummy[i].get_BlockSize(); row++){
				if(dummy[i].get_Y() + row < 0)continue;
				if(dummy[i].get_Y() + row > FieldRows-1)break;
				for(int col=0; col < dummy[i].get_BlockSize(); col++){
					if(dummy[i].get_X() + col < 0)continue;
					if(dummy[i].get_X() + col > FieldCols-1)break;
					if(dummy[i].get_Block(row, col) != 0 && field.get_Field(dummy[i].get_Y() + row, dummy[i].get_X() + col) != 0){
						blockMovePermission[i] = false;
						break;
					}
				}
				if(!blockMovePermission[i])break;
			}
		}
	}

	public boolean blockGround(){
		return !blockMovePermission[0];
	}

	//blockをクリア
	public void clear(){
		block = new int[0][0];
	}

	public int[][] get_AllBlock(){
		return block;
	}

	public int get_Block(int row, int col){
		return block[row][col];
	}

	public int get_BlockSize(){
		return block.length;
	}

	public int get_X(){
		return x;
	}

	public int get_Y(){
		return y;
	}

	public void debug(){
		System.out.println("y="+y+",x="+x);
		for(int i = 0; i < block.length; i++){
			for(int j = 0; j < block.length; j++){
				System.out.print(block[i][j]);
			}
			System.out.println();
		}
	}
}
