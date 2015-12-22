package com.example.model;

import java.util.ArrayList;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="indicator")
public class Indicator {

	@Id
    @GeneratedValue
    private Integer indicator_id;
	
	private double alpha;
    private double beta;
    private double perf;
    private double vol;
	
    public double getBeta() {
        return beta;
    }

    public double getAlpha() {
        return alpha;
    }

    public double getPerf() {
        return perf;
    }

    public double getVol() {
        return vol;
    }

    public Indicator(ArrayList<String> dates, ArrayList<Double> price, ArrayList<Double> priceBench, int period){
        int last=price.size()-1;

        perf=Math.pow(price.get(last)/price.get(last-period), 52/period)-1;

        Double perfBench=Math.pow(priceBench.get(last)/priceBench.get(last-period), 52/period)-1;

        Double sum=0.0, sumBench=0.0, summ=0.0;
        for (int i=last; i>last-period; i--)
            sum += price.get(i);
        Double avg=sum/period;
        for (int i=last; i>last-period; i--)
            sum += priceBench.get(i);
        Double avgBench=sum/period;

        for (int i=last; i>last-period; i--)
            summ+=Math.pow(price.get(i)-avg, 2);
        vol=Math.sqrt(period * summ / (period-1));

        Double sum1=0.0, sum2=0.0;
        for (int i=last; i>last-period; i--) {
            sum1 += (price.get(i) - avg) * (priceBench.get(i) - avgBench);
            sum2 += Math.pow(priceBench.get(i) - avgBench, 2);
        }
        beta=sum1/sum2;

        alpha=perf-beta*perfBench;

    }



}
