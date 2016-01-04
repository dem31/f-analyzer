package com.example.service;

import java.util.List;
import com.example.model.*;

public interface AnalysisService {
    
    public void addAnalysis(Analysis a);
    public List<Analysis> listAnalysis();
    public void removeAnalysis(Integer id);
    public Analysis findAnalysis(String asset, String bench, String date);
    public Analysis findAnalysisById(Integer id);
}
