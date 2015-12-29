package com.example.model;

import com.opencsv.CSVReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;

@Entity
@Table(name="analysis")
public class Analysis implements java.io.Serializable {
	
    private Integer analysisId;
    private List<String> dates = new ArrayList<String>();
    private List<Double> price = new ArrayList<Double>();
    private List<Double> priceBench = new ArrayList<Double>();
    private List<Indicator> indicators = new ArrayList<Indicator>();
    private Double te;
    private Double ir;
    
    public Analysis(){}
    
    public Analysis(String link, String bench){

        URL stockURL = null;
        try {
            stockURL = new URL(bench);
            BufferedReader in = new BufferedReader(new InputStreamReader(stockURL.openStream()));
            CSVReader reader = new CSVReader(in);
            List<String[]> pathBench = reader.readAll();
            pathBench.remove(0);

            stockURL = new URL(link);
            in = new BufferedReader(new InputStreamReader(stockURL.openStream()));
            reader = new CSVReader(in);
            List<String[]> path= reader.readAll();
            path.remove(0);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal = Calendar.getInstance();

            int size=pathBench.size();
            double d0=Double.parseDouble(pathBench.get(size-1)[6]);
            Date cursorDate = sdf.parse(pathBench.get(size-1)[0]);
            cal.setTime(cursorDate);
            for (int i=pathBench.size()-1; i>=0; i--){
                if (pathBench.get(i)[0].equals(sdf.format(cursorDate))) {
                    dates.add(pathBench.get(i)[0]);
                    priceBench.add(Double.parseDouble(pathBench.get(i)[6]) * 100 / d0);
                    cal.add(Calendar.DATE, 7);
                    cursorDate = new Date(cal.get(Calendar.YEAR)-1900,
                            cal.get(Calendar.MONTH),
                            cal.get(Calendar.DAY_OF_MONTH));
                } else if (pathBench.get(i)[0].compareTo(sdf.format(cursorDate))>0) {
                    int j=0;
                    while (pathBench.get(i-j)[0].compareTo(sdf.format(cursorDate))>0 && i-j>0 && j<3) j++;
                    dates.add(pathBench.get(i-j)[0]);
                    priceBench.add(Double.parseDouble(pathBench.get(i - j)[6]) * 100 / d0);
                    cal.add(Calendar.DATE, 7);
                    cursorDate = new Date(cal.get(Calendar.YEAR)-1900,
                            cal.get(Calendar.MONTH),
                            cal.get(Calendar.DAY_OF_MONTH));
                }
            }

            int j=0;
            d0=Double.parseDouble(path.get(path.size()-1)[6]);
            for (int i=path.size()-1; i>=0; i--) {
                if (path.get(i)[0].equals(dates.get(j))) {
                    price.add(Double.parseDouble(path.get(i)[6]) * 100 / d0);
                    if (j<dates.size()-1) j++;
                    else break;
                } else if (path.get(i)[0].compareTo(dates.get(j))>=0){
                    price.add(Double.parseDouble(path.get(i)[6]) * 100 / d0);
                    if (j<dates.size()-1) j++;
                }
            }

            double avgPerf=0.0;
            for (int i=price.size()-1; i>=0; i--)
                avgPerf += Math.pow(priceBench.get(price.size()-1) / priceBench.get(i), 52 / (i+1)) - Math.pow(price.get(price.size()-1) / price.get(i), 52 / (i+1));
            avgPerf /= price.size()-1;

            te=0.0;
            for (int i=price.size()-1; i>=0; i--)
                te+=Math.pow(Math.pow(priceBench.get(price.size() - 1) / priceBench.get(i), 52 / (i+1)) - Math.pow(price.get(price.size() - 1) / price.get(i), 52 / (i+1)) - avgPerf, 2);
            te /= price.size()-1;

            ir=(Math.pow(priceBench.get(price.size()-1) / priceBench.get(0), 52 / price.size()) - Math.pow(price.get(price.size()-1) / price.get(0), 52 / price.size()))/te ;



            indicators.add(new Indicator(this, dates, price, priceBench, 13));
            indicators.add(new Indicator(this, dates, price, priceBench, 26));
            indicators.add(new Indicator(this, dates, price, priceBench, 52));

        } catch (ParseException e){
            e.printStackTrace();
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
	@CollectionTable(name = "dates")
	public List<String> getDates() {
        return dates;
    }

	public void setDates(List<String> dates) {
		this.dates = dates;
	}

	@ElementCollection
	@CollectionTable(name = "price")
	public List<Double> getPrice() {
        return price;
    }
	
	public void setPrice(List<Double> price) {
		this.price = price;
	}

	@ElementCollection
	@CollectionTable(name = "priceBench")
	public List<Double> getPriceBench() {
		return priceBench;
	}
	   
	public void setPriceBench(List<Double> priceBench) {
		this.priceBench = priceBench;
	}

    @OneToMany(cascade = {CascadeType.PERSIST}, mappedBy = "analysis")
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
	
}

