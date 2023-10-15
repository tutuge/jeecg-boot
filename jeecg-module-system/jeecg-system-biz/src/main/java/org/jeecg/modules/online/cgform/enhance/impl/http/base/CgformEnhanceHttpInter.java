package org.jeecg.modules.online.cgform.enhance.impl.http.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import jakarta.servlet.http.HttpServletRequest;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.util.RestUtil;
import org.jeecg.common.util.SpringContextUtils;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.online.cgform.entity.OnlCgformEnhanceJava;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;

import java.util.Enumeration;
import java.util.List;
import java.util.Map;

public interface CgformEnhanceHttpInter {
    public static final Logger logger = LoggerFactory.getLogger(CgformEnhanceHttpInter.class);

    default void execute(String tableName, JSONObject json, OnlCgformEnhanceJava enhance) {
    }

    default void execute(String tableName, List<Map<String, Object>> dataList, OnlCgformEnhanceJava enhance) {
    }

    default Object sendPost(JSONObject params, OnlCgformEnhanceJava enhance) {
        if (enhance == null)
            return null;
        String str = enhance.getCgJavaValue();
        if (oConvertUtils.isEmpty(str))
            return null;
        if (!str.startsWith("http") && !str.startsWith("https")) {
            String str1 = RestUtil.getBaseUrl();
            if (str.startsWith("/")) {
                str = str1 + str;
            } else {
                str = str1 + "/" + str;
            }
        }
        HttpServletRequest httpServletRequest = SpringContextUtils.getHttpServletRequest();
        HttpHeaders httpHeaders = getHeaders(httpServletRequest);
        ResponseEntity responseEntity = RestUtil.request(str, HttpMethod.POST, httpHeaders, null, params, String.class);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            String str1 = (String) responseEntity.getBody();
            if (oConvertUtils.isNotEmpty(str1))
                try {
                    JSONObject jSONObject = JSON.parseObject(str1);
                    if (jSONObject.getBoolean("success"))
                        return jSONObject.get("result");
                    throw new JeecgBootException(jSONObject.getString("message"));
                } catch (JeecgBootException jeecgBootException) {
                    throw jeecgBootException;
                } catch (Exception exception) {
                    logger.warn("请求Online表单Java增强http接口时转换数据出错：" + exception.getMessage() + "\n body: " + str1);
                    throw new JeecgBootException("Online表单Java增强http接口JSON转换失败！");
                }
        }
        return null;
    }

    default HttpHeaders getHeaders(HttpServletRequest request) {
        HttpHeaders httpHeaders = new HttpHeaders();
        Enumeration<String> enumeration = request.getHeaderNames();
        while (enumeration.hasMoreElements()) {
            String str = enumeration.nextElement();
            Enumeration<String> enumeration1 = request.getHeaders(str);
            while (enumeration1.hasMoreElements())
                httpHeaders.set(str, enumeration1.nextElement());
        }
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.remove("Accept-Encoding");
        httpHeaders.remove("accept-encoding");
        httpHeaders.remove("Accept");
        httpHeaders.add("Accept", "application/json");
        return httpHeaders;
    }
}


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\cgform\enhance\impl\http\base\CgformEnhanceHttpInter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
