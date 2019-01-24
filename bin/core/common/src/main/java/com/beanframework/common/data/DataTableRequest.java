/**
 * 
 */
package com.beanframework.common.data;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.criteria.AuditCriterion;
import org.hibernate.envers.query.order.AuditOrder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;

import com.beanframework.common.utils.AppUtil;

/**
 * The Class DataTableRequest.
 *
 * @author pavan.solapure
 */
public class DataTableRequest<T> {

	/** The unique id. */
	private String uniqueId;

	/** The draw. */
	private String draw;

	/** The start. */
	private Integer start;

	/** The length. */
	private Integer length;

	/** The search. */
	private String search;

	/** The regex. */
	private boolean regex;

	/** The columns. */
	private List<DataTableColumnSpecs> columns;

	/** The order. */
	private DataTableColumnSpecs order;

	/** The is global search. */
	private boolean isGlobalSearch;

	/**
	 * Instantiates a new data table request.
	 *
	 * @param request the request
	 */
	public DataTableRequest(HttpServletRequest request) {
		prepareDataTableRequest(request);
	}

	/**
	 * Gets the unique id.
	 *
	 * @return the uniqueId
	 */
	public String getUniqueId() {
		return uniqueId;
	}

	/**
	 * Sets the unique id.
	 *
	 * @param uniqueId the uniqueId to set
	 */
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	/**
	 * Gets the start.
	 *
	 * @return the start
	 */
	public Integer getStart() {
		return start;
	}

	/**
	 * Sets the start.
	 *
	 * @param start the start to set
	 */
	public void setStart(Integer start) {
		this.start = start;
	}

	/**
	 * Gets the length.
	 *
	 * @return the length
	 */
	public Integer getLength() {
		return length;
	}

	/**
	 * Sets the length.
	 *
	 * @param length the length to set
	 */
	public void setLength(Integer length) {
		this.length = length;
	}

	/**
	 * Gets the search.
	 *
	 * @return the search
	 */
	public String getSearch() {
		return search;
	}

	/**
	 * Sets the search.
	 *
	 * @param search the search to set
	 */
	public void setSearch(String search) {
		this.search = search;
	}

	/**
	 * Checks if is regex.
	 *
	 * @return the regex
	 */
	public boolean isRegex() {
		return regex;
	}

	/**
	 * Sets the regex.
	 *
	 * @param regex the regex to set
	 */
	public void setRegex(boolean regex) {
		this.regex = regex;
	}

	/**
	 * Gets the columns.
	 *
	 * @return the columns
	 */
	public List<DataTableColumnSpecs> getColumns() {
		return columns;
	}

	/**
	 * Sets the columns.
	 *
	 * @param columns the columns to set
	 */
	public void setColumns(List<DataTableColumnSpecs> columns) {
		this.columns = columns;
	}

	/**
	 * Gets the order.
	 *
	 * @return the order
	 */
	public DataTableColumnSpecs getOrder() {
		return order;
	}

	/**
	 * Sets the order.
	 *
	 * @param order the order to set
	 */
	public void setOrder(DataTableColumnSpecs order) {
		this.order = order;
	}

	/**
	 * Gets the draw.
	 *
	 * @return the draw
	 */
	public String getDraw() {
		return draw;
	}

	/**
	 * Sets the draw.
	 *
	 * @param draw the draw to set
	 */
	public void setDraw(String draw) {
		this.draw = draw;
	}

	/**
	 * Checks if is global search.
	 *
	 * @return the isGlobalSearch
	 */
	public boolean isGlobalSearch() {
		return isGlobalSearch;
	}

	/**
	 * Sets the global search.
	 *
	 * @param isGlobalSearch the isGlobalSearch to set
	 */
	public void setGlobalSearch(boolean isGlobalSearch) {
		this.isGlobalSearch = isGlobalSearch;
	}

	/**
	 * Prepare data table request.
	 *
	 * @param request the request
	 */
	private void prepareDataTableRequest(HttpServletRequest request) {

		Enumeration<String> parameterNames = request.getParameterNames();

		if (parameterNames.hasMoreElements()) {

			this.setStart(Integer.parseInt(request.getParameter(PaginationCriteria.PAGE_NO)));
			this.setLength(Integer.parseInt(request.getParameter(PaginationCriteria.PAGE_SIZE)));
			this.setUniqueId(request.getParameter("_"));
			this.setDraw(request.getParameter(PaginationCriteria.DRAW));

			this.setSearch(request.getParameter("search[value]"));
			this.setRegex(Boolean.valueOf(request.getParameter("search[regex]")));

			int sortableCol = Integer.parseInt(request.getParameter("order[0][column]"));

			List<DataTableColumnSpecs> columns = new ArrayList<DataTableColumnSpecs>();

			if (!AppUtil.isObjectEmpty(this.getSearch())) {
				this.setGlobalSearch(true);
			}

			maxParamsToCheck = getNumberOfColumns(request);

			for (int i = 0; i < maxParamsToCheck; i++) {
				if (null != request.getParameter("columns[" + i + "][data]") && !"null".equalsIgnoreCase(request.getParameter("columns[" + i + "][data]"))
						&& !AppUtil.isObjectEmpty(request.getParameter("columns[" + i + "][data]"))) {
					DataTableColumnSpecs colSpec = new DataTableColumnSpecs(request, i);
					if (i == sortableCol) {
						this.setOrder(colSpec);
					}
					columns.add(colSpec);

					if (!AppUtil.isObjectEmpty(colSpec.getSearch())) {
						this.setGlobalSearch(false);
					}
				}
			}

			if (!AppUtil.isObjectEmpty(columns)) {
				this.setColumns(columns);
			}
		}
	}

