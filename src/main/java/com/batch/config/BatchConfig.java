package com.batch.config;

import com.batch.steps.ItemDescompressStep;
import com.batch.steps.ItemProcesorStep;
import com.batch.steps.ItemReaderStep;
import com.batch.steps.ItemWriterStep;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    @JobScope
    public ItemDescompressStep itemDescompressStep(){
        return new ItemDescompressStep();
    }

    @Bean
    @JobScope
    public ItemProcesorStep itemProcesorStep(){
        return new ItemProcesorStep();
    }

    @Bean
    @JobScope
    public ItemReaderStep itemReaderStep(){
        return new ItemReaderStep();
    };

    @Bean
    @JobScope
    public ItemWriterStep itemWriterStep(){
        return new ItemWriterStep();
    };

    @Bean
    public Step descompressFileStep(){
    return stepBuilderFactory.get("descompressFileStep")
            .tasklet(itemDescompressStep())
            .build();
    }

    @Bean
    public Step readPersonStep() {
        return stepBuilderFactory.get("readPersonStep")
                .tasklet(itemReaderStep())
                .build();
    }

    @Bean
    public Step processPersonStep(){
        return stepBuilderFactory.get("processPerson")
                .tasklet(itemProcesorStep())
                .build();
    }

    @Bean
    public Step writePersonStep() {
        return stepBuilderFactory.get("writePersonStep")
                .tasklet(itemWriterStep())
                .build();
    }

    @Bean
    public Job readCSVJob() {
        return jobBuilderFactory.get("readCSVJob")
                .start(descompressFileStep())
                .next(readPersonStep())
                .next(processPersonStep())
                .next(writePersonStep())
                .build();
    }
}
