package tourGuide;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import gpsUtil.location.Location;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import gpsUtil.GpsUtil;
import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import rewardCentral.RewardCentral;
import tourGuide.helper.InternalTestHelper;
import tourGuide.microservice.GpsMicroservice;
import tourGuide.microservice.GpsMicroserviceImpl;
import tourGuide.microservice.RewardMicroservice;
import tourGuide.microservice.RewardMicroserviceImpl;
import tourGuide.service.RewardsService;
import tourGuide.service.TourGuideService;
import tourGuide.user.User;
import tourGuide.user.UserReward;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestRewardsService {

	@Autowired
	private GpsMicroservice gpsMicroservice;
	@Autowired
	private RewardMicroservice rewardMicroservice;
	private RewardsService rewardsService;
	private int defaultProximityBuffer = 10;


	@Before
	public void init() {
		Locale.setDefault(Locale.US);
		this.rewardsService = new RewardsService(new RewardCentral(), rewardMicroservice);
		rewardMicroservice.setproximitybuffer(defaultProximityBuffer);
	}

	@Test
	public void userGetRewards() throws ExecutionException, InterruptedException {
		InternalTestHelper.setInternalUserNumber(0);
		TourGuideService tourGuideService = new TourGuideService(gpsMicroservice, rewardsService);
		
		User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
		Attraction attraction = gpsMicroservice.getAttractions().get(0);
		Location attractionLocation = new Location(attraction.latitude,attraction.longitude);
		user.addToVisitedLocations(new VisitedLocation(user.getUserId(), attractionLocation, new Date()));
		tourGuideService.trackUserLocation(user).get();
		List<UserReward> userRewards = user.getUserRewards();
		tourGuideService.tracker.stopTracking();
		assertTrue(userRewards.size() == 1);
	}
	
	@Test
	public void isWithinAttractionProximity() {
		Attraction attraction = gpsMicroservice.getAttractions().get(0);
		assertTrue(rewardsService.isWithinAttractionProximity(attraction, attraction));
	}
	

	@Test
	public void nearAllAttractions() throws ExecutionException, InterruptedException {
		rewardMicroservice.setproximitybuffer(Integer.MAX_VALUE);

		InternalTestHelper.setInternalUserNumber(1);
		TourGuideService tourGuideService = new TourGuideService(gpsMicroservice, rewardsService);
		
		rewardsService.calculateRewards(tourGuideService.getAllUsers().get(0)).get();
		List<UserReward> userRewards = tourGuideService.getUserRewards(tourGuideService.getAllUsers().get(0));
		tourGuideService.tracker.stopTracking();

		assertEquals(gpsMicroservice.getAttractions().size(), userRewards.size());
	}
	
}
