package com.beanframework.core.specification;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.domain.GenericEntity;
import com.beanframework.common.specification.AbstractSpecification;
import com.beanframework.common.specification.CommonSpecification;
import com.beanframework.logentry.domain.Logentry;

public class LogentrySpecification extends CommonSpecification {
  public static <T> AbstractSpecification<T> getPageSpecification(
      DataTableRequest dataTableRequest) {
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
        multiselect.add(root.get(Logentry.UUID));
        multiselect.add(root.get(Logentry.TYPE));
        multiselect.add(root.get(Logentry.MESSAGE));
        return multiselect;
      }
    };
  }

  public static <T> AbstractSpecification<T> getOldLogentryByToDate(Date date) {
    return new AbstractSpecification<T>() {
      private static final long serialVersionUID = 1L;

      @Override
      public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<Predicate>();

        if (date != null) {
          predicates.add(cb.lessThanOrEqualTo(root.get(GenericEntity.CREATED_DATE), date));
        }

        if (predicates.isEmpty()) {
          return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        } else {
          return cb.or(predicates.toArray(new Predicate[predicates.size()]));
        }
      }

      @Override
      public List<Selection<?>> toSelection(Root<T> root) {

        return null;
      }
    };
  }

}
