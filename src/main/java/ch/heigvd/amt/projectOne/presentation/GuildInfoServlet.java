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
import java.util.List;

@WebServlet(urlPatterns = "/guilds/info")
public class GuildInfoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @EJB
    GuildManagerLocal guildManager;

    @EJB
    MembershipManagerLocal membershipManager;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Guild guild = guildManager.getGuildById(Integer.parseInt(req.getParameter("id")));
        boolean isCharacterMemberOfThisGuild = membershipManager.checkCharacterMembership(
                (Character) req.getSession().getAttribute("character"), guild);
        int numberOfCharacter = membershipManager.getNumberOfMembershipsForGuild(Integer.parseInt(req.getParameter("id")));


        req.setAttribute("numberOfCharacter", numberOfCharacter);
        req.setAttribute("numberOfPage", ((numberOfCharacter - 1) / 25) + 1);
        req.setAttribute("currentCharMembership", isCharacterMemberOfThisGuild);
        req.setAttribute("guild", guild);
        req.getRequestDispatcher("/WEB-INF/pages/guild_info.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        int pageNumber = Integer.parseInt(req.getParameter("page"));
        int guildId = Integer.parseInt(req.getParameter("guildId"));
        List<Membership> memberships = membershipManager.getMembershipsByGuildIdByPage(guildId, pageNumber -1);

        PrintWriter out = resp.getWriter();
        out.print(gson.toJson(memberships));
        out.flush();
        out.close();
    }
}
