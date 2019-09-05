package com.systemsoftrust.thinkcell;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Optional;

@Component
public class DatasetReader {

    private final ConfigProperties configProperties;

    public DatasetReader(ConfigProperties configProperties) {

        this.configProperties = configProperties;
    }

    public Iterable<CSVRecord> parse(File file) {
        return parse(file, Optional.of(configProperties.getCsv().getFormatEnum()));
    }

    public Iterable<CSVRecord> parse(File inputFile, Optional<CSVFormat> inputFormat) {
        try {
            Reader in = new FileReader(inputFile);
            if (in == null) {
                throw new ThinkCellException(String.format("Could not find input file %s", inputFile.getAbsolutePath()));
            }

            CSVFormat format = inputFormat.orElse(configProperties.getCsv().getFormatEnum()).withDelimiter(configProperties.getCsv().getDelimiter().charAt(0));
            if (configProperties.getCsv().isHeaderLinePresent()) {
                format.withFirstRecordAsHeader();
            }
            return format.parse(in);
        } catch (IOException ioe) {
            throw new ThinkCellException(ioe);
        }
    }

}
