package org.aspire.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aspire.constants.LoanConstants;
import org.aspire.data.User;
import org.aspire.model.RequestMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthorizationInterceptor implements HandlerInterceptor {

    @Autowired
    private RequestMetadata requestMetadata;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String servletPath = request.getServletPath();
        User user = requestMetadata.getUser();
        if (servletPath.contains("/admin/")) {
            if (!LoanConstants.ADMIN_USER_TYPE.equals(user.getUserType())) {
                response.setStatus(HttpStatus.FORBIDDEN.value());
                return false;
            }
        } else if (servletPath.contains("/applicant/")) {
            if (!LoanConstants.APPLICANT_USER_TYPE.equals(user.getUserType())) {
                response.setStatus(HttpStatus.FORBIDDEN.value());
                return false;
            }
        }

        return true;
    }
}
