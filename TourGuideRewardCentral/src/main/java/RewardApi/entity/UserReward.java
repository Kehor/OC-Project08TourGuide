package RewardApi.entity;

import RewardApi.Dto.AttractionDto;
import RewardApi.Dto.VisitedLocationDto;
import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;

public class UserReward {

	public VisitedLocationDto visitedLocation;
	public AttractionDto attraction;
	private int rewardPoints;

	public UserReward() {
	}

	public UserReward(VisitedLocationDto visitedLocation, AttractionDto attraction, int rewardPoints) {
		this.visitedLocation = visitedLocation;
		this.attraction = attraction;
		this.rewardPoints = rewardPoints;
	}
	
	public UserReward(VisitedLocationDto visitedLocation, AttractionDto attraction) {
		this.visitedLocation = visitedLocation;
		this.attraction = attraction;
	}

	public void setRewardPoints(int rewardPoints) {
		this.rewardPoints = rewardPoints;
	}
	
	public int getRewardPoints() {
		return rewardPoints;
	}
	
}
