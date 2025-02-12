package ch.heigvd.amt.projectOne.presentation;

import ch.heigvd.amt.projectOne.model.Character;
import ch.heigvd.amt.projectOne.model.Guild;
import ch.heigvd.amt.projectOne.model.Membership;
import ch.heigvd.amt.projectOne.services.dao.MembershipManagerLocal;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Manage the feature to join a guild
 */
@WebServlet(urlPatterns = "/guilds/join")
public class GuildJoinServlet extends HttpServlet {

    @EJB
    MembershipManagerLocal membershipManager;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        if (!req.getParameterMap().containsKey("id")) {
            req.getRequestDispatcher("/WEB-INF/pages/error_404.jsp").forward(req, resp);
        } else {
            Membership membership = Membership.builder()
                    .guild(Guild.builder()
                            .id(Integer.parseInt(req.getParameter("id")))
                            .build())
                    .character((Character) req.getSession().getAttribute("character"))
                    .build();

            membershipManager.addMembership(membership);

            resp.sendRedirect(req.getContextPath() + "/profile");
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
