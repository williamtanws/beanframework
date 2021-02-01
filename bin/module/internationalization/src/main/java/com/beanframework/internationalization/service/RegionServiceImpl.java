package com.beanframework.internationalization.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beanframework.common.service.ModelService;
import com.beanframework.internationalization.domain.Country;
import com.beanframework.internationalization.domain.Region;

@Service
public class RegionServiceImpl implements RegionService {

	@Autowired
	private ModelService modelService;

	@Override
	public void removeCountryRel(Country model) throws Exception {
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(Region.COUNTRY, model.getUuid());
		List<Region> entities = modelService.findByPropertiesBySortByResult(properties, null, null, null, Region.class);

		if (entities != null)
			for (int i = 0; i < entities.size(); i++) {

				if (entities.get(i).getCountry() != null)
					if (entities.get(i).getCountry().equals(model.getUuid())) {
						entities.get(i).setCountry(null);
						modelService.saveEntityByLegacyMode(entities.get(i), Region.class);
					}
			}
	}
}
