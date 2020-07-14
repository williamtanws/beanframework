package com.beanframework.core.converter.dto;
//package com.beanframework.core.converter;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.flowable.task.api.Task;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.beanframework.common.context.DtoConverterContext;
//import com.beanframework.common.converter.DtoConverter;
//import com.beanframework.common.exception.ConverterException;
//import com.beanframework.core.data.TaskDto;
//
//public class DtoTaskConverter implements DtoConverter<Task, TaskDto> {
//
//	protected static Logger LOGGER = LoggerFactory.getLogger(DtoTaskConverter.class);
//
//	@Override
//	public TaskDto convert(Task source, DtoConverterContext context) throws ConverterException {
//		return convert(source, new TaskDto(), context);
//	}
//
//	public List<TaskDto> convert(List<Task> sources, DtoConverterContext context) throws ConverterException {
//		List<TaskDto> convertedList = new ArrayList<TaskDto>();
//		try {
//			for (Task source : sources) {
//				convertedList.add(convert(source, context));
//			}
//		} catch (ConverterException e) {
//			throw new ConverterException(e.getMessage(), e);
//		}
//		return convertedList;
//	}
//
//	private TaskDto convert(Task source, TaskDto prototype, DtoConverterContext context) throws ConverterException {
//		try {
//			prototype.setId(source.getId());
//			prototype.setId(source.getId());
//			prototype.setName(source.getName());
//
//		} catch (Exception e) {
//			LOGGER.error(e.getMessage(), e);
//			throw new ConverterException(e.getMessage(), e);
//		}
//
//		return prototype;
//	}
//
//}
