package com.beanframework.user.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.beanframework.user.domain.UserAuthority;

@Component
public class SaveUserAuthorityValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return UserAuthority.class == clazz;
	}

	@Override
	public void validate(Object target, Errors errors) {
		final UserAuthority userAuthority = (UserAuthority) target;

		if(userAuthority.getUserGroup() == null) {
			errors.reject("userGroup", "UserGroup model Required.");
		}
		if(userAuthority.getUserGroup().getUuid() == null) {
			errors.reject("userGroup.uuid", "UserGroup uuid Required.");
		}
		if(userAuthority.getUserPermission() == null) {
			errors.reject("userPermission", "UserPermission model Required.");
		}
		if(userAuthority.getUserPermission().getUuid() == null) {
			errors.reject("userPermission.uuid", "UserPermission uuid Required.");
		}
		if(userAuthority.getUserRight() == null) {
			errors.reject("userRight", "UserRight model Required.");
		}
		if(userAuthority.getUserRight().getUuid() == null) {
			errors.reject("userRight.uuid", "UserRight uuid Required.");
		}
	}

}
