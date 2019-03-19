package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.converter.EntityVendorProfileConverter;
import com.beanframework.core.data.VendorDto;
import com.beanframework.core.specification.VendorSpecification;
import com.beanframework.vendor.domain.Vendor;
import com.beanframework.vendor.service.VendorService;

@Component
public class VendorFacadeImpl implements VendorFacade {

	@Autowired
	private ModelService modelService;

	@Autowired
	private VendorService vendorService;

	@Autowired
	private EntityVendorProfileConverter entityVendorProfileConverter;
	
	@Override
	public VendorDto findOneByUuid(UUID uuid) throws Exception {
		Vendor entity = vendorService.findOneEntityByUuid(uuid);

		return modelService.getDto(entity, VendorDto.class);
	}

	@Override
	public VendorDto findOneProperties(Map<String, Object> properties) throws Exception {
		Vendor entity = vendorService.findOneEntityByProperties(properties);

		return modelService.getDto(entity, VendorDto.class);
	}

	@Override
	public VendorDto create(VendorDto model) throws BusinessException {
		return save(model);
	}

	@Override
	public VendorDto update(VendorDto model) throws BusinessException {
		return save(model);
	}

	public VendorDto save(VendorDto dto) throws BusinessException {
		try {
			if (dto.getProfilePicture() != null && dto.getProfilePicture().isEmpty() == false) {
				String mimetype = dto.getProfilePicture().getContentType();
				String type = mimetype.split("/")[0];
				if (type.equals("image") == false) {
					throw new Exception("Wrong picture format");
				}
			}
			
			Vendor entity = modelService.getEntity(dto, Vendor.class);
			entity = (Vendor) vendorService.saveEntity(entity);
			
			vendorService.saveProfilePicture(entity, dto.getProfilePicture());

			return modelService.getDto(entity, VendorDto.class);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public void delete(UUID uuid) throws BusinessException {
		vendorService.deleteByUuid(uuid);
	}

	@Override
	public Page<VendorDto> findPage(DataTableRequest dataTableRequest) throws Exception {
		Page<Vendor> page = vendorService.findEntityPage(dataTableRequest, VendorSpecification.getSpecification(dataTableRequest));

		List<VendorDto> dtos = modelService.getDto(page.getContent(), VendorDto.class);
		return new PageImpl<VendorDto>(dtos, page.getPageable(), page.getTotalElements());
	}

	@Override
	public int count() throws Exception {
		return vendorService.count();
	}

	@Override
	public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception {

		List<Object[]> revisions = vendorService.findHistory(dataTableRequest);
		for (int i = 0; i < revisions.size(); i++) {
			Object[] entityObject = revisions.get(i);
			if (entityObject[0] instanceof Vendor) {

				entityObject[0] = modelService.getDto(entityObject[0], VendorDto.class);
			}
			revisions.set(i, entityObject);
		}

		return revisions;
	}

	@Override
	public int countHistory(DataTableRequest dataTableRequest) throws Exception {
		return vendorService.findCountHistory(dataTableRequest);
	}

	@Override
	public VendorDto createDto() throws Exception {

		return modelService.getDto(vendorService.create(), VendorDto.class);
	}
	
	@Override
	public VendorDto saveProfile(VendorDto dto) throws BusinessException {

		try {
			if (dto.getProfilePicture() != null && dto.getProfilePicture().isEmpty() == false) {
				String mimetype = dto.getProfilePicture().getContentType();
				String type = mimetype.split("/")[0];
				if (type.equals("image") == false) {
					throw new Exception("Wrong picture format");
				}
			}
			Vendor entity = entityVendorProfileConverter.convert(dto);

			entity = (Vendor) vendorService.saveEntity(entity);
			vendorService.updatePrincipal(entity);
			vendorService.saveProfilePicture(entity, dto.getProfilePicture());

			return modelService.getDto(entity, VendorDto.class);

		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public VendorDto getCurrentUser() throws Exception {
		Vendor vendor = vendorService.getCurrentUser();

		return modelService.getDto(vendorService.findOneEntityByUuid(vendor.getUuid()), VendorDto.class);
	}

}
