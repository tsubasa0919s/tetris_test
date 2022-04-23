package gametetris;

public class GameTimer extends Thread {
	private long time0;
	private long time1;
	private long pastTime;
	private boolean timer_on;
	
	public Thread TetrisTimerThread;
	
	GameTimer(){
		time0		= 0;
		time1		= 0;
		pastTime	= 0;
		timer_on	= false;	//初期状態では計測未開始
	}
	
	
	public void timerStart(){
		timer_on = true;
	}
	
	
	public void timerStop(){
		timer_on = false;
	}
	
	public void timerReset(){
		time0 = 0;
		pastTime = 0;
	}
	
	public long getTime(){
		return pastTime;
	}
	
	public void run(){
		try {
			while(true){
				if(timer_on){
					if(time0 == 0){
						time0 = System.currentTimeMillis();
						pastTime = 0;
					}else{
						time1		= System.currentTimeMillis();
						pastTime	= pastTime + (time1 - time0);
						time0 		= time1;
					}
				}else{
					time0 = 0;
				}
				this.sleep(10);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}	
	}
	
	public static void main(String[] args){
		GameTimer timer = new GameTimer();
		timer.start();
		timer.timerStart();
		while(true){
			long a = timer.getTime();
			System.out.println(a);
		}
	}
}
