package ch.heigvd.amt.projectOne.presentation;

import ch.heigvd.amt.projectOne.services.dao.CharacterManagerLocal;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Manage the registration part
 */
@WebServlet(urlPatterns = "/register")
public class RegistrationServlet extends HttpServlet {

    @EJB
    CharacterManagerLocal characterManager;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Get parameters
            String name = req.getParameter("name");
            String password = req.getParameter("password");
            String passwordVerify = req.getParameter("passwordVerify");

            List<String> errors = new ArrayList<>();

            if (name == null || name.trim().equals("")) {
                errors.add("Username cannot be empty");
            }
            if (password == null || password.trim().equals("") || passwordVerify == null || passwordVerify.trim().equals("")) {
                errors.add("Password cannot be empty");
            } else {
                if (!password.equals(passwordVerify)) {
                    errors.add("Password are not the same");
                }
            }
            if (!characterManager.isUsernameFree(name)) {
                errors.add("This name is already taken");
            }
            req.setAttribute("name", name);
            if (errors.size() == 0) {
                if (!characterManager.addCharacter(name, password, false)) {
                    errors.add("Unable to create Character, contact an administrator");
                    req.setAttribute("errors", errors);
                    req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);

                } else {
                    resp.sendRedirect(req.getContextPath() + "/login");
                }
            } else {
                req.setAttribute("errors", errors);
                req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
            }
        } catch (SQLException ex) {

        }


    }


}
