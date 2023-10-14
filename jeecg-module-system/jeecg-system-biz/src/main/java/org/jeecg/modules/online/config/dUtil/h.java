 package org.jeecg.modules.online.config.dUtil;

 import freemarker.core.TemplateClassResolver;
 import freemarker.template.Configuration;
 import freemarker.template.Template;
 import java.io.StringWriter;
 import java.util.Map;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;

 public class h {
   private static final Logger a = LoggerFactory.getLogger(h.class);

   private static Configuration b = new Configuration(Configuration.VERSION_2_3_28);

   static {
     b.setNumberFormat("0.#####################");
     b.setClassForTemplateLoading(h.class, "/");
     b.setNewBuiltinClassResolver(TemplateClassResolver.SAFER_RESOLVER);
   }

   public static String a(String paramString1, String paramString2, Map<String, Object> paramMap) {
     try {
       StringWriter stringWriter = new StringWriter();
       Template template = null;
       template = b.getTemplate(paramString1, paramString2);
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


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\config\d\h.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
