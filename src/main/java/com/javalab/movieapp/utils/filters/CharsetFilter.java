package com.javalab.movieapp.utils.filters;

import javax.servlet.*;
import java.io.IOException;

public class CharsetFilter implements Filter {

    public static final String UTF_8_CHAR_ENCODING = "UTF-8";
    public static final String UTF_8_CONTEXT_TYPE = "text/html; charset=UTF-8";

    public void init(FilterConfig config) {
    }

    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain next) throws IOException, ServletException {
        request.setCharacterEncoding(UTF_8_CHAR_ENCODING);
        response.setContentType(UTF_8_CONTEXT_TYPE);
        response.setCharacterEncoding(UTF_8_CHAR_ENCODING);

        next.doFilter(request, response);
    }

    public void destroy() {
    }

}
