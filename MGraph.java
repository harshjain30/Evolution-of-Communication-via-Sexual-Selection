import java.util.Random;

public class MGraph {
	int firstNote;
	double[][][] weights = new double[Controller.nnote][Controller.npitch][Controller.npitch];
	
	public MGraph() {
		Random z = new Random();
		//firstNote = z.nextInt(Controller.npitch);
		firstNote = 0;
		for(int i=0;i<Controller.nnote;i++){
			for(int j=0;j<Controller.npitch;j++){
				for(int k=0;k<Controller.npitch;k++){
					if(j==i%Controller.npitch && k==(i+1)%Controller.npitch) weights[i][j][k] = 1;
					else 
					weights[i][j][k] = z.nextDouble();
				}
			}
		}
	}
	
	public void tweak(){
		Random z = new Random();
		int i = Controller.nnote-1;
		int n = z.nextInt(2);
		while(n!=0 && i!=0){
			i--;
			n = z.nextInt(2+Controller.nnote-1-i);
		}
		if(i==0) firstNote = z.nextInt(Controller.npitch);
		else{
			for(int j=0;j<Controller.npitch;j++){
				for(int k=0;k<Controller.npitch;k++){
					weights[i][j][k] = z.nextDouble();
				}
			}
		}
	}
	
	public String negFb(String str){
		int curNote = Character.getNumericValue(str.charAt(0));
		for(int i=0;i<Controller.nnote;i++){
			if(weights[i][curNote][Character.getNumericValue(str.charAt(i+1))] > 0.1)
				weights[i][curNote][Character.getNumericValue(str.charAt(i+1))] -= 0.1;
			else weights[i][curNote][Character.getNumericValue(str.charAt(i+1))] = 0;
			curNote = Character.getNumericValue(str.charAt(i+1));
		}
		return genSong();
	}
	
	public String posFb(String str){
		int curNote = Character.getNumericValue(str.charAt(0));
		for(int i=0;i<Controller.nnote;i++){
			if(weights[i][curNote][Character.getNumericValue(str.charAt(i+1))] < 0.95)
				weights[i][curNote][Character.getNumericValue(str.charAt(i+1))] += 0.05;
			else weights[i][curNote][Character.getNumericValue(str.charAt(i+1))] = 1;
			curNote = Character.getNumericValue(str.charAt(i+1));
		}
		return genSong();
	}
	
	public String genSong(){
		
		int curNote = firstNote;
		StringBuilder sb = new StringBuilder(String.valueOf(firstNote));
		
		for(int i=0;i<Controller.nnote;i++){
			int nextNode = 0;
			double max = Double.MIN_VALUE;
			
			for(int k=0;k<Controller.npitch;k++){
				if(weights[i][curNote][k] > max){
					max = weights[i][curNote][k];
					nextNode = k;
				}
			}
			sb.append(String.valueOf(nextNode));
			curNote = nextNode;
		}
		return sb.toString();
	}
}
