package com.sovon9.RatePlan_service.config;

import java.util.Collections;
import java.util.Optional;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

import com.sovon9.RatePlan_service.dto.RoomDto;
import com.sovon9.RatePlan_service.job.RatePlanProcessor;
import com.sovon9.RatePlan_service.model.RatePlanRoomMapping;
import com.sovon9.RatePlan_service.repository.RatePlanRepository;
import com.sovon9.RatePlan_service.repository.RoomRepository;

@Configuration
public class BatchConfig {

	@Autowired
	private RatePlanRepository ratePlanRepository;
	@Autowired
	private RoomRepository roomRepository;
	@Autowired
	private JobRepository jobRepository;
	@Autowired
	private PlatformTransactionManager transactionManager;
	
	@Bean
	public FlatFileItemReader<RatePlanRoomMapping> reader()
	{
		FlatFileItemReader<RatePlanRoomMapping> reader = new FlatFileItemReaderBuilder<RatePlanRoomMapping>()
		         .resource(new FileSystemResource("src/main/resources/rateplandata.csv"))
		         .name("csvreader")
		         .linesToSkip(1)
		         .lineMapper(lineMapper())
		         .build();
		return reader;
	}
	
	//
	public RepositoryItemReader<RatePlanRoomMapping> ratePlanRoomreader()
	{
		RepositoryItemReader<RatePlanRoomMapping> reader = new RepositoryItemReaderBuilder<RatePlanRoomMapping>()
		         .repository(ratePlanRepository)
		         .name("rateplanroom")
		         .methodName("findAll")
		         .pageSize(10)
		         .sorts(Collections.singletonMap("roomnum", Sort.Direction.ASC))
		         .build();
		return reader;
	}
	
	@Bean
	public ItemProcessor<RatePlanRoomMapping, RoomDto> rateplantoRoomProcessor()
	{
		return (rprm)->{
			RoomDto roomDto = null;
			Optional<RoomDto> roomOptional = roomRepository.findById(rprm.getRoomnum());
			if(roomOptional.isPresent())
			{
				roomDto = roomOptional.get();
				roomDto.setRatePlan(rprm.getRateplan());
			}
			else
			{
				System.out.println(".....................room number not present");
			}
			return roomDto;
		};
	}
	
	@Bean
	public RepositoryItemWriter<RoomDto> roomWriter()
	{
		RepositoryItemWriter<RoomDto> itemWriter = new RepositoryItemWriter<>();
		itemWriter.setRepository(roomRepository); // the repository to use
		itemWriter.setMethodName("save"); // the method to use for writing
		return itemWriter;
	}
	
	@Bean
	public Step step2()
	{
		return new StepBuilder("updaterateplan", jobRepository)
				.<RatePlanRoomMapping, RoomDto>chunk(10, transactionManager) // required for data consistency
				.reader(ratePlanRoomreader())
				.processor(rateplantoRoomProcessor())
				.writer(roomWriter())
				.build();
	}
	//

	private LineMapper<RatePlanRoomMapping> lineMapper() {
		// create Default Bean mapper
		DefaultLineMapper<RatePlanRoomMapping> lineMapper = new DefaultLineMapper<>();
		
		// setting delimitter for getting value from csv
		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		lineTokenizer.setDelimiter(",");
		lineTokenizer.setStrict(false);
		lineTokenizer.setNames("roomnum", "rateplan");
		
		// build RatePlanRoomMapping bean from field data
		BeanWrapperFieldSetMapper<RatePlanRoomMapping> beanMapper = new BeanWrapperFieldSetMapper<>();
		beanMapper.setTargetType(RatePlanRoomMapping.class);
		
		lineMapper.setFieldSetMapper(beanMapper);
		lineMapper.setLineTokenizer(lineTokenizer);
		
		return lineMapper;
	}
	
	@Bean
	public RatePlanProcessor processor()
	{
		return new RatePlanProcessor();
	}
	
	@Bean
	public RepositoryItemWriter<RatePlanRoomMapping> writer()
	{
		RepositoryItemWriter<RatePlanRoomMapping> itemWriter = new RepositoryItemWriter<>();
		itemWriter.setRepository(ratePlanRepository); // the repository to use
		itemWriter.setMethodName("save"); // the method to use for writing
		return itemWriter;
	}
	
	@Bean
	public Step step()
	{
		return new StepBuilder("csv-import", jobRepository)
				.<RatePlanRoomMapping, RatePlanRoomMapping>chunk(1, transactionManager) // required for data consistency
				.reader(reader())
				.processor(processor())
				.writer(writer())
				.build();
	}
	
	@Bean
	public Job job()
	{
		return new JobBuilder("rateplan-update", jobRepository)
				.start(step()) // add step to the job
				.next(step2())
				.build();
	}
	
}
