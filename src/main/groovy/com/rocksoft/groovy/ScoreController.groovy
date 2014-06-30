package com.rocksoft.groovy

import groovy.json.JsonBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class ScoreController {

  @Autowired
  ScoreRepository scoreRepository

  @RequestMapping("/scores/date/{date}")
  @ResponseBody
  String scoreByDate(@PathVariable String date) {
    List<GameResult> results = scoreRepository.byDate(Date.parse("yyyy-MM-dd", date))
    return new JsonBuilder(results).toString()
  }

  @RequestMapping("/scores/team/{countryCode}")
  @ResponseBody
  String scoreByTeam(@PathVariable String countryCode) {
    List<GameResult> results = scoreRepository.byCountry(countryCode)
    return new JsonBuilder(results).toString()
  }

  @RequestMapping("/scores/round/{round}")
  @ResponseBody
  String scoreByRound(@PathVariable String round) {
    List<GameResult> results = scoreRepository.byRound(round)
    return new JsonBuilder(results).toString()
  }
}
