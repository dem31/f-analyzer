package com.example.service;

import org.hibernate.ejb.HibernateEntityManager;
import org.hibernate.engine.SessionImplementor;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@Service
public class SupportService{

    @PersistenceContext
    EntityManager em;
        
    public String updateComponents(String index) {
    	String sql = "select * from index where index_id=?";
    	HibernateEntityManager hem = (HibernateEntityManager) em;
    	SessionImplementor sim = (SessionImplementor) hem.getSession();
    	Connection c = sim.connection(); 	
    	PreparedStatement ps = null;
    	try {
	        ps = c.prepareStatement(sql);
	        ps.setString(1, index);
	        ResultSet rs=ps.executeQuery();
	        if (rs.next()){
	        	String date = rs.getString("last_modified");
	        	int id = rs.getInt("id");
	        	DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
	        	DateTime  d = DateTime.parse(date, formatter);
	        	if (d.isAfter(DateTime.now().withTimeAtStartOfDay()))
		        	return "?already_updated";
	        	System.out.println("now is "+d.toString()+" StartOfDay is "+DateTime.now().withTimeAtStartOfDay().toString());
	        	
	        	c.setAutoCommit(false);
	        	sql = "update index set last_modified=? where id=?";
	        	ps.close();
	        	ps = c.prepareStatement(sql);
	        	ps.setTimestamp(1, new Timestamp(DateTime.now().getMillis()));
		        ps.setInt(2, id);
	        	ps.executeUpdate();
	        	System.out.println("updated");
	        	
	        	sql = "delete from asset where id_index=?";
	        	ps.close();
	        	ps = c.prepareStatement(sql);
	        	ps.setInt(1, id);
	        	ps.executeUpdate();
	        	System.out.println("deleted");
	        	
	        	sql = "insert into asset(id_index, symbol, name) values(?,?,?)";
	        	ps.close();
	        	ps = c.prepareStatement(sql);
	        	
	        	String link="http://finance.yahoo.com/q/cp?s="+index;
	        	URL url=null;
	        	Document doc=null;
	        	Element table = null;
	        	Elements rows = null;
	        	System.out.println("try table");
				try {
					url = new URL(link);
					doc = Jsoup.parse(url, 3000);
					table = doc.select("table").get(9);
		        	rows = table.select("tr");
				} catch (MalformedURLException e) {
					return "?"+e.toString();
				} catch (IOException e) {
					return "?"+e.toString();
				}
				System.out.println("loaded table");
	        	Map<String, String> assets = new HashMap<String, String>();
	        	for (int i = 1; i < rows.size(); i++) {
	        	    Element row = rows.get(i);
	        	    assets.put(row.select("td").get(0).text(), row.select("td").get(1).text());
	        	}
	        	for (Map.Entry<String, String> entry : assets.entrySet())
	        	{
	        		ps.setInt(1, id);
	        		ps.setString(2, entry.getKey());
	                ps.setString(3, entry.getValue());
	                ps.executeUpdate();
	        	}
	        	System.out.println("inserted");
	        	c.commit();	
	        } else return "?not_found";
    	} catch (SQLException e ) {
    		try { c.rollback();}
        	catch(SQLException ex) { System.err.print("SQL exception"); }
    		return "?"+e.toString();
        } finally {
        	try { if (ps != null) ps.close(); }
        	catch(SQLException ex) { System.err.print("SQL exception"); }
        }
        return "?ok";
    }

    
}