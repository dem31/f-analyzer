package com.example.model;

import com.opencsv.CSVReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Table;
import org.joda.time.LocalDate;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;

@Entity
@Table(name="analysis")
public class Analysis{
	
    private Integer analysisId;
    private List<PriceItem> pricePath = new ArrayList<PriceItem>();
    private List<Indicator> indicators = new ArrayList<Indicator>();
    private String asset;
    private String bench;
    private String startDate;
	private Double te;
    private Double ir;
    
    public Analysis(){}
    
    public Analysis(String link, String asset, String bench, String startDate){

    	this.asset=asset;
    	this.bench=bench;
    	this.startDate=startDate;
    	
        URL stockURL = null;
        try {
            stockURL = new URL(link+bench);
            BufferedReader in = new BufferedReader(new InputStreamReader(stockURL.openStream()));
            CSVReader reader = new CSVReader(in);
            List<String[]> pathBench = reader.readAll();
            reader.close();
            pathBench.remove(0);

            stockURL = new URL(link+asset);
            in = new BufferedReader(new InputStreamReader(stockURL.openStream()));
            reader = new CSVReader(in);
            List<String[]> path= reader.readAll();
            reader.close();
            path.remove(0);

            int size=pathBench.size();
            double d0=Double.parseDouble(path.get(size-1)[6]);
            double b0=Double.parseDouble(pathBench.get(size-1)[6]);
            LocalDate cursorDate = new LocalDate(pathBench.get(size-1)[0]);
            for (int i=size-1; i>=0; i--){
            	LocalDate current=new LocalDate(pathBench.get(i)[0]);
            	if (cursorDate.isEqual(current)) {
                	pricePath.add(new PriceItem(pathBench.get(i)[0], Double.parseDouble(path.get(i)[6])*100/d0, Double.parseDouble(pathBench.get(i)[6])*100/b0));
                	cursorDate=cursorDate.plusDays(7);
                	System.out.println("price="+Double.parseDouble(path.get(i)[6])+" and d="+d0+" and value="+Double.parseDouble(path.get(i)[6])*100/d0+" and bench="+Double.parseDouble(pathBench.get(i)[6])*100/b0);
                	System.err.println("errprice="+Double.parseDouble(path.get(i)[6])+" and d="+d0+" and value="+Double.parseDouble(path.get(i)[6])*100/d0+" and bench="+Double.parseDouble(pathBench.get(i)[6])*100/b0);
            	} else if (cursorDate.isBefore(current)) {
                    int j=0;
                    while (cursorDate.isBefore(new LocalDate(pathBench.get(i-j)[0])) && i-j>0 && j<3) j++;
                    pricePath.add(new PriceItem(pathBench.get(i - j)[0], Double.parseDouble(path.get(i - j)[6])*100/d0, Double.parseDouble(pathBench.get(i - j)[6])*100/b0));
                    cursorDate=cursorDate.plusDays(7);
                }
            }

            double avgPerf=0.0;
            int last=pricePath.size()-1;
            for (int i=pricePath.size()-1; i>=0; i--)
                avgPerf += Math.pow(pricePath.get(last).getPriceBench() / pricePath.get(i).getPriceBench(), 52 / (i+1)) - Math.pow(pricePath.get(last).getPrice() / pricePath.get(i).getPrice(), 52 / (i+1));
            avgPerf /= last;

            te=0.0;
            for (int i=last; i>=0; i--)
                te+=Math.pow(Math.pow(pricePath.get(last).getPriceBench() / pricePath.get(i).getPriceBench(), 52 / (i+1)) - Math.pow(pricePath.get(last).getPrice() / pricePath.get(i).getPrice(), 52 / (i+1)) - avgPerf, 2);
            te /= last;

            ir=(Math.pow(pricePath.get(last).getPriceBench() / pricePath.get(0).getPriceBench(), 52 / last+1) - Math.pow(pricePath.get(last).getPrice() / pricePath.get(0).getPrice(), 52 / last+1))/te ;

            indicators.add(new Indicator(this, pricePath, 13));
            indicators.add(new Indicator(this, pricePath, 26));
            indicators.add(new Indicator(this, pricePath, 52));

        } catch (IOException e){
            e.printStackTrace();
        }
    }
	
    @Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ANALYSIS_ID")
    public Integer getAnalysisId() {
		return analysisId;
	}

	public void setAnalysisId(Integer analysisId) {
		this.analysisId = analysisId;
	}
	
	@ElementCollection
	public List<PriceItem> getPricePath() {
        return pricePath;
    }

	public void setPricePath(List<PriceItem> pricePath) {
		this.pricePath = pricePath;
	}

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "analysis")
    public List<Indicator> getIndicators() {
        return indicators;
    }
    
    public void setIndicators(List<Indicator> indicators) {
		this.indicators = indicators;
	}

    public Double getTe() {
        return te;
    }
    
    public void setTe(Double te) {
		this.te = te;
	}

    public Double getIr() {
        return ir;
    }

	public void setIr(Double ir) {
		this.ir = ir;
	}
	
	public String getAsset() {
		return asset;
	}

	public void setAsset(String asset) {
		this.asset = asset;
	}

	public String getBench() {
		return bench;
	}

	public void setBench(String bench) {
		this.bench = bench;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	
}

