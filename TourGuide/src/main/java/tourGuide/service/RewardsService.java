package tourGuide.service;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import gpsUtil.GpsUtil;
import gpsUtil.location.Attraction;
import gpsUtil.location.Location;
import gpsUtil.location.VisitedLocation;
import rewardCentral.RewardCentral;
import tourGuide.microservice.RewardMicroservice;
import tourGuide.user.User;
import tourGuide.user.UserReward;

@Service
public class RewardsService {
    private static final double STATUTE_MILES_PER_NAUTICAL_MILE = 1.15077945;
	private Logger logger = LoggerFactory.getLogger(RewardsService.class);
	// proximity in miles
	private int attractionProximityRange = 10000;
	private final RewardCentral rewardsCentral;
	private final RewardMicroservice rewardMicroservice;
	private ExecutorService executor = Executors.newFixedThreadPool(400);
	
	public RewardsService(RewardCentral rewardCentral, RewardMicroservice rewardMicroservice) {
		this.rewardsCentral = rewardCentral;
		this.rewardMicroservice = rewardMicroservice;
	}

	public Future<List<UserReward>> calculateRewards(User user) {
		return executor.submit(() -> {
		logger.debug("CalculateRewards - thread : "+Thread.currentThread().getName());
		ObjectMapper Obj = new ObjectMapper();
		String jsonVisitedLocations = null;
		String jsonUserReward = null;
		try {
			jsonVisitedLocations = Obj.writeValueAsString(user.getVisitedLocations());
			jsonUserReward = Obj.writeValueAsString(user.getUserRewards());

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		List<UserReward> userRewards = ((userRewards =rewardMicroservice.getuserReward(user.getUserId(),jsonUserReward,jsonVisitedLocations)) != null ? userRewards : Collections.<UserReward>emptyList());
		user.setUserRewards(userRewards);
			return userRewards;
		});
	}
	
	public boolean isWithinAttractionProximity(Attraction attraction, Location location) {
		return getDistance(attraction, location) > attractionProximityRange ? false : true;
	}

	public double getDistance(Location loc1, Location loc2) {
        double lat1 = Math.toRadians(loc1.latitude);
        double lon1 = Math.toRadians(loc1.longitude);
        double lat2 = Math.toRadians(loc2.latitude);
        double lon2 = Math.toRadians(loc2.longitude);

        double angle = Math.acos(Math.sin(lat1) * Math.sin(lat2)
                               + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));

        double nauticalMiles = 60 * Math.toDegrees(angle);
        double statuteMiles = STATUTE_MILES_PER_NAUTICAL_MILE * nauticalMiles;
        return statuteMiles;
	}

}
