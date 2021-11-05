package tourGuide.proxy;

import gpsUtil.location.VisitedLocation;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tourGuide.Dto.AttractionDto;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import tourGuide.Dto.VisitedLocationDto;

@Component
@FeignClient(name = "microservice-GPS", url = "http://localhost:8081")
public interface GpsProxy {

    @GetMapping(value = "/getattractions")
    List<AttractionDto> gpsGetAttractions();

    @GetMapping(value = "/getuserlocation")
    VisitedLocationDto gpsGetUserLocation(@RequestParam String userId);
}
