package org.jeecg.modules.online.cgform.converter;

import java.util.Map;

public interface FieldCommentConverter {
  String converterToVal(String paramString);

  String converterToTxt(String paramString);

  Map<String, String> getConfig();
}
