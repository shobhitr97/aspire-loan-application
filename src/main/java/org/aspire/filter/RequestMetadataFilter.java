package org.aspire.filter;

import jakarta.servlet.*;
import org.aspire.model.RequestMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
public class RequestMetadataFilter implements Filter {

    @Autowired
    private RequestMetadata requestMetadata;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        requestMetadata.setRequestId(UUID.randomUUID().toString());
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
