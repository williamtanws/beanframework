package com.beanframework.dynamicfield.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.beanframework.common.service.ModelService;
import com.beanframework.dynamicfield.domain.DynamicFieldSlot;
import com.beanframework.dynamicfield.domain.DynamicFieldTemplate;

@Service
public class DynamicFieldTemplateServiceImpl implements DynamicFieldTemplateService {

	@Autowired
	private ModelService modelService;

	@Override
	public void removeDynamicFieldSlotsRel(DynamicFieldSlot model) throws Exception {
		Specification<DynamicFieldTemplate> specification = new Specification<DynamicFieldTemplate>() {
			private static final long serialVersionUID = 1L;

			public String toString() {
				return model.getUuid().toString();
			}

			@Override
			public Predicate toPredicate(Root<DynamicFieldTemplate> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();

				predicates.add(cb.or(root.join(DynamicFieldTemplate.DYNAMIC_FIELD_SLOTS, JoinType.LEFT).in(model.getUuid())));

				if (predicates.isEmpty()) {
					return cb.and(predicates.toArray(new Predicate[predicates.size()]));
				} else {
					return cb.or(predicates.toArray(new Predicate[predicates.size()]));
				}

			}
		};

		List<DynamicFieldTemplate> entities = modelService.findBySpecificationBySort(specification, DynamicFieldTemplate.class);

		for (int i = 0; i < entities.size(); i++) {

			for (int j = 0; j < entities.get(i).getDynamicFieldSlots().size(); j++) {
				entities.get(i).getDynamicFieldSlots().remove(model.getUuid());
			}
			modelService.saveEntityByLegacyMode(entities.get(i), DynamicFieldTemplate.class);
		}
	}
}
