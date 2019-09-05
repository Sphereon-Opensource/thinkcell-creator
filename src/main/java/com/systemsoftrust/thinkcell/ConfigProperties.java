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

import org.apache.commons.csv.CSVFormat;
import org.hibernate.validator.constraints.Length;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Configuration
@ConfigurationProperties(prefix = "thinkcell")
@Validated
public class ConfigProperties {

    private Csv csv = new Csv();
    private Template template = new Template();


    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }


    public Csv getCsv() {
        return csv;
    }

    public void setCsv(Csv csv) {
        this.csv = csv;
    }

    @Validated
    public class Csv {
        private boolean headerLinePresent = true;
        private String format = "excel";

        @Length(min=1, max=1)
        private String delimiter = ";";

        public boolean isHeaderLinePresent() {
            return headerLinePresent;
        }

        public void setHeaderLinePresent(boolean headerLinePresent) {
            this.headerLinePresent = headerLinePresent;
        }

        public String getFormat() {
            return format;
        }

        public CSVFormat getFormatEnum() {
            if (StringUtils.isEmpty(format)) {
                return CSVFormat.DEFAULT;
            } else if (format.equalsIgnoreCase("excel")) {
                return CSVFormat.EXCEL;
            }
            return CSVFormat.valueOf(format.toUpperCase());
        }

        public void setFormat(String format) {
            this.format = format;
        }


        public String getDelimiter() {
            return delimiter;
        }

        public void setDelimiter(String delimiter) {
            this.delimiter = delimiter;
        }
    }

    @Validated
    public class Template {
        @NotNull
        private String outputFile = "config/output-template.ftl";
        private String directory = "";
        private String locale = "US";

        public String getLocale() {
            return locale;
        }

        public void setLocale(String locale) {
            this.locale = locale;
        }

        public String getDirectory() {
            return directory;
        }

        public void setDirectory(String directory) {
            this.directory = directory;
        }


        public String getOutputFile() {
            return outputFile;
        }

        public void setOutputFile(String outputFile) {
            this.outputFile = outputFile;
        }



    }
}
