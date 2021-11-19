package GpsApi;

import java.util.List;
import java.util.UUID;

import GpsApi.Dto.AttractionDto;
import GpsApi.Dto.VisitedLocationDto;
import gpsUtil.location.VisitedLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import GpsApi.service.GpsService;

@RestController
public class GpsController {

    private Logger logger = LoggerFactory.getLogger(GpsController.class);

	@Autowired
    GpsService gpsService;

    @GetMapping("/")
    public String gpsHome() {
        logger.info("GPSController : gpsHome");
        return  "gpsApi";
    }

    @GetMapping("/updateAttractionsList")
    public String updateAttractionsList() {
        logger.info("updateAttractionsList");
        gpsService.updateAllAttractions();
        return  "done";
    }

    @GetMapping("/getattractions")
    public List<AttractionDto> getAttractions() {
        logger.info("GPSController : getattractions");
        Thread.currentThread().interrupt();
        return gpsService.getAttractions();
    }


    @GetMapping("/getuserlocation")
    public VisitedLocationDto getUserLocation(@RequestParam String userId) {
        logger.info("GPSController : getuserlocation");
        if (userId.isEmpty()) {
            logger.error("GPSController : getuserlocation => userId is mandatory");
        }
        VisitedLocationDto visitedLocationDto = gpsService.getUserLocation(UUID.fromString(userId));
        Thread.currentThread().interrupt();
        return visitedLocationDto;
    }

}