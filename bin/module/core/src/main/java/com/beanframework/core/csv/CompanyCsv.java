package com.beanframework.core.csv;

import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.Trim;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;

import com.beanframework.common.data.AbstractCsv;

public class CompanyCsv extends AbstractCsv {

	private String name;
	private String addressIds;

	public static CellProcessor[] getUpdateProcessors() {
		final CellProcessor[] processors = new CellProcessor[] { //
				new Optional(new Trim()), // ModeType
				new NotNull(new Trim()), // id
				new Optional(new Trim()), // name
				new Optional(new Trim()) // addressIds
		};

		return processors;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddressIds() {
		return addressIds;
	}

	public void setAddressIds(String addressIds) {
		this.addressIds = addressIds;
	}

	@Override
	public String toString() {
		return "CompanyCsv [name=" + name + ", addressIds=" + addressIds + "]";
	}

}
