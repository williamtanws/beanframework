package com.beanframework.history.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.beanframework.history.domain.History;
import com.beanframework.history.validator.DeleteHistoryValidator;
import com.beanframework.history.validator.SaveHistoryValidator;

@Component
public class HistoryFacadeImpl implements HistoryFacade {

	@Autowired
	private HistoryService historyService;

	@Autowired
	private SaveHistoryValidator saveHistoryValidator;
	
	@Autowired
	private DeleteHistoryValidator deleteHistoryValidator;

	@Override
	public History create() {
		return historyService.create();
	}

	@Override
	public History initDefaults(History history) {
		return historyService.initDefaults(history);
	}

	@Override
	public History save(History history, Errors bindingResult) {
		saveHistoryValidator.validate(history, bindingResult);

		if (bindingResult.hasErrors()) {
			return history;
		}

		return historyService.save(history);
	}

	@Override
	public void delete(UUID uuid, Errors bindingResult) {
		deleteHistoryValidator.validate(uuid, bindingResult);
		
		if (bindingResult.hasErrors() == false) {
			historyService.delete(uuid);
		}
	}

	@Override
	public void deleteAll() {
		historyService.deleteAll();
	}

	@Override
	public History findByUuid(UUID uuid) {
		return historyService.findByUuid(uuid);
	}

	@Override
	public Page<History> page(History history, int page, int size, Direction direction, String... properties) {

		// Change page to index's page
		page = page <= 0 ? 0 : page - 1;
		size = size <= 0 ? 1 : size;

		PageRequest pageRequest = PageRequest.of(page, size, direction, properties);

		return historyService.page(history, pageRequest);
	}

	public HistoryService getHistoryService() {
		return historyService;
	}

	public void setHistoryService(HistoryService historyService) {
		this.historyService = historyService;
	}

	public SaveHistoryValidator getSaveHistoryValidator() {
		return saveHistoryValidator;
	}

	public void setSaveHistoryValidator(SaveHistoryValidator saveHistoryValidator) {
		this.saveHistoryValidator = saveHistoryValidator;
	}

}
