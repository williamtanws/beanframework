//package com.beanframework.common.utils;
//
//import java.io.UnsupportedEncodingException;
//import java.net.URLEncoder;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Map.Entry;
//
//import org.springframework.data.domain.Sort.Direction;
//import org.springframework.ui.Model;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.util.StringUtils;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//import org.springframework.web.util.UriComponents;
//import org.springframework.web.util.UriComponentsBuilder;
//
//public class PaginationUtils {
//
//	public static final String PARAM_PAGE = "page";
//	public static final String PARAM_SIZE = "size";
//	public static final String PARAM_DIRECTION = "direction";
//	public static final String PARAM_PROPERTIES = "properties";
//	public static final String PARAM_PROPERTIES_SPLIT = ",";
//
//	public static final int DEFAULT_SIZE = 10;
//
//	public static Map<String, Object> getPaginationParam(Model model, @RequestParam Map<String, Object> requestParams) {
//		Map<String, Object> params = new HashMap<String, Object>();
//
//		int page = isNotBlank(requestParams.get(PARAM_PAGE)) ? Integer.valueOf(requestParams.get(PARAM_PAGE).toString()) - 1 : 0;
//		int size = isNotBlank(requestParams.get(PARAM_SIZE)) ? Integer.valueOf(requestParams.get(PARAM_SIZE).toString()) : DEFAULT_SIZE;
//		Direction direction = isNotBlank(requestParams.get(PARAM_DIRECTION)) ? Direction.valueOf(requestParams.get(PARAM_DIRECTION).toString().toUpperCase()) : null;
//		String properties = isNotBlank(requestParams.get(PARAM_PROPERTIES)) ? String.valueOf(requestParams.get(PARAM_PROPERTIES).toString().toUpperCase()) : null;
//		
//		params.put(PARAM_PAGE, page);
//		params.put(PARAM_SIZE, size);
//		params.put(PARAM_DIRECTION, direction);
//		params.put(PARAM_PROPERTIES, properties);
//
//		return params;
//	}
//
//	public static String getPaginationPath(String path, @RequestParam Map<String, Object> requestParams) {
//
//		int page = isNotBlank(requestParams.get(PARAM_PAGE)) ? Integer.valueOf(requestParams.get(PARAM_PAGE).toString()) - 1 : 0;
//		int size = isNotBlank(requestParams.get(PARAM_SIZE)) ? Integer.valueOf(requestParams.get(PARAM_SIZE).toString()) : DEFAULT_SIZE;
//		String properties = isNotBlank(requestParams.get(PARAM_PROPERTIES)) ? String.valueOf(requestParams.get(PARAM_PROPERTIES).toString().toUpperCase()) : null;
//		Direction direction = isNotBlank(requestParams.get(PARAM_DIRECTION)) ? Direction.valueOf(requestParams.get(PARAM_DIRECTION).toString().toUpperCase()) : null;
//		
//		MultiValueMap<String, String> paginationParams = new LinkedMultiValueMap<String, String>();
//		paginationParams.set(PARAM_PAGE, String.valueOf(page));
//		paginationParams.set(PARAM_SIZE, String.valueOf(size));
//		paginationParams.set(PARAM_PROPERTIES, convertParam(properties));
//		paginationParams.set(PARAM_DIRECTION, convertParam(direction));
//
//		UriComponents paginationPath = UriComponentsBuilder.fromPath(path).queryParams(paginationParams).build();
//
//		return paginationPath.toString();
//	}
//
//	public static String generatePath(String path, Map<String, String> additionalParams, @RequestParam Map<String, Object> requestParams) {
//
//		int page = isNotBlank(requestParams.get(PARAM_PAGE)) ? Integer.valueOf(requestParams.get(PARAM_PAGE).toString()) - 1 : 0;
//		int size = isNotBlank(requestParams.get(PARAM_SIZE)) ? Integer.valueOf(requestParams.get(PARAM_SIZE).toString()) : DEFAULT_SIZE;
//		String properties = isNotBlank(requestParams.get(PARAM_PROPERTIES)) ? String.valueOf(requestParams.get(PARAM_PROPERTIES).toString().toUpperCase()) : null;
//		Direction direction = isNotBlank(requestParams.get(PARAM_DIRECTION)) ? Direction.valueOf(requestParams.get(PARAM_DIRECTION).toString().toUpperCase()) : null;
//		
//		MultiValueMap<String, String> paginationParams = new LinkedMultiValueMap<String, String>();
//		paginationParams.set(PARAM_PAGE, String.valueOf(page));
//		paginationParams.set(PARAM_SIZE, String.valueOf(size));
//		paginationParams.set(PARAM_PROPERTIES, convertParam(properties));
//		paginationParams.set(PARAM_DIRECTION, convertParam(direction));
//
//		for (Entry<String, String> additionalParam : additionalParams.entrySet()) {
//			paginationParams.set(additionalParam.getKey(), additionalParam.getValue());
//		}
//
//		UriComponents paginationPath = UriComponentsBuilder.fromPath(path).queryParams(paginationParams).build();
//
//		return paginationPath.toString();
//	}
//
//	public static void setPaginationParams(Model model, @RequestParam Map<String, Object> requestParams) {
//
//		model.addAttribute(PARAM_SIZE, convertParamEncode(requestParams.get(PARAM_SIZE)));
//		model.addAttribute(PARAM_PAGE, convertParamEncode(requestParams.get(PARAM_PAGE)));
//		model.addAttribute(PARAM_PROPERTIES, convertParamEncode(requestParams.get(PARAM_PROPERTIES)));
//		model.addAttribute(PARAM_DIRECTION, convertParamEncode(requestParams.get(PARAM_DIRECTION)));
//	}
//
//	public static void setPaginationParams(RedirectAttributes redirectAttributes, @RequestParam Map<String, Object> requestParams) {
//
//		redirectAttributes.addAttribute(PARAM_SIZE, convertParamEncode(requestParams.get(PARAM_SIZE)));
//		redirectAttributes.addAttribute(PARAM_PAGE, convertParamEncode(requestParams.get(PARAM_PAGE)));
//		redirectAttributes.addAttribute(PARAM_PROPERTIES, convertParamEncode(requestParams.get(PARAM_PROPERTIES)));
//		redirectAttributes.addAttribute(PARAM_DIRECTION, convertParamEncode(requestParams.get(PARAM_DIRECTION)));
//	}
//
//	public static String convertParam(Object value) {
//		if (isNotBlank(value)) {
//			return value.toString();
//		} else {
//			return "";
//		}
//	}
//
//	public static String convertParamEncode(Object value) {
//		if (isNotBlank(value)) {
//			try {
//				return URLEncoder.encode(value.toString(), "UTF-8").toString();
//			} catch (UnsupportedEncodingException e) {
//				e.printStackTrace();
//				return value.toString();
//			}
//		} else {
//			return "";
//		}
//	}
//
//	public static boolean isNotBlank(Object object) {
//		if (object == null) {
//			return false;
//		} else if (object instanceof String) {
//			if (StringUtils.isBlank(object.toString())) {
//				return false;
//			}
//		}
//
//		return true;
//	}
//
//}
