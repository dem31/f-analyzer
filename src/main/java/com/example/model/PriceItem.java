package com.example.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import org.hibernate.annotations.Type;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;

@Embeddable
public class PriceItem implements Serializable
{
	private static final long serialVersionUID = 1L;
    private LocalDateTime date;
    private Double price;
    private Double priceBench;

    public PriceItem(){}
    
    public PriceItem(String date, Double price, Double priceBench){
    	this.date=new LocalDateTime(date, DateTimeZone.UTC);
    	this.price=price;
    	this.priceBench=priceBench;
    }
    
    @Column
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

    @Column
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Column
	public Double getPriceBench() {
		return priceBench;
	}

	public void setPriceBench(Double priceBench) {
		this.priceBench = priceBench;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

    
} 
