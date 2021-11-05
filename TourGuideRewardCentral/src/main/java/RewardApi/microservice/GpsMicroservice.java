package RewardApi.microservice;

import RewardApi.Dto.AttractionDto;
import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;

import java.util.List;
import java.util.UUID;

public interface GpsMicroservice {

    public List<AttractionDto> gpsAttractions();

}
