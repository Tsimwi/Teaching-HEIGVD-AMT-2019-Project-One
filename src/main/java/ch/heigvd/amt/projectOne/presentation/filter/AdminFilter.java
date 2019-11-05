package ch.heigvd.amt.projectOne.presentation.filter;

import ch.heigvd.amt.projectOne.model.Character;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "AdminFilter")
public class AdminFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest filteredReq = (HttpServletRequest) req;
        HttpServletResponse filteredResp = (HttpServletResponse) resp;

        Character character = (Character) filteredReq.getSession().getAttribute("character");

        if (character.isIsadmin()) {
            chain.doFilter(req, resp);
        } else {
            filteredResp.sendRedirect(filteredReq.getContextPath() + "/home");
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
