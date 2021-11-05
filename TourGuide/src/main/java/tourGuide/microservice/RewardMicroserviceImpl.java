package tourGuide.microservice;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import gpsUtil.location.VisitedLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tourGuide.Dto.UserRewardDto;
import tourGuide.proxy.RewardProxy;
import tourGuide.service.RewardsService;
import tourGuide.user.UserReward;

import java.io.IOException;
import java.util.List;
import java.util.UUID;


public class RewardMicroserviceImpl implements RewardMicroservice{
    @Autowired
    private RewardProxy rewardProxy;
    private Logger logger = LoggerFactory.getLogger(RewardMicroserviceImpl.class);

    @Override
    public List<UserReward> getuserReward(UUID userID, String jsonUserReward, String jsonVisitedLocations) {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = rewardProxy.getuserReward(userID.toString(), jsonUserReward, jsonVisitedLocations);
        List<UserReward> userRewards = null;
        try {
            userRewards = objectMapper.readValue(json, new TypeReference<List<UserRewardDto>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userRewards;
    }

    @Override
    public String setproximitybuffer(int proximityBuffer){
        return rewardProxy.setproximitybuffer(proximityBuffer);
    }
}
