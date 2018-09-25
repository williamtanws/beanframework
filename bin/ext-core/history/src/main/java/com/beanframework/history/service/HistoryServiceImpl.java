package com.beanframework.history.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.beanframework.history.HistoryConstants;
import com.beanframework.history.converter.DtoHistoryConverter;
import com.beanframework.history.converter.EntityHistoryConverter;
import com.beanframework.history.domain.History;
import com.beanframework.history.domain.HistorySpecification;
import com.beanframework.history.repository.HistoryRepository;

@Service
public class HistoryServiceImpl implements HistoryService {

	@Autowired
	private HistoryRepository historyRepository;

	@Autowired
	private EntityHistoryConverter entityHistoryConverter;

	@Autowired
	private DtoHistoryConverter dtoHistoryConverter;

	@Override
	public History create() {
		return initDefaults(new History());
	}

	@Override
	public History initDefaults(History history) {
		return history;
	}

	@CacheEvict(value = { HistoryConstants.Cache.HISTORY,  HistoryConstants.Cache.HISTORIES}, allEntries = true)
	@Transactional(readOnly = false)
	@Override
	public History save(History history) {

		history = entityHistoryConverter.convert(history);
		history = historyRepository.save(history);
		history = dtoHistoryConverter.convert(history);

		return history;
	}

	@CacheEvict(value = { HistoryConstants.Cache.HISTORY,  HistoryConstants.Cache.HISTORIES}, allEntries = true)
	@Transactional(readOnly = false)
	@Override
	public void delete(UUID uuid) {
		historyRepository.deleteById(uuid);
	}

	@CacheEvict(value = { HistoryConstants.Cache.HISTORY,  HistoryConstants.Cache.HISTORIES}, allEntries = true)
	@Transactional(readOnly = false)
	@Override
	public void deleteAll() {
		historyRepository.deleteAll();
	}
	
	@Transactional(readOnly = true)
	@Override
	public Optional<History> findEntityByUuid(UUID uuid) {
		return historyRepository.findByUuid(uuid);
	}

	@Transactional(readOnly = true)
	@Override
	public History findByUuid(UUID uuid) {
		Optional<History> history = historyRepository.findByUuid(uuid);

		if (history.isPresent()) {
			return dtoHistoryConverter.convert(history.get());
		} else {
			return null;
		}
	}

	@Transactional(readOnly = true)
	@Override
	public Page<History> page(History history, Pageable pageable) {
		Page<History> page = historyRepository.findAll(HistorySpecification.findByCriteria(history), pageable);
		List<History> content = dtoHistoryConverter.convert(page.getContent());
		return new PageImpl<History>(content, page.getPageable(), page.getTotalElements());
	}

	public HistoryRepository getHistoryRepository() {
		return historyRepository;
	}

	public void setHistoryRepository(HistoryRepository historyRepository) {
		this.historyRepository = historyRepository;
	}

	public EntityHistoryConverter getEntityHistoryConverter() {
		return entityHistoryConverter;
	}

	public void setEntityHistoryConverter(EntityHistoryConverter entityHistoryConverter) {
		this.entityHistoryConverter = entityHistoryConverter;
	}

	public DtoHistoryConverter getDtoHistoryConverter() {
		return dtoHistoryConverter;
	}

	public void setDtoHistoryConverter(DtoHistoryConverter dtoHistoryConverter) {
		this.dtoHistoryConverter = dtoHistoryConverter;
	}
}
