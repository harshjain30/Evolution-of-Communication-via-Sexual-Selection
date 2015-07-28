import org.jfree.ui.RefineryUtilities;

public class Run{
	static Controller c;

	public static void main(String[] args) {
		
		try{
			c = new Controller();
		}
		catch(Exception e){
			System.out.println("Controller could not be created");
		}
		Thread t = new Thread(c,"Controller Thread");
		t.start();
		
		/*JFrame f = new JFrame("Animats");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Toolkit tk = Toolkit.getDefaultToolkit();
		f.setSize((int) (tk.getScreenSize().getWidth()/2),(int) (tk.getScreenSize().getHeight()/2));
		f.setResizable(false); 
		//f.setUndecorated(true);
		Display d = new Display(c);
		f.add(d);
		f.setVisible(true);*/
		
		final PlotAvgSongDiv chart = new PlotAvgSongDiv("Progress Plot");
        chart.pack();
        RefineryUtilities.centerFrameOnScreen(chart);
        chart.setVisible(true);
        
		final PlotSongDiv chart2 = new PlotSongDiv("Scatter Plot");
        chart2.pack();
        RefineryUtilities.centerFrameOnScreen(chart2);
        chart2.setVisible(true);
	}

}