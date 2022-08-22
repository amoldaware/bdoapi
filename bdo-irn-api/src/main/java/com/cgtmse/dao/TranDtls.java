package com.cgtmse.dao;

public class TranDtls {


	 private String TaxSch;
	 private String SupTyp;
	 private String RegRev;
	 private String IgstOnIntra;
	 private String EcmGstin;
	 private String supplydir;

	 // Getter Methods 

	 public String getTaxSch() {
	  return TaxSch;
	 }

	 public String getSupTyp() {
	  return SupTyp;
	 }

	 public String getRegRev() {
	  return RegRev;
	 }

	 public String getIgstOnIntra() {
	  return IgstOnIntra;
	 }

	 // Setter Methods 

	 public void setTaxSch(String TaxSch) {
	  this.TaxSch = TaxSch;
	 }

	 public void setSupTyp(String SupTyp) {
	  this.SupTyp = SupTyp;
	 }

	 public void setRegRev(String RegRev) {
	  this.RegRev = RegRev;
	 }

	 public void setIgstOnIntra(String IgstOnIntra) {
	  this.IgstOnIntra = IgstOnIntra;
	 }

	public String getEcmGstin() {
		return EcmGstin;
	}

	public void setEcmGstin(String ecmGstin) {
		EcmGstin = ecmGstin;
	}

	public String getSupplydir() {
		return supplydir;
	}

	public void setSupplydir(String supplydir) {
		this.supplydir = supplydir;
	}
	
	 
}
