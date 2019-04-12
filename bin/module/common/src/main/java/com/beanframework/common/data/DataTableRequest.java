/**
 * 
 */
package com.beanframework.common.data;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

import com.beanframework.common.domain.GenericEntity;
import com.beanframework.common.utils.AbstractSpecification;
import com.beanframework.common.utils.AppUtil;

/**
 * The Class DataTableRequest.
 *
 * @author pavan.solapure
 */
public class DataTableRequest {

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
	private List<DataTableColumnSpecs> columns = new ArrayList<DataTableColumnSpecs>(0);

	/** The order. */
	private List<DataTableColumnSpecs> orders = new ArrayList<DataTableColumnSpecs>(0);

	/** The is global search. */
	private boolean isGlobalSearch;

	private Set<Integer> skipColumnIndexes = new HashSet<Integer>();

	private Pageable pageable;

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
	public List<DataTableColumnSpecs> getOrders() {
		return orders;
	}

	/**
	 * Sets the order.
	 *
	 * @param order the order to set
	 */
	public void setOrders(List<DataTableColumnSpecs> orders) {
		this.orders = orders;
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

	public void setPageable(Pageable pageable) {
		this.pageable = pageable;
	}

	public Pageable getPageable() {
		return this.pageable;
	}

	/**
	 * Prepare data table request.
	 *
	 * @param request the request
	 */
	public void prepareDataTableRequest(HttpServletRequest request) {

		Enumeration<String> parameterNames = request.getParameterNames();

		if (parameterNames.hasMoreElements()) {

			this.setStart(Integer.parseInt(request.getParameter(PaginationCriteria.PAGE_NO)));
			this.setLength(Integer.parseInt(request.getParameter(PaginationCriteria.PAGE_SIZE)));
			this.setDraw(request.getParameter(PaginationCriteria.DRAW));

			this.setSearch(request.getParameter("search[value]"));
			this.setRegex(Boolean.valueOf(request.getParameter("search[regex]")));

			List<DataTableColumnSpecs> columns = new ArrayList<DataTableColumnSpecs>();
			List<DataTableColumnSpecs> orders = new ArrayList<DataTableColumnSpecs>();

			if (!AppUtil.isObjectEmpty(this.getSearch())) {
				this.setGlobalSearch(true);
			}

			maxParamsToCheck = getNumberOfColumns(request);

			for (int i = 0; i < maxParamsToCheck; i++) {
				if (skipColumnIndexes.contains(i) == false) {
					if (null != request.getParameter("columns[" + i + "][data]") && !"null".equalsIgnoreCase(request.getParameter("columns[" + i + "][data]"))
							&& !AppUtil.isObjectEmpty(request.getParameter("columns[" + i + "][data]"))) {
						DataTableColumnSpecs colSpec = new DataTableColumnSpecs(request, i);

						columns.add(colSpec);

						if (!AppUtil.isObjectEmpty(colSpec.getSearch())) {
							this.setGlobalSearch(false);
						}
					}
				}
			}

			if (!AppUtil.isObjectEmpty(columns)) {
				this.setColumns(columns);
			}

			for (int i = 0; i < maxParamsToCheck; i++) {
				if (skipColumnIndexes.contains(i) == false) {
					if (request.getParameter("order[" + i + "][column]") != null) {
						String columnIndex = request.getParameter("order[" + i + "][column]");

						if (request.getParameter("columns[" + columnIndex + "][data]") != null) {
							String sortDir = request.getParameter("order[" + i + "][dir]");

							DataTableColumnSpecs colSpec = new DataTableColumnSpecs();
							colSpec.setData(request.getParameter("columns[" + columnIndex + "][data]"));
							colSpec.setName(request.getParameter("columns[" + columnIndex + "][name]"));
							colSpec.setOrderable(Boolean.valueOf(request.getParameter("columns[" + columnIndex + "][orderable]")));
							colSpec.setRegex(Boolean.valueOf(request.getParameter("columns[" + columnIndex + "][search][regex]")));
							colSpec.setSearch(request.getParameter("columns[" + columnIndex + "][search][value]"));
							colSpec.setSearchable(Boolean.valueOf(request.getParameter("columns[" + columnIndex + "][searchable]")));
							colSpec.setSortDir(sortDir);
							orders.add(colSpec);
						}
					}
				}

			}

			if (!AppUtil.isObjectEmpty(orders)) {
				this.setOrders(orders);
			}
		}

		List<Order> sortOrders = new ArrayList<Order>();
		if (orders.isEmpty() == false) {
			for (DataTableColumnSpecs spec : orders) {
				if (StringUtils.isNotBlank(spec.getSortDir())) {
					Order order = new Order(Direction.fromString(spec.getSortDir()), spec.getData());
					sortOrders.add(order);
				}
			}
		} else {
			Order order = new Order(Direction.DESC, GenericEntity.CREATED_DATE);
			sortOrders.add(order);
		}
		this.setPageable(PageRequest.of(this.getPaginationRequest().getPageNumber(), this.getPaginationRequest().getPageSize(), Sort.by(sortOrders)));
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
		for (DataTableColumnSpecs colSpec : this.getOrders()) {
			sortBy = new SortBy();
			sortBy.addSort(colSpec.getData(), SortOrder.fromValue(colSpec.getSortDir()));
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

	public AuditCriterion getAuditCriterion() {

		AuditCriterion orCriterion = null;

		if (isGlobalSearch() && StringUtils.isNotEmpty(getSearch())) {
			for (DataTableColumnSpecs specs : columns) {
				orCriterion = AuditEntity.or(orCriterion, AuditEntity.property(specs.getData()).like(AbstractSpecification.convertToLikePattern(getSearch())));
			}
		} else {
			for (DataTableColumnSpecs specs : columns) {
				if (StringUtils.isNotBlank(specs.getSearch())) {
					orCriterion = AuditEntity.or(orCriterion, AuditEntity.property(specs.getData()).like(AbstractSpecification.convertToLikePattern(specs.getSearch())));
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

	public Map<String, Object> getProperties() {

		Map<String, Object> properties = new HashMap<String, Object>();

		if (isGlobalSearch() && StringUtils.isNotEmpty(getSearch())) {
			for (DataTableColumnSpecs specs : columns) {
				properties.put(specs.getData(), getSearch());
			}
		} else {
			for (DataTableColumnSpecs specs : columns) {
				if (StringUtils.isNotBlank(specs.getSearch())) {
					properties.put(specs.getData(), specs.getSearch());
				}
			}
		}

		return properties;
	}

	@Override
	public String toString() {
		return "DataTableRequest [uniqueId=" + uniqueId + ", start=" + start + ", length=" + length + ", search=" + search + ", regex=" + regex + ", columns=" + columns + ", orders=" + orders
				+ ", isGlobalSearch=" + isGlobalSearch + ", maxParamsToCheck=" + maxParamsToCheck + ", pageable=" + pageable + "]";
	}

	public Set<Integer> getSkipColumnIndexes() {
		return skipColumnIndexes;
	}

	public void setSkipColumnIndexes(Set<Integer> skipColumnIndexes) {
		this.skipColumnIndexes = skipColumnIndexes;
	}

}
