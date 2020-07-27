package com.beanframework.common.converter;

import com.beanframework.common.exception.PopulatorException;

public interface Populator<S, T> {

	void populate(S source, T target) throws PopulatorException;
}
