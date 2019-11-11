package ch.heigvd.amt.projectOne.presentation;

import ch.heigvd.amt.projectOne.model.Character;
import ch.heigvd.amt.projectOne.model.Guild;
import ch.heigvd.amt.projectOne.model.Membership;
import ch.heigvd.amt.projectOne.presentation.admin.AdminCharactersAddServlet;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

class GuildInfoServletTest {

    @Mock
    Guild guild;

    @Mock
    Character character;

    @Mock
    Membership membership;

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    MembershipManagerLocal membershipManager;

    @Mock
    RequestDispatcher requestDispatcher;

    @Mock
    PrintWriter printWriter;

    @Mock
    GuildManagerLocal guildManager;

    @Mock
    HttpSession session;

    @Mock
    Map<String, String[]> map;

    private List<Membership> memberships;
    private GuildInfoServlet servlet;

    @BeforeEach
    void setUp() {
        servlet = new GuildInfoServlet();
        servlet.membershipManager = membershipManager;
        servlet.guildManager = guildManager;
        memberships = new ArrayList<>();
        memberships.add(membership);
    }

    @Test
    void itShouldBePossibleToLoadThePage() throws IOException, ServletException {
        when(request.getRequestDispatcher("/WEB-INF/pages/guild_info.jsp")).thenReturn(requestDispatcher);
        when(request.getParameterMap()).thenReturn(map);
        when(request.getParameterMap().containsKey("id")).thenReturn(true);
        when(guildManager.getNumberOfGuild()).thenReturn(5);
        when(request.getParameter("id")).thenReturn("1");
        when(request.getSession()).thenReturn(session);
        when(guild.getId()).thenReturn(1);
        when(guildManager.getGuildById(1)).thenReturn(guild);
        when(membershipManager.checkCharacterMembership(any(), any())).thenReturn(true);
        when(membershipManager.getNumberOfMembershipsForGuild(1)).thenReturn(0);
        when(membershipManager.getMembershipsByGuildIdWithPage(1,0)).thenReturn(memberships);
        servlet.doGet(request, response);

        verify(request, atLeastOnce()).setAttribute("numberOfPage", ((servlet.numberOfUser - 1) / 25) + 1);
        verify(request, atLeastOnce()).setAttribute("memberships", memberships);
        verify(request, atLeastOnce()).setAttribute("currentCharMembership", true);
        verify(request, atLeastOnce()).setAttribute("guild", guild);
        verify(requestDispatcher, atLeastOnce()).forward(request, response);

    }

    @Test
    void itShouldBePossibleToAskOtherMembershipWithPage() throws ServletException, IOException {
        when(request.getParameter("page")).thenReturn("1");
        when(request.getParameter("guildId")).thenReturn("1");
        when(membershipManager.getMembershipsByGuildIdWithPage(1,0)).thenReturn(memberships);
        when(membership.getCharacter()).thenReturn(character);
        when(character.getId()).thenReturn(1);
        when(response.getWriter()).thenReturn(printWriter);
        servlet.doPost(request, response);
        verify(response, atLeastOnce()).getWriter();
    }
}