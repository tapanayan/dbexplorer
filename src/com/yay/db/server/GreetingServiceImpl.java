package com.yay.db.server;

import com.yay.db.client.GreetingService;
import com.yay.db.server.connection.DatabaseUtility;
import com.yay.db.shared.TableModel;
import com.yay.db.shared.TreeModel;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
		GreetingService {

	@Override
	public TreeModel getDatabaseTree() {
		return new DatabaseUtility().getDbTree();
	}

	@Override
	public TableModel getTableData(String schema, String table) {
		return new DatabaseUtility().getDbTableData(schema,table);
	}

	@Override
	public TableModel getTableData(String query) {
		return new DatabaseUtility().executeQuery(query);
	}
}
