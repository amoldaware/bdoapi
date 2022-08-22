package com.cgtmse.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.cgtmse.utility.DBConnection;
import com.google.gson.Gson;
//import com.irn.DBConnection;

public class JavaCodeToJson extends Thread {

	public static String convertDataToJson(String invoiceNos) {		
		Connection con=null; 
		PreparedStatement pStmt=null;
		ResultSet rs=null;
		FieldData fieldData = new FieldData();
		try {
			System.out.println(":::::INVOICE_NO::::::"+invoiceNos);
			Thread.sleep(200);
			if(con==null) {
				con = DBConnection.getConnection();
			}
				
			String ttt=null;
	//		System.out.println("DB connection:::::::"+con);		
	//		PreparedStatement ps= con.prepareStatement("SELECT * FROM EINVOICE_STAG WHERE INVOICE_NO=?");  
			pStmt= con.prepareStatement("select e.*,d.IGST_RATE,d.IGST_AMT as IGST_AMOUNT ,d.CGST_RATE,d.CGST_AMT as CGST_AMOUNT,d.SGST_RATE,d.SGST_AMT SGST_AMOUNT "
					+ "from einvoice_stag e,gst_tax_invoice g,dan_cgpan_info d "
					+ "where e.invoice_no=g.tax_inv_id and g.dan_id=d.dan_id "
					+ "and e.invoice_no=?"); 
	//		PreparedStatement ps= con.prepareStatement("SELECT * FROM EINVOICE_STAG ES,GST_TAX_INVOICE GTI WHERE ES.INVOICE_NO=GTI.TAX_INV_ID AND GTI.GENERATED_IRRN is null AND GTI.STATUS is null AND ES.INVOICE_NO=?"); 
			
			pStmt.setString(1,invoiceNos);
			rs=pStmt.executeQuery();
			while(rs.next()) {
				int count=1;
//				fieldData.setVersion(rs.getString("VERSION"));

				TranDtls tranDtlsObj=new TranDtls();
				tranDtlsObj.setTaxSch(rs.getString("TAX_SCHEME"));//Mandiatroy Field
				tranDtlsObj.setSupTyp(rs.getString("SUPPLY_TYPE_CODE"));//a)The Type of Supply field is required. b)The field Type of Supply must be a string with a minimum length of 3 and a maximum length of 10.c)Invalid value in Type of Supply
				tranDtlsObj.setRegRev("N");//a)The field Reverse Charge must be a string with a minimum length of 1 and a maximum length of 1.
				tranDtlsObj.setIgstOnIntra("N");//a)The field IGST on Intra-State Supply must be a string with a minimum length of 1 and a maximum length of 1
				tranDtlsObj.setEcmGstin(ttt);
				tranDtlsObj.setSupplydir("O");
				fieldData.setTranDtls(tranDtlsObj);

				DocDtls docObj = new DocDtls();		
				docObj.setTyp(rs.getString("DOCUMENT_TYPE_CODE"));//a)The Document Type field is required.b)The field Document Type must be a string with a minimum length of 3 and a maximum length of 3. c)Invalid value in Document Type
				docObj.setNo(invoiceNos);//a)The Document Number field is required. b)The field Document Number must be a string with a minimum length of 1 and a maximum length of 16. 
				DateFormat dffrom = new SimpleDateFormat("dd/MM/yyyy");
				String assa=convertDateFormat(rs.getString("INV_GEN_DATE"));
				System.out.println("convertDateFormat(rs.getString(\"INV_GEN_DATE\")"+assa);
				DateFormat dfto = new SimpleDateFormat("dd-MM-yyyy");  
				Date today = dffrom.parse(assa) ;
				String s = dfto.format(today);
				docObj.setDt(s);//a)The Document Date field is required. b)The field Document Date must be a string with a minimum length of 10 and a maximum length of 10

				fieldData.setDocDtls(docObj);

				SellerDtls sellerDtlsObj = new SellerDtls();
				sellerDtlsObj.setGstin(rs.getString("SUPPLIER_GSTIN"));//a)The GSTIN field is required. b)The field GSTIN must be a string with a minimum length of 15 and a maximum length of 15.
				sellerDtlsObj.setLglNm(rs.getString("SUPPLIER_LEGAL_NAME"));//a)The Legal Name field is required. b)The field Legal Name must be a string with a minimum length of 3 and a maximum length of 100.
				sellerDtlsObj.setAddr1(rs.getString("SUPPLIER_ADDRESS1"));//a)The Address 1 field is required. b)The field Address 1 must be a string with a minimum length of 1 and a maximum length of 100.
				sellerDtlsObj.setAddr2(rs.getString("SUPPLIER_ADDRESS2"));//a)The field Address 2 must be a string with a minimum length of 3 and a maximum length of 100.
				sellerDtlsObj.setLoc(rs.getString("SUPPLIER_PLACE"));//a)The Location field is required. b)The field Location must be a string with a minimum length of 3 and a maximum length of 50.
				sellerDtlsObj.setPin(rs.getInt("SUPPLIER_PINCODE"));//a)The field Pin Code must be between 100000 and 999999.
				sellerDtlsObj.setStcd(rs.getString("SUPPLIER_STATE_CODE"));//a)The State field is required. b)The field State must be a string with a minimum length of 1 and a maximum length of 2.
				sellerDtlsObj.setPh("1800222659");//a)The field Phone Number must be a string with a minimum length of 6 and a maximum length of 12.
				sellerDtlsObj.setEm("accounts@cgtmse.in");//a)The field e-Mail must be a string with a minimum length of 6 and a maximum length of 100.
				fieldData.setSellerDtls(sellerDtlsObj);	

				BuyerDtls buyerDtlsObj = new BuyerDtls();
				buyerDtlsObj.setGstin(rs.getString("RECIPIENT_GSTIN"));	//a)The GSTIN field is required. b)The field GSTIN must be a string with a minimum length of 3 and a maximum length of 15.		
				buyerDtlsObj.setLglNm(rs.getString("RECIPIENT_LEGAL_NAME"));////a)The Legal Name field is required. b)The field Legal Name must be a string with a minimum length of 3 and a maximum length of 100.
				buyerDtlsObj.setTrdNm(rs.getString("RECIPIENT_LEGAL_NAME"));////a)The field Trade Name must be a string with a minimum length of 3 and a maximum length of 100.
				buyerDtlsObj.setPos(rs.getString("PLACE_OF_SUPPLY_STATE_CODE"));//The POS field is required. b)The field POS must be a string with a minimum length of 1 and a maximum length of 2.
				buyerDtlsObj.setAddr1(rs.getString("RECIPIENT_ADDRESS1"));//a)The Address 1 field is required. b)The field Address 1 must be a string with a minimum length of 1 and a maximum length of 100.
				buyerDtlsObj.setAddr2(rs.getString("RECIPIENT_ADDRESS2"));//a)The field Address 2 must be a string with a minimum length of 3 and a maximum length of 100
				buyerDtlsObj.setLoc(rs.getString("RECIPIENT_PLACE"));//a)The Location field is required. b)The field Location must be a string with a minimum length of 3 and a maximum length of 100.
				String strbuyerPincode = rs.getString("RECIPIENT_PINCODE")!=null ? rs.getString("RECIPIENT_PINCODE") :"0";
				if(strbuyerPincode.matches("[0-9]+") && strbuyerPincode.length() > 2) {
					buyerDtlsObj.setPin(Integer.parseInt(strbuyerPincode));
			      }
				else {
					buyerDtlsObj.setPin(0);
				}
				Integer buyerPincode = buyerDtlsObj.getPin();
				buyerDtlsObj.setStcd(rs.getString("PLACE_OF_SUPPLY_STATE_CODE"));//a)The State field is required. b)The field State must be a string with a minimum length of 1 and a maximum length of 2.
				if(rs.getString("RECIPIENT_PHONE")!=null) {
					buyerDtlsObj.setPh(rs.getString("RECIPIENT_PHONE"));//a)The field Phone Number must be a string with a minimum length of 6 and a maximum length of 12.
				}else {
					buyerDtlsObj.setPh("91111111111");//a)The field Phone Number must be a string with a minimum length of 6 and a maximum length of 12.
				}
				if(rs.getString("RECIPIENT_EMAIL")!=null) {
					buyerDtlsObj.setEm(rs.getString("RECIPIENT_EMAIL"));//a)The field e-Mail must be a string with a minimum length of 6 and a maximum length of 100.
				}else {
					buyerDtlsObj.setEm("91111111111");//a)The field Phone Number must be a string with a minimum length of 6 and a maximum length of 12.
				}				
				fieldData.setBuyerDtls(buyerDtlsObj);//

				DispDtls  dispDtlsObj = new DispDtls();
				dispDtlsObj.setNm(rs.getString("RECIPIENT_LEGAL_NAME"));
				dispDtlsObj.setAddr1(rs.getString("RECIPIENT_ADDRESS1"));
				dispDtlsObj.setAddr2(rs.getString("RECIPIENT_ADDRESS2"));
				dispDtlsObj.setLoc(rs.getString("RECIPIENT_PLACE"));
				dispDtlsObj.setPin(buyerPincode);
				dispDtlsObj.setStcd(rs.getString("PLACE_OF_SUPPLY_STATE_CODE"));

				fieldData.setDispDtls(dispDtlsObj);

				ShipDtls  shipDtlsObj = new ShipDtls();
				shipDtlsObj.setGstin(rs.getString("RECIPIENT_GSTIN"));//a)The field GSTIN must be a string with a minimum length of 3 and a maximum length of 15.
				shipDtlsObj.setLglNm(rs.getString("RECIPIENT_LEGAL_NAME"));//a)The Legal Name field is required. b))The field Legal Name must be a string with a minimum length of 3 and a maximum length of 100.
				shipDtlsObj.setTrdNm(rs.getString("RECIPIENT_LEGAL_NAME"));//a)The field Trade Name must be a string with a minimum length of 3 and a maximum length of 100.
				shipDtlsObj.setAddr1(rs.getString("RECIPIENT_ADDRESS1"));//a)The Address 1 field is required. b)The field Address 1 must be a string with a minimum length of 1 and a maximum length of 100.
				shipDtlsObj.setAddr2(rs.getString("RECIPIENT_ADDRESS1"));//a)The field Address 2 must be a string with a minimum length of 3 and a maximum length of 100.
				shipDtlsObj.setLoc(rs.getString("RECIPIENT_PLACE"));//a)The Location field is required. b)The field Location must be a string with a minimum length of 3 and a maximum length of 100
				shipDtlsObj.setPin(buyerPincode);//a)The field Pin Code must be between 100000 and 999999.
				shipDtlsObj.setStcd(rs.getString("PLACE_OF_SUPPLY_STATE_CODE"));//a)The State field is required. b)The field State must be a string with a minimum length of 1 and a maximum length of 2.

				fieldData.setShipDtls(shipDtlsObj);

//				ArrayList<ItemList> itemListArray = new ArrayList <ItemList>();
				ItemList  itemListObj = new ItemList();				
				ArrayList<Item> itemArray = new ArrayList <Item>();
				
				Item item=new Item();
				
				item.setSlNo("1");//a)The Sl No. field is required. b)The field Sl No. must be a string with a minimum length of 1 and a maximum length of 6.
				item.setPrdDesc(rs.getString("ITEM_DESCRIPTION"));//a)The field Product Description must be a string with a minimum length of 3 and a maximum length of 300.
				item.setIsServc(rs.getString("IS_SERVICE"));//a)The Supply or Service field is required. b)The field Supply or Service must be a string with a minimum length of 1 and a maximum length of 1.
				item.setHsnCd(rs.getString("HSN_CODE"));//a)The HSN Code field is required. b)The field HSN Code must be a string with a minimum length of 4 and a maximum length of 8
				
				BchDtls  bchDtlsObj = new BchDtls();
				bchDtlsObj.setNm(rs.getString("CGPAN"));//a)The Batch Name field is required. b)The field Batch Name must be a string with a minimum length of 3 and a maximum length of 20
				bchDtlsObj.setExpDt("");
				bchDtlsObj.setWrDt("");
				item.setBchDtls(bchDtlsObj);
				item.setBarcde(ttt);
				item.setQty(0.0);
				item.setFreeQty(0);
				item.setUnit("NOS");
				item.setUnitPrice(rs.getDouble("ITEM_PRICE"));
				item.setTotAmt(rs.getDouble("ITEM_PRICE"));
				item.setDiscount(0);
				item.setPreTaxVal(rs.getDouble("ITEM_PRICE"));
				item.setAssAmt(rs.getDouble("ITEM_PRICE"));//a)AssAmt value should be equal to (TotAmt - Discount) for HSN - 997113 and Sl. No 1
				
				Double itemGstRate= Double.parseDouble(rs.getString("CGST_RATE"));
				item.setCgstRt(itemGstRate);
				Double itemCgstAmt= Double.parseDouble(rs.getString("CGST_AMOUNT"));
				item.setCgstAmt(itemCgstAmt);
				Double itemIgstRate= Double.parseDouble(rs.getString("IGST_RATE"));
				item.setIgstRt(itemIgstRate);
				Double itemIgstAmt= Double.parseDouble(rs.getString("IGST_AMOUNT"));
				item.setIgstAmt(itemIgstAmt);
				Double itemSgstRate= Double.parseDouble(rs.getString("SGST_RATE"));
				item.setSgstRt(itemSgstRate);
				Double itemSgstAmt= Double.parseDouble(rs.getString("SGST_AMOUNT"));
				item.setSgstAmt(itemSgstAmt);
				
//				Double itemCgstAmt= rs.getDouble("CGST_AMOUNT");
//				item.setCgstAmt(itemCgstAmt);
//				Double itemSgstAmt= rs.getDouble("SGST_UTGST_AMT");
//				
//				item.setSgstAmt(itemSgstAmt);
				item.setCesRt(0);
				item.setCesAmt(0.0);
				item.setCesNonAdvlAmt(0);
				item.setStateCesRt(0);
				item.setStateCesAmt(0.0);
				item.setStateCesNonAdvlAmt(0);
				item.setOthChrg(0);
				
				item.setTotItemVal(rs.getDouble("GROSS_AMOUNT"));
				item.setOrdLineRef("0");
				item.setOrgCntry("IN");//a)The field Orgin Country must be a string with a minimum length of 2 and a maximum length of 2
//				itemListObj.setItem(item);
				item.setPrdSlNo("0");
				ArrayList<AttribDtls> attribDtlsArray = new ArrayList <AttribDtls>();
				AttribDtls  attribDtlsObj = new AttribDtls();
				attribDtlsObj.setNm("");
				attribDtlsObj.setVal("0");
				attribDtlsArray.add(attribDtlsObj);
				item.setAttribDtls(attribDtlsArray);
//				fieldData.setItemList(itemListArray);
				
				EGST EGST=new EGST();
				EGST.setCredit_gl_id("");
				EGST.setCredit_gl_name("");
				EGST.setDebit_gl_id("");
				EGST.setDebit_gl_name("");
				EGST.setExempted_amt("");
				EGST.setNilrated_amt("");
				EGST.setNon_gst_amt("");
				EGST.setReason("");
				EGST.setSublocation("");
				item.setEGST(EGST);
				
//				itemListObj.setItem(item);
				itemArray.add(item);
				itemListObj.setItem(itemArray);
				fieldData.setItemList(itemListObj);		

				ValDtls  valObj = new ValDtls();			  
				valObj.setAssVal(rs.getDouble("ITEM_PRICE"));
				valObj.setCgstVal(itemCgstAmt);
				valObj.setSgstVal(itemSgstAmt);
				valObj.setIgstVal(itemIgstAmt);
				valObj.setCesVal(0.0);
				valObj.setStCesVal(0.0);
				valObj.setDiscount(0);
				valObj.setOthChrg(0);
				valObj.setRndOffAmt(0.0);
				valObj.setTotInvVal(rs.getDouble("GROSS_AMOUNT"));
				valObj.setTotInvValFc(rs.getDouble("GROSS_AMOUNT"));
				fieldData.setValDtls(valObj);	
				
				ArrayList<AddlDocDtls> addlDocDtlsArray = new ArrayList <AddlDocDtls>();
				
				AddlDocDtls addlDocDtls=new AddlDocDtls();
				addlDocDtls.setDocs("CGTMSE");
				addlDocDtls.setInfo("CGTMSE");
				addlDocDtls.setUrl("https://einv-apisandbox.nic.in");
				addlDocDtlsArray.add(addlDocDtls);
				fieldData.setAddlDocDtls(addlDocDtlsArray);
			}

		}catch(Exception e) {
			System.out.println("Exception JavaCodeToJson convertDataToJson()"+e.getMessage());
			e.printStackTrace();			
			DBConnection.freeResultPStmtConn(rs,pStmt,con);
			DBConnection.freeConnection(con);
		}
		finally {
			DBConnection.freeResultPStmtConn(rs,pStmt,con);
			DBConnection.freeConnection(con);
			
		}
		Gson gson = new Gson();
		String jsonString = gson.toJson(fieldData);
		System.out.println(jsonString);

		return jsonString;
	}


