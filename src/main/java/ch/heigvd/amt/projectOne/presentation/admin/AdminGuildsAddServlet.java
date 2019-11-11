package ch.heigvd.amt.projectOne.presentation.admin;

import ch.heigvd.amt.projectOne.model.Guild;
import ch.heigvd.amt.projectOne.services.dao.CharacterManagerLocal;
import ch.heigvd.amt.projectOne.services.dao.GuildManagerLocal;

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
 * Manage to add a guild
 */
@WebServlet(urlPatterns = "/admin/guilds/add")
public class AdminGuildsAddServlet extends HttpServlet {

    @EJB
    GuildManagerLocal guildManager;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/pages/admin/admin_guilds_add.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String description = req.getParameter("description");

        List<String> errors = new ArrayList<>();

        if (name == null || name.trim().equals("")) {
            errors.add("Name cannot be empty");
        }

        if (description == null || description.trim().equals("")) {
            errors.add("Description cannot be empty");
        }

        if (!guildManager.isNameFree(name)) {
            errors.add("This name is already taken");
        }

        if (errors.size() == 0) {
            Guild newGuild = Guild.builder()
                    .name(name)
                    .description(description)
                    .build();
            if (!guildManager.addGuild(newGuild)) {
                errors.add("Unable to create Character, contact an administrator");
                req.setAttribute("errors", errors);
                req.getRequestDispatcher("/WEB-INF/pages/admin/admin_guilds_add.jsp").forward(req, resp);

            } else {
                resp.sendRedirect(req.getContextPath() + "/admin/guilds");
            }
        } else {
            req.setAttribute("errors", errors);
            req.getRequestDispatcher("/WEB-INF/pages/admin/admin_guilds_add.jsp").forward(req, resp);
        }
    }
}
