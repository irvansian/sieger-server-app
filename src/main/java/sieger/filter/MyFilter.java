package sieger.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.google.common.base.Splitter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;

@Order(1)
@Component
@WebFilter(filterName = "MyFilter", urlPatterns = {"/**"}) 
public class MyFilter implements Filter {



    @Value("${filter.config.excludeUrls}")
    private String excludeUrls; 

    private List<String> excludes;

    
    private static final String VALID_ERROR = "{\"code\": \"6000\",\"message\": \"TOKEN 验证失败\"}";

    @Override
    public void init(FilterConfig filterConfig) {
    	
        excludes = Splitter.on(",").trimResults().splitToList(this.excludeUrls); 
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        String uri = request.getRequestURI();
        String token = request.getHeader("token");
        try {
            if (this.isExcludesUrl(uri)) { 
                chain.doFilter(req, resp); 
            } else {
                if (!valicateToken(token)) { 
                    response.getWriter().write(VALID_ERROR); 
                    return;
                }
                chain.doFilter(request, resp);
            }
        } catch (Exception ex) {
            
            response.getWriter().write(VALID_ERROR); 
        } finally {
            response.flushBuffer(); 
        }

    }

	public boolean valicateToken(String idToken) {
		FirebaseToken decodedToken = null;
		try {
			decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
		} catch (FirebaseAuthException e) {
			e.printStackTrace();
		}
		String uid = decodedToken.getUid();
		if(decodedToken != null && uid != null) {
			return true;
		} else {
			return false;
		}
	}
    
    private boolean isExcludesUrl(String path) {
        for (String v : this.excludes) { 
            if (path.startsWith(v)) {
                return true;
            }
        }
        return false;
    }
}