package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.converter.entity.EntityVendorProfileConverter;
import com.beanframework.core.converter.populator.VendorBasicPopulator;
import com.beanframework.core.converter.populator.VendorFullPopulator;
import com.beanframework.core.converter.populator.history.VendorHistoryPopulator;
import com.beanframework.core.data.VendorDto;
import com.beanframework.user.service.UserService;
import com.beanframework.vendor.domain.Vendor;
import com.beanframework.vendor.service.VendorService;
import com.beanframework.vendor.specification.VendorSpecification;

@Component
public class VendorFacadeImpl implements VendorFacade {

	@Autowired
	private ModelService modelService;

	@Autowired
	private UserService userService;

	@Autowired
	private VendorService vendorService;

	@Autowired
	private EntityVendorProfileConverter entityVendorProfileConverter;

	@Autowired
	private VendorFullPopulator vendorFullPopulator;

	@Autowired
	private VendorBasicPopulator vendorBasicPopulator;

	@Autowired
	private VendorHistoryPopulator vendorHistoryPopulator;

	@Override
	public VendorDto findOneByUuid(UUID uuid) throws Exception {
		Vendor entity = modelService.findOneByUuid(uuid, Vendor.class);
		VendorDto dto = modelService.getDto(entity, VendorDto.class, new DtoConverterContext(vendorFullPopulator));

		return dto;
	}

	@Override
	public VendorDto findOneProperties(Map<String, Object> properties) throws Exception {
		Vendor entity = modelService.findOneByProperties(properties, Vendor.class);
		VendorDto dto = modelService.getDto(entity, VendorDto.class, new DtoConverterContext(vendorFullPopulator));

		return dto;
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
			if (dto.getProfilePicture() != null && dto.getProfilePicture().isEmpty() == Boolean.FALSE) {
				String mimetype = dto.getProfilePicture().getContentType();
				String type = mimetype.split("/")[0];
				if (type.equals("image") == Boolean.FALSE) {
					throw new Exception("Wrong picture format");
				}
			}

			Vendor entity = modelService.getEntity(dto, Vendor.class);
			entity = modelService.saveEntity(entity, Vendor.class);

			userService.saveProfilePicture(entity, dto.getProfilePicture());

			return modelService.getDto(entity, VendorDto.class, new DtoConverterContext(vendorFullPopulator));
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public void delete(UUID uuid) throws BusinessException {
		modelService.deleteByUuid(uuid, Vendor.class);
	}

	@Override
	public Page<VendorDto> findPage(DataTableRequest dataTableRequest) throws Exception {
		Page<Vendor> page = modelService.findPage(VendorSpecification.getSpecification(dataTableRequest), dataTableRequest.getPageable(), Vendor.class);

		List<VendorDto> dtos = modelService.getDto(page.getContent(), VendorDto.class, new DtoConverterContext(vendorBasicPopulator));
		return new PageImpl<VendorDto>(dtos, page.getPageable(), page.getTotalElements());
	}

	@Override
	public int count() throws Exception {
		return modelService.countAll(Vendor.class);
	}

	@Override
	public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception {

		List<Object[]> revisions = vendorService.findHistory(dataTableRequest);
		for (int i = 0; i < revisions.size(); i++) {
			Object[] entityObject = revisions.get(i);
			if (entityObject[0] instanceof Vendor) {

				entityObject[0] = modelService.getDto(entityObject[0], VendorDto.class, new DtoConverterContext(vendorHistoryPopulator));
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
		Vendor vendor = modelService.create(Vendor.class);
		return modelService.getDto(vendor, VendorDto.class, new DtoConverterContext(vendorFullPopulator));
	}

	@Override
	public VendorDto saveProfile(VendorDto dto) throws BusinessException {

		try {
			if (dto.getProfilePicture() != null && dto.getProfilePicture().isEmpty() == Boolean.FALSE) {
				String mimetype = dto.getProfilePicture().getContentType();
				String type = mimetype.split("/")[0];
				if (type.equals("image") == Boolean.FALSE) {
					throw new Exception("Wrong picture format");
				}
			}
			Vendor entity = entityVendorProfileConverter.convert(dto);

			entity = modelService.saveEntity(entity, Vendor.class);
			vendorService.updatePrincipal(entity);
			userService.saveProfilePicture(entity, dto.getProfilePicture());

			return modelService.getDto(entity, VendorDto.class, new DtoConverterContext(vendorFullPopulator));

		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public VendorDto getCurrentUser() throws Exception {
		Vendor entity = vendorService.getCurrentUser();

		return modelService.getDto(entity, VendorDto.class, new DtoConverterContext(vendorFullPopulator));
	}

}
