package ch.heigvd.amt.projectOne.presentation.admin;

import ch.heigvd.amt.projectOne.model.Character;
import ch.heigvd.amt.projectOne.services.dao.CharacterManagerLocal;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Display users on admi menu with action (delete, update)
 */
@WebServlet(urlPatterns = "/admin/characters")
public class AdminCharactersServlet extends HttpServlet {

    @EJB
    CharacterManagerLocal characterManager;

    int pageNumberInt;
    int numberOfUser;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Character> characters;

        if (!req.getParameterMap().containsKey("page")) {
            pageNumberInt = 1;
        } else {
            pageNumberInt = Integer.parseInt(req.getParameter("page"));
        }

        if (req.getParameterMap().containsKey("letter")) {
            characters = characterManager.getCharactersByPattern(req.getParameter("letter"), pageNumberInt - 1);
            numberOfUser = characterManager.countRows("character", "WHERE character.name ILIKE '" + req.getParameter("letter") + "%'");
        } else if (req.getParameterMap().containsKey("searchBar")) {
            characters = characterManager.getCharactersByPattern(req.getParameter("searchBar"), pageNumberInt - 1);
            numberOfUser = characterManager.countRows("character", "WHERE character.name ILIKE '" + req.getParameter("searchBar") + "%'");
        } else {
            numberOfUser = characterManager.countRows("character", "");
            characters = characterManager.getCharactersByPage(pageNumberInt - 1);
        }

        req.setAttribute("numberOfPage", ((numberOfUser - 1) / 25) + 1);
        req.setAttribute("characters", characters);

        req.getRequestDispatcher("/WEB-INF/pages/admin/admin_characters.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
