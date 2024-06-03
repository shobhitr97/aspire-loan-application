package org.aspire.utils;

import jakarta.servlet.http.HttpServletRequest;

public class AuthUtils {

    public static boolean isRegisterUserCall(HttpServletRequest request) {
        String servletPath = request.getServletPath();
        return servletPath.endsWith("/auth/register");
    }

    public static String getCredentialsFromHeader(String header) {
        return header.split(" ")[1];
    }
}
