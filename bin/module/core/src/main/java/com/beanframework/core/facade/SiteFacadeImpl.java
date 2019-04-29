package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import com.beanframework.cms.domain.Site;
import com.beanframework.cms.service.SiteService;
import com.beanframework.common.context.ConvertRelationType;
import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.SiteDto;

@Component
public class SiteFacadeImpl implements SiteFacade {

	@Autowired
	private ModelService modelService;

	@Autowired
	private SiteService siteService;

	@Override
	public SiteDto findOneByUuid(UUID uuid) throws Exception {
		Site entity = siteService.findOneEntityByUuid(uuid);
		return modelService.getDto(entity, SiteDto.class, new DtoConverterContext(ConvertRelationType.ALL));
	}

	@Override
	public SiteDto findOneProperties(Map<String, Object> properties) throws Exception {
		Site entity = siteService.findOneEntityByProperties(properties);
		return modelService.getDto(entity, SiteDto.class);
	}

	@Override
	public SiteDto create(SiteDto model) throws BusinessException {
		return save(model);
	}

	@Override
	public SiteDto update(SiteDto model) throws BusinessException {
		return save(model);
	}

	public SiteDto save(SiteDto dto) throws BusinessException {
		try {
			Site entity = modelService.getEntity(dto, Site.class);
			entity = (Site) siteService.saveEntity(entity);

			return modelService.getDto(entity, SiteDto.class);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public void delete(UUID uuid) throws BusinessException {
		siteService.deleteByUuid(uuid);
	}

	@Override
	public Page<SiteDto> findPage(DataTableRequest dataTableRequest) throws Exception {
		Page<Site> page = siteService.findEntityPage(dataTableRequest);

		List<SiteDto> dtos = modelService.getDto(page.getContent(), SiteDto.class, new DtoConverterContext(ConvertRelationType.RELATION));
		return new PageImpl<SiteDto>(dtos, page.getPageable(), page.getTotalElements());
	}

	@Override
	public int count() throws Exception {
		return siteService.count();
	}

	@Override
	public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception {

		List<Object[]> revisions = siteService.findHistory(dataTableRequest);
		for (int i = 0; i < revisions.size(); i++) {
			Object[] entityObject = revisions.get(i);
			if (entityObject[0] instanceof Site) {

				entityObject[0] = modelService.getDto(entityObject[0], SiteDto.class);
			}
			revisions.set(i, entityObject);
		}

		return revisions;
	}

	@Override
	public int countHistory(DataTableRequest dataTableRequest) throws Exception {
		return siteService.findCountHistory(dataTableRequest);
	}

	@Override
	public SiteDto createDto() throws Exception {

		return modelService.getDto(siteService.create(), SiteDto.class);
	}
}
