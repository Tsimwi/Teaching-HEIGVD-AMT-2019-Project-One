package ch.heigvd.amt.projectOne.presentation.admin;

import ch.heigvd.amt.projectOne.model.Character;
import ch.heigvd.amt.projectOne.model.Guild;
import ch.heigvd.amt.projectOne.model.Membership;
import ch.heigvd.amt.projectOne.services.dao.CharacterManagerLocal;
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

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminCharactersUpdateServletTest {

    @Mock
    Character character;

    @Mock
    Membership membership;

    @Mock
    Guild guild;

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    private CharacterManagerLocal characterManager;

    @Mock
    private MembershipManagerLocal membershipManager;

    @Mock
    private GuildManagerLocal guildManager;

    @Mock
    RequestDispatcher requestDispatcher;

    @Mock
    Map<String, String[]> map;

    private AdminCharactersUpdateServlet servlet;

    private List<Membership> memberships;
    private List<Guild> guilds;

    @BeforeEach
    void setup() {

        servlet = new AdminCharactersUpdateServlet();
        servlet.characterManager = characterManager;
        servlet.membershipManager = membershipManager;
        servlet.guildManager = guildManager;
        memberships = new ArrayList<>();
        guilds = new ArrayList<>();
        memberships.add(membership);
        memberships.add(membership);
        guilds.add(guild);
        guilds.add(guild);
    }

    @Test
    void weShouldSeeThePageToUpdateCharacter() throws ServletException, IOException {
        when(request.getRequestDispatcher("/WEB-INF/pages/admin/admin_characters_update.jsp")).thenReturn(requestDispatcher);
        when(request.getParameter("id")).thenReturn("1");
        when(characterManager.getCharacterById(1)).thenReturn(character);
        when(membershipManager.getMembershipsByUserId(1)).thenReturn(memberships);
        when(guildManager.getAllGuilds()).thenReturn(guilds);
        servlet.doGet(request, response);

        verify(request, atLeastOnce()).setAttribute("character", character);
        verify(request, atLeastOnce()).setAttribute("memberships", memberships);
        verify(request, atLeastOnce()).setAttribute("guilds", guilds);
        verify(requestDispatcher, atLeastOnce()).forward(request, response);
    }

    @Test
    void itShouldBePossibleToUpdateMembershipsOfACharacter() throws ServletException, IOException {
        String[] guildsSelected = {"1","2", "3"};
        when(request.getParameterMap()).thenReturn(map);
        when(request.getParameterMap().containsKey("updateMemberships")).thenReturn(true);
        when(request.getParameter("id")).thenReturn("1");
        when(membershipManager.getMembershipsByUserId(1)).thenReturn(memberships);
        when(request.getParameterMap().containsKey("membershipsSelect")).thenReturn(true);
        when(request.getParameterValues("membershipsSelect")).thenReturn(guildsSelected);
        when(membership.getGuild()).thenReturn(guild);
        when(membership.getGuild().getId()).thenReturn(1);
        servlet.doPost(request, response);
        verify(response, atLeastOnce()).sendRedirect(request.getContextPath() + "/admin/characters");
    }
}