package com.cgtmse.dao;

public class ShipDtls {


	private String Gstin;
	private String LglNm;
	private String TrdNm;
	private String Addr1;
	private String Addr2;
	private String Loc;
	private Integer Pin;
	private String Stcd;


	// Getter Methods 

	public String getGstin() {
		return Gstin;
	}

	public String getLglNm() {
		return LglNm;
	}

	public String getTrdNm() {
		return TrdNm;
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

	public void setGstin(String Gstin) {
		this.Gstin = Gstin;
	}

	public void setLglNm(String LglNm) {
		this.LglNm = LglNm;
	}

	public void setTrdNm(String TrdNm) {
		this.TrdNm = TrdNm;
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
