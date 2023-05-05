package com.nowakkacper;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.util.ArrayList;
import java.util.List;

public class CreatingChart {

    public static void createSignalChart(List<Double> signal, String xySeriesName){
        //Data to chart
        XYSeries series = new XYSeries(xySeriesName);

        if(signal.size() < 1600){
            for(int i=0; i<signal.size(); i++){
                series.add(i, signal.get(i));
            }
        }
        else{
            for(int i=0; i<1600; i++){
                series.add(i, signal.get(i));
            }
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        //Creating XY chart
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Signal",//title
                "t [s]", // x-axis description
                "u(t)", // y-axis description
                dataset, // Dane
                PlotOrientation.VERTICAL, // Chart orientation /HORIZONTAL
                true, // show legend
                true, // tooltips
                false
        );

        //Adding chart to window
        ChartFrame frame1=new ChartFrame("Signal Chart",chart);
        frame1.setVisible(true);
        frame1.setSize(400,400);
    }

    public static void createSignalChart(List<Integer> whereSignalCrossZero) {
        List<Double> signal;
        signal = create(whereSignalCrossZero);

        createSignalChart(signal, "Signal showing where signal cross zero");
    }

    private static List<Double> create(List<Integer> whereSignalCrossZero) {
        List<Double> signal = new ArrayList<>();
        int bound = whereSignalCrossZero.get(whereSignalCrossZero.size()-1)+100;
        for(int i = 0; i<bound; i++){
            if(!whereSignalCrossZero.isEmpty()){
                if(i == whereSignalCrossZero.get(0)){
                    signal.add(1.0);
                    whereSignalCrossZero.remove(0);
                }
                else signal.add(0.0);
            }
            else signal.add(0.0);
        }
        return signal;
    }
}
