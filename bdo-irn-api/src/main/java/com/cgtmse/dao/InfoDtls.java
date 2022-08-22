package com.cgtmse.dao;

public class InfoDtls {

	private String InfCd;
	private Desc Desc;

	public void setInfCd(String InfCd){
		this.InfCd = InfCd;
	}
	public String getInfCd(){
		return this.InfCd;
	}
	public void setDesc(Desc Desc){
		this.Desc = Desc;
	}
	public Desc getDesc(){
		return this.Desc;
	}

	@Override
	public String toString() {
		return "InfoDtls [InfCd=" + InfCd  +", Desc=" + Desc+ "]";
	}
}
