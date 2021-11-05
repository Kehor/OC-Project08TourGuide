package RewardApi.proxy;

import gpsUtil.location.VisitedLocation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import RewardApi.Dto.AttractionDto;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "microservice-GPS", url = "http://localhost:8081")
public interface GpsProxy {
    @GetMapping(value = "/getattractions")
    List<AttractionDto> gpsGetAttractions();

}
