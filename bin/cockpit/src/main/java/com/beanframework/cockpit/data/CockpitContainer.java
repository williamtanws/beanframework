package com.beanframework.cockpit.data;

public class CockpitContainer {
	private NavigationArea navigationArea;
	private BrowserArea browserArea;
	private EditorArea editorArea;

	public NavigationArea getNavigationArea() {
		return navigationArea;
	}

	public void setNavigationArea(NavigationArea navigationArea) {
		this.navigationArea = navigationArea;
	}

	public BrowserArea getBrowserArea() {
		return browserArea;
	}

	public void setBrowserArea(BrowserArea browserArea) {
		this.browserArea = browserArea;
	}

	public EditorArea getEditorArea() {
		return editorArea;
	}

	public void setEditorArea(EditorArea editorArea) {
		this.editorArea = editorArea;
	}

}
