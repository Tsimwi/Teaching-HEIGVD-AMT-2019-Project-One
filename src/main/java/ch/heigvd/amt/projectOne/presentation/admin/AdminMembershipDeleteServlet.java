package ch.heigvd.amt.projectOne.presentation.admin;

import ch.heigvd.amt.projectOne.model.Guild;
import ch.heigvd.amt.projectOne.services.dao.GuildManagerLocal;
import ch.heigvd.amt.projectOne.services.dao.MembershipManagerLocal;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = "/admin/guilds/memberships/delete")
public class AdminMembershipDeleteServlet extends HttpServlet {

    @EJB
    MembershipManagerLocal membershipManager;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (req.getParameterMap().containsKey("id") && req.getParameterMap().containsKey("guildId")) {
            int guildId = Integer.parseInt(req.getParameter("guildId"));
            membershipManager.removeMembership(Integer.parseInt(req.getParameter("id")));
            resp.sendRedirect(req.getContextPath() + "/admin/guilds/update?id=" + guildId);
        }else{

            req.getRequestDispatcher("/WEB-INF/pages/error_404.jsp").forward(req, resp);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
