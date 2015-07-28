
public class AdultAnimat extends Animat{
	boolean dead = false;
	
	public AdultAnimat(int x, int y, Controller c, int gen) {
		super(x,y,c,gen);
	}
	
	public AdultAnimat(int id, int x, int y, Controller c, int gen, int e){
		super(id,x,y,c,gen,e);
	}
	
	public void mate(){
		libido = 0;
		energy -= 40;
		if(energy<=0)
			Controller.deadBySex++;
	}
	
	public void sense(){
		super.sense();
		 
		if(--life <= 0){
			c.kill(this);
			Controller.deadByLife++;
			dead = true;
		}
		
		if(!dead){
			if(c.timeUnits%5 == 0){
				if(++libido>100)
					libido = 100;
			}
			if(c.timeUnits%15 == 0){
				if(--energy<=0){
					c.kill(this);
					Controller.deadByEnergy++;
					dead = true;
				}
			}
		}
	}

}
