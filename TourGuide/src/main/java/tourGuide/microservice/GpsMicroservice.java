package tourGuide.microservice;

import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

public interface GpsMicroservice {

    public List<Attraction> getAttractions();

    public VisitedLocation getUserLocation(UUID userID);
}
