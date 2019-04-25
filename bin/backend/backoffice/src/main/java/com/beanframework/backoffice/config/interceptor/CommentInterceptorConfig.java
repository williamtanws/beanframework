package com.beanframework.backoffice.config.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.comment.domain.Comment;
import com.beanframework.comment.interceptor.CommentInitialDefaultsInterceptor;
import com.beanframework.comment.interceptor.CommentLoadInterceptor;
import com.beanframework.comment.interceptor.CommentPrepareInterceptor;
import com.beanframework.comment.interceptor.CommentRemoveInterceptor;
import com.beanframework.comment.interceptor.CommentValidateInterceptor;
import com.beanframework.comment.interceptor.relationship.CommentUserRelationshipRemoveInterceptor;
import com.beanframework.common.interceptor.InterceptorMapping;
import com.beanframework.customer.domain.Customer;
import com.beanframework.employee.domain.Employee;
import com.beanframework.user.domain.User;
import com.beanframework.vendor.domain.Vendor;

@Configuration
public class CommentInterceptorConfig {

	//////////////////////////////////
	// Initial Defaults Interceptor //
	//////////////////////////////////

	@Bean
	public CommentInitialDefaultsInterceptor commentInitialDefaultsInterceptor() {
		return new CommentInitialDefaultsInterceptor();
	}

	@Bean
	public InterceptorMapping commentInitialDefaultsInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(commentInitialDefaultsInterceptor());
		mapping.setTypeCode(Comment.class.getSimpleName());

		return mapping;
	}

	//////////////////////
	// Load Interceptor //
	//////////////////////

	@Bean
	public CommentLoadInterceptor commentLoadInterceptor() {
		return new CommentLoadInterceptor();
	}

	@Bean
	public InterceptorMapping commentLoadInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(commentLoadInterceptor());
		mapping.setTypeCode(Comment.class.getSimpleName());

		return mapping;
	}

	/////////////////////////
	// Prepare Interceptor //
	/////////////////////////

	@Bean
	public CommentPrepareInterceptor commentPrepareInterceptor() {
		return new CommentPrepareInterceptor();
	}

	@Bean
	public InterceptorMapping commentPrepareInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(commentPrepareInterceptor());
		mapping.setTypeCode(Comment.class.getSimpleName());

		return mapping;
	}

	//////////////////////////
	// Validate Interceptor //
	//////////////////////////

	@Bean
	public CommentValidateInterceptor commentValidateInterceptor() {
		return new CommentValidateInterceptor();
	}

	@Bean
	public InterceptorMapping commentValidateInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(commentValidateInterceptor());
		mapping.setTypeCode(Comment.class.getSimpleName());

		return mapping;
	}

	////////////////////////
	// Remove Interceptor //
	////////////////////////

	@Bean
	public CommentRemoveInterceptor commentRemoveInterceptor() {
		return new CommentRemoveInterceptor();
	}

	@Bean
	public InterceptorMapping commentRemoveInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(commentRemoveInterceptor());
		mapping.setTypeCode(Comment.class.getSimpleName());

		return mapping;
	}

	//////////////////////////
	// User Interceptor //
	//////////////////////////

	@Bean
	public CommentUserRelationshipRemoveInterceptor commentUserRemoveInterceptor() {
		return new CommentUserRelationshipRemoveInterceptor();
	}

	@Bean
	public InterceptorMapping commentUserRemoveInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(commentUserRemoveInterceptor());
		mapping.setTypeCode(User.class.getSimpleName());
		mapping.setOrder(Integer.MIN_VALUE);

		return mapping;
	}

	@Bean
	public CommentUserRelationshipRemoveInterceptor commentEmployeeRemoveInterceptor() {
		return new CommentUserRelationshipRemoveInterceptor();
	}

	@Bean
	public InterceptorMapping commentEmployeeRemoveInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(commentEmployeeRemoveInterceptor());
		mapping.setTypeCode(Employee.class.getSimpleName());
		mapping.setOrder(Integer.MIN_VALUE);

		return mapping;
	}

	@Bean
	public CommentUserRelationshipRemoveInterceptor commentCustomerRemoveInterceptor() {
		return new CommentUserRelationshipRemoveInterceptor();
	}

	@Bean
	public InterceptorMapping commentCustomerRemoveInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(commentEmployeeRemoveInterceptor());
		mapping.setTypeCode(Customer.class.getSimpleName());
		mapping.setOrder(Integer.MIN_VALUE);

		return mapping;
	}

	@Bean
	public CommentUserRelationshipRemoveInterceptor commentVendorRelationshipRemoveInterceptor() {
		return new CommentUserRelationshipRemoveInterceptor();
	}

	@Bean
	public InterceptorMapping commentVendorRelationshipRemoveInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(commentVendorRelationshipRemoveInterceptor());
		mapping.setTypeCode(Vendor.class.getSimpleName());
		mapping.setOrder(Integer.MIN_VALUE);

		return mapping;
	}
}
