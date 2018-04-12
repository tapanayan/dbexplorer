package com.yay.db.server.connection;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.yay.db.server.helper.PropertiesHelper;
import com.yay.db.shared.TableModel;
import com.yay.db.shared.TreeModel;

public class DatabaseUtility {
	public static void main(String[] args) {
		new DatabaseUtility().getDbTree();
	}
	
	public TreeModel getDbTree(){
		String host=PropertiesHelper.getResource("host");
		String port=PropertiesHelper.getResource("port");
		String user=PropertiesHelper.getResource("user");
		String password=PropertiesHelper.getResource("password");
		String db_type=PropertiesHelper.getResource("db_type");
		TreeModel rootModel=new TreeModel("DB@"+host+":"+port);
		rootModel.setType("root");
		Connection con = null;
		if("mysql".equalsIgnoreCase(db_type)){
			try {
				con = getMySqlConnection(host, port, user, password);
				DatabaseMetaData metaData = con.getMetaData();
				populateSchemaInfo(metaData, rootModel);				
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}finally{
				if(con!=null){
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		System.out.println("getDbTree call finished.");
		return rootModel;
	}
	
	private void populateTableInfo(DatabaseMetaData metaData,TreeModel schemaModel,String schemaName) throws SQLException{
		ResultSet tableResultSet = metaData.getTables(schemaName, null, null, new String[] {"TABLE"});
		while(tableResultSet.next()){
			String tableName = tableResultSet.getString("TABLE_NAME");
//			System.out.println("tableName:"+tableName);
			TreeModel tableModel = new TreeModel(tableName);
			tableModel.setType("table");
			tableModel.setParentName(schemaName);
			schemaModel.getChildren().add(tableModel);
		}
		
	}
	
	private void populateSchemaInfo(DatabaseMetaData metaData,TreeModel rootModel) throws SQLException{
		//for mysql getSchemas not working.instead use getCatalogs
		ResultSet rs = metaData.getCatalogs();
		while(rs.next()){
			String catalogName = rs.getString("TABLE_CAT");
//			System.out.println("catalogName:"+catalogName);
			TreeModel schemaModel = new TreeModel(catalogName);
			schemaModel.setType("schema");
			populateTableInfo(metaData, schemaModel, catalogName);
			rootModel.getChildren().add(schemaModel);
		}
	}
	
	private Connection getMySqlConnection(String host,String port,String user,String password) throws ClassNotFoundException, SQLException{
		Class.forName("com.mysql.jdbc.Driver");  
		return DriverManager.getConnection(  
		"jdbc:mysql://"+host+":"+port,user,password);
	}

	public TableModel getDbTableData(String schema, String table) {
		TableModel model = new TableModel();
		String host=PropertiesHelper.getResource("host");
		String port=PropertiesHelper.getResource("port");
		String user=PropertiesHelper.getResource("user");
		String password=PropertiesHelper.getResource("password");
		String db_type=PropertiesHelper.getResource("db_type");
		TreeModel rootModel=new TreeModel("DB@"+host+":"+port);
		rootModel.setType("root");
		Connection con = null;
		if("mysql".equalsIgnoreCase(db_type)){
			try {
				con = getMySqlConnection(host, port, user, password);
				DatabaseMetaData metaData = con.getMetaData();
				ResultSet rs = metaData.getColumns(schema, null, table, null);
				List<String> colNames = new ArrayList<String>();
				String sql="SELECT ";
				while(rs.next()){
					String colName = rs.getString("COLUMN_NAME");
					colNames.add(colName);
					sql = sql+ colName+",";
				}
				if(sql.endsWith(",")){
					sql = sql.substring(0,sql.length()-1);
				}
				sql = sql+" FROM "+schema+"."+table;
				
				model.setColNames(colNames);
				model.setSql(sql);
				
				System.out.println("SQL to fire:"+sql);
				List<List<String>> data = new ArrayList<List<String>>();
				PreparedStatement stmt = (PreparedStatement) con.prepareStatement(sql);
				rs = stmt.executeQuery();
				while(rs.next()){
					List<String> row = new ArrayList<String>();
					for(int i =0;i<colNames.size();i++){
						String colName = colNames.get(i);
						if(rs.getString(colName)==null){
							row.add("NULL");
						}else{
							row.add(rs.getString(colName));
						}
					}
					data.add(row);
				}
				System.out.println("No of rows retrieved:"+data.size());
				model.setData(data);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
				model.setErrorMessage(e.getMessage());
			}finally{
				if(con!=null){
					try {
						con.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		System.out.println("getDbTableData call finished.");
		return model;
	}

	public TableModel executeQuery(String query) {
		TableModel model = new TableModel();
		String host=PropertiesHelper.getResource("host");
		String port=PropertiesHelper.getResource("port");
		String user=PropertiesHelper.getResource("user");
		String password=PropertiesHelper.getResource("password");
		String db_type=PropertiesHelper.getResource("db_type");
		Connection con = null;
		if("mysql".equalsIgnoreCase(db_type)){
			try {
				con = getMySqlConnection(host, port, user, password);
				PreparedStatement stmt = (PreparedStatement) con.prepareStatement(query);
				ResultSet rs = stmt.executeQuery();
				ResultSetMetaData metaData =  stmt.getMetaData();
				List<String> colNames = new ArrayList<String>();
				for(int i=0;i<metaData.getColumnCount();i++){
					colNames.add(metaData.getColumnName(i+1));
				}
				List<List<String>> data = new ArrayList<List<String>>();
				while(rs.next()){
					List<String> row = new ArrayList<String>();
					for(int i =0;i<colNames.size();i++){
						String colName = colNames.get(i);
						if(rs.getString(colName)==null){
							row.add("NULL");
						}else{
							row.add(rs.getString(colName));
						}
					}
					data.add(row);
				}
				System.out.println("SQL to fire:"+query);
				System.out.println("No of rows retrieved:"+data.size());
				model.setColNames(colNames);
				model.setSql(query);				
				
				model.setData(data);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
				model.setErrorMessage(e.getMessage());
			}finally{
				if(con!=null){
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		System.out.println("executeQuery call finished.");
		return model;
	}

}
