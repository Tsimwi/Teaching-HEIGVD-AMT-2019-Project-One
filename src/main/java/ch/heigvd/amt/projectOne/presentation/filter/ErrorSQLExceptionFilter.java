package ch.heigvd.amt.projectOne.presentation.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebFilter(filterName = "ExceptionFilter")
public class ErrorSQLExceptionFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest filteredReq = (HttpServletRequest) req;
        HttpServletResponse filteredResp = (HttpServletResponse) resp;


        if (false) {
            filteredReq.getRequestDispatcher("/WEB-INF/pages/error_404.jsp").forward(req, resp);
        } else {
            chain.doFilter(req, resp);
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
