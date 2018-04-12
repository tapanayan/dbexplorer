package com.yay.db.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class DatabaseExplorer implements EntryPoint {
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		TabLayoutPanel tabPanel = new TabLayoutPanel(2.5, Unit.EM);
		tabPanel.add(new MainPanel(),"Explorer");
		tabPanel.add(new QueryExecutorPanel(),"Executor");
		tabPanel.setHeight("100%");
		tabPanel.setWidth("100%");
		RootLayoutPanel.get().add(tabPanel);	
		
	}
	
	
}
