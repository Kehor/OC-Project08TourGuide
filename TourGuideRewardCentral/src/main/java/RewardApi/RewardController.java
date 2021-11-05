package RewardApi;

import java.util.List;
import java.util.UUID;

import RewardApi.entity.UserReward;
import RewardApi.service.RewardsService;
import com.jsoniter.output.JsonStream;
import gpsUtil.location.Attraction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gpsUtil.location.VisitedLocation;

@RestController
public class RewardController {

    private Logger logger = LoggerFactory.getLogger(RewardController.class);

	@Autowired
    RewardsService rewardsService;

    @GetMapping("/")
    public String rewardHome() {

        logger.info("RewardController : rewardHome");

        return  "rewardApi";
    }

    @GetMapping("/updateAttractionsList")
    public String updateAttractionsList() {
        logger.info("updateAttractionsList");
        rewardsService.updateAllAttractions();
        return  "done";
    }

    @RequestMapping("/getuserreward")
    public String getuserreward(@RequestParam String userId,@RequestHeader String jsonUserReward,@RequestBody String jsonVisitedLocations) {
        logger.info("RewardController : getuserreward");
        if (userId.isEmpty() || jsonUserReward.isEmpty() || jsonVisitedLocations.isEmpty()) {
            logger.error("RewardController : getuserreward => userId, UserRewards and visitedLocations are mandatory");
        }
        Thread.currentThread().interrupt();
        return rewardsService.calculateRewards(UUID.fromString(userId),jsonUserReward,jsonVisitedLocations);
    }

    @RequestMapping("/setproximitybuffer")
    public String setproximitybuffer(@RequestParam int proximityBuffer) {
        logger.info("RewardController : setproximitybuffer");
        return rewardsService.setProximityBuffer(proximityBuffer);
    }
   

}