	private int getNumberOfColumns(HttpServletRequest request) {
		Pattern p = Pattern.compile("columns\\[[0-9]+\\]\\[data\\]");
		@SuppressWarnings("rawtypes")
		Enumeration params = request.getParameterNames();
		List<String> lstOfParams = new ArrayList<String>();
		while (params.hasMoreElements()) {
			String paramName = (String) params.nextElement();
			Matcher m = p.matcher(paramName);
			if (m.matches()) {
				lstOfParams.add(paramName);
			}
		}
		return lstOfParams.size();
	}

	/**
	 * Gets the pagination request.
	 *
	 * @return the pagination request
	 */
	public PaginationCriteria getPaginationRequest() {

		PaginationCriteria pagination = new PaginationCriteria();
		pagination.setPageNumber(this.getStart() / this.getLength());
		pagination.setPageSize(this.getLength());

		SortBy sortBy = null;
		if (!AppUtil.isObjectEmpty(this.getOrder())) {
			sortBy = new SortBy();
			sortBy.addSort(this.getOrder().getData(), SortOrder.fromValue(this.getOrder().getSortDir()));
		}

		FilterBy filterBy = new FilterBy();
		filterBy.setGlobalSearch(this.isGlobalSearch());
		for (DataTableColumnSpecs colSpec : this.getColumns()) {
			if (colSpec.isSearchable()) {
				if (!AppUtil.isObjectEmpty(this.getSearch()) || !AppUtil.isObjectEmpty(colSpec.getSearch())) {
					filterBy.addFilter(colSpec.getData(), (this.isGlobalSearch()) ? this.getSearch() : colSpec.getSearch());
				}
			}
		}

		pagination.setSortBy(sortBy);
		pagination.setFilterBy(filterBy);

		return pagination;
	}

	/** The max params to check. */
	private int maxParamsToCheck = 0;

	public Pageable getPageable() {
		List<Order> orders = new ArrayList<Order>();
		for (DataTableColumnSpecs spec : columns) {
			if (StringUtils.isNotBlank(spec.getSortDir())) {
				Order order = new Order(Direction.fromString(spec.getSortDir()), spec.getData());
				orders.add(order);
			}
		}
		return PageRequest.of(this.getPaginationRequest().getPageNumber(), this.getPaginationRequest().getPageSize(), Sort.by(orders));
	}

	public Specification<T> getSpecification() {
		return new Specification<T>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();

				if (isGlobalSearch() && StringUtils.isNotEmpty(getSearch())) {
					for (DataTableColumnSpecs specs : columns) {
						predicates.add(cb.or(cb.like(root.get(specs.getData()), convertToLikePattern(getSearch()))));
					}
				} else {
					for (DataTableColumnSpecs specs : columns) {
						if (StringUtils.isNotBlank(specs.getSearch())) {
							predicates.add(cb.or(cb.like(root.get(specs.getData()), convertToLikePattern(specs.getSearch()))));
						}
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

	public static String convertToLikePattern(String value) {
		if (value.contains("%") == false) {
			value = "%" + value + "%";
		}
		return value;
	}

	public AuditCriterion getAuditCriterion() {

		AuditCriterion orCriterion = null;

		if (isGlobalSearch() && StringUtils.isNotEmpty(getSearch())) {
			for (DataTableColumnSpecs specs : columns) {
				orCriterion = AuditEntity.or(orCriterion, AuditEntity.property(specs.getData()).like(convertToLikePattern(getSearch())));
			}
		} else {
			for (DataTableColumnSpecs specs : columns) {
				if (StringUtils.isNotBlank(specs.getSearch())) {
					orCriterion = AuditEntity.or(orCriterion, AuditEntity.property(specs.getData()).like(convertToLikePattern(specs.getSearch())));
				}
			}
		}

		if (StringUtils.isNotBlank(getUniqueId())) {
			if (orCriterion != null) {
				return AuditEntity.and(AuditEntity.id().eq(UUID.fromString(getUniqueId())), orCriterion);
			} else {
				return AuditEntity.id().eq(UUID.fromString(getUniqueId()));
			}
		} else {
			return orCriterion;
		}
	}

	public AuditOrder getAuditOrder() {
		return AuditEntity.revisionNumber().desc();
	}

	@Override
	public String toString() {
		return "DataTableRequest [uniqueId=" + uniqueId + ", start=" + start + ", length=" + length + ", search=" + search + ", regex=" + regex + ", columns=" + columns + ", order="
				+ order + ", isGlobalSearch=" + isGlobalSearch + ", maxParamsToCheck=" + maxParamsToCheck + "]";
	}

}
