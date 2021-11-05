package tourGuide;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import gpsUtil.GpsUtil;
import rewardCentral.RewardCentral;
import tourGuide.microservice.GpsMicroservice;
import tourGuide.microservice.GpsMicroserviceImpl;
import tourGuide.microservice.RewardMicroservice;
import tourGuide.microservice.RewardMicroserviceImpl;
import tourGuide.service.RewardsService;

@Configuration
public class TourGuideModule {

	@Bean
	public GpsUtil getGpsUtil() {return new GpsUtil();}

	@Bean
	public GpsMicroservice getGpsMicroservice() {return new GpsMicroserviceImpl();}

	@Bean
	public RewardMicroservice getRewardMicroservice() {return new RewardMicroserviceImpl();}

	@Bean
	public RewardsService getRewardsService() { return new RewardsService(getRewardCentral(),getRewardMicroservice()); }
	
	@Bean
	public RewardCentral getRewardCentral() { return new RewardCentral(); }
	
}
