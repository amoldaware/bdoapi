package com.cgtmse.dao;

import java.util.ArrayList;


public class RefDtls {
	
	private String InvRm;
	private DocPerdDtls DocPerdDtls;
	private ArrayList <PrecDocDtls> PrecDocDtls = new ArrayList <PrecDocDtls>();
	private ArrayList <ContrDtls> ContrDtls = new ArrayList <ContrDtls>();
	
	public String getInvRm() {
		return InvRm;
	}
	public void setInvRm(String invRm) {
		InvRm = invRm;
	}
	public DocPerdDtls getDocPerdDtls() {
		return DocPerdDtls;
	}
	public void setDocPerdDtls(DocPerdDtls docPerdDtls) {
		DocPerdDtls = docPerdDtls;
	}
	public ArrayList<PrecDocDtls> getPrecDocDtls() {
		return PrecDocDtls;
	}
	public void setPrecDocDtls(ArrayList<PrecDocDtls> precDocDtls) {
		PrecDocDtls = precDocDtls;
	}
	public ArrayList<ContrDtls> getContrDtls() {
		return ContrDtls;
	}
	public void setContrDtls(ArrayList<ContrDtls> contrDtls) {
		ContrDtls = contrDtls;
	}
	

}
