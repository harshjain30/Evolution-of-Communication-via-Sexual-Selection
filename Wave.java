
public class Wave extends Signature{
	String song;
	
	public Wave(int x, int y, Controller c, MaleAnimat ma) {
		super(x,y,c,ma);
		type = 5;
		song = ma.song;
	}
}
