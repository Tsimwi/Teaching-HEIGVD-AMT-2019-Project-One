package ch.heigvd.amt.projectOne.presentation.admin;

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
import java.util.Map;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminMembershipDeleteServletTest {
    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    MembershipManagerLocal membershipManager;
    @Mock
    Map<String, String[]> map;

    private AdminMembershipDeleteServlet servlet;


    @BeforeEach
    void setUp() {
        servlet = new AdminMembershipDeleteServlet();
        servlet.membershipManager = membershipManager;
        when(request.getParameterMap()).thenReturn(map);

    }

    @Test
    void itShouldBePossibleToDeleteAMembership() throws IOException, ServletException {
        when(request.getParameterMap().containsKey("id")).thenReturn(true);
        when(request.getParameterMap().containsKey("guildId")).thenReturn(true);
        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("guildId")).thenReturn("1");
        servlet.doGet(request, response);

        verify(response, atLeastOnce()).sendRedirect(request.getContextPath()+"/admin/guilds/update?id="+1);
    }

    @Test
    void itShouldFailedBecauseIdIsMissing() throws IOException, ServletException {
        when(request.getParameterMap().containsKey("id")).thenReturn(false);
        servlet.doGet(request, response);

        verify(response, atLeastOnce()).sendRedirect(request.getContextPath()+"/admin/guilds");
    }

    @Test
    void itShouldFailedBecauseGuildIdIsMissing() throws IOException, ServletException {
        when(request.getParameterMap().containsKey("id")).thenReturn(true);
        when(request.getParameterMap().containsKey("guildId")).thenReturn(false);
        servlet.doGet(request, response);

        verify(response, atLeastOnce()).sendRedirect(request.getContextPath()+"/admin/guilds");
    }
}