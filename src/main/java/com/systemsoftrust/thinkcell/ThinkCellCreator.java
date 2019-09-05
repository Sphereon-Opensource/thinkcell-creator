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

    public Iterable<CSVRecord> execute(String inputCsf) {
        Iterable<CSVRecord> records = datasetReader.parse(new File(inputCsf));

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
