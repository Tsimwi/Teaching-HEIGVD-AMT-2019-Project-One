package ch.heigvd.amt.projectOne.presentation.admin;

import ch.heigvd.amt.projectOne.services.dao.GuildManagerLocal;
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
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminGuildsDeleteServletTest {

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    GuildManagerLocal guildManager;

    @Mock
    HttpSession session;

    @Mock
    Map<String, String[]> map;

    private AdminGuildsDeleteServlet servlet;

    @BeforeEach
    void setUp() {
        servlet = new AdminGuildsDeleteServlet();
        servlet.guildManager = guildManager;
        when(request.getParameterMap()).thenReturn(map);
    }

    @Test
    void itShouldBePossibleToDeleteAGuild() throws ServletException, IOException {
        when(request.getParameterMap().containsKey("id")).thenReturn(true);
        when(request.getParameter("id")).thenReturn("1");
        when(request.getSession()).thenReturn(session);
        when(guildManager.deleteGuild(any())).thenReturn(true);
        servlet.doGet(request, response);
        verify(session, atLeastOnce()).setAttribute("deleteStatus", "Guild deleted");
        verify(response, atLeastOnce()).sendRedirect(request.getContextPath() + "/admin/guilds");

    }

    @Test
    void itShouldFailedToDeleteAGuildBecauseQueryFailed() throws ServletException, IOException {
        when(request.getParameterMap().containsKey("id")).thenReturn(true);
        when(request.getParameter("id")).thenReturn("1");
        when(request.getSession()).thenReturn(session);
        when(guildManager.deleteGuild(any())).thenReturn(false);
        servlet.doGet(request, response);
        verify(session, atLeastOnce()).setAttribute("deleteStatus", "Unable to delete the guild");
        verify(response, atLeastOnce()).sendRedirect(request.getContextPath() + "/admin/guilds");
    }

    @Test
    void itShouldFailedBecauseIdIsMissing() throws ServletException, IOException {
        when(request.getParameterMap().containsKey("id")).thenReturn(false);

        servlet.doGet(request, response);
        verify(response, atLeastOnce()).sendRedirect(request.getContextPath() + "/admin/guilds");
    }
}