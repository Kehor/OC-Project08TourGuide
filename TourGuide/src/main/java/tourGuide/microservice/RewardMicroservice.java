package tourGuide.microservice;

import tourGuide.user.UserReward;
import java.util.List;
import java.util.UUID;


public interface RewardMicroservice {

    public List<UserReward> getuserReward(UUID userID, String jsonUserReward, String jsonVisitedLocations);
    public String setproximitybuffer(int proximityBuffer);
}
