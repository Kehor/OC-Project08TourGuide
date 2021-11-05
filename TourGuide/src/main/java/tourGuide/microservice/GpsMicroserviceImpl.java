package tourGuide.microservice;

import gpsUtil.location.Attraction;
import gpsUtil.location.Location;
import gpsUtil.location.VisitedLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import tourGuide.Dto.AttractionDto;
import tourGuide.Dto.VisitedLocationDto;
import tourGuide.proxy.GpsProxy;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GpsMicroserviceImpl implements GpsMicroservice{
    @Autowired
    private GpsProxy gpsProxy;

    @Override
    public List<Attraction> getAttractions(){

        List<Attraction> attractions = new ArrayList<>();
        List<AttractionDto> attractionDTOList = gpsProxy.gpsGetAttractions();
        for (AttractionDto attr : attractionDTOList) {
            Attraction attraction = new Attraction(attr.getAttractionName(), attr.getCity(), attr.getState(), attr.getLatitude(), attr.getLongitude());
            attractions.add(attraction);
        }
        return attractions;

    }
    @Override
    public VisitedLocation getUserLocation(UUID userID) {
        VisitedLocationDto visitedLocationDto = gpsProxy.gpsGetUserLocation(userID.toString());
        Location location = new Location(visitedLocationDto.location.latitude, visitedLocationDto.location.longitude);
        VisitedLocation visitedLocation = new VisitedLocation(userID,location,visitedLocationDto.timeVisited);
        return visitedLocation;
    }
}
