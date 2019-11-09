package ch.heigvd.amt.projectOne.presentation.filter;

import ch.heigvd.amt.projectOne.model.Character;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebFilter(filterName = "ErrorFilter")
public class ErrorParametersEmptyFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest filteredReq = (HttpServletRequest) req;
        HttpServletResponse filteredResp = (HttpServletResponse) resp;

        boolean error = false;

        /* We test if parameters have empty value */
        for (Map.Entry<String, String[]> param : filteredReq.getParameterMap().entrySet()) {
            /* These field can be empty because we inform the user with errors */
            if(param.getKey().equals("name") || param.getKey().equals("password") || param.getKey().equals("passwordVerify") || param.getKey().equals("isAdminCheckbox") || param.getKey().equals("username")) {
                break;
            }else{
                if(param.getValue()[0].trim().equals("")){
                    error = true;
                    break;
                }
            }
        }

        if (error) {
            filteredReq.getRequestDispatcher("/WEB-INF/pages/error_404.jsp").forward(req, resp);
        } else {
            chain.doFilter(req, resp);
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
