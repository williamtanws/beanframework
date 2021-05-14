package com.beanframework.console.web;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.beanframework.console.EnvironmentWebConstants;

@Controller
public class EnvironmentController {

  @Autowired
  private Environment env;

  @Value(EnvironmentWebConstants.View.ENVIRONMENT)
  private String VIEW_ENVIRONMENT;

  @SuppressWarnings("rawtypes")
  @RequestMapping(EnvironmentWebConstants.Path.ENVIRONMENT)
  public String envs(Model model) throws IOException {

    Map<String, Object> envList = new LinkedHashMap<>();
    envList.put("Active profiles", Arrays.toString(env.getActiveProfiles()));

    final MutablePropertySources sources = ((AbstractEnvironment) env).getPropertySources();
    StreamSupport.stream(sources.spliterator(), false)
        .filter(ps -> ps instanceof EnumerablePropertySource)
        .map(ps -> ((EnumerablePropertySource) ps).getPropertyNames()).flatMap(Arrays::stream)
        .sorted().distinct()
        // .filter(prop -> !(prop.contains("credentials") || prop.contains("password")))
        .forEach(prop -> envList.put(prop, env.getProperty(prop)));

    model.addAttribute("envList", envList);

    return VIEW_ENVIRONMENT;
  }
}
