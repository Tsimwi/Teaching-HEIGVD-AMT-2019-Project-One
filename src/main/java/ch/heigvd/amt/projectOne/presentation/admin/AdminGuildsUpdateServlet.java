package ch.heigvd.amt.projectOne.presentation.admin;

import ch.heigvd.amt.projectOne.model.Guild;
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

@WebServlet(urlPatterns = "/admin/guilds/update")
public class AdminGuildsUpdateServlet extends HttpServlet {

    @EJB
    private GuildManagerLocal guildManager;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String guildId = req.getParameter("id");
        Guild guild = guildManager.getGuildById(Integer.parseInt(guildId));
        req.setAttribute("guild", guild);

        req.getRequestDispatcher("/WEB-INF/pages/admin/admin_guilds_update.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameterMap().containsKey("updateGuild")) {
            updateGuild(req, resp);
        }
    }

    private void updateGuild(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String guildId = req.getParameter("id");
        Guild guild = guildManager.getGuildById(Integer.parseInt(guildId));
        String name = req.getParameter("name");
        String description = req.getParameter("description");

        List<String> errors = new ArrayList<>();

        if (name == null || name.trim().equals("")) {
            errors.add("Guild name cannot be empty");
        }

        if (!guild.getName().equals(name) && !guildManager.isNameFree(name)) {
            errors.add("This name is already taken");
        }

        Guild updatedGuild = Guild.builder()
                .id(Integer.parseInt(guildId))
                .name(name)
                .description(description)
                .build();

        req.setAttribute("guild", updatedGuild);

        if (errors.size() == 0) {
            if (!guildManager.updateGuild(updatedGuild)) {
                errors.add("Unable to update the guild");
                req.setAttribute("errors", errors);
                req.getRequestDispatcher("/WEB-INF/pages/admin/admin_guilds_update.jsp").forward(req, resp);
            } else {
                resp.sendRedirect(req.getContextPath() + "/admin/guilds");
            }
        } else {
            req.setAttribute("errors", errors);
            req.getRequestDispatcher("/WEB-INF/pages/admin/admin_guilds_update.jsp").forward(req, resp);
        }
    }
}
