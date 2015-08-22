import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.btrace.BTraceUtils;
import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.OnMethod;
@BTrace(unsafe=true)
public class SpringDefaultHandlerMonitor {
    @OnMethod(clazz="org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver",method="doResolveException")
    public static void begin(HttpServletRequest request, HttpServletResponse response,
            Object handler, Exception ex){
        String uri = request.getRequestURI();
        BTraceUtils.println("uri=" + uri);
        if (uri != null && uri.contains("/around")) {
            Map map = request.getParameterMap();
            List<String> param = new ArrayList<String>();
            for (Iterator iter = map.keySet().iterator(); iter.hasNext();) {
				String key = (String) iter.next();
				String[] values = (String[]) map.get(key);
				if (values != null) {
					for (String val : values) {
						param.add(key + "=" + val);
					}
				} else {
					param.add(key + "=NULL");
				}
			}
            String args = request.getQueryString();
            BTraceUtils.println("param=" + param);
        }
    }
}
