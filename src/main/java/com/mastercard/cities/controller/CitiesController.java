package com.mastercard.cities.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mastercard.cities.service.CitiesService;

//standard template for creating a spring controller.
//respective api calls will call cities service model to implement core logic
@RestController
public class CitiesController{

	@Autowired
	private CitiesService citiesService;

	@RequestMapping(value = "/connected")
	public String getConnections(@RequestParam("origin") String origin, @RequestParam("destination") String destination) {
		return citiesService.getConnections(origin, destination);
	}
}
