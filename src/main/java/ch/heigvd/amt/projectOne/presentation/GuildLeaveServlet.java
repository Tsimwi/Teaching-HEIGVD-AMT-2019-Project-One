package ch.heigvd.amt.projectOne.presentation;

import ch.heigvd.amt.projectOne.services.dao.MembershipManagerLocal;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Manage the feature to leave a guild
 */
@WebServlet(urlPatterns = "/guilds/leave")
public class GuildLeaveServlet extends HttpServlet {

    @EJB
    MembershipManagerLocal membershipManager;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        if (!req.getParameterMap().containsKey("id")) {
            req.getRequestDispatcher("/WEB-INF/pages/error_404.jsp").forward(req, resp);
        } else {
            membershipManager.removeMembership(Integer.parseInt(req.getParameter("id")));

            resp.sendRedirect(req.getContextPath() + "/profile");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
