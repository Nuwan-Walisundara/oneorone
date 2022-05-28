package com.dmz.charging.station.finder.service.model.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="station")
public class Station {
	
	public Station(Long id,String name,double latitude,double longitude,Long company_id) {
		this.id= id;
		this.name =name;
		this.latitudeInRadian = latitude;
		this.longitudeInRadian =longitude;
		company= new Company();
		
		this.company.setId(company_id);
	}
	
	@javax.persistence.Id
	@Column(name="id")
	@GeneratedValue
	private Long id ;
	
	@Column(name="name")
	private String  name;
	
	/*
	 * @Column(name="latitude") private double latitude;
	 * 
	 * @Column(name="longitude") private double longitude;
	 */
	
	private double   latitudeInRadian;
	private double   longitudeInRadian;
	
	@ManyToOne(cascade={CascadeType.ALL})
	@JoinColumn(name="company_id")
	private Company company;


	
	
	
}
