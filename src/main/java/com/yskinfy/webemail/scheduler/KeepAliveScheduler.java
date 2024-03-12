package com.yskinfy.webemail.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
public class KeepAliveScheduler {

	
	Logger logger=LoggerFactory.getLogger(getClass());
	@Scheduled(cron="0 */10 * * * *")
	public void keepAlive()
	{
		logger.info("KeepAlive Cron Scheduler");
	}
	
}
