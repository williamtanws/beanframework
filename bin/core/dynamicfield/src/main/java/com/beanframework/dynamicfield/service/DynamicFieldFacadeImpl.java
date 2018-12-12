package com.beanframework.dynamicfield.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.beanframework.dynamicfield.domain.DynamicField;
import com.beanframework.dynamicfield.validator.DeleteDynamicFieldValidator;
import com.beanframework.dynamicfield.validator.SaveDynamicFieldValidator;

@Component
public class DynamicFieldFacadeImpl implements DynamicFieldFacade {

	@Autowired
	private DynamicFieldService dynamicFieldService;

	@Autowired
	private SaveDynamicFieldValidator saveDynamicFieldValidator;
	
	@Autowired
	private DeleteDynamicFieldValidator deleteDynamicFieldValidator;

	@Override
	public DynamicField create() {
		return dynamicFieldService.create();
	}

	@Override
	public DynamicField initDefaults(DynamicField dynamicField) {
		return dynamicFieldService.initDefaults(dynamicField);
	}

	@Override
	public DynamicField save(DynamicField dynamicField, Errors bindingResult) {
		saveDynamicFieldValidator.validate(dynamicField, bindingResult);

		if (bindingResult.hasErrors()) {
			return dynamicField;
		}

		return dynamicFieldService.save(dynamicField);
	}

	@Override
	public void delete(UUID uuid, Errors bindingResult) {
		deleteDynamicFieldValidator.validate(uuid, bindingResult);
		
		if (bindingResult.hasErrors() == false) {
			dynamicFieldService.delete(uuid);
		}
	}

	@Override
	public void deleteAll() {
		dynamicFieldService.deleteAll();
	}

	@Override
	public DynamicField findByUuid(UUID uuid) {
		return dynamicFieldService.findByUuid(uuid);
	}

	@Override
	public Page<DynamicField> page(DynamicField dynamicField, int page, int size, Direction direction, String... properties) {

		// Change page to index's page
		page = page <= 0 ? 0 : page - 1;
		size = size <= 0 ? 1 : size;

		PageRequest pageRequest = PageRequest.of(page, size, direction, properties);

		return dynamicFieldService.page(dynamicField, pageRequest);
	}

	public DynamicFieldService getDynamicFieldService() {
		return dynamicFieldService;
	}

	public void setDynamicFieldService(DynamicFieldService dynamicFieldService) {
		this.dynamicFieldService = dynamicFieldService;
	}

	public SaveDynamicFieldValidator getSaveDynamicFieldValidator() {
		return saveDynamicFieldValidator;
	}

	public void setSaveDynamicFieldValidator(SaveDynamicFieldValidator saveDynamicFieldValidator) {
		this.saveDynamicFieldValidator = saveDynamicFieldValidator;
	}

}
