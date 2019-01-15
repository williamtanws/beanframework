package com.beanframework.language.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.language.domain.Language;

@Service
public class LanguageServiceImpl implements LanguageService {

	@Autowired
	private ModelService modelService;

	@Override
	public Language create() throws Exception {
		return modelService.create(Language.class);
	}

	@Override
	public Language saveEntity(Language model) throws BusinessException {
		return (Language) modelService.saveEntity(model, Language.class);
	}

	@Override
	public void deleteById(String id) throws BusinessException {

		try {
			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(Language.ID, id);
			Language model = modelService.findOneEntityByProperties(properties, Language.class);
			modelService.deleteByEntity(model, Language.class);

		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public List<Language> findEntityBySorts(Map<String, Direction> sorts) throws Exception {
		return modelService.findCachedEntityByPropertiesAndSorts(null, sorts, null, null, Language.class);
	}
}
