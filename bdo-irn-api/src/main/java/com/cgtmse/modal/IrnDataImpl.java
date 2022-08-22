package com.cgtmse.modal;

import java.security.PublicKey;
/*
 * 
 * Author:Amol Daware
 * 
 * 
 * */
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Logger;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import com.cgtmse.bdo.RSAEncryptionUtility;
import com.cgtmse.dao.JavaCodeToJson;
import com.cgtmse.dao.JsonResponceSuccess;
import com.cgtmse.utility.DBConnection;
import com.cgtmse.utility.IrnUtility;
import com.google.gson.Gson;

public class IrnDataImpl {
	private static final String DATE_FORMAT = "dd-M-yyyy hh:mm:ss a";

	static Logger log = Logger.getLogger(IrnDataImpl.class.getName());
	static final long DAY_1 = 24 * 60 * 60 * 1000;
	final static String client_id = "ad3130832048cab8051412474b0681";
	
	@Value("${bdo.public.key.path}")
	private String bdoPubKeyPath;
	@Value("${nic.public.key.path}")
	private String nicPubKeyPath;

	String encryptedAppKey = null, encryptedClientSecret = null, decryptedBDoSek = null;
	String decryptedResponse = null, bdo_authtoken = null, bdo_sek = null;

	//String client_id = "ad3130832048cab8051412474b0681";
	final String client_secret = "13fec05d14ae83308e01cdd2370c70";

	public static ArrayList<String> getInvoiceIds(String appType, String fromdate, String todate) {
		System.out.println(":::::getInvoiceIds method execution started===");
		ArrayList<String> invoiceIdList = new ArrayList<String>();
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		Connection connection = null;
		String getData = null;
		System.out.println("fromdate:::::::::" + fromdate + "  todate:::::::::" + todate);
	
		try {
			connection = DBConnection.getConnection();
			
			/*
			 * if(connection==null) { connection = DBConnection.getConnection();
			 * System.out.println("fail"); }else { System.out.println("success"); }
			 */
			String query = "SELECT GTI.TAX_INV_ID FROM GST_TAX_INVOICE GTI,EINVOICE_STAG ES WHERE GTI.TAX_INV_ID=ES.INVOICE_NO "
					+ "AND ES.INV_GEN_DATE BETWEEN TO_DATE(?,'dd-MON-yyyy') AND TO_DATE(?,'dd-MON-yyyy') "
					+ "AND GTI.GENERATED_IRRN is null AND GTI.STATUS is null AND ES.APPL_TYPE=? "
					+ "AND rownum <=? "
//					+ "AND ES.INV_GEN_DATE>'01-Apr-2021' "
//					+ "AND ES.INVOICE_NO IN ('2021-22000131631')"
//					+,'2021-22000416678','2021-22000131674', "
//					+ "'2021-22000110571','2021-22000474614','2021-22000474615','2021-22000489120','2021-22000489121',"
//					+ "'2021-22000239345','2021-22000237206','2021-22000484338','2021-22000484337','2021-22000489122',"
//					+ "'2021-22000507120','2021-22000489119','2021-22000519019','2021-22000519018','2021-22000519017',"
//					+ "'2021-22000476178','2021-22000476177','2021-22000446077','2021-22000476172','2021-22000446078',"
//					+ "'2021-22000446075','2021-22000519034','2021-22000519033','2021-22000446074')"
					+ " order by ES.INV_GEN_DATE ";

			System.out.println(":::::connection for geting tax invoice id:::::::" + connection);
			System.out.println("query::::::" + query);
			pStmt = connection.prepareStatement(query);
			pStmt.setString(1, fromdate);
			pStmt.setString(2, todate);
			pStmt.setString(3, appType);
			pStmt.setString(4, "1");
			rs = pStmt.executeQuery();
			System.out.println("Pending Invoice IRN Generation Query::::::" + query);
			while (rs.next()) {
				if (rs.getString("TAX_INV_ID") != null) {
					invoiceIdList.add(rs.getString("TAX_INV_ID"));
				}
			}
			System.out.println("invoiceIdList Size::::::" + invoiceIdList.size());
		} catch (Exception exception) {
			exception.printStackTrace();
			log.info(exception + " ,Exception in getInvoiceIds():::" + exception.getMessage());
		} finally {
			// DBConnection.freeConnection(connection);
			DBConnection.freeResultPStmtConn(rs, pStmt, connection);
		}
		return invoiceIdList;

	}

