package com.yay.db.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.yay.db.shared.TableModel;
import com.yay.db.shared.TreeModel;

public class MainPanel extends HorizontalPanel {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting
	 * service.
	 */
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);

	public MainPanel() {
//		final SplitLayoutPanel panel = new SplitLayoutPanel(5);
		Mask.instance().show();
		final ScrollPanel rightPanel = new ScrollPanel();
		final ScrollPanel leftPanel = new ScrollPanel();
		int panelHeight=Window.getClientHeight()-40;
		rightPanel.setHeight(panelHeight+"px");
		rightPanel.setWidth((Window.getClientWidth()-270)+"px");
		
		leftPanel.setHeight(panelHeight+"px");
		leftPanel.setWidth("250px");
		
		greetingService.getDatabaseTree(new AsyncCallback<TreeModel>() {

			@Override
			public void onSuccess(TreeModel result) {
				Mask.instance().hide();
				Tree tree = new Tree();
				tree.addSelectionHandler(new SelectionHandler<TreeItem>() {
					
					@Override
					public void onSelection(SelectionEvent<TreeItem> event) {
						TreeItem item = event.getSelectedItem();
						Object userObject = item.getUserObject();
						if(userObject instanceof TreeModel){
							TreeModel selectedModel = (TreeModel) userObject;
							if("table".equalsIgnoreCase(selectedModel.getType())){
								rightPanel.clear();
								fetchTableData(selectedModel, rightPanel);
							}else{
								rightPanel.clear();
								rightPanel.add(new Label("Select a table to view it's data."));
							}
						}
						
					}
				});
				TreeItem rootItem = new TreeItem(result.getName());
				tree.addItem(rootItem);
				addChild(rootItem, result);
				leftPanel.clear();
				leftPanel.add(tree);
				
				rightPanel.clear();
				rightPanel.add(new Label("Select a table to view it's data."));
				
			}

			@Override
			public void onFailure(Throwable caught) {
				Mask.instance().hide();
				RootPanel.get().add(new Label(SERVER_ERROR));
			}
		});
		add(leftPanel);
		add(rightPanel);
	}
	
	private void addChild(TreeItem parentItem,TreeModel parentModel){
		for(TreeModel child:parentModel.getChildren()){
			TreeItem childItem = new TreeItem(child.getName());
			parentItem.addItem(childItem);
			addChild(childItem, child);
			childItem.setUserObject(child);
		}
	}
	
	
	private void fetchTableData(TreeModel selectedModel,final Panel panel){
		Mask.instance().show();
		greetingService.getTableData(selectedModel.getParentName(), selectedModel.getName(), new AsyncCallback<TableModel>() {
			
			@Override
			public void onSuccess(TableModel result) {
				Mask.instance().hide();
				new TableView().drawResultTable(panel, result);			
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Mask.instance().hide();
				panel.clear();
				panel.add(new Label(SERVER_ERROR));
				
			}
		});
		
	}

}
