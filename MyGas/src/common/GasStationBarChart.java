/************************************************************************** 
 * Copyright (©) MyGas System 2015-2016 - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Ohad Zino <zinoohad@gmail.com>
 * 			  Adir Notes <adirno@zahav.net.il>
 * 			  Litaf Kupfer <litafkupfer@gmail.com>
 * 			  Avi Rubin <avi8rubin@gmail.com>
 **************************************************************************/
package common;

import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.JPanel;
import org.jfree.chart.*;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
/**
 * Bar chart the display the current fuel amount in the station.
 * @author Ohad
 */
public class GasStationBarChart extends JPanel {

	private static final long serialVersionUID = 1L;
	private static Float[] AmountAndLevel;
	private static JFreeChart jfreechart;
	private static ChartPanel chartPanel;
	/**
	 * Constructor that gets the details of the fuel level in the station.
	 * @param AmountAndLevel - All the current fuel amounts
	 */
	public GasStationBarChart(Float[] AmountAndLevel) {
		this.AmountAndLevel = AmountAndLevel;
	}
	/**
	 * 	Creating chart with the values in the Float array.
	 * @return DefaultCategoryDataset object
	 */
	private static CategoryDataset createDataset()
	{
		DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();
        defaultcategorydataset.addValue(AmountAndLevel[0], "Current Amount", "95");
        defaultcategorydataset.addValue(AmountAndLevel[1]-AmountAndLevel[0], "Empty Level", "95");
        defaultcategorydataset.addValue(AmountAndLevel[2], "Current Amount", "Scooter");
        defaultcategorydataset.addValue(AmountAndLevel[3]-AmountAndLevel[2], "Empty Level", "Scooter");
        defaultcategorydataset.addValue(AmountAndLevel[4], "Current Amount", "Diesel");
        defaultcategorydataset.addValue(AmountAndLevel[5]-AmountAndLevel[4], "Empty Level", "Diesel");
		return defaultcategorydataset;
	}
/**
 * Creating chart.
 * Use JFreeChart JRE
 * @param categorydataset - all the data entered to chart
 * @return JFreeChart object
 */
	private static JFreeChart createChart(CategoryDataset categorydataset)
	{
		jfreechart = ChartFactory.createStackedBarChart("Current Station Fuels Level", "Fuel Type", "Level", categorydataset, PlotOrientation.VERTICAL, true, false, false);
		CategoryPlot categoryplot = (CategoryPlot)jfreechart.getPlot();
		//jfreechart.getXYPlot();
		ExtendedStackedBarRenderer extendedstackedbarrenderer = new ExtendedStackedBarRenderer();
		extendedstackedbarrenderer.setBaseItemLabelsVisible(true);
		extendedstackedbarrenderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		//extendedstackedbarrenderer.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator());
		categoryplot.setRenderer(extendedstackedbarrenderer);
		NumberAxis numberaxis = (NumberAxis)categoryplot.getRangeAxis();
		numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		numberaxis.setLowerMargin(0.14999999999999999D);
		numberaxis.setUpperMargin(0.14999999999999999D);
		ChartUtilities.applyCurrentTheme(jfreechart);
		jfreechart.getPlot().setBackgroundPaint(SystemColor.control);
		jfreechart.getPlot().setOutlineVisible(false);
		jfreechart.setBackgroundPaint(SystemColor.control);
		// Set the color of the chart
		 BarRenderer r = (BarRenderer)jfreechart.getCategoryPlot().getRenderer();
		    r.setSeriesPaint(0, Color.GREEN);
		    r.setSeriesPaint(1, Color.darkGray);
		return jfreechart;
	}
/**
 * Create JPanel with the bar chart
 * @return JPanel contains the chart with the specific values.
 */
	public static JPanel createBarChartPanel()
	{
		JFreeChart jfreechart = createChart(createDataset());
		chartPanel = new ChartPanel(jfreechart);
		return chartPanel;
	}

}
