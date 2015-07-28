import java.util.Random;


public class MaleAnimat extends AdultAnimat {
	static final int r = 100;
	final int totallastMatedTimer = 5*r;
	int lastMatedTimer = totallastMatedTimer;
	
	Wave[][] w;
	MGraph g;
	String song;

	public MaleAnimat(int x, int y, Controller c, int gen) {
		super(x, y, c, gen);
		type = 1;
		g = new MGraph();
		song = g.genSong();
				
		w = new Wave[2*r+1][2*r+1];		
		for(int i=-r;i<=r;i++){
			int jval = (int)Math.sqrt(r*r-i*i);
			for(int j=0;j<=jval;j++){
				w[i+r][j] = new Wave(posx+i,posy+j,c,this);
				if(j!=0)
					w[i+r][j+jval] = new Wave(posx+i,posy-j,c,this);
			}
		}
		c.add(this);
		Controller.genCount[0]++;
		Controller.allGenSongs.get(0).add(song);
		//Controller.allGenSongs.get(0).addFirst(String.valueOf((Integer.parseInt(Controller.allGenSongs.get(0).getFirst()))++);
//		Controller.genCounter++;
//		Controller.genSongs.add(song);
	}
	
	public MaleAnimat(int id, int x, int y, Controller c, int gen, int e, MGraph mg) {
		super(id, x, y, c, gen, e);
		type = 1;
		g = mg;
		
		Random z = new Random();
		boolean b = z.nextBoolean();
		if(b){
			g.tweak();
		}
		
		song = g.genSong();

		w = new Wave[2*r+1][2*r+1];		
		for(int i=-r;i<=r;i++){
			int jval = (int)Math.sqrt(r*r-i*i);
			for(int j=0;j<=jval;j++){
				w[i+r][j] = new Wave(posx+i,posy+j,c,this);
				if(j!=0)
					w[i+r][j+jval] = new Wave(posx+i,posy-j,c,this);
			}
		}
		c.add(this);
		if(gen<=Controller.ngens){
			Controller.genCount[gen]++;
			Controller.allGenSongs.get(gen).add(song);
		}
		/*if(Controller.cgen == gen){
			Controller.genCounter++;
			Controller.genSongs.add(song);
		}*/
	}
	
	@Override
	public void die(){
		Controller.mdeaths++;
		if(gen<=Controller.ngens)
			Controller.genCount[gen]--;
		/*if(Controller.cgen == this.gen){
			Controller.genCounter--;
		}*/
		
		try{
			c.e[posx][posy].remove(this);
			c.ll_ma.remove(this);
			c.clqa.remove(this);
			
			for(int i=-r;i<=r;i++){
				int jval = (int)Math.sqrt(r*r-i*i);
				for(int j=0;j<=jval;j++){
					Wave k = w[i+r][j];
					c.e[k.posx][k.posy].remove(k);
					//c.ll_wa.remove(k);
					k = null;
					w[i+r][j] = null;
					if(j!=0){
						k = w[i+r][j+jval];
						c.e[k.posx][k.posy].remove(k);
						//c.ll_w.remove(k);
						k = null;
						w[i+r][j] = null;
					}
				}
			}
		}
		catch(NullPointerException e){
			System.out.println(e);
		}
	}
	
	@Override
	public void moveTo(int m, int n){
		//System.out.println("move:"+id);
		int x = -1+m;
		int y = -1+n;
		
		try{
			c.e[posx][posy].remove(this);
			
			/*for(int i=0;i<2*r+1;i++)
				for(int j=0;j<2*r+1;j++){
					Wave k = w[i][j];
					c.e[k.posx][k.posy].remove(k);
					k.posx = (k.posx+x)%Controller.m;
					k.posy = (k.posy+y)%Controller.n;
					if(k.posx == -1)k.posx = Controller.m-1;
					if(k.posy == -1)k.posy = Controller.n-1;
					c.e[w[i][j].posx][w[i][j].posy].add(k);
				}*/
			
			for(int i=-r;i<=r;i++){
				int jval = (int)Math.sqrt(r*r-i*i);
				for(int j=0;j<=jval;j++){
					Wave k = w[i+r][j];
					c.e[k.posx][k.posy].remove(k);
					k.posx = (k.posx+x)%c.m;
					k.posy = (k.posy+y)%c.n;
					if(k.posx == -1)k.posx = c.m-1;
					if(k.posy == -1)k.posy = c.n-1;
					c.e[k.posx][k.posy].add(k);
					if(j!=0){
						k = w[i+r][j+jval];
						c.e[k.posx][k.posy].remove(k);
						k.posx = (k.posx+x)%c.m;
						k.posy = (k.posy+y)%c.n;
						if(k.posx == -1)k.posx = c.m-1;
						if(k.posy == -1)k.posy = c.n-1;
						c.e[k.posx][k.posy].add(k);
					}
				}
			}
			posx = (posx+x)%c.m;
			posy = (posy+y)%c.n;
			if(posx == -1)posx = c.m-1;
			if(posy == -1)posy = c.n-1;
			c.e[posx][posy].add(this);
		}
		catch(NullPointerException e){
			System.out.println(e);
		}	
	}
	
	public void feedback(String s){
		song = s;
		for(int i=-r;i<=r;i++){
			int jval = (int)Math.sqrt(r*r-i*i);
			for(int j=0;j<=jval;j++){
				w[i+r][j].song = song;
				if(j!=0)
					w[i+r][j+jval].song = song;
			}
		}
	}
	
	public void mate(){
		super.mate();
		lastMatedTimer = totallastMatedTimer;
		feedback(g.posFb(song));
		//System.out.println("+:"+song);
	}
	
	public void sense(){
		if(--lastMatedTimer <= 0){
			lastMatedTimer = totallastMatedTimer;
			feedback(g.negFb(song));
		}		
		super.sense();
	}
	
}
