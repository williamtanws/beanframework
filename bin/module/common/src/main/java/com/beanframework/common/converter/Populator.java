package com.beanframework.common.converter;

import com.beanframework.common.exception.PopulatorException;

public interface Populator<SOURCE, TARGET> {

	void populate(SOURCE source, TARGET target) throws PopulatorException;
}
