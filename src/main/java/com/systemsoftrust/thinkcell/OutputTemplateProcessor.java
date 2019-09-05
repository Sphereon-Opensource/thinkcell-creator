package com.systemsoftrust.thinkcell;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;
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
