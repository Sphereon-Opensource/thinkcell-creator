package com.systemsoftrust.thinkcell;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ConfigProperties.class)
public class ThinkCellApplication implements CommandLineRunner {

    @Autowired ThinkCellCreator thinkCellCreator;

    private static Logger logger = LoggerFactory.getLogger(ThinkCellApplication.class);

    public static void main(String[] args) {

        SpringApplication.run(ThinkCellApplication.class, args);

    }

    @Override
    public void run(String... args) {

        for (int i = 0; i < args.length; ++i) {
            logger.info("args[{}]: {}", i, args[i]);
        }


        try {
            logger.info("--------- START CUT HERE --------------");
            thinkCellCreator.execute(args[0]);
            logger.info("--------- END CUT HERE --------------");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

    }
}
