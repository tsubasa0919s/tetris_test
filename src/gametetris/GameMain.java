package gametetris;

public class GameMain {
	private GameTimer timer;
	private GameKey key;
	private GameGUI game;
	private GameField field1;
	private GameField field2;
	private GameInfo info1;
	private GameInfo info2;
	private GameField dummy;
	private GameBlock block1;
	private GameClient client;
	private GameEffect effect;
	private int waitingTime = 500; //落下タイム
	private int sleepTime = 180; //キー押下時のWatingTime
	
	public GameMain(GameClient c) { //ソケット接続時
		timer = new GameTimer();
		key = new GameKey();
		game = new GameGUI(key);
		effect = new GameEffect();
		field1 = new GameField();
		field2 = new GameField();
		info1 = new GameInfo();
		info2 = new GameInfo();
		block1 = new GameBlock();
		client = c;
		
		clientSet();
		
		timer.start();
		timer.timerStart(); //タイマーをスタート
		
		block1.newBlock(); //新規ブロック生成
		field1.set_BlockField(block1); //Fieldにブロックを設置
		
		game.show(); //画面表示
		game.fieldDraw(0, field1.get_AllField());
		game.fieldDraw(1, field2.get_AllField());
		info1.setInfo(field1);
		info2.setInfo(field2);
		game.inforDraw(0, info1);
		game.inforDraw(1, info2);
		game.display();
	}
	
	public GameMain() { //ソケット未接続時
		timer = new GameTimer();
		key = new GameKey();
		game = new GameGUI(key);
		effect = new GameEffect();
		field1 = new GameField();
		field2 = new GameField();
		info1 = new GameInfo();
		info2 = new GameInfo();
		block1 = new GameBlock();
		client = null;
		
		timer.start();
		timer.timerStart(); //タイマーをスタート
			
		block1.newBlock(); //新規ブロック生成
		field1.set_BlockField(block1); //Fieldにブロックを設置
		
		game.show(); //画面表示
		game.fieldDraw(0, field1.get_AllField());
		game.fieldDraw(1, field2.get_AllField());
		info1.setInfo(field1);
		info2.setInfo(field2);
		game.inforDraw(0, info1);
		game.inforDraw(1, info2);
		game.display();
	}

	public void clientSet(){
		field1.sendCheck(); //送信チェック付加
		client.sendGameField(field1); //自分のFieldデータ送信
		
		System.out.println("Wainting Other Player");
		while(true){ //受信チェック
			dummy = client.fetchGameField();
			if(dummy != null){
				field1.returnCheck(dummy);
				client.sendGameField(field1);
				if(dummy.check(field1)){
					break;
				}
			}
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Start");
		field2 = dummy;
	}

	public boolean run(){ //ゲームメイン処理
		if(client != null){
			field2 = client.fetchGameField(); //相手のFieldデータを取得
			if(field2.getEnd() || field2 == null){ //相手が終了した際
				info2.setInfo(field2);
				game.inforDraw(1, info2);
				game.fieldDraw(1, field2.get_AllField());
				game.winDraw(true);
				game.display();
				effect.win();
				client.close(); //ソケットを閉じる
				effect.close(); //MIDIを閉じる
				return false;
			}
			if(!field1.check(field2)){ //受信チェック
				dummy = field2;
				field1.returnCheck(field2);
			}
			if(dummy.hindPermisson()){ //受信チェックで重複した場合はfalse
				field1.addHindNum(dummy.getDleteNumber() + (dummy.getRen() - 1)); //Hindranceを追加
				dummy.setHindFrag(false);
			}
			client.sendGameField(field1); //自分のFieldを送信
		}
		
		field1.unset_BlockField(block1); //FieldからCurrent Blockを消去

		if(key.get_moveUP()){ //上を押下
			block1.movePermissionUpdate(field1);
			block1.moveUP(field1);
			effect.hardDrop();
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			effect.stop();
		}

		if(key.get_moveDown()){ //下を押下
			block1.movePermissionUpdate(field1);
			block1.moveDown();
			effect.moveDown();
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			effect.stop();
		}

		if(key.get_moveLeft()){ //左を押下
			block1.movePermissionUpdate(field1);
			block1.moveLeft();
			effect.moveLeft();
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			effect.stop();
		}

		if(key.get_moveRight()){ //右を押下
			block1.movePermissionUpdate(field1);
			block1.moveRight();
			effect.moveRight();
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			effect.stop();
		}

		if(key.get_turnLeft()){ //zを押下
			block1.movePermissionUpdate(field1);
			block1.turnLeft();
			effect.turnLeft();
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			effect.stop();
		}

		if(key.get_turnRight()){ //zを押下
			block1.movePermissionUpdate(field1);
			block1.turnRight();
			effect.turnRight();
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			effect.stop();
		}

		if(key.get_Start()){ //spaceを押下
			field1.setHindNum(20); //自殺宣告
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}


		block1.movePermissionUpdate(field1);
		if(block1.blockGround()){ //ブロックが接地
			field1.set_BlockField(block1); //BlockをFieldに追加
			field1.setDeleteNumber(0); //Delete Line数を初期化
			if(timer.getTime() >= waitingTime || key.get_moveDown() || block1.get_HardDrop()){
				if(field1.checkLine()){ //消去ラインのチェック
					field1.deleteLine(); //消去
					field1.killLine();
					field1.sendCheck(); //送信チェック
					effect.delete(field1.getDleteNumber(), field1.getRen());
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					effect.stop();
				}else{
					field1.setRenZero(); //Ren数の初期化
				}
				field1.hindrance(); //おじゃま追加
				effect.hind(field1.getHind());
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				field1.setHindZero();
				effect.stop();
				block1.newBlock(); //新規ブロック生成
				if(!block1.newBlockPermission(field1)){ //ゲームオーバ判定
					game.fieldDraw(0, field1.get_AllField());
					game.fieldDraw(1, field2.get_AllField());
					info1.setInfo(field1);
					info2.setInfo(field2);
					game.inforDraw(0, info1);
					game.inforDraw(1, info2);
					game.display();
					field1.setEnd();
					field1.sendCheck();
					if(client != null){
						client.sendGameField(field1);
						game.winDraw(false);
						game.display();
						effect.lose();
						while(client.fetchGameField() != null){}
						client.close();
						effect.close();
					}else{
						effect.lose();
					}
					return false;
				}
				timer.timerReset(); //タイマーリセット
			}
		}else if(timer.getTime() >= waitingTime){ //自由落下
			block1.movePermissionUpdate(field1);
			block1.moveDown();
			timer.timerReset();
		}

		field1.set_BlockField(block1); //BlockをFieldに追加
		game.fieldDraw(0, field1.get_AllField()); //Fieldを描画
		game.fieldDraw(1, field2.get_AllField());
		info1.setInfo(field1);
		info2.setInfo(field2);
		game.inforDraw(0, info1);
		game.inforDraw(1, info2);
		game.display();
		return true;
	}
}
