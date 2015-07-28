
public class NeuralNet {
	static final int nn = 17;
	Neuron[] nnet;
	Animat a;
	Controller c;
	String songInMemory;
	boolean likedSongInMemory = false;
	
	public NeuralNet(Animat a, Controller c){
		this.a = a;
		this.c = c;
		
		nnet = new Neuron[nn];
		nnet[0] = new Neuron(1);
		nnet[1] = new Neuron(3);
		nnet[2] = new Neuron(3);
		nnet[3] = new Neuron(1);
		nnet[4] = new Neuron(2);
		nnet[5] = new Neuron(3);
		nnet[6] = new Neuron(3);
		nnet[7] = new Neuron(2);
		nnet[8] = new Neuron(1);//horny
		nnet[9] = new Neuron(1);//hungry
		nnet[10] = new Neuron(2);//se for 1
		nnet[11] = new Neuron(2);//se for 5
		nnet[10].weight = 1000;
		nnet[2].weight = 1000;
		nnet[4].weight = 1000;
		
		if(a.type == 1){
			nnet[2].input[1] = 1;
			nnet[6].input[1] = 1;
		}
		else if(a.type==2){
			nnet[1].input[1] = 1;
			nnet[5].input[1] = 1;
		}
	}
	
	public void reset(){
		for(int i=0;i<12;i++)
			nnet[i].reset();
		
		if(a.type == 1){
			nnet[1].input[1] = 0;
			nnet[5].input[1] = 0;
			nnet[2].input[1] = 1;
			nnet[6].input[1] = 1;
		}
		else if(a.type==2){
			nnet[1].input[1] = 1;
			nnet[5].input[1] = 1;
			nnet[2].input[1] = 0;
			nnet[6].input[1] = 0;
		}
	}
	
	public void chooseTask(){
		int n = -1;
		int max = -1;
		for(int i=0;i<12;i++){
			if(i==1 || i==5|| i==8 || i==9)
				continue;
			if(max<nnet[i].out){
				max = nnet[i].out;
				n = i;
			}
		}
		if(max == 0)n=0;
		switch(n){
		case 0: //no elements found
			a.moveToRandom();
			break;
		case 2: //wait to mate with the female
			break;
		case 3: //found kid animat, keep moving
			a.moveToRandom();
			break;
		case 4://eat the worm
			a.dirTimeout = 0;
			c.eat((Animat)a, (Worm) nnet[4].e);
			break;
		case 6://follow the female
			a.dirTimeout = 0;
			a.gradientSearch((Pheromone)nnet[6].e);
			break;
		case 7://follow the worm
			a.dirTimeout = 0;
			a.gradientSearch((Odour)nnet[7].e);
			break;
		case 10://mate with the mate
			a.dirTimeout = 0;
			c.mate((FemaleAnimat)a,(MaleAnimat)nnet[10].e);
			break;
		case 11://follow the male
			a.dirTimeout = 0;
			a.gradientSearch((Wave)nnet[11].e);
			break;
		default: break;
		}
	}
	
	public void process(){
		nnet[0].out = nnet[0].input[0];
		
		nnet[8].input[0] = a.libido;
		nnet[8].out = nnet[8].input[0]-30;
		if(nnet[8].out<0)nnet[8].out = 0;
		
		nnet[9].input[0] = a.energy;
		nnet[9].out = 80-nnet[9].input[0];
		if(nnet[9].out<0)nnet[9].out = 0;
		
		nnet[1].input[2] = nnet[8].out;
		
		nnet[2].input[2] = nnet[8].out;

		nnet[4].input[1] = nnet[9].out;
		
		nnet[5].input[2] = nnet[8].out;
		
		nnet[6].input[2] = nnet[8].out;
		
		nnet[7].input[1] = nnet[9].out;
		
		for(int i=1;i<8;i++)
			nnet[i].out = nnet[i].fire();
		
		if(nnet[1].out>0){
			nnet[10].input[0] = nnet[1].out;
			nnet[10].e = nnet[1].e;
			
			String song = ((MaleAnimat)nnet[10].e).song;
			if(songInMemory == song){
				if(likedSongInMemory)
					nnet[10].input[1] = 1;
				else
					nnet[10].input[1] = 0;
			}
			else{
				nnet[10].input[1] = ((FemaleAnimat)a).g.calScoreAndSaturate(song);
				songInMemory = song;
				if(nnet[10].input[1] == 1)
					likedSongInMemory = true;
				else
					likedSongInMemory = false;
			}
			
			if(nnet[10].input[1] == 0 && ((FemaleAnimat)a).on)
				((FemaleAnimat)a).toggleOff();
			if(nnet[10].input[1] == 1 && !((FemaleAnimat)a).on)
				((FemaleAnimat)a).toggleOn();
			
			nnet[10].out = (int)nnet[10].fire();
		}
		
		if(nnet[5].out>0){
			nnet[11].input[0] = nnet[5].out;
			nnet[11].e = nnet[5].e;
			
			String song = ((Wave)nnet[11].e).song;
			if(songInMemory == song){
				if(likedSongInMemory)
					nnet[11].input[1] = 1;
				else
					nnet[11].input[1] = 0;
			}
			else{
				nnet[11].input[1] = ((FemaleAnimat)a).g.calScoreAndSaturate(song);
				songInMemory = song;
				if(nnet[11].input[1] == 1)
					likedSongInMemory = true;
				else
					likedSongInMemory = false;
			}
			
			if(nnet[11].input[1] == 0 && ((FemaleAnimat)a).on)
				((FemaleAnimat)a).toggleOff();
			if(nnet[11].input[1] == 1 && !((FemaleAnimat)a).on)
				((FemaleAnimat)a).toggleOn();
			
			nnet[11].out = nnet[11].fire();
		}
		chooseTask();
		reset();
	}
}
