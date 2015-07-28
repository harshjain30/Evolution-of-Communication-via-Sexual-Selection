import java.util.Random;


public class FGraph {
	double scoreSum;
	int songsHeard;
	double[][][] weights = new double[Controller.nnote][Controller.npitch][Controller.npitch];
	
	public FGraph() {
		Random r = new Random();
		for(int i=0;i<Controller.nnote;i++){
			for(int j=0;j<Controller.npitch;j++){
				for(int k=0;k<Controller.npitch;k++){
					if(j==i%Controller.npitch && k==(i+1)%Controller.npitch) weights[i][j][k] = 1;
					else
					weights[i][j][k] = 0;
				}
			}
		}
	}
	
	//orthodox preferences and no reinforcements
	/*public int calScoreAndSaturate(String str){
		if(str.length()!=Controller.nnote+1)
			return 0;
		double score = 0;
		int curNote = Character.getNumericValue(str.charAt(0));
		for(int i=0;i<Controller.nnote;i++){
			int toNote = Character.getNumericValue(str.charAt(i+1));
			score += weights[i][curNote][toNote];
			curNote = toNote;
		}
		scoreSum += score;
		double avg = scoreSum/++songsHeard;
		if(score>=avg){
			Controller.l++;
			return 1;
		}
		else{
			Controller.h++;
			return 0;
		}
	}*/
	
	//orthodox preferences with reinforcements
	/*public int calScoreAndSaturate(String str){
		if(str.length()!=Controller.nnote+1)
			return 0;
		double score = 0;
		int curNote = Character.getNumericValue(str.charAt(0));
		for(int i=0;i<Controller.nnote;i++){
			int toNote = Character.getNumericValue(str.charAt(i+1));
			score += weights[i][curNote][toNote];
			for(int j=0;j<Controller.npitch;j++){
				if(j == toNote)continue;
				if(weights[i][curNote][j] > 0.025)
					weights[i][curNote][j] -= 0.025;
				else weights[i][curNote][j] = 0;
			}
			if(weights[i][curNote][toNote] < 0.95)
				weights[i][curNote][toNote] += 0.05;
			else weights[i][curNote][toNote] = 1;
			curNote = toNote;
		}
		scoreSum += score;
		double avg = scoreSum/++songsHeard;
		if(score>=avg){
			Controller.l++;
			return 1;
		}
		else{
			Controller.h++;
			return 0;
		}
	}*/
	
	//surprise preferences
	public int calScoreAndSaturate(String str){
		if(str.length()!=Controller.nnote+1)
			return 0;
		double score = 0;
		int curNote = Character.getNumericValue(str.charAt(0));
		for(int i=0;i<Controller.nnote;i++){
			int toNote = Character.getNumericValue(str.charAt(i+1));
			double max = -Double.MAX_VALUE;
			int nextExpectedNote = -1;
			for(int j=0;j<Controller.npitch;j++){
				if(weights[i][curNote][j] > max){
					max = weights[i][curNote][j];
					nextExpectedNote = j;
				}
			}
			score += weights[i][curNote][nextExpectedNote] - weights[i][curNote][toNote];
			for(int j=0;j<Controller.npitch;j++){
				if(j == toNote)continue;
				if(weights[i][curNote][j] > 0.025)
					weights[i][curNote][j] -= 0.025;
				else weights[i][curNote][j] = 0;
			}
			if(weights[i][curNote][toNote] < 0.95)
				weights[i][curNote][toNote] += 0.05;
			else weights[i][curNote][toNote] = 1;
			curNote = toNote;
		}
		scoreSum += score;
		double avg = scoreSum/++songsHeard;
		if(score>=avg){
			Controller.l++;
			return 1;
		}
		else{
			Controller.h++;
			return 0;
		}
	}
}