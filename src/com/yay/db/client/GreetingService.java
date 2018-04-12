package com.yay.db.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.yay.db.shared.TableModel;
import com.yay.db.shared.TreeModel;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface GreetingService extends RemoteService {
	TreeModel getDatabaseTree();
	TableModel getTableData(String schema,String table);
	TableModel getTableData(String query);
}
