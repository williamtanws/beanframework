package com.beanframework.core.specification;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import org.apache.commons.lang3.StringUtils;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.domain.GenericEntity;
import com.beanframework.common.specification.AbstractSpecification;
import com.beanframework.common.specification.CommonSpecification;
import com.beanframework.notification.domain.Notification;

public class NotificationSpecification extends CommonSpecification {
  public static <T> AbstractSpecification<T> getPageSpecification(
      DataTableRequest dataTableRequest) {
    return new AbstractSpecification<T>() {
      private static final long serialVersionUID = 1L;

      @Override
      public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<Predicate>();

        String search = clean(dataTableRequest.getSearch());

        if (StringUtils.isNotBlank(search)) {
          predicates.add(cb.or(cb.like(root.get(Notification.TYPE), convertToLikePattern(search))));
          predicates
              .add(cb.or(cb.like(root.get(Notification.MESSAGE), convertToLikePattern(search))));
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
        multiselect.add(root.get(Notification.UUID));
        multiselect.add(root.get(Notification.TYPE));
        multiselect.add(root.get(Notification.MESSAGE));
        multiselect.add(root.get(Notification.CREATED_DATE));
        return multiselect;
      }
    };
  }

  public static <T> AbstractSpecification<T> getNewNotificationByFromDate(Date date) {
    return new AbstractSpecification<T>() {
      private static final long serialVersionUID = 1L;

      @Override
      public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<Predicate>();

        if (date != null) {
          predicates.add(cb.greaterThanOrEqualTo(root.get(GenericEntity.CREATED_DATE), date));
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

  public static <T> AbstractSpecification<T> getOldNotificationByToDate(Date date) {
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