	public static boolean chkExpiry() throws IllegalArgumentException {
		boolean status = false;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		Connection connection = null;
		String getData = null;
		String query = "select EXPIRY from irn_auth_data where irn_id=(select max(irn_id) from irn_auth_data )";

		try {
			if (connection == null) {
				connection = DBConnection.getConnection();
			}

			pStmt = connection.prepareStatement(query);
			rs = pStmt.executeQuery(query);
			System.out.println("Get the max expiry date::::::" + query);
			Date dt = null;
			// DateTime datetime = New DateTime(dt);

//			Date result = dateTime.toDate(); 
//			DateTime datetime = New DateTime(dt);

//			Date result = dateTime.toDate();
			if (rs.next()) {
				if (rs.getString(1) != null) {
//					invoiceIdList.add(
					System.out.println("getExpiry Date:::::::" + rs.getString(1));

					getData = rs.getString(1);
					@SuppressWarnings("deprecation")
					String strMnth = getData.substring(4, 7);
					String day = getData.substring(8, 10);
					String year = getData.substring(24, 28);
					String time = getData.substring(11, 19);
					String strMonth = null;
					System.out.println("sssssss:::::::" + strMnth);
					System.out.println("time:::::::" + time);
					if (strMnth == "Jan") {
						strMonth = "1";
					} else if (strMnth == "Feb") {
						strMonth = "2";
					} else if (strMnth == "Mar") {
						strMonth = "3";
					} else if (strMnth == "Apr") {
						strMonth = "4";
					} else if (strMnth == "May") {
						strMonth = "5";
					} else if (strMnth == "Jun") {
						strMonth = "6";
					} else if (strMnth == "Jul") {
						strMonth = "7";
					} else if (strMnth == "Aug") {
						strMonth = "8";
					} else if (strMnth == "Sep") {
						strMonth = "9";
					} else if (strMnth == "Oct") {
						strMonth = "10";
					} else if (strMnth == "Nov") {
						strMonth = "11";
					} else if (strMnth == "Dec") {
						strMonth = "12";
					}

					String strDate = strMnth + '/' + day + '/' + year;

					Date dattt = new Date(strDate);
					System.out.println("------>" + strDate);
					boolean statusDate = inLastDay(dattt);
					if (statusDate == true) {
						// System.out.println("true");
						status = true;
					} else {

						status = false;
						// System.out.println("false");
					}

//							Date getData1 = strDate.parse(strDate);
//							System.debug("------>"+getData1 );
//					        System.out.println("ZonedDateTime : " + jpTime);
//						 System.out.println("dateToString:::::::"+format+"    cheeeeeeeeeeee"+TimeUnit.HOURS.toMillis(24));

					// status=true;
				} else {
					status = false;
				}
			} else {
				status = false;
			}
			// System.out.println("invoiceIdList Size::::::"+invoiceIdList.size());
		} catch (Exception exception) {
			exception.printStackTrace();
			log.info(exception + " ,Exception in Max Expiry date:::" + exception.getMessage());
		} finally {
			// DBConnection.freeConnection(connection);
			DBConnection.freeResultPStmtConn(rs, pStmt, connection);
		}
		return status;

	}

	public static void main(String args[]) {
//		ArrayList<String> lst = getInvoiceIds("CGT", "01-MAY-2021", "31-MAY-2021");
	String date="Aug/09/2022";
		Date dattt = new Date(date);
		boolean chkExpiryDate = IrnDataImpl.inLastDay1(dattt);
		System.out.println(chkExpiryDate);

//		  String invoiceIdList = IrnDataImpl.getInvoiceIds("CGT","01-Jun-2021","30-May-2022");
//	    	String Request_json = JavaCodeToJson.convertDataToJson(invoiceIdList);//checking connection and closing connection
//			
//	    	System.out.println("Request_json:::::::::::::::::"+Request_json);
	}
	public static boolean inLastDay1(java.util.Date aDate) {
	    java.util.Date today = DateFormat.getDateTimeInstance().getCalendar().getTime();

	    java.util.Date twentyfourhoursbefore = DateFormat.getDateTimeInstance().getCalendar().getTime();
	    twentyfourhoursbefore.setTime(twentyfourhoursbefore.getTime() - (24*60*60*1000));

	    System.out.println(DateFormat.getDateTimeInstance().format(today));
	    System.out.println(DateFormat.getDateTimeInstance().format(twentyfourhoursbefore));
	    System.out.println(DateFormat.getDateTimeInstance().format(aDate));
	    if(aDate.after(twentyfourhoursbefore) && aDate.before(today)){
	        return true;
	    }

	    return false;
	}
	public static boolean inLastDay(Date aDate) {
		return aDate.getTime() > System.currentTimeMillis() - DAY_1;
	}

