
public class Signature extends Element{
	double intensity;
	double dist;
	double distsq;
	
	public Signature(int x, int y, Controller c, Creature cr) {
		super(x,y,c);	
		id = cr.id;
		dist = Math.sqrt((Math.pow(x-cr.posx, 2)) + (Math.pow(y-cr.posy, 2)));
		//distsq = Math.pow(x-cr.posx, 2) + Math.pow(y-cr.posy, 2);
		//intensity = 201-Math.abs(cr.posx-posx)-Math.abs(cr.posy-posy);
		//if(distsq==0)intensity=1000;
		//else intensity = 1000/(dist*dist);
		intensity = 101 - dist;
	}

}
