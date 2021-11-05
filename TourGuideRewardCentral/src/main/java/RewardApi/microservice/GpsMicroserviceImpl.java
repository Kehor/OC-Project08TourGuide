package RewardApi.microservice;

import RewardApi.Dto.AttractionDto;
import RewardApi.proxy.GpsProxy;
import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GpsMicroserviceImpl implements GpsMicroservice{
    @Autowired
    private GpsProxy gpsProxy;

    @Override
    public List<AttractionDto> gpsAttractions(){
    //public List<Attraction> gpsAttractions(){

        List<Attraction> attractions = new ArrayList<>();
        List<AttractionDto> attractionDTOList = gpsProxy.gpsGetAttractions();
        /*
        for (AttractionDto attr : attractionDTOList) {
            Attraction attraction = new Attraction(attr.getAttractionName(), attr.getCity(), attr.getState(), attr.getLatitude(), attr.getLongitude());
            attractions.add(attraction);
        }

        return attractions;

         */
        return attractionDTOList;
    }
}
