import java.util.Iterator;

public class Animat extends Creature{
	
	int life = 1000;
	int gen;
	NeuralNet nn;
	int energy;
	int libido;
	
	public Animat(int x, int y, Controller c, int gen){
		super(x,y,c);
		
		this.gen = gen;
		nn = new NeuralNet(this,c);
		energy = 100;
		libido = 50;			
	}
	
	public Animat(int id, int x, int y, Controller c, int gen, int e){
		super(id,x,y,c);
		
		this.gen = gen;
		nn = new NeuralNet(this,c);
		energy = e;
		libido = 0;			
	}
	
	public void eat(){
		energy += 30;
		if(energy>=100)
			energy = 100;
	}
	
	public void gradientSearch(Element e){
		int u = -1;
		int v = -1;
		double max = 0;
		boolean flag = false;		
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				flag = false;
				if(i==1 && j==1)continue;
				int ix = (e.posx-1+i)%c.m;
				int iy = (e.posy-1+j)%c.n;
				if(ix<0)
					ix += c.m;
				if(iy<0)
					iy += c.n;
				Iterator<Element> ie = c.e[ix][iy].iterator();
				while(ie.hasNext()){
					Element el = ie.next();
					if(el.id == e.id && el.type == e.type){
						if(((Signature)el).intensity >= ((Signature)e).intensity && ((Signature)el).intensity>max){
							max = ((Signature)el).intensity;
							u = i;
							v = j;
							flag = true;
							break;
						}
					}
					if(flag)break;
				}
			}
		}
		if(u<0 || v<0){
			this.sense();
		}
		else{
			moveTo(u,v);
		}
	}
	
	public void sense(){//senses all elements, processes only the last element of each type
		//System.out.println(c.e[posx][posy]);
		Iterator<Element> i_el = c.e[posx][posy].iterator();
		nn.nnet[0].input[0] = 1;
		while(i_el.hasNext()){
			Element el = i_el.next();
			if(el.id == id)
				continue;
			nn.nnet[0].input[0] = 0;
			switch(el.type){
			case 1://male animat
				nn.nnet[1].input[0] = 1;
				nn.nnet[1].e = (MaleAnimat)el;
				break;
			case 2://female animat
				if(((FemaleAnimat)el).on){
					nn.nnet[2].input[0] = 1;
					nn.nnet[2].e = (FemaleAnimat)el;
				}
				break;
			case 3://kid animat
				nn.nnet[3].input[0] = 1;
				nn.nnet[3].e = (KidAnimat)el;
				break;
			case 4://worm
				nn.nnet[4].input[0] = 1;
				nn.nnet[4].e = (Worm)el;
				break;
			case 5://wave
				nn.nnet[5].input[0] = 1;
				nn.nnet[5].e = (Wave)el;
				nn.nnet[5].weight = ((Wave)nn.nnet[5].e).intensity;
				break;
			case 6://pheromone
				if(((Pheromone)el).on){
					nn.nnet[6].input[0] = 1;
					nn.nnet[6].e = (Pheromone)el;
					nn.nnet[6].weight = ((Pheromone)nn.nnet[6].e).intensity;
				}
				break;
			case 7://odour
				nn.nnet[7].input[0] = 1;
				nn.nnet[7].e = (Odour)el;
				nn.nnet[7].weight = ((Odour)nn.nnet[7].e).intensity;
				break;
			default: break;
			}
		}
		nn.process();
	}
}
