package RewardApi;

import RewardApi.microservice.GpsMicroservice;
import RewardApi.microservice.GpsMicroserviceImpl;
import gpsUtil.GpsUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import rewardCentral.RewardCentral;

@Configuration
public class RewardApiModule {

	@Bean
	public GpsUtil getGpsUtil() {
		return new GpsUtil();
	}

	@Bean
	public GpsMicroservice getGpsMicroservice() {return new GpsMicroserviceImpl();}

	@Bean
	public RewardCentral getRewardCentral() {
		return new RewardCentral();
	}
	
}
