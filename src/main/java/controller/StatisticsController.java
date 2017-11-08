package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import model.Statistic;
import service.StatisticsService;



@RestController
@RequestMapping("/n26/api")
public class StatisticsController {

	@Autowired
	StatisticsService statisticService;

	@RequestMapping(value = "/statistics", method = RequestMethod.GET)
	public Statistic displayStatistics() {
		return  statisticService.getStatistics();
	}

}
