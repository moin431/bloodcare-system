package bd.edu.seu.bdms.config;

import bd.edu.seu.bdms.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationProvider extends OncePerRequestFilter {
    private final JwtService jwtService;

    public JwtAuthenticationProvider(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = null;

        if(request.getCookies() != null){
            for(Cookie c : request.getCookies()){
                if(c.getName().equals("JWT")){
                    token = c.getValue();
                }
            }
        }

        if(token != null){

            try{
                String email = jwtService.getEmail(token);
                String role  = jwtService.getRole(token);

                SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);

                Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, List.of(authority));

                SecurityContextHolder.getContext().setAuthentication(authentication);


            }catch(Exception e){
                SecurityContextHolder.clearContext();
            }
        }

        filterChain.doFilter(request,response);

    }
}
