package by.arvisit.modsenlibapp.bookservice.filter;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import by.arvisit.modsenlibapp.bookservice.client.UserClient;
import by.arvisit.modsenlibapp.bookservice.dto.UserDto;
import by.arvisit.modsenlibapp.exceptionhandlingstarter.exception.BadRequestException;
import by.arvisit.modsenlibapp.exceptionhandlingstarter.exception.InvalidTokenException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final UserClient userClient;
    private final AuthenticationEntryPoint authenticationEntryPoint;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
//        if (isEmpty(header) || !header.startsWith("Bearer ")) {
//            chain.doFilter(request, response);
//            return;
//        }
        if (StringUtils.isEmpty(header)) {
            chain.doFilter(request, response);
            return;
        }

        UserDto userDto = null;
        try {
            userDto = userClient.validate(header);
            
        } catch(BadRequestException | InvalidTokenException e) {
            authenticationEntryPoint.commence(request, response, new AuthenticationException(e.getMessage()) {});
            return;
        }
        UserDetails userDetails = new User(userDto.username(), "",
                userDto.authorities().stream().map(SimpleGrantedAuthority::new).toList());

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null,
                userDetails.getAuthorities());

        authentication.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

}
