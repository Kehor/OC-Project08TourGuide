package RewardApi.service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import RewardApi.Dto.AttractionDto;
import RewardApi.Dto.LocationDto;
import RewardApi.Dto.UserRewardDto;
import RewardApi.Dto.VisitedLocationDto;
import RewardApi.microservice.GpsMicroservice;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import gpsUtil.location.Attraction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import gpsUtil.location.Location;
import rewardCentral.RewardCentral;
import RewardApi.entity.UserReward;

@Service
public class RewardsService {
    private static final double STATUTE_MILES_PER_NAUTICAL_MILE = 1.15077945;
	private Logger logger = LoggerFactory.getLogger(RewardsService.class);
	// proximity in miles
    private int defaultProximityBuffer = 5000;
	private int proximityBuffer = defaultProximityBuffer;
	private final RewardCentral rewardsCentral;
	private final GpsMicroservice gpsMicroservice;
	private List<AttractionDto> allAttractions;

	public RewardsService(GpsMicroservice gpsMicroservice, RewardCentral rewardCentral) {
		this.gpsMicroservice = gpsMicroservice;
		this.rewardsCentral = rewardCentral;
		this.allAttractions = gpsMicroservice.gpsAttractions();
	}

	public void updateAllAttractions(){
		this.allAttractions = gpsMicroservice.gpsAttractions();
	}

	public String setProximityBuffer(int proximityBuffer) {
		this.proximityBuffer = proximityBuffer;
		return "proximityBuffer : "+ this.proximityBuffer;
	}

	public String calculateRewards(UUID userId,String jsonUserReward,String jsonVisitedLocations){
		logger.debug("calculateRewards - thead : "+Thread.currentThread().getName());
		ObjectMapper objectMapper = new ObjectMapper();
		List<VisitedLocationDto> visitedLocations = null;
		List<UserRewardDto> userRewards = null;
		try {
			visitedLocations = objectMapper.readValue(jsonVisitedLocations, new TypeReference<List<VisitedLocationDto>>(){});
			userRewards = objectMapper.readValue(jsonUserReward, new TypeReference<List<UserRewardDto>>(){});
		} catch (IOException e) {
			e.printStackTrace();
		}
		for(VisitedLocationDto visitedLocation : visitedLocations) {
			for(AttractionDto attraction : allAttractions) {
				if(userRewards.stream().filter(r -> r.attraction.attractionName.equals(attraction.attractionName)).count() == 0) {
					if(nearAttraction(visitedLocation, attraction)) {
						userRewards.add(new UserRewardDto(visitedLocation, attraction, getRewardPoints(attraction, userId)));

					}
				}
			}
		}
		String jsonUserRewards = null;
		try {
			jsonUserRewards = objectMapper.writeValueAsString(userRewards);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return jsonUserRewards;
	}

	private boolean nearAttraction(VisitedLocationDto visitedLocation, AttractionDto attraction) {
		return getDistance(new Location(attraction.latitude,attraction.longitude), visitedLocation.location) > proximityBuffer ? false : true;
	}

	private int getRewardPoints(AttractionDto attraction, UUID userId) {
		return rewardsCentral.getAttractionRewardPoints(attraction.attractionId, userId);
	}

	public double getDistance(Location loc1, LocationDto loc2) {
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
