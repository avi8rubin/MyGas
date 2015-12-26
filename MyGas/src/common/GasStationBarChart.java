package common;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.SystemColor;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import org.jfree.chart.*;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
public class GasStationBarChart extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel jpanel;
	private Float[] AmountAndLevel;
	
	
	public GasStationBarChart(Float[] AmountAndLevel) {
		this.AmountAndLevel = AmountAndLevel;
		//JFreeChart jfreechart = createChart(createDataset());
		//new ChartPanel(jfreechart);
		//jpanel = createDemoPanel();
		//jpanel.setPreferredSize(new Dimension(500, 270));
		// TODO Auto-generated constructor stub
	}
	private static CategoryDataset createDataset()
	{
		DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();
        defaultcategorydataset.addValue(200, "Current Amount", "95");
        defaultcategorydataset.addValue(1600-200, "Empty Level", "95");
        defaultcategorydataset.addValue(300, "Current Amount", "98");
        defaultcategorydataset.addValue(1200-300, "Empty Level", "98");
        defaultcategorydataset.addValue(900, "Current Amount", "Diesel");
        defaultcategorydataset.addValue(1500-900, "Empty Level", "Diesel");
		return defaultcategorydataset;
	}

	private static JFreeChart createChart(CategoryDataset categorydataset)
	{
		JFreeChart jfreechart = ChartFactory.createStackedBarChart("Current Station Fuels Level", "Fuel Type", "Level", categorydataset, PlotOrientation.VERTICAL, true, false, false);
		CategoryPlot categoryplot = (CategoryPlot)jfreechart.getPlot();
		ExtendedStackedBarRenderer extendedstackedbarrenderer = new ExtendedStackedBarRenderer();
		extendedstackedbarrenderer.setBaseItemLabelsVisible(true);
		extendedstackedbarrenderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		extendedstackedbarrenderer.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator());
		categoryplot.setRenderer(extendedstackedbarrenderer);
		NumberAxis numberaxis = (NumberAxis)categoryplot.getRangeAxis();
		numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		numberaxis.setLowerMargin(0.14999999999999999D);
		numberaxis.setUpperMargin(0.14999999999999999D);
		ChartUtilities.applyCurrentTheme(jfreechart);
		jfreechart.getPlot().setBackgroundPaint(SystemColor.control);
		jfreechart.getPlot().setOutlineVisible(false);
		jfreechart.setBackgroundPaint(SystemColor.control);

		 BarRenderer r = (BarRenderer)jfreechart.getCategoryPlot().getRenderer();
		    r.setSeriesPaint(0, Color.GREEN);
		    r.setSeriesPaint(1, Color.darkGray);
		return jfreechart;
	}

	public static JPanel createBarChartPanel()
	{
		JFreeChart jfreechart = createChart(createDataset());
		return new ChartPanel(jfreechart);
	}


}
