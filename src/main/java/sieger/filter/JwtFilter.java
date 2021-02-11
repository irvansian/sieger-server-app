//package sieger.filter;
//
//import java.io.IOException;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseAuthException;
//import com.google.firebase.auth.FirebaseToken;
//
//import sieger.exception.BadRequestException;
//import sieger.payload.ApiResponse;
//
//@Component
//public class JwtFilter extends OncePerRequestFilter {
//
//	@Override
//	protected void doFilterInternal(HttpServletRequest request, 
//			HttpServletResponse response, FilterChain filterChain)
//			throws ServletException, IOException {
//		final String authorizationHeader = request.getHeader("Authorization");
//        
//        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
//        	 ApiResponse res = new ApiResponse(false, "Authorization "
//             		+ "header is missing or not valid.");
//             throw new BadRequestException(res);
//        }
//        
//        String jwt = authorizationHeader.substring(7);
//        String uid = null;
//          
//		try {
//			FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(jwt);
//			uid = decodedToken.getUid();
//		} catch (FirebaseAuthException e) {
//			e.printStackTrace();
//		}
//        
//        request.setAttribute("currentUserId", uid);
//
//        filterChain.doFilter(request, response);
//    }
//
//}
