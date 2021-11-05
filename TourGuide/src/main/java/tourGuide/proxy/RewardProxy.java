package tourGuide.proxy;

import gpsUtil.location.VisitedLocation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import tourGuide.user.UserReward;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "microservice-Reward", url = "http://localhost:8083")
public interface RewardProxy {

    @GetMapping(value = "/getuserreward")
    String getuserReward(@RequestParam String userId, @RequestHeader String jsonUserReward, @RequestBody String jsonVisitedLocations);

    @GetMapping(value = "/setproximitybuffer")
    String setproximitybuffer(@RequestParam int proximityBuffer);
}
