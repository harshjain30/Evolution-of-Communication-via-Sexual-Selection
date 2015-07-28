public class FemaleAnimat extends AdultAnimat{
	static final int r = 100;
	boolean on = true;
	int offTimer = 0;
	
	FGraph g;	
	Pheromone p[][];
	//HashSet<Pheromone> p;// = new AbstractSet<Pheromone>();

	public FemaleAnimat(int x, int y, Controller c, int gen) {
		super(x, y, c, gen);
		type = 2;
		g = new FGraph();
		//p = new HashSet<Pheromone>(4*r*r);
		p = new Pheromone[2*r+1][2*r+1];
		for(int i=-r;i<=r;i++){
			int jval = (int)Math.sqrt(r*r-i*i);
			for(int j=0;j<=jval;j++){
				p[i+r][j] = new Pheromone(posx+i,posy+j,c,this);
				if(j!=0)
					p[i+r][j+jval] = new Pheromone(posx+i,posy-j,c,this);
			}
		}
		c.add(this);
	}
	
	public FemaleAnimat(int id, int x, int y, Controller c, int gen, int e, FGraph fg) {
		super(id, x, y, c, gen, e);
		type = 2;
		g = fg;

		p = new Pheromone[2*r+1][2*r+1];
		
		for(int i=-r;i<=r;i++){
			int jval = (int)Math.sqrt(r*r-i*i);
			for(int j=0;j<=jval;j++){
				p[i+r][j] = new Pheromone(posx+i,posy+j,c,this);
				if(j!=0)
					p[i+r][j+jval] = new Pheromone(posx+i,posy-j,c,this);
			}
		}
		c.add(this);
	}
	
	public void toggleOff(){
		//System.out.println("called:"+id);
		try{
			offTimer = (int) (r*1.5);
			on = false;
			for(int i=-r;i<=r;i++){
				int jval = (int)Math.sqrt(r*r-i*i);
				for(int j=0;j<=jval;j++){
					p[i+r][j].on = false;
					if(j!=0)
						p[i+r][j+jval].on = false;
				}
			}
		}
		catch(NullPointerException e){
			System.out.println(e);
		}
	}
	
	public void toggleOn(){
		//System.out.println("revoked");
		try{
			offTimer = 0;
			on = true;
			for(int i=-r;i<=r;i++){
				int jval = (int)Math.sqrt(r*r-i*i);
				for(int j=0;j<=jval;j++){
					p[i+r][j].on = true;
					if(j!=0)
						p[i+r][j+jval].on = true;
				}
			}
		}
		catch(NullPointerException e){
			System.out.println(e);
		}
	}
	
	@Override
	public void die(){
		//System.out.println("die:"+id);
		//System.out.println(c.clqa);
		Controller.fdeaths++;
		try{
			c.e[posx][posy].remove(this);
			c.ll_fa.remove(this);
			c.clqa.remove(this);
			
			for(int i=-r;i<=r;i++){
				int jval = (int)Math.sqrt(r*r-i*i);
				for(int j=0;j<=jval;j++){
					Pheromone k = p[i+r][j];
					c.e[k.posx][k.posy].remove(k);
					//c.ll_p.remove(k);
					k = null;
					p[i+r][j] = null;
					if(j!=0){
						k = p[i+r][j+jval];
						c.e[k.posx][k.posy].remove(k);
						//c.ll_p.remove(k);
						k = null;
						p[i+r][j] = null;
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
			for(int i=-r;i<=r;i++){
				int jval = (int)Math.sqrt(r*r-i*i);
				for(int j=0;j<=jval;j++){
					Pheromone k = p[i+r][j];
					c.e[k.posx][k.posy].remove(k);
					k.posx = (k.posx+x)%c.m;
					k.posy = (k.posy+y)%c.n;
					if(k.posx == -1)k.posx = c.m-1;
					if(k.posy == -1)k.posy = c.n-1;
					c.e[k.posx][k.posy].add(k);
					if(j!=0){
						k = p[i+r][j+jval];
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
	
	public void sense(){
		super.sense();
		if(!on)
			if(--offTimer == 0)
				toggleOn();
	}
}
