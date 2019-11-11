package ch.heigvd.amt.projectOne.presentation;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * If we have an error that occurred we display the error page corresponding
 */
@WebServlet("/ErrorCodeHandler")
public class ErrorCodeHandler extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processError(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processError(request, response);
    }

    private void processError(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Analyze the servlet exception

        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");

        try {
            switch (statusCode) {
                case 404:
                    request.getRequestDispatcher("WEB-INF/pages/error_404.jsp").forward(request, response);
                    break;
                case 500:
                    request.getRequestDispatcher("WEB-INF/pages/error_500.jsp").forward(request, response);
                    break;
            }

        } catch (ServletException e) {
            e.printStackTrace();
        }
    }
}