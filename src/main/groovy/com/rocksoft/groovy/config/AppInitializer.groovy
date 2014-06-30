package com.rocksoft.groovy.config

import org.springframework.web.WebApplicationInitializer
import org.springframework.web.context.ContextLoaderListener
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext
import org.springframework.web.servlet.DispatcherServlet

import javax.servlet.ServletContext
import javax.servlet.ServletException
import javax.servlet.ServletRegistration

class AppInitializer implements WebApplicationInitializer {
  private static final String CONFIG_LOCATION = "com.rocksoft.groovy.config"
  private static final String MAPPING_URL = "/*"

  @Override
  public void onStartup(ServletContext servletContext) throws ServletException {
    WebApplicationContext context = new AnnotationConfigWebApplicationContext(configLocation: CONFIG_LOCATION)
    servletContext.addListener(new ContextLoaderListener(context))
    ServletRegistration.Dynamic dispatcher = servletContext.addServlet("DispatcherServlet", new DispatcherServlet(context))
    dispatcher.loadOnStartup = 1
    dispatcher.addMapping(MAPPING_URL)
  }
}
