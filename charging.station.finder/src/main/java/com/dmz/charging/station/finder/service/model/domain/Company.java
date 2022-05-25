package com.dmz.charging.station.finder.service.model.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name="company")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Company {
	
	@javax.persistence.Id
	@Column(name="id")
	@GeneratedValue
	private Long Id;
 
	
	@ManyToOne(cascade={CascadeType.ALL})
	@JoinColumn(name="parent_company_id")
	private Company parent;

	@OneToMany(mappedBy="parent")
	private List<Company> subordinates = new ArrayList<Company>();

	
	private String name;
	
	@Column(name="company_code")
	private String  companyCode;
 
	
	
}
