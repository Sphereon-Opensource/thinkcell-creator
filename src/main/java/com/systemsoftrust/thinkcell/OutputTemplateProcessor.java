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

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

@Component
public class OutputTemplateProcessor {

    private final ConfigProperties configProperties;
    private Configuration freemarkerConfig;

    public OutputTemplateProcessor(ConfigProperties configProperties) {
        this.configProperties = configProperties;
    }


    public Template getTemplate() {
        try {
            return getConfiguration().getTemplate(configProperties.getTemplate().getOutputFile(), Locale.forLanguageTag(configProperties.getTemplate().getLocale()));
        } catch (IOException e) {
            throw new ThinkCellException(e);
        }

    }

    protected Configuration getConfiguration() {

        if (freemarkerConfig == null) {
            this.freemarkerConfig = new Configuration();

            File templateDir = new File(configProperties.getTemplate().getDirectory());
            if (!templateDir.isDirectory()) {
                throw new ThinkCellException(String.format("Template directory '%s' is not a valid directory. Please adjust settings", templateDir.getAbsolutePath()));
            }

            try {
                freemarkerConfig.setDirectoryForTemplateLoading(templateDir);
                freemarkerConfig.setDefaultEncoding("UTF-8");
                freemarkerConfig.setLocale(Locale.forLanguageTag(configProperties.getTemplate().getLocale()));
                freemarkerConfig.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            } catch (IOException e) {
                throw new ThinkCellException(e);
            }
        }
        return freemarkerConfig;
    }

}
