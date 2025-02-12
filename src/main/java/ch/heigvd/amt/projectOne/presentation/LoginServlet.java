package ch.heigvd.amt.projectOne.presentation;

import ch.heigvd.amt.projectOne.model.Character;
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
 * Manage the login page
 */
@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    @EJB
    CharacterManagerLocal characterManager;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String username = req.getParameter("username");
            String password = req.getParameter("password");

            List<String> errors = new ArrayList<>();

            if (username == null || username.trim().equals("")) {
                errors.add("Username cannot be empty.");
            }
            if (password == null || password.trim().equals("")) {
                errors.add("Password cannot be empty.");
            }

            if (errors.size() < 1 && (characterManager.isUsernameFree(username) || !characterManager.checkPassword(username, password))) {
                errors.add("Wrong username or password.");
            }

            if (errors.size() == 0) {
                Character user = characterManager.getCharacterByUsername(username);
                HttpSession session = req.getSession();
                session.setAttribute("character", user);
                resp.sendRedirect(req.getContextPath() + "/home");
            } else {
                req.setAttribute("errors", errors);
                req.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(req, resp);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }
}