import java.awt.Color;
import java.awt.Graphics;
import java.util.Iterator;

import javax.swing.JPanel;

public class Display extends JPanel{
	
	private static final long serialVersionUID = 1L;
	Controller c;
	static final int a_rad = 5;//radius of adult animats
	static final int k_rad = 3;//radius of kid animats
	static final int w_rad = 3;//radius of worms
	static final int s_rad = 2;//radius of signatures

	public Display(Controller c) {
		this.c = c;
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		this.setBackground(Color.WHITE);
		
		g.setColor(Color.BLACK);
		g.drawLine(0, 0, c.m, 0);
		g.drawLine(0, 0, 0, c.n);
		g.drawLine(c.m, 0, c.m, c.n);
		g.drawLine(0, c.n, c.m, c.n);
		
		g.setColor(new Color(0f,0f,1f,0.5f));
		g.fillOval(10, c.n+5, a_rad*2, a_rad*2);
		g.drawString("Male Animats", 20, c.n+15);
		g.drawString(String.valueOf(c.ll_ma.size())+"/"+String.valueOf(Controller.mdeaths), 100, c.n+15);
		
		g.setColor(new Color(1f,0f,0f,0.5f));
		g.fillOval(140, c.n+5, a_rad*2, a_rad*2);
		g.drawString("Female Animats", 150, c.n+15);
		g.drawString(String.valueOf(c.ll_fa.size())+"/"+String.valueOf(Controller.fdeaths), 250, c.n+15);
		
		g.setColor(Color.GREEN);
		g.fillOval(290, c.n+5, w_rad*2, w_rad*2);
		g.drawString("Kid Animats", 300, c.n+15);
		g.drawString(String.valueOf(c.ll_ka.size())+"/"+String.valueOf(Controller.kdeaths), 370, c.n+15);
		
		g.setColor(Color.BLACK);
		g.fillOval(420, c.n+5, k_rad*2, k_rad*2);
		g.drawString("Worms", 430, c.n+15);
		g.drawString(String.valueOf(c.ll_w.size())+"/"+String.valueOf(Controller.wdeaths), 480, c.n+15);
		
		g.setColor(new Color(0f,0f,1f,0.5f));
		Iterator<MaleAnimat> li_ma = c.ll_ma.iterator();
		while(li_ma.hasNext()){
			MaleAnimat ma = li_ma.next();
			g.fillOval(ma.posx-a_rad, ma.posy-a_rad, a_rad*2, a_rad*2);
		}
		
		g.setColor(new Color(1f,0f,0f,0.5f));
		Iterator<FemaleAnimat> li_fa = c.ll_fa.iterator();
		while(li_fa.hasNext()){
			FemaleAnimat fa = li_fa.next();
			g.fillOval(fa.posx-a_rad, fa.posy-a_rad, a_rad*2, a_rad*2);
		}
		
		g.setColor(Color.BLACK);
		Iterator<Worm> li_w = c.ll_w.iterator();
		while(li_w.hasNext()){
			Worm w = li_w.next();
			g.fillOval(w.posx-w_rad, w.posy-w_rad, w_rad*2, w_rad*2);
		}
		
		g.setColor(Color.GREEN);
		Iterator<KidAnimat> li_ka = c.ll_ka.iterator();
		while(li_ka.hasNext()){
			KidAnimat ka = li_ka.next();
			g.fillOval(ka.posx-k_rad, ka.posy-k_rad, k_rad*2, k_rad*2);
		}
		repaint();
	}
}
