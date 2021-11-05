package GpsApi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import GpsApi.Dto.AttractionDto;
import GpsApi.Dto.LocationDto;
import GpsApi.Dto.VisitedLocationDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import gpsUtil.GpsUtil;
import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;

@Service
public class GpsService {
	private Logger logger = LoggerFactory.getLogger(GpsService.class);
	private final GpsUtil gpsUtil;
	private List<Attraction> allAttractions;

	public GpsService(GpsUtil gpsUtil) {
		Locale.setDefault(Locale.US);
		this.gpsUtil = gpsUtil;
		this.allAttractions = gpsUtil.getAttractions();
	}
	public void updateAllAttractions(){
		this.allAttractions = gpsUtil.getAttractions();
	}


	public VisitedLocationDto getUserLocation(UUID userId) {
		logger.debug("getUserLocation - thead : "+Thread.currentThread().getName());
		VisitedLocation visitedLocation = gpsUtil.getUserLocation(userId);
		LocationDto locationDto = new LocationDto(visitedLocation.location.latitude,visitedLocation.location.longitude);
		VisitedLocationDto visitedLocationDto = new VisitedLocationDto(userId,locationDto,visitedLocation.timeVisited);
		logger.debug("getUserLocation end : ");
		return visitedLocationDto;
	}

	public List<AttractionDto> getAttractions() {
		logger.debug("getAttractions - thead : "+Thread.currentThread().getName());
		List<AttractionDto> attractionDtoList = new ArrayList<>();
		for(Attraction attraction : allAttractions) {
			AttractionDto attractionDto = new AttractionDto(attraction.attractionName,attraction.city,attraction.state,attraction.attractionId,attraction.longitude,attraction.latitude);
			attractionDtoList.add(attractionDto);
		}
		return attractionDtoList;
	}
	
}
