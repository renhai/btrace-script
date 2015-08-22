import static com.sun.btrace.BTraceUtils.*;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.Kind;
import com.sun.btrace.annotations.Location;
import com.sun.btrace.annotations.OnMethod;

@BTrace(unsafe = true)
public class DispatcherServletMonitor {
	@OnMethod(clazz = "org.springframework.web.servlet.DispatcherServlet", method = "doService", location = @Location(Kind.RETURN))
    public static void begin(HttpServletRequest request, HttpServletResponse response){
        String uri = request.getRequestURI();
        println(uri);
        Map map = request.getParameterMap();
		Integer userId = (Integer) request.getAttribute("user_id");
		if (userId == null) {
			userId = 0;
		}
		String uidStr = strcat("user_id = ", userId.toString());
		println(strcat(uidStr, map.toString()));
    }
}
