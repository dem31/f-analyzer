package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.model.Person;
import com.example.service.PersonService;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import java.util.Calendar;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/indicator")
public class IndicatorController {

    @RequestMapping
    public String provideLink(Map<String, Object> map) {
    	String link="http://real-chart.finance.yahoo.com/table.csv?g=d&ignore=.csv&";
        String[] parts=new String("2012-01-15").split("-");
        String year = String.format("%02d", Calendar.getInstance().get(Calendar.YEAR));
        String month = String.format("%02d", Calendar.getInstance().get(Calendar.MONTH)-1);
        String day = String.format("%02d", Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        link+="e="+day+"&d="+month+"&f="+year+"&b="+parts[2]+"&a="+parts[1]+"&c="+parts[0]+"&s=";
        //link+="BNP.PA"link+="^FCHI";
        map.put("link", link);

        return "indicator";
    }
    
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addPerson(HttpServletRequest request) {
    	String asset=request.getParameter("asset");
        //personService.addPerson(person);

        return "redirect:/indicator?asset="+asset;
    }

}
