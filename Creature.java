import java.util.Random;


public class Creature extends Element{
	static int count = 0;
	int dirTimeout;
	int diri,dirj;
	
	public Creature(int x, int y, Controller c) {
		super(x,y,c);
		id = ++count;
		//c.hm.put(id, this);
		dirTimeout = 0;
		diri = -1;
		dirj = -1;
	}
	
	public Creature(int id, int x, int y, Controller c) {
		super(x,y,c);
		this.id = id;
		//c.hm.put(id, this);
		dirTimeout = 0;
		diri = -1;
		dirj = -1;
	}
	
	public void moveToRandom(){
		if(dirTimeout==0){
			Random r = new Random();
			int i = r.nextInt(3);
			int j = r.nextInt(3);
			if(i==1 && j==1)
				moveToRandom();
			else{
				diri = i;
				dirj = j;
				dirTimeout = 20;
				moveTo(i,j);
			}
		}
		else{
			dirTimeout--;
			moveTo(diri,dirj);
		}
	}
	
	public void die(){}
	
	public void moveTo(int nposx, int nposy){}
}
