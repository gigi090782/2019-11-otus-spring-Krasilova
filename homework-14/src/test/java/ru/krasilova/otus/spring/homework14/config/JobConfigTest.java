package ru.krasilova.otus.spring.homework14.config;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.AssertFile;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.assertj.core.api.Assertions.assertThat;

import static ru.krasilova.otus.spring.homework14.config.JobConfig.*;

@SpringBootTest
@SpringBatchTest
class JobConfigTest {

    private static final String EXPECTED_OUTPUT_FILE_NAME = "expected_output.dat";
    private static final String TEST_OUTPUT_FILE_NAME = "test_output.dat";

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;

    @BeforeEach
    void clearMetaData() {
        jobRepositoryTestUtils.removeJobExecutions();
    }

    @Test
    @DisplayName("Запуск job")
    void testLibraryJob() throws Exception {
        Job job = jobLauncherTestUtils.getJob();
        assertThat(job).isNotNull()
                .extracting(Job::getName)
                .isEqualTo(IMPORT_LIBRARY_JOB_NAME);

        JobParameters parameters = new JobParametersBuilder()
                .addString(OUTPUT_FILE_NAME, TEST_OUTPUT_FILE_NAME)
                .toJobParameters();
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(parameters);
        assertThat(jobExecution.getExitStatus().getExitCode()).isEqualTo("COMPLETED");
    }


}