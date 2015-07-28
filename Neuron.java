
public class Neuron {
	int n;
	int input[];
	Element e;
	double weight;
	int out;

	public Neuron(int n) {
		this.n = n;
		if(n >= 1){
			input = new int[n];
			input[0] = 0;
		}
		e = new Element();
		weight = 1;
		out = 0;
		
	}
	
	public void reset(){
		for(int i=0;i<n;i++)
			input[i] = 0;
		e = null;
		out = 0;
	}
	
	public int fire(){
		int o = 1;
		for(int i=0;i<n;i++){
			o *= input[i];
		}
		o *= weight;
		return o;
	}

}
