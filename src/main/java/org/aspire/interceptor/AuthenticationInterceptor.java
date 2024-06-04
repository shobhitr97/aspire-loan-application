package org.aspire.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.aspire.data.User;
import org.aspire.handler.IUserHandler;
import org.aspire.model.RequestMetadata;
import org.aspire.utils.GenericUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


@Component
@Log4j2
public class AuthenticationInterceptor implements HandlerInterceptor {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    @Autowired
    private IUserHandler userHandler;

    @Autowired
    private RequestMetadata requestMetadata;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);
        String encodedCredentials = GenericUtils.getCredentialsFromHeader(authorizationHeader);

        User user = this.userHandler.getUserByCredentials(encodedCredentials);

        if (user == null) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }
        requestMetadata.setUser(user);
        return true;
    }
}
