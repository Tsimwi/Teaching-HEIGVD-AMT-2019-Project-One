package ch.heigvd.amt.projectOne.presentation;

import ch.heigvd.amt.projectOne.model.Character;
import ch.heigvd.amt.projectOne.model.Membership;
import ch.heigvd.amt.projectOne.services.dao.CharacterManagerLocal;
import ch.heigvd.amt.projectOne.services.dao.MembershipManagerLocal;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Manage to displaya profile page with all info on a user
 */
@WebServlet(urlPatterns = "/profile")
public class ProfileServlet extends HttpServlet {

    @EJB
    MembershipManagerLocal membershipManager;
    @EJB
    CharacterManagerLocal characterManager;

    Character character;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();

        // If an id is giver we are on a other character than us so we fetch it from db
        if (req.getParameterMap().containsKey("id")) {
            character = characterManager.getCharacterById(Integer.parseInt(req.getParameter("id")));
        } else {
            character = (Character) session.getAttribute("character");
        }

        List<Membership> memberships = membershipManager.getMembershipsByUserId(character.getId());

        req.setAttribute("character", character);
        req.setAttribute("memberships", memberships);
        req.getRequestDispatcher("/WEB-INF/pages/profile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
