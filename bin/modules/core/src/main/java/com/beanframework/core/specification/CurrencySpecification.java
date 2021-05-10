package com.beanframework.core.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.specification.AbstractSpecification;
import com.beanframework.common.specification.CommonSpecification;
import com.beanframework.internationalization.domain.Currency;

public class CurrencySpecification extends CommonSpecification {
	public static <T> AbstractSpecification<T> getPageSpecification(DataTableRequest dataTableRequest) {
		return new AbstractSpecification<T>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return getPredicate(dataTableRequest, root, query, cb);
			}

			public String toString() {
				return dataTableRequest.toString();
			}

			@Override
			public List<Selection<?>> toSelection(Root<T> root) {
				List<Selection<?>> multiselect = new ArrayList<Selection<?>>();
				multiselect.add(root.get(Currency.UUID));
				multiselect.add(root.get(Currency.ID));
				multiselect.add(root.get(Currency.NAME));
				multiselect.add(root.get(Currency.ACTIVE));
				return multiselect;
			}
		};
	}
}
