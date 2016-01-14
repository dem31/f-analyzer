package com.example.controller;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.example.model.Analysis;
import com.example.service.AnalysisService;
import com.example.service.SupportService;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;

@Controller
@RequestMapping("indicator")
public class IndicatorController {
	
	@Autowired
    private AnalysisService analysisService;
	@Autowired
    private SupportService supportService;

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
    	String date=request.getParameter("date");
        LocalDate point=new LocalDate(date);
        LocalDate lastPossibleDate=new LocalDate().minusDays(370);
        if (point.isAfter(lastPossibleDate)) point=lastPossibleDate;
        
    	String link="http://ichart.finance.yahoo.com/table.csv?g=d&ignore=.csv&";
        String year = String.format("%02d", Calendar.getInstance().get(Calendar.YEAR));
        String month = String.format("%02d", Calendar.getInstance().get(Calendar.MONTH));
        String day = String.format("%02d", Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        link+="e="+day+"&d="+month+"&f="+year+"&b="+point.getDayOfMonth()+"&a="+(point.getMonthOfYear()-1)+"&c="+point.getYear()+"&s=";
    	
        Analysis a=analysisService.findAnalysis(asset, bench, date+year+"-"+("0"+(String.valueOf(month)+1)).substring(2)+"-"+day);
        if (a==null){
        	a=new Analysis(link, asset, bench, date+year+"-"+("0"+(String.valueOf(month)+1)).substring(2)+"-"+day);
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
    
    @Transactional
    @RequestMapping(value = "refresh", method = RequestMethod.GET)
    public String refreshIndex(HttpServletRequest request) {
    	String index=request.getParameter("index");
    	String response="";
    	if (!index.isEmpty()) response=supportService.updateComponents(index);
        return "redirect:/indicator"+response;
    }

}
