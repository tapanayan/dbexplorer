package com.yay.db.shared;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class TableModel implements IsSerializable{
	private List<String> colNames = new ArrayList<String>();
	private List<List<String>> data = new ArrayList<List<String>>();
	private String sql;
	private String errorMessage;
	public List<String> getColNames() {
		return colNames;
	}
	public void setColNames(List<String> colNames) {
		this.colNames = colNames;
	}
	public List<List<String>> getData() {
		return data;
	}
	public void setData(List<List<String>> data) {
		this.data = data;
	}
	public TableModel() {
		super();
	}
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	

}
