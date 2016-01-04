package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.example.model.Analysis;
import com.example.service.AnalysisService;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;

@Controller
@RequestMapping("indicator")
public class IndicatorController {
	
	@Autowired
    private AnalysisService analysisService;

	@RequestMapping
    public String provideLink(Map<String, Object> map) {
        map.put("analysis", new Analysis());
        map.put("analysisList", analysisService.listAnalysis());
        return "indicator";
    }
    
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String addAnalysis(HttpServletRequest request) {
    	String asset=request.getParameter("asset");
    	String bench=request.getParameter("bench");
        
    	String link="http://real-chart.finance.yahoo.com/table.csv?g=d&ignore=.csv&";
    	String date="2012-01-15";
        String[] parts=date.split("-");
        String year = String.format("%02d", Calendar.getInstance().get(Calendar.YEAR));
        String month = String.format("%02d", Calendar.getInstance().get(Calendar.MONTH)-1);
        String day = String.format("%02d", Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        link+="e="+day+"&d="+month+"&f="+year+"&b="+parts[2]+"&a="+parts[1]+"&c="+parts[0]+"&s=";
    	
        Analysis a=analysisService.findAnalysis(asset, bench, date+year+month+day);
        if (a==null){
        	a=new Analysis(link, asset, bench, date+year+month+day);
        	analysisService.addAnalysis(a);
        }
    	
        return "redirect:/indicator";
    }
    
    @RequestMapping(value = "show", method = RequestMethod.POST)
    public String showAnalysis(Map<String, Object> map, HttpServletRequest request) {
    	Integer id=Integer.parseInt(request.getParameter("id"));
        Analysis a=analysisService.findAnalysisById(id);
        map.put("analysis", a);
    	
        return "show";
    }

}
