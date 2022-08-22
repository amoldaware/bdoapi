package com.cgtmse.dao;

import java.util.ArrayList;

public class Item {
	private String SlNo;
	private String PrdDesc;
	private String IsServc;
	private String HsnCd;
	private String Barcde;
	private Double Qty;
	private Integer FreeQty;
	private String Unit;
	private Double UnitPrice;
	private Double TotAmt;
	private Integer Discount;
	private Double PreTaxVal;
    private Double AssAmt;
    private Double CgstRt;
    private Double CgstAmt;
    private Double IgstRt;
    private Double IgstAmt;
    private Double SgstRt;
   private Double SgstAmt;
    private Integer CesRt;
    private Double CesAmt;
    private Integer CesNonAdvlAmt;
    private Integer StateCesRt;
    private Double StateCesAmt;
    private Integer StateCesNonAdvlAmt;
    private Integer OthChrg;
    private Double TotItemVal;
    private String OrdLineRef;
    private String OrgCntry;
    private String PrdSlNo;
    private BchDtls BchDtls;    
    private ArrayList <AttribDtls> AttribDtls = new ArrayList <AttribDtls>();
	
    private EGST EGST;
   
	public String getSlNo() {
		return SlNo;
	}
	public void setSlNo(String slNo) {
		SlNo = slNo;
	}
	public String getPrdDesc() {
		return PrdDesc;
	}
	public void setPrdDesc(String prdDesc) {
		PrdDesc = prdDesc;
	}
	public String getIsServc() {
		return IsServc;
	}
	public void setIsServc(String isServc) {
		IsServc = isServc;
	}
	public String getHsnCd() {
		return HsnCd;
	}
	public void setHsnCd(String hsnCd) {
		HsnCd = hsnCd;
	}
	public String getBarcde() {
		return Barcde;
	}
	public void setBarcde(String barcde) {
		Barcde = barcde;
	}
	public Double getQty() {
		return Qty;
	}
	public void setQty(Double qty) {
		Qty = qty;
	}
	public Integer getFreeQty() {
		return FreeQty;
	}
	public void setFreeQty(Integer freeQty) {
		FreeQty = freeQty;
	}
	public String getUnit() {
		return Unit;
	}
	public void setUnit(String unit) {
		Unit = unit;
	}
	public Double getUnitPrice() {
		return UnitPrice;
	}
	public void setUnitPrice(Double unitPrice) {
		UnitPrice = unitPrice;
	}
	public Double getTotAmt() {
		return TotAmt;
	}
	public void setTotAmt(Double totAmt) {
		TotAmt = totAmt;
	}
	public Integer getDiscount() {
		return Discount;
	}
	public void setDiscount(Integer discount) {
		Discount = discount;
	}
	public Double getPreTaxVal() {
		return PreTaxVal;
	}
	public void setPreTaxVal(Double preTaxVal) {
		PreTaxVal = preTaxVal;
	}
	public Double getAssAmt() {
		return AssAmt;
	}
	public void setAssAmt(Double assAmt) {
		AssAmt = assAmt;
	}
	public Double getCgstRt() {
		return CgstRt;
	}
	public void setCgstRt(Double cgstRt) {
		CgstRt = cgstRt;
	}
	public Double getCgstAmt() {
		return CgstAmt;
	}
	public void setCgstAmt(Double cgstAmt) {
		CgstAmt = cgstAmt;
	}
	public Double getIgstRt() {
		return IgstRt;
	}
	public void setIgstRt(Double igstRt) {
		IgstRt = igstRt;
	}
	public Double getIgstAmt() {
		return IgstAmt;
	}
	public void setIgstAmt(Double igstAmt) {
		IgstAmt = igstAmt;
	}
	public Double getSgstRt() {
		return SgstRt;
	}
	public void setSgstRt(Double sgstRt) {
		SgstRt = sgstRt;
	}
	public Double getSgstAmt() {
		return SgstAmt;
	}
	public void setSgstAmt(Double sgstAmt) {
		SgstAmt = sgstAmt;
	}
	public Integer getCesRt() {
		return CesRt;
	}
	public void setCesRt(Integer cesRt) {
		CesRt = cesRt;
	}
	public Double getCesAmt() {
		return CesAmt;
	}
	public void setCesAmt(Double cesAmt) {
		CesAmt = cesAmt;
	}
	public Integer getCesNonAdvlAmt() {
		return CesNonAdvlAmt;
	}
	public void setCesNonAdvlAmt(Integer cesNonAdvlAmt) {
		CesNonAdvlAmt = cesNonAdvlAmt;
	}
	public Integer getStateCesRt() {
		return StateCesRt;
	}
	public void setStateCesRt(Integer stateCesRt) {
		StateCesRt = stateCesRt;
	}
	public Double getStateCesAmt() {
		return StateCesAmt;
	}
	public void setStateCesAmt(Double stateCesAmt) {
		StateCesAmt = stateCesAmt;
	}
	public Integer getStateCesNonAdvlAmt() {
		return StateCesNonAdvlAmt;
	}
	public void setStateCesNonAdvlAmt(Integer stateCesNonAdvlAmt) {
		StateCesNonAdvlAmt = stateCesNonAdvlAmt;
	}
	public Integer getOthChrg() {
		return OthChrg;
	}
	public void setOthChrg(Integer othChrg) {
		OthChrg = othChrg;
	}
	public Double getTotItemVal() {
		return TotItemVal;
	}
	public void setTotItemVal(Double totItemVal) {
		TotItemVal = totItemVal;
	}
	public String getOrdLineRef() {
		return OrdLineRef;
	}
	public void setOrdLineRef(String ordLineRef) {
		OrdLineRef = ordLineRef;
	}
	public String getOrgCntry() {
		return OrgCntry;
	}
	public void setOrgCntry(String orgCntry) {
		OrgCntry = orgCntry;
	}
	public String getPrdSlNo() {
		return PrdSlNo;
	}
	public void setPrdSlNo(String prdSlNo) {
		PrdSlNo = prdSlNo;
	}
	public BchDtls getBchDtls() {
		return BchDtls;
	}
	public void setBchDtls(BchDtls bchDtls) {
		BchDtls = bchDtls;
	}
	public EGST getEGST() {
		return EGST;
	}
	public void setEGST(EGST eGST) {
		EGST = eGST;
	}
	public ArrayList<AttribDtls> getAttribDtls() {
		return AttribDtls;
	}
	public void setAttribDtls(ArrayList<AttribDtls> attribDtls) {
		AttribDtls = attribDtls;
	}
	
}
