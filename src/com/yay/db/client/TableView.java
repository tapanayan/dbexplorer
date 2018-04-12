package com.yay.db.client;

import java.util.List;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.yay.db.shared.TableModel;

public class TableView {
	public void drawResultTable(final Panel panel, TableModel result) {
		panel.clear();
		FlexTable flexTable = new FlexTable();
		flexTable.setCellSpacing(0);
		flexTable.setCellPadding(2);
		if (result.getErrorMessage()==null) {
			flexTable.setHTML(0, 0,
					"<b>SQL:  </b><font color='blue'>" + result.getSql()
							+ "</font>");
			int row = 0;
			flexTable.getFlexCellFormatter().setColSpan(row++, 0,
					result.getColNames().size());
			flexTable.setHTML(row, 0,
					"<b># of records:  </b><font color='green'>"
							+ result.getData().size() + "</font>");
			flexTable.getFlexCellFormatter().setColSpan(row++, 0,
					result.getColNames().size());
			for (int col = 0; col < result.getColNames().size(); col++) {
				flexTable.setHTML(row, col,
						"<b>" + result.getColNames().get(col) + "</b>");
				flexTable.getFlexCellFormatter().addStyleName(row, col,
						"flexCellBorder");
			}
			row++;
			for (List<String> rowData : result.getData()) {
				for (int col = 0; col < result.getColNames().size(); col++) {
					Label text = new Label(rowData.get(col));
					text.setTitle(text.getText());
					flexTable.setWidget(row, col, text);
					flexTable.getFlexCellFormatter().addStyleName(row, col,
							"flexCellBorder");
				}
				row++;
			}
		}else{
			flexTable.setHTML(0, 0,
					"<b><font color='red'>Error occurred:" + result.getErrorMessage()
							+ "</font></b>");
		}
		panel.add(flexTable);
	}

}
