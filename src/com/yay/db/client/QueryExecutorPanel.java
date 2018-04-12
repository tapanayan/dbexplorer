package com.yay.db.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.yay.db.shared.TableModel;

public class QueryExecutorPanel extends VerticalPanel {
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

	public QueryExecutorPanel() {
		add(new Label("Enter query to execute"));
		final TextArea queryTextArea = new TextArea();
		queryTextArea.setWidth((Window.getClientWidth() - 20) + "px");
		queryTextArea.setHeight("100px");
		add(queryTextArea);
		Button executeButton = new Button("Execute");
		add(executeButton);
		final ScrollPanel resultPanel = new ScrollPanel();
		resultPanel.setHeight((Window.getClientHeight() - 140) + "px");
		resultPanel.setWidth((Window.getClientWidth() - 20) + "px");
		executeButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				Mask.instance().show();
				greetingService.getTableData(queryTextArea.getValue(),
						new AsyncCallback<TableModel>() {

							@Override
							public void onSuccess(TableModel result) {
								Mask.instance().hide();
								new TableView().drawResultTable(resultPanel, result);
							}

							@Override
							public void onFailure(Throwable caught) {
								Mask.instance().hide();
								resultPanel.clear();
								resultPanel.add(new Label(SERVER_ERROR));

							}
						});

			}
		});
		add(resultPanel);
	}

}
