package com.rocksoft.groovy.integration

import groovy.json.JsonSlurper
import spock.lang.Specification

class ScoreApiIntegrationSpec extends Specification {

  def "Gets response by team"() {
    when:
    String response = new URL("http://localhost:8088/scores/team/NED").openStream().text
    List parsedResponse = new JsonSlurper().parseText(response)

    then:
    parsedResponse.first().score.NED
  }

  def "Gets response by date"() {
    when:
    String response = new URL("http://localhost:8088/scores/date/2014-06-29").openStream().text
    List parsedResponse = new JsonSlurper().parseText(response)

    then:
    parsedResponse.first().gameDate.contains("2014-06-29")
  }

  def "Gets response by round"() {
    when:
    String response = new URL("http://localhost:8088/scores/round/Group%20B").openStream().text
    List parsedResponse = new JsonSlurper().parseText(response)

    then:
    parsedResponse.first().description.contains("Group B")
  }
}
