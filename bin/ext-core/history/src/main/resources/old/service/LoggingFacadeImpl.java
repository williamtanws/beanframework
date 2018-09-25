package com.beanframework.logging.service;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.beanframework.common.utils.RequestUtils;
import com.beanframework.logging.domain.Logging;

@Component
public class LoggingFacadeImpl implements LoggingFacade {

	@Autowired
	private LoggingService logService;

	public Page<Logging> getLogPage(Logging logging, int page, int size, Direction direction, String... properties) {
		// Change page to index's page
		page = page <= 0 ? 0 : page - 1;
		size = size <= 0 ? 1 : size;

		PageRequest pageRequest;
		if (direction != null) {
			pageRequest = PageRequest.of(page, size, direction, properties);
		} else {
			pageRequest = PageRequest.of(page, size, Sort.Direction.DESC, Logging.CREATED_DATE);
		}

		return logService.page(logging, pageRequest);
	}

	public void log(String channel, String operate, String result) {

		log(channel, operate, result, null);
	}

	public void log(String channel, String operate, Exception e) {

		log(channel, operate, null, e);
	}

	public void log(String channel, String operate, String result, Exception e) {
		Logging log = new Logging();
		log.setChannel(channel);
		log.setOperate(operate);
		log.setResult(result);
		log.setException(e == null ? null : ExceptionUtils.getStackTrace(e));

		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

		if (attr != null) {
			HttpServletRequest request = attr.getRequest();
			log.setIp(RequestUtils.getIpAddress(request));
		}

		logService.save(log);
	}

}