	public static String convertDateFormat(String invoiceDate)  {
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
		String strDate = null;
		try {
			strDate = sdf2.format(sdf1.parse(invoiceDate));
	//		System.out.println(":::::::::::strDate::::::"+strDate);
		} catch (ParseException e) {
			System.out.println("convertDateFormat ParseException::"+e.getStackTrace());
			e.printStackTrace();
		}
		return strDate;
	}


	public static java.sql.Date convertJavaDateToSql() {
		java.util.Date uDate = (java.util.Date) new Date();
		java.sql.Date sDate = new java.sql.Date(uDate.getTime());
		DateFormat df = new SimpleDateFormat("dd/MM/YYYY hh:mm:ss");
		System.out.println("Date formatted     : " + df.format(uDate));
		return sDate;
	}


	public static java.util.Date convertSqlToJavaDate(String strDate) {
		strDate = "1980-04-09";
		java.sql.Date sqlDate = java.sql.Date.valueOf(strDate);
		java.util.Date utilDate = new java.util.Date(sqlDate.getTime());
		DateFormat df = new SimpleDateFormat("dd/MM/YYYY hh:mm:ss");
		System.out.println("Date formatted: " + df.format(utilDate)+",sqlDate:"+sqlDate+".utilDate::"+utilDate);        
		return utilDate;
	}
}
