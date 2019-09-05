/**
 * Copyright 2019 Systems of Trust BV
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.systemsoftrust.thinkcell;

import freemarker.template.TemplateException;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.*;

@Component
public class ThinkCellCreator {

    private static Logger logger = LoggerFactory.getLogger(ThinkCellCreator.class);

    private final ConfigProperties configProperties;
    private final DatasetReader datasetReader;
    private final OutputTemplateProcessor templateProcessor;

    public ThinkCellCreator(ConfigProperties configProperties, DatasetReader datasetReader, OutputTemplateProcessor templateProcessor) {
        this.configProperties = configProperties;
        this.datasetReader = datasetReader;
        this.templateProcessor = templateProcessor;
    }

    public Iterable<CSVRecord> execute(String inputCsv) {
        File file = new File(inputCsv);
        if (!file.isFile()) {
            throw new ThinkCellException("Input file is not found: " + inputCsv);
        }
        Iterable<CSVRecord> records = datasetReader.parse(file);

        if (records == null) {
            throw new ThinkCellException("Could not retrieve CSV records from file");

        }


        Iterator<CSVRecord> recordIterator = records.iterator();
        List<List<String>> recordList = new ArrayList();
        Map<String, Object> input = new HashMap<>();
        if (configProperties.getCsv().isHeaderLinePresent()) {
            CSVRecord headerRecord = recordIterator.next();
            List<String> headers = new ArrayList<>();
            headerRecord.iterator().forEachRemaining(s -> headers.add(s.trim()));
            input.put("headers", headers);
        }

        records.iterator().forEachRemaining(record -> {
            List<String> recordValues = new ArrayList<>();
            record.iterator().forEachRemaining(s -> {
                recordValues.add(s.trim());
            });
            recordList.add(recordValues);
        });
        input.put("records", recordList);


        Writer consoleWriter = new OutputStreamWriter(System.out);
        try {
            templateProcessor.getTemplate().process(input, consoleWriter);
        } catch (TemplateException | IOException e) {
            throw new ThinkCellException(e);
        }

        return records;
    }
}
