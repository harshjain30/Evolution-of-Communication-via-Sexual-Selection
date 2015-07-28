
public class KidAnimat extends Animat{
	int timeToPuberty = 100;
	MGraph mg;
	FGraph fg;
	boolean isFemale; 

	public KidAnimat(int x, int y, Controller c, int gen, MGraph g) {
		super(x, y, c, gen);
		type = 3;
		mg = g;
		fg = null;
		isFemale = false;
		c.add(this);
	}
	
	public KidAnimat(int x, int y, Controller c, int gen, FGraph g) {
		super(x, y, c, gen);
		type = 3;
		fg = g;
		mg = null;
		isFemale = true;
		c.add(this);
	}
	
	@Override
	public void die(){
		Controller.kdeaths++;
		c.e[posx][posy].remove(this);
		c.ll_ka.remove(this);
		c.clqa.remove(this);
	}
	
	@Override
	public void moveTo(int m, int n){
		int x = -1+m;
		int y = -1+n;
		c.e[posx][posy].remove(this);
		posx = (posx+x)%c.m;
		posy = (posy+y)%c.n;
		if(posx == -1)posx = c.m-1;
		if(posy == -1)posy = c.n-1;
		c.e[posx][posy].add(this);
	}
	
	public void sense(){
		super.sense();
		
		boolean dead = false;
		
		if(--timeToPuberty == 0){
			c.mature((KidAnimat)this);
			dead = true;
		}
		
		if(!dead){
			if(c.timeUnits%10 == 0){
				if(--energy<=0){
					c.kill(this);
					Controller.deadByEnergy++;
					dead = true;
				}
			}
		}
	}

}
