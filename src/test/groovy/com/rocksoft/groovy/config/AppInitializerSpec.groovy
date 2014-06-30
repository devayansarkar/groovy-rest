package com.rocksoft.groovy.config

import org.springframework.web.context.ContextLoaderListener
import org.springframework.web.servlet.DispatcherServlet
import spock.lang.Specification

import javax.servlet.ServletContext
import javax.servlet.ServletRegistration

class AppInitializerSpec extends Specification {
  AppInitializer appInitializer = new AppInitializer()

  def "Initializes context and configures servlet"() {
    setup:
    ServletContext mockServletContext = Mock()
    ServletRegistration.Dynamic mockRegistration = Mock()

    when:
    appInitializer.onStartup(mockServletContext)

    then:
    1 * mockServletContext.addListener(_ as ContextLoaderListener)
    1 * mockServletContext.addServlet("DispatcherServlet", _ as DispatcherServlet) >> mockRegistration
    1 * mockRegistration.setLoadOnStartup(1)
    1 * mockRegistration.addMapping(AppInitializer.MAPPING_URL)
  }
}
