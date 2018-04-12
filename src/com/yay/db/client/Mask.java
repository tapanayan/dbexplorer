package com.yay.db.client;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;

public class Mask extends PopupPanel  {
	private static Mask instance = null;
	private Mask(){
		Label label = new Label("Loading...");		
		setAutoHideEnabled(false);
		setGlassEnabled(true);
		setModal(true);
		setHeight(Window.getClientHeight()+"");
		setWidth(Window.getClientWidth()+"");
		setWidget(label);
		center();
	}
	


	public static Mask instance(){
		if(instance==null){
			instance = new Mask();
		}
		return instance;		
	}
	

}
