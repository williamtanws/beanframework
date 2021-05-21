package com.beanframework.api.v1;

import java.util.List;
import java.util.UUID;
import javax.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.beanframework.common.service.ModelService;

@RestController
@RequestMapping("/api/v1")
class UserApiResource {

  @Autowired
  private ModelService modelService;

  // Resource auth
  @RequestMapping(value = "/users", method = RequestMethod.GET)
  public List<User> users() {
    // String auth = (String) authentication.getUserAuthentication().getPrincipal();
    // String authority = authentication.getAuthorities().iterator().next().getAuthority();
    // if (authority.equals("api_user_read")) {
    return modelService.findAll(User.class);
    // } else if (authority.equals("api_employee_read")) {
    // return modelService.findAll(Employee.class);
    // } else if (authority.equals("api_customer_read")) {
    // return modelService.findAll(Customer.class);
    // } else if (authority.equals("api_vendor_read")) {
    // return modelService.findAll(Vendor.class);
    // }

    // return null;
  }

  // Oauth
  @RequestMapping(value = "/user", method = RequestMethod.GET)
  public User user(@PathParam("uuid") String uuid) throws Exception {
    // String auth = (String) authentication.getUserAuthentication().getPrincipal();
    // String authority = authentication.getAuthorities().iterator().next().getAuthority();
    // if (authority.equals("api_user_read")) {
    return modelService.findOneByUuid(UUID.fromString(uuid), User.class);
    // } else if (authority.equals("api_employee_read")) {
    // return modelService.findOneByUuid(UUID.fromString(uuid), Employee.class);
    // } else if (authority.equals("api_customer_read")) {
    // return modelService.findOneByUuid(UUID.fromString(uuid), Customer.class);
    // } else if (authority.equals("api_vendor_read")) {
    // return modelService.findOneByUuid(UUID.fromString(uuid), Vendor.class);
    // }

    // return null;
  }
}
