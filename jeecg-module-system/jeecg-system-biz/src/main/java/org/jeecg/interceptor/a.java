package org.jeecg.interceptor;

import com.alibaba.fastjson.JSON;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jeecg.common.api.dto.OnlineAuthDTO;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.OnlineAuth;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.util.SpringContextUtils;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.online.cgform.service.IOnlineBaseAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class a implements HandlerInterceptor {
    private static final Logger a = LoggerFactory.getLogger(a.class);

    private IOnlineBaseAPI b;

    private ISysBaseAPI c;

    private static final String d = "/online/cgform";

    private static final String[] e = new String[]{"/online/cgformInnerTableList", "/online/cgformErpList", "/online/cgformList", "/online/cgformTreeList", "/online/cgformTabList"};

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean bool = handler.getClass().isAssignableFrom(HandlerMethod.class);
        if (bool) {
            OnlineAuth onlineAuth = (OnlineAuth) ((HandlerMethod) handler).getMethodAnnotation(OnlineAuth.class);
            if (onlineAuth != null) {
                String str1 = request.getRequestURI().substring(request.getContextPath().length());
                str1 = a(str1);
                String str2 = onlineAuth.value();
                String str3 = str1.substring(str1.lastIndexOf(str2) + str2.length());
                if ("form".equals(str2) && "DELETE".equals(request.getMethod()))
                    str3 = str3.substring(0, str3.lastIndexOf("/"));
                String str4 = request.getParameter("tabletype");
                if (this.b == null)
                    this.b = (IOnlineBaseAPI) SpringContextUtils.getBean(IOnlineBaseAPI.class);
                str3 = this.b.getOnlineErpCode(str3, str4);
                ArrayList<String> arrayList = new ArrayList();
                for (String str : e)
                    arrayList.add(str + str3);
                if (this.c == null)
                    this.c = (ISysBaseAPI) SpringContextUtils.getBean(ISysBaseAPI.class);
                String str5 = JwtUtil.getUserNameByToken(request);
                OnlineAuthDTO onlineAuthDTO = new OnlineAuthDTO(str5, arrayList, "/online/cgform");
                boolean bool1 = this.c.hasOnlineAuth(onlineAuthDTO);
                if (!bool1) {
                    a(response, str2);
                    return false;
                }
            }
        }
        return true;
    }

    private String a(String paramString) {
        String str = "";
        if (oConvertUtils.isNotEmpty(paramString)) {
            str = paramString.replace("\\", "/");
            str = str.replace("//", "/");
            if (str.contains("//"))
                str = a(str);
        }
        return str;
    }

    private void a(HttpServletResponse paramHttpServletResponse, String paramString) {
        PrintWriter printWriter = null;
        paramHttpServletResponse.setCharacterEncoding("UTF-8");
        paramHttpServletResponse.setContentType("application/json; charset=utf-8");
        paramHttpServletResponse.setHeader("auth", "fail");
        try {
            printWriter = paramHttpServletResponse.getWriter();
            if ("exportXls".equals(paramString)) {
                printWriter.print("");
            } else {
                Result result = Result.error("无权限访问(操作)");
                printWriter.print(JSON.toJSON(result));
            }
        } catch (IOException iOException) {
            a.error(iOException.getMessage());
        } finally {
            if (printWriter != null)
                printWriter.close();
        }
    }
}


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\interceptor\a.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
