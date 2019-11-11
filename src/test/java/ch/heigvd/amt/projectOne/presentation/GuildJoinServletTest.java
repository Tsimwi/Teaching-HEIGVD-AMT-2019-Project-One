package ch.heigvd.amt.projectOne.presentation;

import ch.heigvd.amt.projectOne.model.Character;
import ch.heigvd.amt.projectOne.model.Membership;
import ch.heigvd.amt.projectOne.services.dao.MembershipManagerLocal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Map;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GuildJoinServletTest {

    @Mock
    Membership membership;

    @Mock
    Character character;

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    MembershipManagerLocal membershipManager;

    @Mock
    HttpSession session;

    @Mock
    Map<String, String[]> map;

    private GuildJoinServlet servlet;


    @BeforeEach
    void setUp() {
        servlet = new GuildJoinServlet();
        servlet.membershipManager = membershipManager;
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("character")).thenReturn(character);
        when(request.getParameterMap()).thenReturn(map);
    }

    @Test
    void itShouldBePossibleToJoinAGuild() throws IOException, ServletException {
        when(request.getParameterMap().containsKey("id")).thenReturn(true);
        when(request.getParameter("id")).thenReturn("1");

        servlet.doGet(request, response);
        verify(response, atLeastOnce()).sendRedirect(request.getContextPath() + "/profile");

    }
}