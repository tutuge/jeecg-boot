package org.jeecg.modules.online.config.dUtil;

import freemarker.core.TemplateClassResolver;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.StringWriter;
import java.util.Map;

public class h {
    private static final Logger a = LoggerFactory.getLogger(h.class);

    private static Configuration configuration = new Configuration(Configuration.VERSION_2_3_28);

    static {
        configuration.setNumberFormat("0.#####################");
        configuration.setClassForTemplateLoading(h.class, "/");
        configuration.setNewBuiltinClassResolver(TemplateClassResolver.SAFER_RESOLVER);
    }

    public static String a(String paramString1, String paramString2, Map<String, Object> paramMap) {
        try {
            StringWriter stringWriter = new StringWriter();
            Template template = null;
            template = configuration.getTemplate(paramString1, paramString2);
            template.process(paramMap, stringWriter);
            return stringWriter.toString();
        } catch (Exception exception) {
            a.error(exception.getMessage(), exception);
            return exception.toString();
        }
    }

    public static String a(String paramString, Map<String, Object> paramMap) {
        return a(paramString, "utf-8", paramMap);
    }
}
