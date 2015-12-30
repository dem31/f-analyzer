package com.example.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.model.Analysis;
import com.example.model.Person;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

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
    public int findAnalysis(Analysis a) {
    	Metamodel m = em.getMetamodel();
    	EntityType<Analysis> Analysis_ = m.entity(Analysis.class);
        CriteriaQuery<Analysis> c = em.getCriteriaBuilder().createQuery(Analysis.class);
        c.from(Analysis.class);
        //c.where(em.equal(c.get("te"), "Fido")
        	    //.and(em.equal(a.get(Analysis_.color), "brown");
        return 1;//em.createQuery(c).getResultList();
    }

    @Transactional
    public void removeAnalysis(Integer id) {
    	Analysis a = em.find(Analysis.class, id);
        if (null != a) {
            em.remove(a);
        }
    }
    
}

