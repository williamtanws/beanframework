package com.beanframework.common.data;

public class TreeJsonState {

  private boolean opened = false;
  private boolean selected = false;

  public TreeJsonState() {
    super();
  }

  public TreeJsonState(boolean opened, boolean selected) {
    super();
    this.opened = opened;
    this.selected = selected;
  }

  public TreeJsonState(boolean selected) {
    super();
    this.selected = selected;
  }

  public boolean isOpened() {
    return opened;
  }

  public void setOpened(boolean opened) {
    this.opened = opened;
  }

  public boolean isSelected() {
    return selected;
  }

  public void setSelected(boolean selected) {
    this.selected = selected;
  }
}
