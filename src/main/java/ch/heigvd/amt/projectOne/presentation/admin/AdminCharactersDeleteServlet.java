package ch.heigvd.amt.projectOne.presentation.admin;

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
import java.util.ArrayList;
import java.util.List;

/**
 * Manage to delete a character
 */
@WebServlet(urlPatterns = "/admin/characters/delete")
public class AdminCharactersDeleteServlet extends HttpServlet {

    @EJB
    CharacterManagerLocal characterManager;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameterMap().containsKey("id")) {
            HttpSession session = req.getSession();
            if (!characterManager.deleteCharacter(Integer.parseInt(req.getParameter("id")))) {
                session.setAttribute("error", "true");
            } else {
                session.setAttribute("error", "false");
            }
            resp.sendRedirect(req.getContextPath() + "/admin/characters");
        } else {
            req.getRequestDispatcher("/WEB-INF/pages/error_404.jsp").forward(req, resp);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
