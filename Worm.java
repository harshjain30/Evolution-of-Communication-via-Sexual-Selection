
public class Worm extends Creature{
	static final int r = 100;
	
	Odour o[][];

	public Worm(int x, int y, Controller c) {
		super(x, y, c);
		type = 4;
		c.add(this);
		
		o = new Odour[2*r+1][2*r+1];
				
		for(int i=-r;i<=r;i++){
			int jval = (int)Math.sqrt(r*r-i*i);
			for(int j=0;j<=jval;j++){
				o[i+r][j] = new Odour(posx+i,posy+j,c,this);
				if(j!=0)
					o[i+r][j+jval] = new Odour(posx+i,posy-j,c,this);
			}
		}
	}
	
	@Override
	public void die(){
		Controller.wdeaths++;
		c.e[posx][posy].remove(this);
		c.ll_w.remove(this);
		
		for(int i=-r;i<=r;i++){
			int jval = (int)Math.sqrt(r*r-i*i);
			for(int j=0;j<=jval;j++){
				Odour k = o[i+r][j];
				c.e[k.posx][k.posy].remove(k);
				//c.ll_o.remove(k);
				k = null;
				o[i+r][j] = null;
				if(j!=0){
					k = o[i+r][j+jval];
					c.e[k.posx][k.posy].remove(k);
					//c.ll_o.remove(k);
					k = null;
					o[i+r][j] = null;
				}
			}
		}
	}
	
	@Override
	public void moveTo(int m, int n){
		int x = -1+m;
		int y = -1+n;
		c.e[posx][posy].remove(this);
		for(int i=-r;i<=r;i++){
			int jval = (int)Math.sqrt(r*r-i*i);
			for(int j=0;j<=jval;j++){
				Odour k = o[i+r][j];
				c.e[k.posx][k.posy].remove(k);
				k.posx = (k.posx+x)%c.m;
				k.posy = (k.posy+y)%c.n;
				if(k.posx == -1)k.posx = c.m-1;
				if(k.posy == -1)k.posy = c.n-1;
				c.e[k.posx][k.posy].add(k);
				if(j!=0){
					k = o[i+r][j+jval];
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
}
