package gametetris;

import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class GameField implements Serializable {

	private int	FieldRows = 23;
	private int	FieldCols = 12;
	private int [][] field = new int[FieldRows][FieldCols];
	private int delNumber;
	private int[] delRows = new int[FieldRows];
	private int hindNum; 
	private boolean hindFlag;
	private int sendCheckNum;
	private int getCheckNum;
	private boolean endFlag;
	private int renNumber;
	private int point;

	public GameField(){
		hindNum = 0;
		delNumber = 0;
		sendCheckNum = 0;
		getCheckNum = -1;
		renNumber = 0;
		point = 0;
		hindFlag = false;
		endFlag = false;
		newField();
	}

	public void newField(){
		for(int col=0; col < FieldCols; col++){
			if(col < FieldCols/2-2 || FieldCols/2+1 < col){
				field[0][col] = 100;
			}else{
				field[0][col] = 0;
			}
		}
		for(int col=0; col < FieldCols; col++){
			if(col < FieldCols/2-2 || FieldCols/2+1 < col){
				field[1][col] = 9;
			}
		}
		for(int row=2; row < FieldRows; row++){
			//左の壁
			field[row][0] = 9;
			//空間
			for(int col=1; col < FieldCols; col++){
				field[row][col] = 0;
			}
			//右の壁
			field[row][FieldCols-1] = 9;
		}
		//フィールドの底
		for(int col=0; col < FieldCols; col++){
			field[FieldRows-1][col] = 9;
		}
	}

	public int get_Field(int row, int col){
		return field[row][col];
	}

	public int[][] get_AllField(){
		return field;
	}

	public void set_Field(int row, int col, int blockNum){
		field[row][col] = blockNum;
	}

	public void set_BlockField(GameBlock block){
		for(int i = 0; i < block.get_BlockSize(); i++){
			for(int j = 0; j < block.get_BlockSize(); j++){
				if(i+block.get_Y() < 0 || j+block.get_X() < 0)continue;
				if(block.get_Block(i, j) != 0 && get_Field(i+block.get_Y(), j+block.get_X()) != 9){
					set_Field(i+block.get_Y(), j+block.get_X(), block.get_Block(i, j));
				}
			}
		}
	}

	public void unset_BlockField(GameBlock block){
		for(int i = 0; i < block.get_BlockSize(); i++){
			for(int j = 0; j < block.get_BlockSize(); j++){
				if(i+block.get_Y() < 0 || j+block.get_X() < 0)continue;
				if(block.get_Block(i, j) != 0 && get_Field(i+block.get_Y(), j+block.get_X()) != 9){
					set_Field(i+block.get_Y(), j+block.get_X(), 0);
				}
			}
		}
	}

	public boolean checkLine(){
		int del = 0;
		int cells;
		for(int row=2; row < FieldRows-1; row++){
			cells = 0;
			for(int col=1; col < FieldCols-1; col++){
				if(field[row][col]!=0){
					cells++;
				}else{
					break;
				}
			}
			if(cells == FieldCols-2){
				delRows[del++] = row;
			}
		}
		if(del != 0){
			delNumber = del;
			return true;
		}else{
			return false;
		}
	}

	public void deleteLine(){
		for(int n = 0; n < delNumber; n++){
			for(int col=1; col < FieldCols-1; col++){
				field[delRows[n]][col] = 0;
			}
		}
		renNumber++;
		point += (delNumber * delNumber + renNumber) * 100;
	}

	public void killLine(){

		for(int n=0; n < delNumber; n++){

			for(int row=delRows[n]; row > 2; row--){
				for(int col=1; col < FieldCols-1; col++){
					field[row][col] = field[row-1][col];
				}
			}

			for(int col=1; col < FieldCols-1; col++){
				field[3][col] = 0;
			}

		}
	}

	public void hindrance(){
		for(int row = 0; row < 2; row++){
			for(int col=FieldCols/2-2; col < FieldCols/2+2; col++){
				if(row + hindNum < FieldRows - 1){
					field[row][col]=field[row+hindNum][col];
				}
			}
		}
		for(int row = 2; row < FieldRows-1; row++){
			for(int col=1; col < FieldCols-1; col++){
				if(row + hindNum < FieldRows - 1){
					field[row][col]=field[row+hindNum][col];
				}
			}
		}
		for(int row = FieldRows - hindNum - 1; row < FieldRows-1; row++){
			int x = ((int)(Math.random()*10))+1;
			if(row < 0)continue;
			if(row < 2){
				for(int col=FieldCols/2-2; col < FieldCols/2+2; col++){
					if(col == x){
						field[row][col] = 0;
					}else{
						field[row][col] = 10;
					}
				}
			}else{
				for(int col=1; col < FieldCols-1; col++){
					if(col == x){
						field[row][col] = 0;
					}else{
						field[row][col] = 10;
					}
				}
			}
		}
		hindFlag = true;
	}
	
	public int getHind(){
		return hindNum;
	}
	
	public void setHindZero(){
		hindNum = 0;
	}
	
	public void setDeleteNumber(int n){
		delNumber = n;
	}
	
	public int  getDleteNumber(){
		int number  = delNumber;
		return number;
	}
	
	public void setRenNumber(int n){
		renNumber = n;
	}
	
	public void setHindNum(int n){
		hindNum = n;
	}
	
	public void addHindNum(int n){
		hindNum = hindNum + n;
	}
	
	public void addPoint(int n){
		point += n;
	}
	
	public int getPoint(){
		return point;
	}

	public void debug(){
		for(int i = 0; i < FieldRows; i++){
			for(int j = 0; j < FieldCols; j++){
				System.out.print(field[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public void sendCheck(){
		int n = (int)(Math.random()*10000000);
		if(sendCheckNum == n){
			sendCheckNum = n + (int)(Math.random()*10000000) + 1;
		}else{
			sendCheckNum = n;
		}
	}
	
	public int getsendCheck(){
		return sendCheckNum;
	}
	
	public void returnCheck(GameField field){
		getCheckNum = field.getsendCheck();
	}
	
	public int getCheck(){
		return getCheckNum;
	}
	
	public boolean check(GameField field){
		if(getCheckNum == field.getsendCheck()){
			return true;
		}
		return false;
	}
	
	public void setCheck(int n){
		getCheckNum = n;
	}
	
	
	public GameField serialize() {
		return this;
	}
	
	public void setEnd(){
		endFlag = true;
	}
	
	public boolean getEnd(){
		return endFlag;
	}
	
	public void setRenZero(){
		renNumber = 0;
	}
	
	public int getRen(){
		return renNumber;
	}
	
	public void setHindFrag(boolean b){
		hindFlag = b;
	}
	
	public boolean hindPermisson(){
		return hindFlag;
	}
}
