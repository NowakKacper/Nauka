package com.nowakkacper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataProcessing {

    private List<Double> signal = new ArrayList<>();
    private List<Double> testSignalThatBestReflectsTheOriginal = new ArrayList<>();
    private List<Integer> whereSignalCrossZero = new ArrayList<>();
    private final double SAMPLING_FREQUENCY = 8000;
    private final double SAMPLING_INTERVAL = 1/SAMPLING_FREQUENCY;
    private int numberOfSamples;

    public double approximationMethod(String howManyIntervalsAsString){
        int howManyIntervals = createIntFromString(howManyIntervalsAsString);
        double minSum=0;
        double minSumFrequency = 0;
        Random random = new Random();
        List<Double> testSignal = new ArrayList<>();

        double sum;
        double A_test;
        double f_test;

        //generating time vector
        List<Double> time = new ArrayList<>();
        for(double i=0; i<=SAMPLING_INTERVAL*howManyIntervals*161; i+=SAMPLING_INTERVAL){
            time.add(i);
        }

        //looking for max amplitude of the signal
        double A_max = 0;
        for(double sample : signal){
            A_max = Math.max(A_max, sample);
        }

        double signalShift = Math.asin(signal.get(0));
        for(int i=1; i<=20; i++){
            for(int j=1; j<=100; j++){
                sum=0;
                A_test = A_max - random.nextDouble()*0.2*A_max;
                f_test = random.nextInt(5) + (i-1)*5 + random.nextDouble();
                testSignal.clear();
                for(double t : time){
                    testSignal.add(A_test*Math.sin(2*Math.PI*f_test*t+signalShift));
                }
                int testSignalNumberOfSamples = 161*howManyIntervals;

                if(testSignalNumberOfSamples > numberOfSamples){
                    testSignalNumberOfSamples = numberOfSamples;
                }

                for(int k=0; k<testSignalNumberOfSamples; k++){
                    sum += Math.pow(signal.get(k) - testSignal.get(k), 2);
                }

                if((i==1 && j==1) || (sum < minSum)){
                    minSumFrequency = f_test;
                    minSum = sum;
                    testSignalThatBestReflectsTheOriginal.clear();
                    testSignalThatBestReflectsTheOriginal.addAll(testSignal);
                }
            }
        }

        return Math.round(minSumFrequency * 100.0) / 100.0;
    }

    public double zeroCrossingMethod(String howManyCompartmentsAsString){
        int howManyCompartments = createIntFromString(howManyCompartmentsAsString);
        int howManyZeroCrossing=0;
        double sumOfIntervalFrequencies=0;


        for(int i=0; i<howManyCompartments-1; i++){
            for(int j=i*numberOfSamples/howManyCompartments; j<(i+1)*numberOfSamples/howManyCompartments; j++){
                //we want to check if we cross zero
                double x = signal.get(j) * signal.get(j+1);
                if(x <= 0){
                    howManyZeroCrossing++;
                    whereSignalCrossZero.add(j);
                }
            }
            sumOfIntervalFrequencies = getSumOfIntervalFrequencies(howManyZeroCrossing, sumOfIntervalFrequencies);
        }

        //loop for last interval that isn't full
        for(int j=(howManyCompartments-1)*numberOfSamples/howManyCompartments; j<numberOfSamples-1; j++){
            //we want to check if we cross zero
            double x = signal.get(j) * signal.get(j+1);
            if(x <= 0){
                howManyZeroCrossing++;
                whereSignalCrossZero.add(j);
            }
        }
        sumOfIntervalFrequencies = getSumOfIntervalFrequencies(howManyZeroCrossing, sumOfIntervalFrequencies);

        double calculatedFrequency = sumOfIntervalFrequencies/howManyCompartments;

        return Math.round(calculatedFrequency * 100.0) / 100.0;
    }

    private int createIntFromString(String howManyCompartmentsAsString) {
        int number=0;
        char[] chars;
        chars = howManyCompartmentsAsString.toCharArray();
        for(int i=0; i<howManyCompartmentsAsString.length(); i++){
            if(chars[i] == '.' || chars[i] == ',') break;
            number *= 10;
            number += chars[i] - '0';
        }
        System.out.println(number);
        return number;
    }

    private double getSumOfIntervalFrequencies(int howManyZeroCrossing, double sumOfIntervalFrequencies) {
        //taking first and last sample from each compartment
        int first = whereSignalCrossZero.get(0);
        int last = whereSignalCrossZero.get(whereSignalCrossZero.size()-1);

        //corrections
        double correction1 = SAMPLING_INTERVAL *Math.abs(signal.get(first)/Math.abs(signal.get(first)-signal.get(first+1)));
        double correction2 = SAMPLING_INTERVAL *Math.abs(signal.get(last)/Math.abs(signal.get(last)-signal.get(last+1)));

        //time for L-1 half-periods
        double T0 = (last-first)* SAMPLING_INTERVAL;
        //after corrections
        double corrected = T0 - correction1 + correction2;
        sumOfIntervalFrequencies += 0.5*(howManyZeroCrossing -1)/corrected;
        return sumOfIntervalFrequencies;
    }

    public void loadData(String path){
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line;
            while ((line = br.readLine()) != null) {
                String numberString = line.trim();
                double number = changeScienceNotationToNormal(numberString);
                signal.add(number);
            }
            numberOfSamples = signal.size();
        } catch (Exception e) {
            System.err.println("An error occurred while loading data.");
            e.printStackTrace();
        }
    }

    private double changeScienceNotationToNormal(String numberString){
        char[] numberStringChar = numberString.toCharArray();
        int length = numberString.length();
        int multiplier = numberStringChar[length -1] - '0';
        double number;
        if(numberStringChar[0] == '-'){
            number = numberStringChar[1] - '0';
            for(int i = 3; i< length -4; i++){
                number += (double) (numberStringChar[i] - '0') / Math.pow(10, i-1);
            }
            number *= -1;
        }
        else {
            number = numberStringChar[0] - '0';
            for(int i = 2; i< length -4; i++){
                number += (double) (numberStringChar[i] - '0') / Math.pow(10, i-1);
            }
        }

        number = number / Math.pow(10, multiplier);
        return number;
    }

    public List<Double> getSignal() {
        return signal;
    }

    public List<Integer> getWhereSignalCrossZero() {
        return whereSignalCrossZero;
    }

    public List<Double> getTestSignalThatBestReflectsTheOriginal() {
        return testSignalThatBestReflectsTheOriginal;
    }
}
