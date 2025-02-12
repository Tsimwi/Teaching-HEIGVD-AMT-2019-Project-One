package ch.heigvd.amt.projectOne.presentation.admin;

import ch.heigvd.amt.projectOne.services.dao.GuildManagerLocal;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Manage to display all guild
 */
@WebServlet(urlPatterns = "/admin/guilds")
public class AdminGuildsServlet extends HttpServlet {

    @EJB
    GuildManagerLocal guildManager;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("guilds", guildManager.getAllGuilds());
        req.getRequestDispatcher("/WEB-INF/pages/admin/admin_guilds.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
