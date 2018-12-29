package com.beanframework.backoffice.data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import com.beanframework.language.domain.Language;

public class LanguageSpecification {
	public static Specification<Language> findByCriteria(final LanguageSearch languageSearch) {

		return new Specification<Language>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 3162338087352704668L;

			@Override
			public Predicate toPredicate(Root<Language> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				List<Predicate> predicates = new ArrayList<Predicate>();
				
				if(StringUtils.isNotEmpty(languageSearch.getSearchAll())) {
					predicates.add(cb.or(cb.like(root.get(Language.ID), "%" + languageSearch.getSearchAll() + "%")));
					predicates.add(cb.or(cb.like(root.get(Language.NAME), "%" + languageSearch.getSearchAll() + "%")));
				}
				else {
					if (StringUtils.isNotBlank(languageSearch.getId())) {
						predicates.add(cb.and(cb.like(root.get(Language.ID), "%" + languageSearch.getId() + "%")));
					}
					if (StringUtils.isNotBlank(languageSearch.getName())) {
						predicates.add(cb.and(cb.like(root.get(Language.NAME), "%" + languageSearch.getName() + "%")));
					}
				}

				if (predicates.isEmpty()) {
					return cb.and(predicates.toArray(new Predicate[predicates.size()]));
				} else {
					return cb.or(predicates.toArray(new Predicate[predicates.size()]));
				}
			}
		};
	}
}