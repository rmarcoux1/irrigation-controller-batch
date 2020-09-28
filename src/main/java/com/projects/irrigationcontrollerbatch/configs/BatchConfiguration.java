package com.projects.irrigationcontrollerbatch.configs;

import com.projects.irrigationcontrollerbatch.listener.MyJobListener;
import com.projects.irrigationcontrollerbatch.model.IrrigationSystem;
import com.projects.irrigationcontrollerbatch.writer.IrrigationSystemWriter;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.HashMap;

/**
 * @author Ryan G. Marcoux
 */
@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private IrrigationSystemWriter writer;

    @Autowired
    private MyJobListener listener;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    JobLauncher jobLauncher;

    @Bean
    public Job importIrrigationSystemConfigurations() {
        return jobBuilderFactory.get("importIrrigationSystemConfigurations")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .start(runSystem())
                .build();
    }

    @Bean
    public Step runSystem() {
        return stepBuilderFactory.get("runSystem")
                .<IrrigationSystem, IrrigationSystem>chunk(10)
                .reader(reader())
                .writer(writer)
                .build();
    }

    @Bean
    @StepScope
    public MongoItemReader<IrrigationSystem> reader() {
        MongoItemReader<IrrigationSystem> reader = new MongoItemReader<>();
        reader.setTemplate(mongoTemplate);
        reader.setSort(new HashMap<String, Sort.Direction>() {{
            put("_id", Sort.Direction.DESC);
        }});
        reader.setTargetType(IrrigationSystem.class);
        reader.setQuery("{}");
        return reader;
    }

    // run every 5000 msec (i.e., every 5 secs)
    //@Scheduled(cron = "/30 * * * * *")
    @Scheduled(fixedRate = 60000)
    public void run() throws Exception {
        JobExecution execution = jobLauncher.run(
                importIrrigationSystemConfigurations(),
                new JobParametersBuilder().addLong("uniqueness", System.nanoTime()).toJobParameters()
        );
    }
}
