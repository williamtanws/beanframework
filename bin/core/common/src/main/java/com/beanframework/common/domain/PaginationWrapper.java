package com.beanframework.common.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

public class PaginationWrapper<T> {
	private Page<T> page;
	private List<PageItem> items;
	private int currentNumber;
	private String url;
	private long total;
	private long recordNumberFrom;
	private long recordNumberTo;
	private String direction;
	private String[] properties;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public PaginationWrapper() {
		this.page = new PageImpl<T>(new ArrayList<T>());
	}

	public PaginationWrapper(Page<T> page, String url, int MAX_PAGE_ITEM_DISPLAY, String direction, String[] properties) {
		this.direction = direction;
		this.properties = properties;
		this.page = page;
		this.total = page.getTotalElements();
		url = url.replaceAll("=null", "=");
		this.url = url;
		items = new ArrayList<PageItem>();

		currentNumber = page.getNumber() + 1; // start from 1 to match page.page

		int start, size;
		if (page.getTotalPages() <= MAX_PAGE_ITEM_DISPLAY) {
			start = 1;
			size = page.getTotalPages();
		} else {
			if (currentNumber <= MAX_PAGE_ITEM_DISPLAY - MAX_PAGE_ITEM_DISPLAY / 2) {
				start = 1;
				size = MAX_PAGE_ITEM_DISPLAY;
			} else if (currentNumber >= page.getTotalPages() - MAX_PAGE_ITEM_DISPLAY / 2) {
				start = page.getTotalPages() - MAX_PAGE_ITEM_DISPLAY + 1;
				size = MAX_PAGE_ITEM_DISPLAY;
			} else {
				start = currentNumber - MAX_PAGE_ITEM_DISPLAY / 2;
				size = MAX_PAGE_ITEM_DISPLAY;
			}
		}

		for (int i = 0; i < size; i++) {
			items.add(new PageItem(start + i, (start + i) == currentNumber));
		}
		
		if(total < MAX_PAGE_ITEM_DISPLAY){
			recordNumberFrom = 1;
			recordNumberTo = total;
		}else{
			recordNumberFrom = ((currentNumber-1)*MAX_PAGE_ITEM_DISPLAY)+1;
			recordNumberTo = (recordNumberFrom+page.getNumberOfElements())-1;
		}
	}

	public List<PageItem> getItems() {
		return items;
	}

	public int getNumber() {
		return currentNumber;
	}

	public List<T> getContent() {
		return page.getContent();
	}

	public int getSize() {
		return page.getSize();
	}

	public int getTotalPages() {
		return page.getTotalPages();
	}

	public boolean isFirst() {
		return page.isFirst();
	}

	public boolean isLast() {
		return page.isLast();
	}

	public boolean isHasPrevious() {
		return page.hasPrevious();
	}

	public boolean isHasNext() {
		return page.hasNext();
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public long getRecordNumberFrom() {
		return recordNumberFrom;
	}

	public void setRecordNumberFrom(long recordNumberFrom) {
		this.recordNumberFrom = recordNumberFrom;
	}

	public long getRecordNumberTo() {
		return recordNumberTo;
	}

	public void setRecordNumberTo(long recordNumberTo) {
		this.recordNumberTo = recordNumberTo;
	}



	public String[] getProperties() {
		return properties;
	}

	public void setProperties(String[] properties) {
		this.properties = properties;
	}



	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}



	public class PageItem {
		private int number;
		private boolean current;

		public PageItem(int number, boolean current) {
			this.number = number;
			this.current = current;
		}

		public int getNumber() {
			return this.number;
		}

		public boolean isCurrent() {
			return this.current;
		}
	}
}