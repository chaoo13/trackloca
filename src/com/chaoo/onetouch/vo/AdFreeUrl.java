package com.chaoo.onetouch.vo;

import java.util.Date;

public class AdFreeUrl {
	
	private String url;
	private String countryCode;
	private String requestType;
	private Date regAton;
	
	public AdFreeUrl(String url, String countryCode, String requestType, Date regAton) {
		super();
		this.url = url;
		this.countryCode = countryCode;
		this.requestType = requestType;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	public Date getRegAton() {
		return regAton;
	}
	public void setRegAton(Date regAton) {
		this.regAton = regAton;
	} 
	
}
