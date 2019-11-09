package ch.heigvd.amt.projectOne.presentation.admin;

import ch.heigvd.amt.projectOne.model.Guild;
import ch.heigvd.amt.projectOne.model.Membership;
import ch.heigvd.amt.projectOne.services.dao.GuildManagerLocal;
import ch.heigvd.amt.projectOne.services.dao.MembershipManagerLocal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminGuildsUpdateServletTest {

    @Mock
    Guild guild;

    @Mock
    Membership membership;

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    GuildManagerLocal guildManager;

    @Mock
    MembershipManagerLocal membershipManager;

    @Mock
    RequestDispatcher requestDispatcher;

    @Mock
    Map<String, String[]> map;

    private AdminGuildsUpdateServlet servlet;

    private List<String> errors;
    private List<Membership> memberships;

    @BeforeEach
    void setUp() {
        servlet = new AdminGuildsUpdateServlet();
        servlet.guildManager = guildManager;
        servlet.membershipManager = membershipManager;
        errors = new ArrayList<>();
        memberships = new ArrayList<>();
        memberships.add(membership);
        memberships.add(membership);
        memberships.add(membership);
        when(request.getParameterMap()).thenReturn(map);
    }

    @Test
    void itShouldBePossibleToLoadThePage() throws ServletException, IOException {
        when(request.getRequestDispatcher("/WEB-INF/pages/admin/admin_guilds_update.jsp")).thenReturn(requestDispatcher);
        when(request.getParameterMap().containsKey("id")).thenReturn(true);
        when(request.getParameterMap().containsKey("guildId")).thenReturn(true);
        when(request.getParameter("id")).thenReturn("1");

        when(guildManager.getGuildById(1)).thenReturn(guild);
        when(membershipManager.getMembershipsByGuildIdWithPage(1, 0)).thenReturn(memberships);
        servlet.doGet(request, response);

        verify(request, atLeastOnce()).setAttribute("guild", guild);
        verify(request, atLeastOnce()).setAttribute("memberships", memberships);
        verify(requestDispatcher, atLeastOnce()).forward(request, response);
    }

    @Test
    void itShouldBePossibleToUpdateAGuild() throws ServletException, IOException {
        when(request.getParameterMap()).thenReturn(map);
        when(request.getParameterMap().containsKey("updateGuild")).thenReturn(true);
        when(request.getParameterMap().containsKey("id")).thenReturn(true);
        when(request.getParameterMap().containsKey("guildId")).thenReturn(true);
        when(request.getParameter("id")).thenReturn("1");
        when(guildManager.getGuildById(1)).thenReturn(guild);
        when(guild.getName()).thenReturn("Guild's name");
        when(request.getParameter("name")).thenReturn("Guild's name");
        when(request.getParameter("description")).thenReturn("Guild's description");
        when(guildManager.updateGuild(any())).thenReturn(true);
        servlet.doPost(request, response);
        verify(response, atLeastOnce()).sendRedirect(request.getContextPath() + "/admin/guilds");
    }

    @Test
    void itShouldFailedBecauseNameIsMissing() throws ServletException, IOException {
        when(request.getRequestDispatcher("/WEB-INF/pages/admin/admin_guilds_update.jsp")).thenReturn(requestDispatcher);
        when(request.getParameterMap()).thenReturn(map);
        when(request.getParameterMap().containsKey("updateGuild")).thenReturn(true);
        when(request.getParameterMap().containsKey("id")).thenReturn(true);
        when(request.getParameterMap().containsKey("guildId")).thenReturn(true);

        when(request.getParameter("id")).thenReturn("1");
        when(guildManager.getGuildById(1)).thenReturn(guild);

        when(guild.getName()).thenReturn("Guild's name");
        when(request.getParameter("name")).thenReturn("");
        errors.add("Guild name cannot be empty");
        when(request.getParameter("description")).thenReturn("Guild's description");
        when(guildManager.isNameFree(any())).thenReturn(true);

        servlet.doPost(request, response);
        verify(request, atLeastOnce()).setAttribute("errors", errors);
        verify(requestDispatcher, atLeastOnce()).forward(request, response);
    }

    @Test
    void itShouldFailedBecauseQueryFailed() throws ServletException, IOException {
        when(request.getRequestDispatcher("/WEB-INF/pages/admin/admin_guilds_update.jsp")).thenReturn(requestDispatcher);
        when(request.getParameterMap()).thenReturn(map);
        when(request.getParameterMap().containsKey("updateGuild")).thenReturn(true);
        when(request.getParameterMap().containsKey("id")).thenReturn(true);
        when(request.getParameterMap().containsKey("guildId")).thenReturn(true);

        when(request.getParameter("id")).thenReturn("1");
        when(guildManager.getGuildById(1)).thenReturn(guild);

        when(guild.getName()).thenReturn("Guild's name");
        when(request.getParameter("name")).thenReturn("Guild's name");
        errors.add("Unable to update the guild");
        when(request.getParameter("description")).thenReturn("Guild's description");
        when(guildManager.updateGuild(any())).thenReturn(false);

        servlet.doPost(request, response);
        verify(request, atLeastOnce()).setAttribute("errors", errors);
        verify(requestDispatcher, atLeastOnce()).forward(request, response);
    }
}