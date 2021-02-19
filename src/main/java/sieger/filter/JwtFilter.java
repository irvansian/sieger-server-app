package sieger.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;

import sieger.exception.BadRequestException;
import sieger.payload.ApiResponse;

/**
 * The filter that extracts the User Id inside a JWT token from a client.
 * @author Irvan Sian Syah Putra
 *
 */
@Component
public class JwtFilter extends OncePerRequestFilter {

	/**
	 * Overriding the doFilterInternal. This method gets the header with
	 * identifier "Authorization". Inside this header there must be 
	 * 'Bearer' prefix, one space, and then the JWT Token. This method
	 * then checks if the JWT Token is valid to Firebase, and extracts the User Id
	 * if the token is valid. This user Id will be than passed to the next filter
	 * with the key 'currentUserId'.
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, 
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		final String authorizationHeader = request.getHeader("Authorization");
        
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
        	 ApiResponse res = new ApiResponse(false, "Authorization "
             		+ "header is missing or not valid.");
             throw new BadRequestException(res);
        }
        
        String jwt = authorizationHeader.substring(7);
        String uid = null;
          
		try {
			FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(jwt);
			uid = decodedToken.getUid();
		} catch (FirebaseAuthException e) {
			e.printStackTrace();
		}
        
        request.setAttribute("currentUserId", uid);

        filterChain.doFilter(request, response);
    }

}
