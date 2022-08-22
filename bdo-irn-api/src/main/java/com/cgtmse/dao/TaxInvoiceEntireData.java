package com.cgtmse.dao;
import java.util.ArrayList;
public class TaxInvoiceEntireData {
	private String version;
	
	private String tranTaxSch;
	private String tranSupTyp;
	private String tranRegRev;
	private String tranIgstOnIntra;

	private String docTyp;
	private String docNo;
	private String docDt;

	private String sellerGstin;
	private String sellerLglNm;
	private String sellerAddr1;
	private String sellerAddr2;
	private String sellerLoc;
	private Integer sellerPin;
	private String sellerStcd;
	private String sellerPh;
	private String sellerEm;

	private String buyerGstin;
	private String buyerLglNm;
	private String buyerTrdNm;
	private String buyerPos;
	private String buyerAddr1;
	private String buyerAddr2;
	private String buyerLoc;
	private Integer buyerPin;
	private String buyerStcd;
	private String buyerPh;
	private String buyerEm;

	private String displayNm;
	private String displayAddr1;
	private String displayAddr2;
	private String displayLoc;
	private Integer displayPin;
	private String displayStcd;

	private String shipGstin;
	private String shipLglNm;
	private String shipTrdNm;
	private String shipAddr1;
	private String shipAddr2;
	private String shipLoc;
	private Integer shipPin;
	private String shipStcd;

	private String itemSlNo;
	private String itemPrdDesc;
	private String itemIsServc;
	private String itemHsnCd;
	private String itemBarcde;
	private Double itemQty;
	private Integer itemFreeQty;
	private String itemUnit;
	private Double itemUnitPrice;
	private Double itemTotAmt;
	private Integer itemDiscount;
	private Double itemPreTaxVal;
	private Double itemAssAmt;
	private Double itemGstRt;
	private Double itemIgstAmt;
	private Double itemCgstAmt;
	private Double itemSgstAmt;
	private Integer itemCesRt;
	private Double itemCesAmt;
	private Integer itemCesNonAdvlAmt;
	private Integer itemStateCesRt;
	private Double itemStateCesAmt;
	private Integer itemStateCesNonAdvlAmt;
	private Integer itemOthChrg;
	private Double itemTotItemVal;
	private String itemOrdLineRef;
	private String itemOrgCntry;
	private String itemPrdSlNo;


	private String bchNm;

