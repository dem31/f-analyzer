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
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="analysis")
public class Analysis {

    public ArrayList<String> getDates() {
        return dates;
    }

    public ArrayList<Double> getPrice() {
        return price;
    }

    public ArrayList<Double> getPriceBench() {
        return priceBench;
    }

    public ArrayList<Indicator> getIndicators() {
        return indicators;
    }

    @Id
    @GeneratedValue
    private Integer analysis_id;
    
    private ArrayList<String> dates = new ArrayList<String>();
    private ArrayList<Double> price = new ArrayList<Double>();
    private ArrayList<Double> priceBench = new ArrayList<Double>();
    
    @ElementCollection
    private ArrayList<Indicator> indicators = new ArrayList<Indicator>();

    public Double getTe() {
        return te;
    }

    public Double getIr() {
        return ir;
    }

    private Double te;
    private Double ir;

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



            indicators.add(new Indicator(dates, price, priceBench, 13));
            indicators.add(new Indicator(dates, price, priceBench, 26));
            indicators.add(new Indicator(dates, price, priceBench, 52));

        } catch (ParseException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}

