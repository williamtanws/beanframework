package com.beanframework.common.specification;

import java.util.List;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import org.springframework.data.jpa.domain.Specification;

public interface AbstractSpecification<T> extends Specification<T> {

  List<Selection<?>> toSelection(Root<T> root);
}
