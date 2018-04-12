package com.yay.db.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.yay.db.shared.TableModel;
import com.yay.db.shared.TreeModel;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
	void getDatabaseTree(AsyncCallback<TreeModel> callBack);
	void getTableData(String schema,String table,AsyncCallback<TableModel> callBack);
	void getTableData(String query,AsyncCallback<TableModel> callBack);
}
