package com.cgtmse.dao;

public class Data {
	 private String ClientId;
	 private String UserName;
	 private String AuthToken;
	 private String Sek;
	 private String TokenExpiry;
	 private String Password;
	 private String AppKey;
	 private boolean ForceRefreshAccessToken;
	 private String expiry;
	 private String bdo_authtoken;
	 private String bdo_sek;
	 private String status;
	 
	 
	public String getExpiry() {
		return expiry;
	}
	public void setExpiry(String expiry) {
		this.expiry = expiry;
	}
	public String getBdo_authtoken() {
		return bdo_authtoken;
	}
	public void setBdo_authtoken(String bdo_authtoken) {
		this.bdo_authtoken = bdo_authtoken;
	}
	public String getBdo_sek() {
		return bdo_sek;
	}
	public void setBdo_sek(String bdo_sek) {
		this.bdo_sek = bdo_sek;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	public String getAppKey() {
		return AppKey;
	}
	public void setAppKey(String appKey) {
		AppKey = appKey;
	}
	public boolean isForceRefreshAccessToken() {
		return ForceRefreshAccessToken;
	}
	public void setForceRefreshAccessToken(boolean forceRefreshAccessToken) {
		ForceRefreshAccessToken = forceRefreshAccessToken;
	}
	public String getClientId() {
		return ClientId;
	}
	public void setClientId(String clientId) {
		ClientId = clientId;
	}
	public String getAuthToken() {
		return AuthToken;
	}
	public void setAuthToken(String authToken) {
		AuthToken = authToken;
	}
	public String getSek() {
		return Sek;
	}
	public void setSek(String sek) {
		Sek = sek;
	}
	public String getTokenExpiry() {
		return TokenExpiry;
	}
	public void setTokenExpiry(String tokenExpiry) {
		TokenExpiry = tokenExpiry;
	}
	 
}
