package com.cgtmse.dao;

public class DispDtls {


	private String Nm;
	private String Addr1;
	private String Addr2;
	private String Loc;
	private Integer Pin;
	private String Stcd;


	// Getter Methods 

	public String getNm() {
		return Nm;
	}

	public String getAddr1() {
		return Addr1;
	}

	public String getAddr2() {
		return Addr2;
	}

	public String getLoc() {
		return Loc;
	}

	public Integer getPin() {
		return Pin;
	}

	public String getStcd() {
		return Stcd;
	}

	// Setter Methods 

	public void setNm(String Nm) {
		this.Nm = Nm;
	}

	public void setAddr1(String Addr1) {
		this.Addr1 = Addr1;
	}

	public void setAddr2(String Addr2) {
		this.Addr2 = Addr2;
	}

	public void setLoc(String Loc) {
		this.Loc = Loc;
	}

	public void setPin(Integer Pin) {
		this.Pin = Pin;
	}

	public void setStcd(String Stcd) {
		this.Stcd = Stcd;
	}

}
