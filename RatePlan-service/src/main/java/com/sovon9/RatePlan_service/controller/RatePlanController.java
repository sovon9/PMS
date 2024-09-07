package com.sovon9.RatePlan_service.controller;

import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sovon9.RatePlan_service.model.RatePlan;
import com.sovon9.RatePlan_service.service.RatePlanService;

@RequestMapping("/rateplan")
@RestController
public class RatePlanController {

	@Autowired
	private JobLauncher jobLauncher;
	@Autowired
	private Job job;
	@Autowired
	private RatePlanService ratePlanService;
	
	@PostMapping("/download")
	public void downloadRatePlan()
	{
		JobParameters jobParameters = new JobParametersBuilder()
				.addLong("time", System.currentTimeMillis())
				.toJobParameters();
		try {
			jobLauncher.run(job, jobParameters);
		} catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException
				| JobParametersInvalidException e) {
			e.printStackTrace();
		}
	}
	
	@GetMapping("/ratePlanCode/{ratePlanCode}")
	public RatePlan getRatePlanDetailsForRatePlan(String ratePlanCode)
	{
		return ratePlanService.getRatePlanDetailsForRatePlan(ratePlanCode);
	}
	
}
