package ch.heigvd.amt.projectOne.presentation;

import ch.heigvd.amt.projectOne.model.Character;
import ch.heigvd.amt.projectOne.model.Membership;
import ch.heigvd.amt.projectOne.services.dao.CharacterManagerLocal;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProfileServletTest {

    @Mock
    Character character;

    @Mock
    Membership membership;

    @Mock
    HttpServletRequest request;;

    @Mock
    HttpServletResponse response;

    @Mock
    CharacterManagerLocal characterManager;

    @Mock
    MembershipManagerLocal membershipManager;

    @Mock
    RequestDispatcher requestDispatcher;

    @Mock
    HttpSession session;

    @Mock
    Map<String, String[]> map;

    private List<Membership> memberships;
    private ProfileServlet servlet;

    @BeforeEach
    void setUp() {
        servlet = new ProfileServlet();
        servlet.characterManager = characterManager;
        servlet.membershipManager = membershipManager;
        memberships = new ArrayList<>();
        when(request.getParameterMap()).thenReturn(map);
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher("/WEB-INF/pages/profile.jsp")).thenReturn(requestDispatcher);
        memberships.add(membership);
        memberships.add(membership);
        memberships.add(membership);

    }

    @Test
    void WeShouldBeAbleToDisplayMyProfile() throws ServletException, IOException {
        when(request.getParameterMap().containsKey("id")).thenReturn(false);
        when(session.getAttribute("character")).thenReturn(character);
        when(character.getId()).thenReturn(1);
        when(membershipManager.getMembershipsByUserId(character.getId())).thenReturn(memberships);

        servlet.doGet(request, response);

        verify(request, atLeastOnce()).setAttribute("character", character);
        verify(request, atLeastOnce()).setAttribute("memberships", memberships);
        verify(requestDispatcher, atLeastOnce()).forward(request, response);

    }

    @Test
    void WeShouldBeAbleToDisplayProfileOfOtherUser() throws ServletException, IOException {
        when(request.getParameterMap().containsKey("id")).thenReturn(true);
        when(request.getParameter("id")).thenReturn("1");
        when(characterManager.getCharacterById(1)).thenReturn(character);
        when(character.getId()).thenReturn(1);
        when(membershipManager.getMembershipsByUserId(character.getId())).thenReturn(memberships);

        servlet.doGet(request, response);

        verify(request, atLeastOnce()).setAttribute("character", character);
        verify(request, atLeastOnce()).setAttribute("memberships", memberships);
        verify(requestDispatcher, atLeastOnce()).forward(request, response);
    }
}