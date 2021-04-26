package com.beanframework.dynamicfield.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.beanframework.common.service.ModelService;
import com.beanframework.dynamicfield.domain.DynamicField;
import com.beanframework.enumuration.domain.Enumeration;
import com.beanframework.internationalization.domain.Language;

@Service
public class DynamicFieldServiceImpl implements DynamicFieldService {

	@Autowired
	private ModelService modelService;

	@Override
	public void removeLanguageRel(Language model) throws Exception {
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(DynamicField.LANGUAGE, model.getUuid());
		List<DynamicField> entities = modelService.findByPropertiesBySortByResult(properties, null, null, null, DynamicField.class);

		if (entities != null)
			for (int i = 0; i < entities.size(); i++) {

				if (entities.get(i).getLanguage() != null)
					if (entities.get(i).getLanguage().equals(model.getUuid())) {
						entities.get(i).setLanguage(null);
						modelService.saveEntityByLegacyMode(entities.get(i), DynamicField.class);
					}
			}
	}

	@Override
	public void removeEnumerationsRel(Enumeration model) throws Exception {
		Specification<DynamicField> specification = new Specification<DynamicField>() {
			private static final long serialVersionUID = 1L;

			public String toString() {
				return model.getUuid().toString();
			}

			@Override
			public Predicate toPredicate(Root<DynamicField> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();

				predicates.add(cb.or(root.join(DynamicField.ENUMERATIONS, JoinType.LEFT).in(model.getUuid())));

				if (predicates.isEmpty()) {
					return cb.and(predicates.toArray(new Predicate[predicates.size()]));
				} else {
					return cb.or(predicates.toArray(new Predicate[predicates.size()]));
				}

			}
		};

		List<DynamicField> entities = modelService.findBySpecificationBySort(specification, DynamicField.class);

		if (entities != null)
			for (int i = 0; i < entities.size(); i++) {

				for (int j = 0; j < entities.get(i).getEnumerations().size(); j++) {
					entities.get(i).getEnumerations().remove(model.getUuid());
				}
				modelService.saveEntityByLegacyMode(entities.get(i), DynamicField.class);
			}
	}
}