	private Double valAssVal;
	private Double valCgstVal;
	private Double valSgstVal;
	private Double valIgstVal;
	private Double valCesVal;
	private Double valStCesVal;
	private Integer valDiscount;
	private Integer valOthChrg;
	private Double valRndOffAmt;
	private Double valTotInvVal;
	private Double valTotInvValFc;
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getTranTaxSch() {
		return tranTaxSch;
	}
	public void setTranTaxSch(String tranTaxSch) {
		this.tranTaxSch = tranTaxSch;
	}
	public String getTranSupTyp() {
		return tranSupTyp;
	}
	public void setTranSupTyp(String tranSupTyp) {
		this.tranSupTyp = tranSupTyp;
	}
	public String getTranRegRev() {
		return tranRegRev;
	}
	public void setTranRegRev(String tranRegRev) {
		this.tranRegRev = tranRegRev;
	}
	public String getTranIgstOnIntra() {
		return tranIgstOnIntra;
	}
	public void setTranIgstOnIntra(String tranIgstOnIntra) {
		this.tranIgstOnIntra = tranIgstOnIntra;
	}
	public String getDocTyp() {
		return docTyp;
	}
	public void setDocTyp(String docTyp) {
		this.docTyp = docTyp;
	}
	public String getDocNo() {
		return docNo;
	}
	public void setDocNo(String docNo) {
		this.docNo = docNo;
	}
	public String getDocDt() {
		return docDt;
	}
	public void setDocDt(String docDt) {
		this.docDt = docDt;
	}
	public String getSellerGstin() {
		return sellerGstin;
	}
	public void setSellerGstin(String sellerGstin) {
		this.sellerGstin = sellerGstin;
	}
	public String getSellerLglNm() {
		return sellerLglNm;
	}
	public void setSellerLglNm(String sellerLglNm) {
		this.sellerLglNm = sellerLglNm;
	}
	public String getSellerAddr1() {
		return sellerAddr1;
	}
	public void setSellerAddr1(String sellerAddr1) {
		this.sellerAddr1 = sellerAddr1;
	}
	public String getSellerAddr2() {
		return sellerAddr2;
	}
	public void setSellerAddr2(String sellerAddr2) {
		this.sellerAddr2 = sellerAddr2;
	}
	public String getSellerLoc() {
		return sellerLoc;
	}
	public void setSellerLoc(String sellerLoc) {
		this.sellerLoc = sellerLoc;
	}
	public Integer getSellerPin() {
		return sellerPin;
	}
	public void setSellerPin(Integer sellerPin) {
		this.sellerPin = sellerPin;
	}
	public String getSellerStcd() {
		return sellerStcd;
	}
	public void setSellerStcd(String sellerStcd) {
		this.sellerStcd = sellerStcd;
	}
	public String getSellerPh() {
		return sellerPh;
	}
	public void setSellerPh(String sellerPh) {
		this.sellerPh = sellerPh;
	}
	public String getSellerEm() {
		return sellerEm;
	}
	public void setSellerEm(String sellerEm) {
		this.sellerEm = sellerEm;
	}
	public String getBuyerGstin() {
		return buyerGstin;
	}
	public void setBuyerGstin(String buyerGstin) {
		this.buyerGstin = buyerGstin;
	}
	public String getBuyerLglNm() {
		return buyerLglNm;
	}
	public void setBuyerLglNm(String buyerLglNm) {
		this.buyerLglNm = buyerLglNm;
	}
	public String getBuyerTrdNm() {
		return buyerTrdNm;
	}
	public void setBuyerTrdNm(String buyerTrdNm) {
		this.buyerTrdNm = buyerTrdNm;
	}
	public String getBuyerPos() {
		return buyerPos;
	}
	public void setBuyerPos(String buyerPos) {
		this.buyerPos = buyerPos;
	}
	public String getBuyerAddr1() {
		return buyerAddr1;
	}
	public void setBuyerAddr1(String buyerAddr1) {
		this.buyerAddr1 = buyerAddr1;
	}
	public String getBuyerAddr2() {
		return buyerAddr2;
	}
	public void setBuyerAddr2(String buyerAddr2) {
		this.buyerAddr2 = buyerAddr2;
	}
	public String getBuyerLoc() {
		return buyerLoc;
	}
	public void setBuyerLoc(String buyerLoc) {
		this.buyerLoc = buyerLoc;
	}
	public Integer getBuyerPin() {
		return buyerPin;
	}
	public void setBuyerPin(Integer buyerPin) {
		this.buyerPin = buyerPin;
	}
	public String getBuyerStcd() {
		return buyerStcd;
	}
	public void setBuyerStcd(String buyerStcd) {
		this.buyerStcd = buyerStcd;
	}
	public String getBuyerPh() {
		return buyerPh;
	}
	public void setBuyerPh(String buyerPh) {
		this.buyerPh = buyerPh;
	}
	public String getBuyerEm() {
		return buyerEm;
	}
	public void setBuyerEm(String buyerEm) {
		this.buyerEm = buyerEm;
	}
	public String getDisplayNm() {
		return displayNm;
	}
	public void setDisplayNm(String displayNm) {
		this.displayNm = displayNm;
	}
	public String getDisplayAddr1() {
		return displayAddr1;
	}
	public void setDisplayAddr1(String displayAddr1) {
		this.displayAddr1 = displayAddr1;
	}
	public String getDisplayAddr2() {
		return displayAddr2;
	}
	public void setDisplayAddr2(String displayAddr2) {
		this.displayAddr2 = displayAddr2;
	}
	public String getDisplayLoc() {
		return displayLoc;
	}
	public void setDisplayLoc(String displayLoc) {
		this.displayLoc = displayLoc;
	}
	public Integer getDisplayPin() {
		return displayPin;
	}
	public void setDisplayPin(Integer displayPin) {
		this.displayPin = displayPin;
	}
	public String getDisplayStcd() {
		return displayStcd;
	}
	public void setDisplayStcd(String displayStcd) {
		this.displayStcd = displayStcd;
	}
	public String getShipGstin() {
		return shipGstin;
	}
	public void setShipGstin(String shipGstin) {
		this.shipGstin = shipGstin;
	}
	public String getShipLglNm() {
		return shipLglNm;
	}
	public void setShipLglNm(String shipLglNm) {
		this.shipLglNm = shipLglNm;
	}
	public String getShipTrdNm() {
		return shipTrdNm;
	}
	public void setShipTrdNm(String shipTrdNm) {
		this.shipTrdNm = shipTrdNm;
	}
	public String getShipAddr1() {
		return shipAddr1;
	}
	public void setShipAddr1(String shipAddr1) {
		this.shipAddr1 = shipAddr1;
	}
	public String getShipAddr2() {
		return shipAddr2;
	}
	public void setShipAddr2(String shipAddr2) {
		this.shipAddr2 = shipAddr2;
	}
	public String getShipLoc() {
		return shipLoc;
	}
	public void setShipLoc(String shipLoc) {
		this.shipLoc = shipLoc;
	}
	public Integer getShipPin() {
		return shipPin;
	}
	public void setShipPin(Integer shipPin) {
		this.shipPin = shipPin;
	}
	public String getShipStcd() {
		return shipStcd;
	}
	public void setShipStcd(String shipStcd) {
		this.shipStcd = shipStcd;
	}
	public String getItemSlNo() {
		return itemSlNo;
	}
	public void setItemSlNo(String itemSlNo) {
		this.itemSlNo = itemSlNo;
	}
	public String getItemPrdDesc() {
		return itemPrdDesc;
	}
	public void setItemPrdDesc(String itemPrdDesc) {
		this.itemPrdDesc = itemPrdDesc;
	}
	public String getItemIsServc() {
		return itemIsServc;
	}
	public void setItemIsServc(String itemIsServc) {
		this.itemIsServc = itemIsServc;
	}
	public String getItemHsnCd() {
		return itemHsnCd;
	}
	public void setItemHsnCd(String itemHsnCd) {
		this.itemHsnCd = itemHsnCd;
	}
	public String getItemBarcde() {
		return itemBarcde;
	}
	public void setItemBarcde(String itemBarcde) {
		this.itemBarcde = itemBarcde;
	}
	public Double getItemQty() {
		return itemQty;
	}
	public void setItemQty(Double itemQty) {
		this.itemQty = itemQty;
	}
	public Integer getItemFreeQty() {
		return itemFreeQty;
	}
	public void setItemFreeQty(Integer itemFreeQty) {
		this.itemFreeQty = itemFreeQty;
	}
	public String getItemUnit() {
		return itemUnit;
	}
	public void setItemUnit(String itemUnit) {
		this.itemUnit = itemUnit;
	}
	public Double getItemUnitPrice() {
		return itemUnitPrice;
	}
	public void setItemUnitPrice(Double itemUnitPrice) {
		this.itemUnitPrice = itemUnitPrice;
	}
	public Double getItemTotAmt() {
		return itemTotAmt;
	}
	public void setItemTotAmt(Double itemTotAmt) {
		this.itemTotAmt = itemTotAmt;
	}
	public Integer getItemDiscount() {
		return itemDiscount;
	}
	public void setItemDiscount(Integer itemDiscount) {
		this.itemDiscount = itemDiscount;
	}
	public Double getItemPreTaxVal() {
		return itemPreTaxVal;
	}
	public void setItemPreTaxVal(Double itemPreTaxVal) {
		this.itemPreTaxVal = itemPreTaxVal;
	}
	public Double getItemAssAmt() {
		return itemAssAmt;
	}
	public void setItemAssAmt(Double itemAssAmt) {
		this.itemAssAmt = itemAssAmt;
	}
	public Double getItemGstRt() {
		return itemGstRt;
	}
	public void setItemGstRt(Double itemGstRt) {
		this.itemGstRt = itemGstRt;
	}
	public Double getItemIgstAmt() {
		return itemIgstAmt;
	}
	public void setItemIgstAmt(Double itemIgstAmt) {
		this.itemIgstAmt = itemIgstAmt;
	}
	public Double getItemCgstAmt() {
		return itemCgstAmt;
	}
	public void setItemCgstAmt(Double itemCgstAmt) {
		this.itemCgstAmt = itemCgstAmt;
	}
	public Double getItemSgstAmt() {
		return itemSgstAmt;
	}
	public void setItemSgstAmt(Double itemSgstAmt) {
		this.itemSgstAmt = itemSgstAmt;
	}
	public Integer getItemCesRt() {
		return itemCesRt;
	}
	public void setItemCesRt(Integer itemCesRt) {
		this.itemCesRt = itemCesRt;
	}
	public Double getItemCesAmt() {
		return itemCesAmt;
	}
	public void setItemCesAmt(Double itemCesAmt) {
		this.itemCesAmt = itemCesAmt;
	}
	public Integer getItemCesNonAdvlAmt() {
		return itemCesNonAdvlAmt;
	}
	public void setItemCesNonAdvlAmt(Integer itemCesNonAdvlAmt) {
		this.itemCesNonAdvlAmt = itemCesNonAdvlAmt;
	}
	public Integer getItemStateCesRt() {
		return itemStateCesRt;
	}
	public void setItemStateCesRt(Integer itemStateCesRt) {
		this.itemStateCesRt = itemStateCesRt;
	}
	public Double getItemStateCesAmt() {
		return itemStateCesAmt;
	}
	public void setItemStateCesAmt(Double itemStateCesAmt) {
		this.itemStateCesAmt = itemStateCesAmt;
	}
	public Integer getItemStateCesNonAdvlAmt() {
		return itemStateCesNonAdvlAmt;
	}
	public void setItemStateCesNonAdvlAmt(Integer itemStateCesNonAdvlAmt) {
		this.itemStateCesNonAdvlAmt = itemStateCesNonAdvlAmt;
	}
	public Integer getItemOthChrg() {
		return itemOthChrg;
	}
	public void setItemOthChrg(Integer itemOthChrg) {
		this.itemOthChrg = itemOthChrg;
	}
	public Double getItemTotItemVal() {
		return itemTotItemVal;
	}
	public void setItemTotItemVal(Double itemTotItemVal) {
		this.itemTotItemVal = itemTotItemVal;
	}
	public String getItemOrdLineRef() {
		return itemOrdLineRef;
	}
	public void setItemOrdLineRef(String itemOrdLineRef) {
		this.itemOrdLineRef = itemOrdLineRef;
	}
	public String getItemOrgCntry() {
		return itemOrgCntry;
	}
	public void setItemOrgCntry(String itemOrgCntry) {
		this.itemOrgCntry = itemOrgCntry;
	}
	public String getItemPrdSlNo() {
		return itemPrdSlNo;
	}
	public void setItemPrdSlNo(String itemPrdSlNo) {
		this.itemPrdSlNo = itemPrdSlNo;
	}
	public String getBchNm() {
		return bchNm;
	}
	public void setBchNm(String bchNm) {
		this.bchNm = bchNm;
	}
	public Double getValAssVal() {
		return valAssVal;
	}
	public void setValAssVal(Double valAssVal) {
		this.valAssVal = valAssVal;
	}
	public Double getValCgstVal() {
		return valCgstVal;
	}
	public void setValCgstVal(Double valCgstVal) {
		this.valCgstVal = valCgstVal;
	}
	public Double getValSgstVal() {
		return valSgstVal;
	}
	public void setValSgstVal(Double valSgstVal) {
		this.valSgstVal = valSgstVal;
	}
	public Double getValIgstVal() {
		return valIgstVal;
	}
	public void setValIgstVal(Double valIgstVal) {
		this.valIgstVal = valIgstVal;
	}
	public Double getValCesVal() {
		return valCesVal;
	}
	public void setValCesVal(Double valCesVal) {
		this.valCesVal = valCesVal;
	}
	public Double getValStCesVal() {
		return valStCesVal;
	}
	public void setValStCesVal(Double valStCesVal) {
		this.valStCesVal = valStCesVal;
	}
	public Integer getValDiscount() {
		return valDiscount;
	}
	public void setValDiscount(Integer valDiscount) {
		this.valDiscount = valDiscount;
	}
	public Integer getValOthChrg() {
		return valOthChrg;
	}
	public void setValOthChrg(Integer valOthChrg) {
		this.valOthChrg = valOthChrg;
	}
	public Double getValRndOffAmt() {
		return valRndOffAmt;
	}
	public void setValRndOffAmt(Double valRndOffAmt) {
		this.valRndOffAmt = valRndOffAmt;
	}
	public Double getValTotInvVal() {
		return valTotInvVal;
	}
	public void setValTotInvVal(Double valTotInvVal) {
		this.valTotInvVal = valTotInvVal;
	}
	public Double getValTotInvValFc() {
		return valTotInvValFc;
	}
	public void setValTotInvValFc(Double valTotInvValFc) {
		this.valTotInvValFc = valTotInvValFc;
	}
	
	
	
	


}
