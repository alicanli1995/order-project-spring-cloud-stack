package com.statistics.app.controller.api;

import com.statistics.app.controller.response.DailyResponse;
import com.statistics.app.service.StatisticService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

@RestController
@CrossOrigin
@RequestScope
@RequiredArgsConstructor
@RequestMapping("/statistic")
@Log4j2
public class StatisticController {

    private final StatisticService statisticService;

    @RequestMapping("/daily")
    public ResponseEntity<DailyResponse> getDailyStatistic() {
        log.info("getDailyStatistic");
        return new ResponseEntity(statisticService.getDailyStatistic(), HttpStatus.OK);
    }


}
