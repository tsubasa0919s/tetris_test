package gametetris;

public class GameInfo {
	private int renNum;
	private int deleteNum;
	private int hindNum;
	private int point;
	
	public GameInfo() {
		renNum = 0;
		deleteNum = 0;
		hindNum = 0;
		point = 0;
	}
	
	public void setInfo(GameField field){
		renNum = field.getRen();
		deleteNum = field.getDleteNumber();
		hindNum = field.getHind();
		point = field.getPoint();
	}
	
	public int getRen(){
		return renNum;
	}
	
	public int getDelete(){
		return deleteNum;
	}
	
	public int getHind(){
		return hindNum;
	}
	
	public int getPoint(){
		return point;
	}
	
	public void debug(){
		System.out.println("renNum: "+ renNum);
		System.out.println("deleteNum: "+deleteNum);
		System.out.println("hindNum: "+hindNum);
		System.out.println("point: "+point);
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
