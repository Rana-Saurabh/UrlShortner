package Security.jwt;

import Models.Users;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtils jwtUtils ;

    @Autowired
    UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //here we will autenticate thr reqquest
        try {
            //get toek form header
            //validate token
            //if user valid then get username ->load user->set auth context

            String token=jwtUtils.getJwtFromheader(request);
            if(token!=null && jwtUtils.verifyToken(token)){
                String username=jwtUtils.getUsernameFromToken(token);
                UserDetails curUser=userDetailsService.loadUserByUsername(username);
                if(curUser!=null ){
                    UsernamePasswordAuthenticationToken authenticationToken= new UsernamePasswordAuthenticationToken(curUser,null,curUser.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
