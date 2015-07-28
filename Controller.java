import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Controller implements Runnable{
	int m;//rows
	int n;//columns
	static final int cremax = 99;//maximum number of creatures
	static final int npitch = 8;
	static final int nnote = 31;
	static final int ngens = 15;
	static final int nma = 40;
	static int l;
	static int h;
	static int deadByEnergy;
	static int deadByLife;
	static int deadBySex;
	static int mdeaths;
	static int fdeaths;
	static int kdeaths;
	static int wdeaths;
	
	int timeUnits;
	String avgSong;
	int avgSongDiversity;
	int modalGen;
	/*int curgen = 0;
	int lastgen = 0;
	int maxDivInCurGen = 0;
	static int genCounter = 0;*/
	static int cgen = 0;
	
	Random r = new Random();
	int indCount[] = new int[nnote+2];
	int mostDivIndCounter[] = new int[nnote+2];
	ConcurrentLinkedQueue<Element>[][] e;
	ConcurrentLinkedQueue<MaleAnimat> ll_ma = new ConcurrentLinkedQueue<MaleAnimat>();
	ConcurrentLinkedQueue<FemaleAnimat> ll_fa = new ConcurrentLinkedQueue<FemaleAnimat>();
	ConcurrentLinkedQueue<KidAnimat> ll_ka = new ConcurrentLinkedQueue<KidAnimat>();
	ConcurrentLinkedQueue<Worm> ll_w = new ConcurrentLinkedQueue<Worm>();
	ConcurrentLinkedQueue<Animat> clqa = new ConcurrentLinkedQueue<Animat>();
	//static ArrayList<String> genSongs = new ArrayList<String>();
	static ArrayList<LinkedList<String>> allGenSongs = new ArrayList<LinkedList<String>>();
	static int genCount[] = new int[ngens+1];
	
	public Controller() {		
		Toolkit tk = Toolkit.getDefaultToolkit();
		m = (int) (tk.getScreenSize().getWidth()/2);//set to /3 for 30 in iteration
		n = (int) (tk.getScreenSize().getHeight()/2)-100;
		e = new ConcurrentLinkedQueue[m][n];
		for(int i=0;i<m;i++)
			for(int j=0;j<n;j++)
				e[i][j] = new ConcurrentLinkedQueue<Element>();
		for(int i=0;i<=ngens;i++){
			allGenSongs.add(new LinkedList<String>());
		}
	}
	
	public int calModalGen(){
		int maxCount = Integer.MIN_VALUE;
		int maxIndex = -1;
		ArrayList<Integer> a = new ArrayList<Integer>();
		Iterator<Animat> ia = clqa.iterator();
		while(ia.hasNext()){
			a.add(ia.next().gen);
		}
		int n = a.size();
		for(int i=0;i<n-1;i++){
			int count = 0;
			for(int j=i+1;j<n;j++){
				if(a.get(j) == a.get(i))
					count++;
			}
			if(count>maxCount){
				maxCount = count;
				maxIndex = i;
			}
		}
		/*if(maxIndex==-1)return "All/But One dead";
		return String.valueOf((modalGen = a.get(maxIndex)));*/
		return (modalGen = a.get(maxIndex));
	}
	
	public int calSongDiversity(){
		int count = 0;
		Arrays.fill(indCount, 0);
		String arr[] = allGenSongs.get(cgen).toArray(new String[0]);
		int n = arr.length;
		for(int i=0;i<n-1;i++){
			for(int j=i+1;j<n;j++){
				int indCounter = 0;
				for(int k=0;k<=nnote;k++){
					if(arr[i].charAt(k) != arr[j].charAt(k)){
						count++;
						indCounter++;
					}
				}
				indCount[indCounter]++;
			}
		}
		if(n == 0) return -1;
		return (avgSongDiversity = count/((n*(n-1))/2));
	}
	
	public String calAvgSong(){
		int[][] count = new int[Controller.nnote+1][Controller.npitch];
		Iterator<MaleAnimat> ia = ll_ma.iterator();
		while(ia.hasNext()){
			String song = ia.next().song;
			for(int i=0;i<=Controller.nnote;i++){
				int pitch = Character.getNumericValue(song.charAt(i));
				count[i][pitch]++;
			}
		}
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<=Controller.nnote;i++){
			int max = Integer.MIN_VALUE;
			int maxIndex = Integer.MIN_VALUE;
			for(int j=0;j<Controller.npitch;j++){
				if(count[i][j]>max){
					max = count[i][j];
					maxIndex = j;
				}
			}
			sb.append(maxIndex);
		}
		avgSong = sb.toString();
		return avgSong;
	}
	
	/*public void calAvgSongScore(){
		double sum = 0;
		Iterator<Double> iss = songScores.iterator();
		while(iss.hasNext()){
			sum += iss.next();
		}
		System.out.println(avgSongScore = sum/songScores.size());
	}*/
	
	public void add(Element e){
		switch(e.type){
		case 1:
			ll_ma.add((MaleAnimat) e);
			clqa.add((Animat) e);
			break;
		case 2:
			ll_fa.add((FemaleAnimat) e);
			clqa.add((Animat) e);
			break;
		case 3:
			ll_ka.add((KidAnimat) e);
			clqa.add((Animat) e);
			break;
		case 4:
			ll_w.add((Worm) e);
			break;
		default: System.out.println("def");;
		}
	}
	
	public void mature(KidAnimat ka){
		if(!ka.isFemale)
			new MaleAnimat(ka.id, ka.posx,ka.posy,this, ka.gen, ka.energy, ka.mg);
		else
			new FemaleAnimat(ka.id, ka.posx,ka.posy,this, ka.gen, ka.energy, ka.fg);
		ka.die();
		ka = null;
	}
	
	public void kill(Creature cr){
		cr.die();
		cr = null;
	}
	
	public void eat(Animat a, Worm w){
		w.die();
		w = null;
		new Worm(r.nextInt(m-1),r.nextInt(n-1),this);
		a.eat();
	}
	
	public void mate(FemaleAnimat fa, MaleAnimat ma){
		ma.mate();
		fa.mate();
		boolean i = r.nextBoolean();
		if(!i)
			new KidAnimat(fa.posx,fa.posy-10,this,ma.gen+1,ma.g);
		else
			new KidAnimat(fa.posx,fa.posy-10,this,fa.gen+1,fa.g);
	}

	@Override
	public void run() {
		//new MaleAnimat(200,200,this,0);
		//new MaleAnimat(200,300,this,0);
		//System.out.println(m.song);
		//new FemaleAnimat(250,200,this,0);
		//new FemaleAnimat(250,300,this,0);
		//System.out.println(f.nn.g.calScore(m.song));
		//new Worm(250,200,this);
		
		//boolean maxVariablesSet = false;
		
		for(int i=0;i<nma;i++){
			new FemaleAnimat(r.nextInt(m-1),r.nextInt(n-1),this,0);
			new MaleAnimat(r.nextInt(m-1),r.nextInt(n-1),this,0);
			new Worm(r.nextInt(m-1),r.nextInt(n-1),this);
			new Worm(r.nextInt(m-1),r.nextInt(n-1),this);
			if(i%2==0)
				new FemaleAnimat(r.nextInt(m-1),r.nextInt(n-1),this,0);
		}
		
		boolean b = true;
		while(b){
			Iterator<Animat> ia = clqa.iterator();
			while(ia.hasNext()){
				Animat a = ia.next();
				a.sense();
			}
			if(timeUnits++%2 == 0){
				Iterator<Worm> iw = ll_w.iterator();
				while(iw.hasNext()){
					Worm w = iw.next();
					w.moveToRandom();
				}
			}
			
			if(timeUnits % 100 == 0)System.out.println(genCount[cgen]);
			if(genCount[cgen] == 0){
				PlotAvgSongDiv.avgSongDivSeries.add(cgen,calSongDiversity());
				System.out.println(String.valueOf(ll_ma.size())+ " : " +Arrays.toString(indCount));
				for(int i=0;i<indCount.length;i++){
					for(int j=0;j<indCount[i];j++){
						PlotSongDiv.songDivSeries.add(cgen,i);
					}
				}
				allGenSongs.get(cgen).clear();
				if(++cgen>ngens)b = false;
				/*Iterator<MaleAnimat> ima = ll_ma.iterator();
				while(ima.hasNext()){
					MaleAnimat ma = ima.next();
					if(ma.gen == cgen){
						genCounter++;
						genSongs.add(ma.song);
					}
				}*/
			}
			
			/*try{
				Thread.sleep(100);
			}
			catch(Exception e){
				System.out.println("Thread Interrupted");
			}*/
			
			
			/*if(timeUnits++ % 100 == 0){
				curgen = calModalGen();
				if(curgen > lastgen){
					if(!maxVariablesSet){
						maxDivInCurGen = calSongDiversity();
						mostDivIndCounter = indCount.clone();
						Arrays.fill(indCount, 0);
					}
					PlotAvgSongDiv.avgSongDivSeries.add(lastgen,maxDivInCurGen);
					System.out.println(String.valueOf(ll_ma.size())+ " : " +Arrays.toString(mostDivIndCounter));
					for(int i=0;i<mostDivIndCounter.length;i++){
						for(int j=0;j<mostDivIndCounter[i];j++){
							PlotSongDiv.songDivSeries.add(lastgen,i);
						}
					}
					maxDivInCurGen = 0;
					Arrays.fill(mostDivIndCounter, 0);
					maxVariablesSet = false;
					lastgen = curgen;
				}
				else if(curgen == lastgen){
					int t = calSongDiversity();
					if(maxDivInCurGen<t){
						maxDivInCurGen = t;
						mostDivIndCounter = indCount.clone();
						Arrays.fill(indCount, 0);
						maxVariablesSet = true;
					}
				}
			}*/
		}
	}

}