	public static boolean saveData(String client_id, String encryptedClientSecret, String encryptedAppKey,
			String bdo_authtoken, String expiry, String bdo_sek, String appKey, String jsonInputString,
			String bdoResponceObj,String decryptedBDoSek) {
		Connection con = null;
		boolean status = false;
		PreparedStatement pStmt = null, pStmtLog = null;
		ResultSet rs = null;
//		FieldData fieldData = new FieldData();
		try {
//			System.out.println(":::::INVOICE_NO::::::"+invoiceNos);
			Thread.sleep(200);
			if (con == null) {
				con = DBConnection.getConnection();
			}
			Date date = new Date();

			pStmt = con.prepareStatement(
					"insert into irn_auth_data (APPKEY,APPSECRETKEY,BDO_AUTHTOKEN,BDO_SEK,CLIENTID,CLIENTSECRETENCRYPTED,EXPIRY,decryptedBDoSek) values(?,?,?,?,?,?,?,?)");
			// PreparedStatement ps= con.prepareStatement("SELECT * FROM EINVOICE_STAG
			// ES,GST_TAX_INVOICE GTI WHERE ES.INVOICE_NO=GTI.TAX_INV_ID AND
			// GTI.GENERATED_IRRN is null AND GTI.STATUS is null AND ES.INVOICE_NO=?");
			String dd = date.toString();
//			con=pStmt.getConnection().prepareStatement(pst)
			pStmt.setString(1, appKey);
			pStmt.setString(2, encryptedClientSecret);
			pStmt.setString(3, bdo_authtoken);
			pStmt.setString(4, bdo_sek);
			pStmt.setString(5, client_id);
			pStmt.setString(6, encryptedClientSecret);
			pStmt.setString(7, expiry);
			pStmt.setString(8, decryptedBDoSek);
			if (pStmt.executeUpdate() > 0) {
				pStmtLog = con.prepareStatement(
						"insert into irn_log_info (INPUTPARAMS,OUTPUTPARAMS,REQUESTDATE) values(?,?,?)");
				pStmtLog.setString(1, jsonInputString);
				pStmtLog.setString(2, bdoResponceObj);
				pStmtLog.setString(3, dd);
				if (pStmtLog.executeUpdate() > 0) {
					status = true;
				} else {
					status = false;
				}

				status = true;
				System.out.println("prrrrrrrrrrr insert");
			} else {
				status = false;
				System.out.println("prrrrrrrrrrr fail");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;

	}

	public static Map<String, String> chkLastAuth() {
		Map<String, String> map = new HashMap<String, String>();
		// map.put("appkey",appKey );
		// map.put("sek", bdo_sek);
		boolean status = false;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		Connection connection = null;
		String getData = null;
		String query = "select BDO_AUTHTOKEN,DECRYPTEDBDOSEK,CLIENTID from irn_auth_data where irn_id=(select max(irn_id) from irn_auth_data)";

		try {
			if (connection == null) {
				connection = DBConnection.getConnection();
			}

			pStmt = connection.prepareStatement(query);
			rs = pStmt.executeQuery(query);
			System.out.println("Get the max expiry date::::::" + query);
			Date dt = null;
			if (rs.next()) {
				map.put("decryptedBDoSek", rs.getString("DECRYPTEDBDOSEK"));
				map.put("bdo_authtoken", rs.getString("BDO_AUTHTOKEN"));
				map.put("client_id", rs.getString("CLIENTID"));
//			return map;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			log.info(exception + " ,Exception in Max Expiry date:::" + exception.getMessage());
		} finally {
			// DBConnection.freeConnection(connection);
			DBConnection.freeResultPStmtConn(rs, pStmt, connection);
		}
		return map;
	}

	public static boolean updateResponse(String requestData,String decryptedResponse, Date sqlDate, String invoiceNo,String bdo_authtoken,String decryptedBDoSek)
			throws JSONException, ParseException, SQLException {
		boolean status = false;
		PreparedStatement pStmt = null,pStmtLog=null;
		Connection con = null;
		if (con == null) {
			con = DBConnection.getConnection();
		}
		SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");

		JSONObject obj = new JSONObject(decryptedResponse);
		Date respDate = sdf2.parse(JavaCodeToJson.convertDateFormat(obj.get("AckDt").toString()));

		Date date=new Date();
		java.sql.Date sqlResDate = new java.sql.Date(respDate.getTime());
		String dd = date.toString();
//		java.sql.Date sqlResDate = new java.sql.Date(respDate.getTime());
		// TODO Auto-generated method stub
		String sqlUpdtQuery = "UPDATE GST_TAX_INVOICE SET GENERATED_IRRN=?, STATUS=? , IRN_PROCESS_DATE = ? ,RES_STATUS=?,"
				+ "RES_ACKNO=?,RES_ACKDT=?,RES_SIGNEDINVOICE=?,IRN_QR_CODE=?,einv_billhid=?,bdoAckNo=?"
				+ ",bdo_authtoken=?,decryptedBDoSek=?  WHERE TAX_INV_ID=?";
//		System.out.println("IRN generating for TaxInvoiceID::::"+invoiceIdList.get(i));
		pStmt = con.prepareStatement(sqlUpdtQuery);
//		con.setAutoCommit(false);
		pStmt.setString(1, obj.get("Irn").toString());
		pStmt.setString(2, "Success");
		pStmt.setDate(3, (java.sql.Date) sqlDate);
		pStmt.setString(4, obj.get("Status").toString());
		pStmt.setString(5, obj.get("AckNo").toString());
		pStmt.setDate(6, sqlResDate);
		pStmt.setString(7, obj.get("SignedInvoice").toString());
		pStmt.setString(8, obj.get("SignedQRCode").toString());
		pStmt.setString(9, obj.get("einv_billhid").toString());
		pStmt.setString(10, obj.get("bdoAckNo").toString());
		pStmt.setString(11, bdo_authtoken);
		pStmt.setString(12, decryptedBDoSek);
		pStmt.setString(13, invoiceNo);
		// pStmt.addBatch();
		if (pStmt.executeUpdate() > 0) {
			pStmtLog = con.prepareStatement(
					"insert into IRN_GENERATION_LOG_INFO (INPUTPARAMS,OUTPUTPARAMS,REQUESTDATE,RESPONSEDATE) values(?,?,?,?)");
			
			System.out.println("checklog::::");
			pStmtLog.setString(1, requestData);
			pStmtLog.setString(2, decryptedResponse);
			pStmtLog.setString(3, dd);
			pStmtLog.setString(4, dd);
			
			if (pStmtLog.executeUpdate() > 0) {
				status = true;
			} else {
				status = false;
			}

			status = true;
			System.out.println("prrrrrrrrrrr insert");

			//status = true;
		} else {
			status = false;
		}

		return status;
	}
	public static boolean updateGetIrnResponse(String requestData,String decryptedResponse, Date sqlDate, String invoiceNo,String bdo_authtoken,String decryptedBDoSek)
			throws JSONException, ParseException, SQLException {
		boolean status = false;
		PreparedStatement pStmt = null,pStmtLog=null;
		Connection con = null;
		if (con == null) {
			con = DBConnection.getConnection();
		}
		SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");

		JSONObject obj = new JSONObject(decryptedResponse);
		Date respDate = sdf2.parse(JavaCodeToJson.convertDateFormat(obj.get("AckDt").toString()));

//		java.sql.Date sqlResDate = new java.sql.Date(respDate.getTime());
		Date date=new Date();
		java.sql.Date sqlResDate = new java.sql.Date(respDate.getTime());
		String dd = date.toString();
//		java.sql.Date sqlResDate = new java.sql.Date(respDate.getTime());
		// TODO Auto-generated method stub
		String sqlUpdtQuery = "UPDATE GST_TAX_INVOICE SET GENERATED_IRRN=?, STATUS=? , IRN_PROCESS_DATE = ? ,RES_STATUS=?,"
				+ "RES_ACKNO=?,RES_ACKDT=?,RES_SIGNEDINVOICE=?,IRN_QR_CODE=?"
				+ ",bdo_authtoken=?,decryptedBDoSek=?,error_des=?  WHERE TAX_INV_ID=?";
//		System.out.println("IRN generating for TaxInvoiceID::::"+invoiceIdList.get(i));
		pStmt = con.prepareStatement(sqlUpdtQuery);
//		con.setAutoCommit(false);
		pStmt.setString(1, obj.get("Irn").toString());
		pStmt.setString(2, "Success");
		pStmt.setDate(3, (java.sql.Date) sqlDate);
		pStmt.setString(4, obj.get("Status").toString());
		pStmt.setString(5, obj.get("AckNo").toString());
		pStmt.setDate(6, sqlResDate);
		pStmt.setString(7, obj.get("SignedInvoice").toString());
		pStmt.setString(8, obj.get("SignedQRCode").toString());
//		pStmt.setString(9, obj.get("einv_billhid").toString());
//		pStmt.setString(9, obj.get("bdoAckNo").toString());
		pStmt.setString(9, bdo_authtoken);
		pStmt.setString(10, decryptedBDoSek);
		pStmt.setString(11, null);
		pStmt.setString(12, invoiceNo);
		// pStmt.addBatch();
		if (pStmt.executeUpdate() > 0) {
			pStmtLog = con.prepareStatement(
					"insert into IRN_GENERATION_LOG_INFO (INPUTPARAMS,OUTPUTPARAMS,REQUESTDATE,RESPONSEDATE) values(?,?,?,?)");
			pStmtLog.setString(1, requestData);
			pStmtLog.setString(2, decryptedResponse);
			pStmtLog.setString(3, dd);
			pStmtLog.setString(4, dd);
			
			if (pStmtLog.executeUpdate() > 0) {
				status = true;
			} else {
				status = false;
			}

			status = true;
		} else {
			status = false;
		}

		return status;
	}

	public static boolean updateErrorResponse(String decryptedResponse, Date sqlDate, String invoiceNo) throws SQLException, JSONException, ParseException {
		boolean status = false;
		PreparedStatement pStmt = null;
		Connection con = null;
		if (con == null) {
			con = DBConnection.getConnection();
		}
		SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");

		JSONObject obj = new JSONObject(decryptedResponse);
		
//		java.sql.Date sqlResDate = new java.sql.Date(respDate.getTime());
		// TODO Auto-generated method stub
		String sqlUpdtQuery = "UPDATE  GST_TAX_INVOICE SET  STATUS=?, ERROR_DES=?,IRN_PROCESS_DATE = ?,"
				+ "IRN_FAILURE_PROCESS_DATE=?,bdoAckNo=? WHERE TAX_INV_ID=?";
//		
//		System.out.println("IRN generating for TaxInvoiceID::::"+invoiceIdList.get(i));
		pStmt = con.prepareStatement(sqlUpdtQuery);
		pStmt.setString(1,"Failure");
		pStmt.setString(2,obj.get("Error").toString());
		pStmt.setDate(3,(java.sql.Date)sqlDate);
		pStmt.setDate(4,(java.sql.Date)sqlDate);
		pStmt.setString(5,obj.get("bdoAckNo").toString());
		pStmt.setString(6, invoiceNo);
		// pStmt.addBatch();
		if (pStmt.executeUpdate() > 0) {
			status = true;
		} else {
			status = false;
		}

		return status;
	}

	public static String geCanceltInvoiceId(String invoiceNo,String reason,String remark) {
		System.out.println(":::::getInvoiceIds method execution started===");
		ArrayList<String> invoiceIdList = new ArrayList<String>();
		PreparedStatement pStmt = null,pstmtUpdate=null,pstmtInsert=null;
		ResultSet rs = null;
		Connection connection = null;
		String getData = null;
		boolean status=false;
		String finalCancelString=null;
		String encryptPayload=null;
		String bdoIrnResponceObj=null;
//		System.out.println("fromdate:::::::::" + fromdate + "  todate:::::::::" + todate);
	
		try {
			connection = DBConnection.getConnection();
			
			/*
			 * if(connection==null) { connection = DBConnection.getConnection();
			 * System.out.println("fail"); }else { System.out.println("success"); }
			 */
			String query = "SELECT GTI.TAX_INV_ID,ES.SUPPLIER_GSTIN,GTI.GENERATED_IRRN,"
					+ "ES.INV_GEN_DATE,GTI.DECRYPTEDBDOSEK,GTI.BDO_AUTHTOKEN"
					+ " FROM GST_TAX_INVOICE GTI,EINVOICE_STAG ES WHERE GTI.TAX_INV_ID=ES.INVOICE_NO "
					+ "AND ES.INVOICE_NO=? ";
//					+ "'2021-22000110571','2021-22000474614','2021-22000474615','2021-22000489120','2021-22000489121',"
//					+ "'2021-22000239345','2021-22000237206','2021-22000484338','2021-22000484337','2021-22000489122',"
//					+ "'2021-22000507120','2021-22000489119','2021-22000519019','2021-22000519018','2021-22000519017',"
//					+ "'2021-22000476178','2021-22000476177','2021-22000446077','2021-22000476172','2021-22000446078',"
//					+ "'2021-22000446075','2021-22000519034','2021-22000519033','2021-22000446074')"
//					+ " order by ES.INV_GEN_DATE ";

			System.out.println(":::::connection for geting tax invoice id:::::::" + connection);
			System.out.println("query::::::" + query);
			pStmt = connection.prepareStatement(query);
			pStmt.setString(1, invoiceNo);
			
			rs = pStmt.executeQuery();
			String BDO_AUTHTOKEN=null;
			System.out.println("Pending Invoice IRN Generation Query::::::" + query);
			if (rs.next()) {
				 Date date = new Date();
				  
//				String jsonInputString = "{\"clientid\":\"" + client_id + "\",\"clientsecretencrypted\":\""
//						+ encryptedClientSecret + "\",\"appsecretkey\":\"" + encryptedAppKey + "\"}";
				finalCancelString="{\"supplier_gstin\":\""+rs.getString("supplier_gstin")+ "\",\"doc_no\":\""+invoiceNo+"\",\"irn_no\":\""+rs.getString("GENERATED_IRRN")+"\",\"doc_date\":\""+rs.getString("INV_GEN_DATE")+ "\",\"reason\":\""+ reason + "\", \"remark\":\""+ remark + "\" }";
//					alCancelString = "{\"supplier_gstin\":\"" + rs.getString("supplier_gstin")   + "\",\"doc_no\":\""
//						+ invoiceNo    + "\",\"irn_no\":\"" + rs.getString("GENERATED_IRRN")+ "\",\"doc_date\":\"" 
//						+ "" + "\" ,\"reason\":\""+ reason + "\", \"remark\":\""+ remark + "\" }";
				 BDO_AUTHTOKEN=rs.getString("BDO_AUTHTOKEN");
				 System.out.println("finalCancelString::::::::"+finalCancelString);
				  String dd = date.toString();
				  encryptPayload = IrnUtility.encryptPayload(finalCancelString, rs.getString("DECRYPTEDBDOSEK"));
					encryptPayload = "{\"Data\":\"" + encryptPayload + "\"}";
				  System.out.println("encryptPayload::::::::"+encryptPayload);
					  
				   bdoIrnResponceObj = IrnUtility.cancelIrn(invoiceNo, BDO_AUTHTOKEN, client_id,encryptPayload);
				   System.out.println("bdoIrnResponceObj cancel::::::" + bdoIrnResponceObj); 
				   JSONObject jobj = new JSONObject(bdoIrnResponceObj);
				   
				   String updateQuery="update GST_TAX_AMOL set CANCEL_DATE=? where tax_inv_id=? ";
				   pstmtUpdate = connection.prepareStatement(updateQuery);
				   pstmtUpdate.setString(1, jobj.getString("CancelDate"));
				   pstmtUpdate.setString(2, invoiceNo);
				   if(pstmtUpdate.executeUpdate()>0) {
					   Date date1 = new Date();
					   String dd1 = date1.toString();
					   String insertQuery="insert into irn_cancel_log_info(INPUTPARAMS,OUTPUTPARAMS,REQUESTDATE,RESPONSEDATE,INVOICE_NO) "
					   		+ "values(?,?,?,?,?)";
					   pstmtInsert = connection.prepareStatement(insertQuery);
					   pstmtInsert.setString(1, finalCancelString);
					   pstmtInsert.setString(2, bdoIrnResponceObj);
					   pstmtInsert.setString(3, dd);
					   pstmtInsert.setString(4, dd1);
					   pstmtInsert.setString(5, invoiceNo);
					   pstmtInsert.executeUpdate();
					   }		
			}
			 
			System.out.println("invoiceIdList Size::::::" + invoiceIdList.size());
		} catch (Exception exception) {
			exception.printStackTrace();
			log.info(exception + " ,Exception in cancel getInvoiceId():::" + exception.getMessage());
		} finally {
			// DBConnection.freeConnection(connection);
			DBConnection.freeResultPStmtConn(rs, pStmt, connection);
		}
		return bdoIrnResponceObj;


//		return null;
	}

	public  String getInvoiceId(String docnum,String doctype,String docdate) {
		
		// -----Generate Random 32 bit App Key
					// TODO Auto-generated method stub
		return null;
	}
}