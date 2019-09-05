/**
 * Copyright 2019 Systems of Trust BV
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

    private static Logger logger = LoggerFactory.getLogger(ThinkCellApplication.class);
    @Autowired
    ThinkCellCreator thinkCellCreator;

    public static void main(String[] args) {

        SpringApplication.run(ThinkCellApplication.class, args);

    }

    @Override
    public void run(String... args) {


        for (int i = 0; i < args.length; ++i) {
            logger.info("args[{}]: {}", i, args[i]);
        }


        try {

            if (args.length != 1) {
                throw new ThinkCellException("Please provide an input path for the CSV file, eg : input.csv");
            }
            logger.info("--------- START CUT HERE --------------");
            thinkCellCreator.execute(args[0]);
            logger.info("--------- END CUT HERE --------------");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

    }
}
