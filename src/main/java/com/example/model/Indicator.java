package com.example.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.FetchType;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name="indicator")
public class Indicator {

    private Integer indicatorId;
	private Analysis analysis;
	private double beta;
    private double perf;
    private double vol;
	private double alpha;
	
	public Indicator(){}
	
	public Indicator(Analysis a, List<PriceItem> pricePath, int period){
    	this.analysis=a;
    	int last=pricePath.size()-1;

        perf=Math.pow(pricePath.get(last).getPrice()/pricePath.get(last-period).getPrice(), 52/period)-1;

        Double perfBench=Math.pow(pricePath.get(last).getPriceBench()/pricePath.get(last-period).getPriceBench(), 52/period)-1;

        Double sum=0.0, summ=0.0;
        for (int i=last; i>last-period; i--)
            sum += pricePath.get(i).getPrice();
        Double avg=sum/period;
        for (int i=last; i>last-period; i--)
            sum += pricePath.get(i).getPriceBench();
        Double avgBench=sum/period;

        for (int i=last; i>last-period; i--)
            summ+=Math.pow(pricePath.get(i).getPrice()-avg, 2);
        vol=Math.sqrt(period * summ / (period-1));

        Double sum1=0.0, sum2=0.0;
        for (int i=last; i>last-period; i--) {
            sum1 += (pricePath.get(i).getPrice() - avg) * (pricePath.get(i).getPriceBench() - avgBench);
            sum2 += Math.pow(pricePath.get(i).getPriceBench() - avgBench, 2);
        }
        beta=sum1/sum2;

        alpha=perf-beta*perfBench;

    }
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "INDICATOR_ID", unique = true, nullable = false)
    public Integer getIndicatorId() {
		return indicatorId;
	}

	public void setIndicatorId(Integer indicator_id) {
		this.indicatorId = indicator_id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ANALYSIS_ID", nullable = false)
	public Analysis getAnalysis() {
		return analysis;
	}

	public void setAnalysis(Analysis analysis) {
		this.analysis = analysis;
	}

	public double getAlpha() {
        return alpha;
    }
	
	public void setAlpha(double alpha) {
		this.alpha = alpha;
	}

	public double getBeta() {
        return beta;
    }
	
	public void setBeta(double beta) {
		this.beta = beta;
	}

	public double getPerf() {
        return perf;
    }
	
	public void setPerf(double perf) {
		this.perf = perf;
	}

	public double getVol() {
        return vol;
    }
	
	public void setVol(double vol) {
		this.vol = vol;
	}
   
}
