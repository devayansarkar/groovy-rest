package com.rocksoft.groovy

import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.FileSystemResource
import spock.lang.Specification

class ScoreRepositorySpec extends Specification {

  ScoreRepository repo = new ScoreRepository()

  def setup() {
    repo.scoreFile = new FileSystemResource(new File("src/test/resources/scores.json"))
  }

  def "Loads scores by country"() {
    setup:
    repo.load()

    when:
    List<GameResult> scores = repo.byCountry("BRA")

    then:
    scores.size() == 1
    scores.first().score.BRA == 3
    scores.first().score.CRO == 1
    scores.first().gameDate == Date.parse("MM-dd-yyyy", "06-12-2014")
    scores.first().description == "Group A"
  }

  def "Loads scores by date"() {
    setup:
    repo.load()

    when:
    List<GameResult> scores = repo.byDate(Date.parse("MM-dd-yyyy", "06-12-2014"))

    then:
    scores.size() == 2

    scores.first().score.BRA == 3
    scores.first().score.CRO == 1
    scores.first().gameDate == Date.parse("MM-dd-yyyy", "06-12-2014")
    scores.first().description == "Group A"

    scores.last().score.NED == 1000
    scores.last().score.GER == 0
    scores.last().gameDate == Date.parse("MM-dd-yyyy", "06-12-2014")
    scores.last().description == "Group Z"
  }

  def "Loads scores by round"() {
    setup:
    repo.load()

    when:
    List<GameResult> scores = repo.byRound("Group A")

    then:
    scores.size() == 3

    scores[0].score.BRA == 3
    scores[0].score.CRO == 1
    scores[0].gameDate == Date.parse("MM-dd-yyyy", "06-12-2014")
    scores[0].description == "Group A"

    scores[1].score.MEX == 1
    scores[1].score.CMR == 0
    scores[1].gameDate == Date.parse("MM-dd-yyyy", "06-13-2014")
    scores[1].description == "Group A"

    scores[2].score.FOO == 8
    scores[2].score.BAR == 5
    scores[2].gameDate == Date.parse("MM-dd-yyyy", "06-13-2014")
    scores[2].description == "Group A"
  }

}
