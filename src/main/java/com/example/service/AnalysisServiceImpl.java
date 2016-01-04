package com.example.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.model.Analysis;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
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
    public Analysis findAnalysis(String asset, String bench, String date) {
    	String hql = "FROM Analysis WHERE asset=:asset AND bench=:bench AND startdate=:startdate";
    	Query query = em.createQuery(hql);
    	query.setParameter("asset",asset);
    	query.setParameter("bench",bench);
    	query.setParameter("startdate",date);
    	List<?> res = query.getResultList();
    	if (res.isEmpty()) return null;
    	else return (Analysis) res.get(0);
    }
    
    @Transactional
    public Analysis findAnalysisById(Integer id) {
    	String hql = "FROM Analysis WHERE analysis_id=:id";
    	Query query = em.createQuery(hql);
    	query.setParameter("id",id);
    	return (Analysis) query.getResultList().get(0);
    }

    @Transactional
    public void removeAnalysis(Integer id) {
    	Analysis a = em.find(Analysis.class, id);
        if (null != a) {
            em.remove(a);
        }
    }
    
}

