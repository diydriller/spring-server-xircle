package com.xircle.outboxservice.presentation.schedule

import com.xircle.outboxservice.infrastructure.job.OutboxProcessorJob
import org.quartz.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OutboxSchedule {
    @Bean
    fun jobDetail(): JobDetail {
        return JobBuilder.newJob(OutboxProcessorJob::class.java)
            .withIdentity("outboxProcessorJob")
            .storeDurably()
            .build()
    }

    @Bean
    fun trigger(jobDetail: JobDetail): Trigger {
        return TriggerBuilder.newTrigger()
            .forJob(jobDetail)
            .withIdentity("outboxTrigger")
            .withSchedule(CronScheduleBuilder.cronSchedule("0 * * * * ?"))
            .build()
    }
}