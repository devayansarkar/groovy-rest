package com.rocksoft.groovy

import groovy.json.JsonSlurper
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Repository

import javax.annotation.PostConstruct

@Repository
class ScoreRepository {

  Map<String, List<Map>> data

  @Value('classpath:scores.json')
  Resource scoreFile

  @PostConstruct
  void load() {
    data = new JsonSlurper().parse(scoreFile.inputStream)
  }

  List<GameResult> byCountry(String countryCode) {
    data.collectMany { String date, List<Map> games ->
      games.findResults { Map game ->
        if (game.teams.find { it.country == countryCode}) {
          return transform(date, game)
        }
      }
    }
  }

  List<GameResult> byDate(Date date) {
    String formattedDate = date.format("yyyy-MM-dd")
    data[formattedDate].findResults { Map game ->
      return transform(formattedDate, game)
    }
  }

  List<GameResult> byRound(String round) {
    data.collectMany { String date, List<Map> games ->
      games.findResults { Map game ->
        if (game.round == round) {
          return transform(date, game)
        }
      }
    }
  }

  private GameResult transform(String date, Map game) {
    String country1 = game.teams[0].country
    String country2 = game.teams[1].country
    Integer score1 = game.teams[0].score
    Integer score2 = game.teams[1].score
    return new GameResult(gameDate: Date.parse("yyyy-MM-dd", date), description: game.round, score: [(country1): score1, (country2): score2])
  }
}
