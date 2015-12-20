package com.chaoo.trackloca;

public class SubmitResult {
	public final static String SUCCESS_DESC="success";
	public final static String FAIL_DESC="fail";
	public final static int SUCCESS_CODE=0;
	public final static int FAIL_CODE=99;
	
	private int retCode;
	private String desc;
	
	public SubmitResult() {
		super();
		this.retCode = SubmitResult.SUCCESS_CODE;
		this.desc = SubmitResult.SUCCESS_DESC;
	}
	public SubmitResult(int retCode, String desc) {
		super();
		this.retCode = retCode;
		this.desc = desc;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public int getRetCode() {
		return retCode;
	}
	public void setRetCode(int retCode) {
		this.retCode = retCode;
	}
	
	
}
