package ch.heigvd.amt.projectOne.presentation;

import ch.heigvd.amt.projectOne.model.Character;
import ch.heigvd.amt.projectOne.model.Guild;
import ch.heigvd.amt.projectOne.model.Membership;
import ch.heigvd.amt.projectOne.services.dao.GuildManagerLocal;
import ch.heigvd.amt.projectOne.services.dao.MembershipManagerLocal;
import com.google.gson.Gson;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = "/guilds/info")
public class GuildInfoServlet extends HttpServlet {

    @EJB
    GuildManagerLocal guildManager;

    @EJB
    MembershipManagerLocal membershipManager;

    int numberOfUser;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        if (!req.getParameterMap().containsKey("id") || guildManager.getNumberOfGuild() == -1) {
            req.getRequestDispatcher("/WEB-INF/pages/error_404.jsp").forward(req, resp);
        } else {
            Guild guild = guildManager.getGuildById(Integer.parseInt(req.getParameter("id")));
            if(guild == null){
                req.getRequestDispatcher("/WEB-INF/pages/error_404.jsp").forward(req, resp);
            }else{
                boolean isCharacterMemberOfThisGuild = membershipManager.checkCharacterMembership(
                        (Character) req.getSession().getAttribute("character"), guild);

                numberOfUser = membershipManager.getNumberOfMembershipsForGuild(guild.getId());
                List<Membership> memberships = membershipManager.getMembershipsByGuildIdWithPage(Integer.parseInt(req.getParameter("id")), 0);

                req.setAttribute("numberOfPage", ((numberOfUser - 1) / 25) + 1);
                req.setAttribute("memberships", memberships);
                req.setAttribute("currentCharMembership", isCharacterMemberOfThisGuild);
                req.setAttribute("guild", guild);
                req.getRequestDispatcher("/WEB-INF/pages/guild_info.jsp").forward(req, resp);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int pageNumber = Integer.parseInt(req.getParameter("page"));
        int guildId = Integer.parseInt(req.getParameter("guildId"));
        StringBuilder table = new StringBuilder();
        List<Membership> memberships = membershipManager.getMembershipsByGuildIdWithPage(guildId, pageNumber - 1);

        for (Membership membership : memberships) {

            String line = String.format("<tr style=\"background-color: black\"><td><h5><a href=\"%s/profile?id=%d\">%s</a></h5></td><td class=\"rankColumn\"><h5>%s</h5></td></tr>",
                    req.getContextPath(), membership.getCharacter().getId(), membership.getCharacter().getName(), membership.getRank());
            table.append(line);
        }
        PrintWriter out = resp.getWriter();
        out.print(table.toString());
        out.flush();
        out.close();
    }
}
