package com.example.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.example.service.SupportService;

@Controller
public class IndexController {
	
	@Autowired
    private SupportService supportService;

	@RequestMapping("/")
    public String provideLink(Map<String, Object> map) {
		Map<Integer, String> m=supportService.getIndexes();
        map.put("indexes", m);
        return "index";
    }
	
	@RequestMapping(value = "/index_assets/{id}", method = RequestMethod.GET, produces={ "application/json"})
    public @ResponseBody
    Map<String, String> getAssets(@PathVariable Integer id) {
		
		Map<String, String> m=supportService.getAssets(id);
        return m;
    }
    

}
