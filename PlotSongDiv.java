import java.awt.Color;
import java.awt.Dimension;
import java.awt.geom.Ellipse2D;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

public class PlotSongDiv extends ApplicationFrame {
	
	private static final long serialVersionUID = 1L;
	static XYSeries songDivSeries = new XYSeries("");

	public PlotSongDiv(String s) {
        super(s);
        JPanel jpanel = createDemoPanel();
        jpanel.setPreferredSize(new Dimension(1366, 768));
        add(jpanel);
    }

    public static JPanel createDemoPanel() {
        JFreeChart jfreechart = ChartFactory.createScatterPlot(
            "Song Diversity Over The Generations", "Generation", "Number of Different Notes", xydataset(),
            PlotOrientation.VERTICAL, false, true, false);
        //jfreechart.setBackgroundPaint(Color.WHITE);
        XYPlot xyPlot = (XYPlot) jfreechart.getPlot();
        xyPlot.setBackgroundPaint(Color.BLACK);
        xyPlot.setDomainGridlinesVisible(false);
        xyPlot.setRangeGridlinesVisible(false);
        XYItemRenderer renderer = xyPlot.getRenderer();
        renderer.setSeriesShape(0, new Ellipse2D.Double(-4, -4, 8, 8));
        renderer.setSeriesPaint(0, new Color(1.0f,1.0f,1.0f,0.1f));
        NumberAxis domain = (NumberAxis) xyPlot.getDomainAxis();
        domain.setRange(0, Controller.ngens);
        domain.setTickUnit(new NumberTickUnit(1));
        domain.setVerticalTickLabels(true);
        NumberAxis range = (NumberAxis) xyPlot.getRangeAxis();
        range.setRange(0, Controller.nnote);
        range.setTickUnit(new NumberTickUnit(1));
        return new ChartPanel(jfreechart);
    }

    private static XYDataset xydataset() {
        XYSeriesCollection xySeriesCollection = new XYSeriesCollection();
        xySeriesCollection.addSeries(songDivSeries);
        return xySeriesCollection;
    }
}