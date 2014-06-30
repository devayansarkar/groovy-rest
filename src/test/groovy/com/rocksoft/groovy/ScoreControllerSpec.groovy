package com.rocksoft.groovy

import groovy.json.JsonSlurper
import spock.lang.Specification

class ScoreControllerSpec extends Specification {

  ScoreController controller

  def setup() {
    controller = new ScoreController(scoreRepository: Mock(ScoreRepository))
  }

  def "Get by date calls repo and renders JSON"() {
    setup:
    String dateText = "2014-06-14"
    Date date = Date.parse("yyyy-MM-dd", dateText)

    when:
    List<Map> response = new JsonSlurper().parseText(controller.scoreByDate("2014-06-14"))

    then:
    1 * controller.scoreRepository.byDate(date) >> [new GameResult(gameDate: date, description: "foobar", score: [FOO: 6, BAR: 66])]
    response.first().score == [FOO: 6, BAR: 66]
    response.first().description == "foobar"
    response.first().gameDate.contains("2014-06-14")
  }

  def "Get by round calls repo and renders JSON"() {
    when:
    List<Map> response = new JsonSlurper().parseText(controller.scoreByRound("Round"))

    then:
    1 * controller.scoreRepository.byRound("Round") >> [new GameResult(gameDate: Date.parse("yyyy-MM-dd", "2014-06-15"), description: "foobaz", score: [FOO: 7, BAR: 77])]
    response.first().score == [FOO: 7, BAR: 77]
    response.first().description == "foobaz"
    response.first().gameDate.contains("2014-06-15")
  }

  def "Get by team calls repo and renders JSON"() {
    when:
    List<Map> response = new JsonSlurper().parseText(controller.scoreByTeam("FOOBAR"))

    then:
    1 * controller.scoreRepository.byCountry("FOOBAR") >> [new GameResult(gameDate: Date.parse("yyyy-MM-dd", "2014-06-16"), description: "foobat", score: [FOO: 8, BAR: 88])]
    response.first().score == [FOO: 8, BAR: 88]
    response.first().description == "foobat"
    response.first().gameDate.contains("2014-06-16")
  }
}