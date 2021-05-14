package com.beanframework.core.specification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import org.apache.commons.lang3.StringUtils;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.domain.GenericEntity;
import com.beanframework.common.specification.AbstractSpecification;
import com.beanframework.common.specification.CommonSpecification;
import com.beanframework.dynamicfield.domain.DynamicFieldTemplate;

public class DynamicFieldTemplateSpecification extends CommonSpecification {

  public static <T> AbstractSpecification<T> getPageSpecification(
      DataTableRequest dataTableRequest) {
    return new AbstractSpecification<T>() {
      private static final long serialVersionUID = 1L;

      @Override
      public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<Predicate>();

        String search = clean(dataTableRequest.getSearch());

        if (StringUtils.isNotBlank(search)) {
          if (isUuid(dataTableRequest.getSearch())) {
            predicates.add(cb.or(cb.equal(root.get(GenericEntity.UUID), UUID.fromString(search))));
            predicates.add(cb.and(root.join(DynamicFieldTemplate.DYNAMIC_FIELD_SLOTS, JoinType.LEFT)
                .in(UUID.fromString(search))));
          }
          predicates.add(cb.or(cb.like(root.get(GenericEntity.ID), convertToLikePattern(search))));
          predicates.add(
              cb.or(cb.like(root.get(DynamicFieldTemplate.NAME), convertToLikePattern(search))));
        }

        if (predicates.isEmpty()) {
          return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        } else {
          return cb.or(predicates.toArray(new Predicate[predicates.size()]));
        }
      }

      public String toString() {
        return dataTableRequest.toString();
      }

      @Override
      public List<Selection<?>> toSelection(Root<T> root) {
        List<Selection<?>> multiselect = new ArrayList<Selection<?>>();
        multiselect.add(root.get(DynamicFieldTemplate.UUID));
        multiselect.add(root.get(DynamicFieldTemplate.ID));
        multiselect.add(root.get(DynamicFieldTemplate.NAME));
        return multiselect;
      }
    };
  }

  public static <T> AbstractSpecification<T> getDynamicFieldTemplateByDynamicFieldSlotUuid(
      UUID... uuid) {
    if (uuid == null) {
      throw new NullPointerException();
    }

    return new AbstractSpecification<T>() {
      private static final long serialVersionUID = 1L;

      @Override
      public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();

        predicates.add(cb.and(root.join(DynamicFieldTemplate.DYNAMIC_FIELD_SLOTS, JoinType.LEFT)
            .in(Arrays.asList(uuid))));

        return cb.and(cb.and(predicates.toArray(new Predicate[predicates.size()])));
      }

      public String toString() {
        return "uuid[" + uuid.toString() + "]" + ", getDynamicFieldTemplateByDynamicFieldSlots";
      }

      @Override
      public List<Selection<?>> toSelection(Root<T> root) {

        return null;
      }

    };
  }
}
