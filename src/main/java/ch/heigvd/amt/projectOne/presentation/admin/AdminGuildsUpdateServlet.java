package ch.heigvd.amt.projectOne.presentation.admin;

import ch.heigvd.amt.projectOne.model.Guild;
import ch.heigvd.amt.projectOne.model.Membership;
import ch.heigvd.amt.projectOne.services.dao.GuildManagerLocal;
import ch.heigvd.amt.projectOne.services.dao.MembershipManagerLocal;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = "/admin/guilds/update")
public class AdminGuildsUpdateServlet extends HttpServlet {

    @EJB
    GuildManagerLocal guildManager;

    @EJB
    MembershipManagerLocal membershipManager;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameterMap().containsKey("id")) {
            String guildId = req.getParameter("id");
            Guild guild = guildManager.getGuildById(Integer.parseInt(guildId));
            int numberOfUser = membershipManager.getNumberOfMembershipsForGuild(guild.getId());
            List<Membership> memberships = membershipManager.getMembershipsByGuildIdWithPage(Integer.parseInt(guildId), 0);

            req.setAttribute("numberOfPage", ((numberOfUser - 1) / 25) + 1);
            req.setAttribute("guild", guild);
            req.setAttribute("memberships", memberships);

            req.getRequestDispatcher("/WEB-INF/pages/admin/admin_guilds_update.jsp").forward(req, resp);
        } else {
            req.getRequestDispatcher("/WEB-INF/pages/error_404.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameterMap().containsKey("updateGuild")) {
            updateGuild(req, resp);
        } else if (req.getParameterMap().containsKey("page")) {
            updatePagination(req, resp);
        }
    }

    private void updateGuild(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameterMap().containsKey("id")) {
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
        } else {
            req.getRequestDispatcher("/WEB-INF/pages/error_404.jsp").forward(req, resp);
        }

    }

    private void updatePagination(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameterMap().containsKey("guildId")) {
            int pageNumber = Integer.parseInt(req.getParameter("page"));
            int guildId = Integer.parseInt(req.getParameter("guildId"));
            StringBuilder table = new StringBuilder();
            List<Membership> memberships = membershipManager.getMembershipsByGuildIdWithPage(guildId, pageNumber - 1);
            int i = 1;
            for (Membership membership : memberships) {

                String line = String.format("<tr style=\"background-color: black\"><td>%d</td><td><h5><a href=\"%s/profile?id=%d\">%s</a></h5></td><td><h5>%s</h5></td>" +
                                "<td><a href=\"%s/admin/guilds/memberships/delete?id=%d&guildId=%d\"><i class=\"fas fa-trash-alt\"></i></a></td></tr>",
                        i, req.getContextPath(), membership.getCharacter().getId(), membership.getCharacter().getName(), membership.getRank(), req.getContextPath(), membership.getId(), guildId);
                table.append(line);
                i++;
            }

            PrintWriter out = resp.getWriter();
            out.print(table.toString());
            out.flush();
            out.close();
        } else {
            req.getRequestDispatcher("/WEB-INF/pages/error_404.jsp").forward(req, resp);
        }
    }
}
