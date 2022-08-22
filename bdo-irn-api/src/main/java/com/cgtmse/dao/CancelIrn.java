package com.cgtmse.dao;

public class CancelIrn {
	
	private String Irn;
	private String CnlRsn;
	private String CnlRem;

	// Getter Methods 
	public String getIrn() {
		return Irn;
	}

	public String getCnlRsn() {
		return CnlRsn;
	}

	public String getCnlRem() {
		return CnlRem;
	}

	// Setter Methods 
	public void setIrn(String Irn) {
		this.Irn = Irn;
	}

	public void setCnlRsn(String CnlRsn) {
		this.CnlRsn = CnlRsn;
	}

	public void setCnlRem(String CnlRem) {
		this.CnlRem = CnlRem;
	}
}
