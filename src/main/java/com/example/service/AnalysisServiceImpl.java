package com.example.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.model.Analysis;
import com.example.model.Person;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import java.util.List;

@Service
public class AnalysisServiceImpl implements AnalysisService {

    @PersistenceContext
    EntityManager em;
        
    @Transactional
    public void addAnalysis(Analysis a) {
        em.persist(a);
    }

    @Transactional
    public List<Analysis> listAnalysis() {
        CriteriaQuery<Analysis> c = em.getCriteriaBuilder().createQuery(Analysis.class);
        c.from(Analysis.class);
        return em.createQuery(c).getResultList();
    }

    @Transactional
    public void removeAnalysis(Integer id) {
    	Analysis a = em.find(Analysis.class, id);
        if (null != a) {
            em.remove(a);
        }
    }
    
}

