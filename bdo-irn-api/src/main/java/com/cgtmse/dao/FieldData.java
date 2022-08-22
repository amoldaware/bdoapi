package com.cgtmse.dao;

import java.util.ArrayList;

public class FieldData {
	
	private String Version;
	private TranDtls TranDtls;
	private DocDtls DocDtls;
	private SellerDtls SellerDtls;
	private BuyerDtls BuyerDtls;
	private DispDtls DispDtls;
	private ShipDtls ShipDtls;
	private ItemList ItemList;
	private ValDtls ValDtls;
	private PayDtls PayDtls;
	private RefDtls RefDtls;
	private ArrayList <Item> item = new ArrayList <Item>();
	
	private ArrayList <AddlDocDtls> AddlDocDtls = new ArrayList <AddlDocDtls>();
	private ExpDtls ExpDtls;
	private EwbDtls EwbDtls;
//	private IrnCancel 
	
	public String getVersion() {
		return Version;
	}
	public void setVersion(String version) {
		Version = version;
	}
	public TranDtls getTranDtls() {
		return TranDtls;
	}
	public void setTranDtls(TranDtls tranDtls) {
		TranDtls = tranDtls;
	}
	public DocDtls getDocDtls() {
		return DocDtls;
	}
	public void setDocDtls(DocDtls docDtls) {
		DocDtls = docDtls;
	}
	public SellerDtls getSellerDtls() {
		return SellerDtls;
	}
	public void setSellerDtls(SellerDtls sellerDtls) {
		SellerDtls = sellerDtls;
	}
	public BuyerDtls getBuyerDtls() {
		return BuyerDtls;
	}
	public void setBuyerDtls(BuyerDtls buyerDtls) {
		BuyerDtls = buyerDtls;
	}
	public DispDtls getDispDtls() {
		return DispDtls;
	}
	public void setDispDtls(DispDtls dispDtls) {
		DispDtls = dispDtls;
	}
	public ShipDtls getShipDtls() {
		return ShipDtls;
	}
	public void setShipDtls(ShipDtls shipDtls) {
		ShipDtls = shipDtls;
	}
	
	public ItemList getItemList() {
		return ItemList;
	}
	public void setItemList(ItemList itemList) {
		ItemList = itemList;
	}
	public ValDtls getValDtls() {
		return ValDtls;
	}
	public void setValDtls(ValDtls valDtls) {
		ValDtls = valDtls;
	}
	public PayDtls getPayDtls() {
		return PayDtls;
	}
	public void setPayDtls(PayDtls payDtls) {
		PayDtls = payDtls;
	}
	public RefDtls getRefDtls() {
		return RefDtls;
	}
	public void setRefDtls(RefDtls refDtls) {
		RefDtls = refDtls;
	}
	public ArrayList<AddlDocDtls> getAddlDocDtls() {
		return AddlDocDtls;
	}
	public void setAddlDocDtls(ArrayList<AddlDocDtls> addlDocDtls) {
		AddlDocDtls = addlDocDtls;
	}
	public ExpDtls getExpDtls() {
		return ExpDtls;
	}
	public void setExpDtls(ExpDtls expDtls) {
		ExpDtls = expDtls;
	}
	public EwbDtls getEwbDtls() {
		return EwbDtls;
	}
	public void setEwbDtls(EwbDtls ewbDtls) {
		EwbDtls = ewbDtls;
	}
	public ArrayList<Item> getItem() {
		return item;
	}
	public void setItem(ArrayList<Item> item) {
		this.item = item;
	}
	
}
