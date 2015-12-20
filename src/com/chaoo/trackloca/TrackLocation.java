package com.chaoo.trackloca;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.GeoPt;

public class TrackLocation {
	
	private String userId;
	private Date regAton;
	private Date putAton;
	private GeoPt geoPt;
	private int seqNo;
	private String locaId;
	

	public TrackLocation(String userId, Date regAton, Date inputAton, GeoPt geoPt) {
		super();
		this.userId = userId;
		this.regAton = regAton;
		this.putAton = inputAton;
		this.geoPt = geoPt;
	}
	public TrackLocation(String userId, Date regAton, Date inputAton, GeoPt geoPt, int seqNo) {
		super();
		this.userId = userId;
		this.regAton = regAton;
		this.putAton = inputAton;
		this.geoPt = geoPt;
		this.seqNo = seqNo;
		this.locaId = "";
	}

	public TrackLocation(String userId, Date regAton, Date inputAton, GeoPt geoPt, int seqNo, String locaId) {
		super();
		this.userId = userId;
		this.regAton = regAton;
		this.putAton = inputAton;
		this.geoPt = geoPt;
		this.seqNo = seqNo;
		this.locaId = locaId;
	}
	
	public String getLocaId() {
		return locaId;
	}
	public void setLocaId(String locaId) {
		this.locaId = locaId;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getRegAton() {
		return regAton;
	}

	public void setRegAton(Date regAton) {
		this.regAton = regAton;
	}

	public Date getPutAton() {
		return putAton;
	}

	public void setPutAton(Date putAton) {
		this.putAton = putAton;
	}

	public GeoPt getGeoPt() {
		return geoPt;
	}

	public void setGeoPt(GeoPt geoPt) {
		this.geoPt = geoPt;
	}

	public int getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(int seqNo) {
		this.seqNo = seqNo;
	}
	
}
