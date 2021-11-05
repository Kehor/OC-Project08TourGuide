package tourGuide.Dto;

import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;

public class UserRewardDto {
    public VisitedLocationDto visitedLocation;
    public AttractionDto attraction;
    private int rewardPoints;

    public UserRewardDto() {
    }
    public UserRewardDto(VisitedLocationDto visitedLocation, AttractionDto attraction, int rewardPoints) {
        this.visitedLocation = visitedLocation;
        this.attraction = attraction;
        this.rewardPoints = rewardPoints;
    }

    public UserRewardDto(VisitedLocationDto visitedLocation, AttractionDto attraction) {
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
