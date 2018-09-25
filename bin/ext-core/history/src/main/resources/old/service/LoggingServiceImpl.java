package com.beanframework.logging.service;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.beanframework.logging.domain.Logging;
import com.beanframework.logging.domain.LoggingSpecification;
import com.beanframework.logging.repository.LoggingRepository;

@Service
@Transactional
public class LoggingServiceImpl implements LoggingService {

	@Autowired
	private LoggingRepository loggingRepository;

	public Page<Logging> page(Logging logging, Pageable pageable) {
		return loggingRepository.findAll(LoggingSpecification.findByCriteria(logging), pageable);
	}

	public Logging save(Logging log) {
		return loggingRepository.save(log);
	}

	public int deleteByLimit(int limit) {

		// Get latest logs
		PageRequest pageRequest = PageRequest.of(0, limit, Sort.Direction.DESC, Logging.CREATED_DATE);
		// Get latest logs Id
		List<UUID> excludedIds = loggingRepository.getLastestLogId(pageRequest);

		if (!excludedIds.isEmpty()) {
			// Delete old logs
			return loggingRepository.deleteOldLogByExcludedId(excludedIds);
		}

		return 0;
	}

	public int deleteByOldDay(int oldDay) {

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -oldDay);

		java.sql.Date date = new java.sql.Date(cal.getTimeInMillis());

		return loggingRepository.removeOlderThan(date);
	}
}
