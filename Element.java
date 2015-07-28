
public class Element {
	int type;
	int posx,posy;
	Controller c;
	int id;
	
	Element(){}
	
	Element(int x, int y, Controller c){
		posx = x%c.m;
		posy = y%c.n;
		if(posx<0)posx = c.m+posx;
		if(posy<0)posy = c.n+posy;
		this.c = c;
		c.e[posx][posy].add(this);
	}	
}
