package kr.co.kpcard.billingservice.app.repository.erp.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class FiAdocuPK implements Serializable{

	private static final long serialVersionUID = 7107379801030150486L;
	
	protected String rowId;   
	protected String rowNo;
	
}
