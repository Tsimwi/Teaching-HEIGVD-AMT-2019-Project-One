package ch.heigvd.amt.projectOne.presentation.admin;

import ch.heigvd.amt.projectOne.services.dao.CharacterManagerLocal;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminGuildsAddServletTest {

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    GuildManagerLocal guildManager;

    @Mock
    RequestDispatcher requestDispatcher;

    private AdminGuildsAddServlet servlet;

    private List<String> errors;

    @BeforeEach
    void setUp() {
        servlet = new AdminGuildsAddServlet();
        servlet.guildManager = guildManager;
        errors = new ArrayList<>();
    }

    @Test
    void weShouldSeeThePageToAddAGuild() throws ServletException, IOException {
        when(request.getRequestDispatcher("/WEB-INF/pages/admin/admin_guilds_add.jsp")).thenReturn(requestDispatcher);
        servlet.doGet(request, response);
        verify(requestDispatcher, atLeastOnce()).forward(request, response);
    }

    @Test
    void itShouldBePossibleToAddAGuild() throws IOException, ServletException {
        when(request.getParameter("name")).thenReturn("Guild's name");
        when(request.getParameter("description")).thenReturn("Guild's description");
        when(guildManager.isNameFree(any())).thenReturn(true);
        when(guildManager.addGuild(any())).thenReturn(true);
        servlet.doPost(request, response);
        verify(response, atLeastOnce()).sendRedirect(request.getContextPath() + "/admin/guilds");

    }

    @Test
    void itShouldFailedToAddGuildBecauseNameIsMissing() throws IOException, ServletException {
        when(request.getRequestDispatcher("/WEB-INF/pages/admin/admin_guilds_add.jsp")).thenReturn(requestDispatcher);
        errors.add("Name cannot be empty");
        when(request.getParameter("name")).thenReturn("");
        when(request.getParameter("description")).thenReturn("Guild's description");
        when(guildManager.isNameFree(any())).thenReturn(true);
        servlet.doPost(request, response);
        verify(request, atLeastOnce()).setAttribute("errors", errors);
        verify(requestDispatcher, atLeastOnce()).forward(request, response);
    }

    @Test
    void itShouldFailedToAddGuildBecauseDescriptionIsMissing() throws IOException, ServletException {
        when(request.getRequestDispatcher("/WEB-INF/pages/admin/admin_guilds_add.jsp")).thenReturn(requestDispatcher);
        errors.add("Description cannot be empty");
        when(request.getParameter("name")).thenReturn("Guild's name");
        when(request.getParameter("description")).thenReturn("");
        when(guildManager.isNameFree(any())).thenReturn(true);
        servlet.doPost(request, response);
        verify(request, atLeastOnce()).setAttribute("errors", errors);
        verify(requestDispatcher, atLeastOnce()).forward(request, response);
    }

    @Test
    void itShouldFailedToAddGuildBecauseNameIsNotFree() throws IOException, ServletException {
        when(request.getRequestDispatcher("/WEB-INF/pages/admin/admin_guilds_add.jsp")).thenReturn(requestDispatcher);
        errors.add("This name is already taken");
        when(request.getParameter("name")).thenReturn("Guild's name");
        when(request.getParameter("description")).thenReturn("Guild's description");
        when(guildManager.isNameFree(any())).thenReturn(false);
        servlet.doPost(request, response);
        verify(request, atLeastOnce()).setAttribute("errors", errors);
        verify(requestDispatcher, atLeastOnce()).forward(request, response);
    }

    @Test
    void itShouldFailedToAddGuildBecauseInsertQueryFailed() throws IOException, ServletException {
        when(request.getRequestDispatcher("/WEB-INF/pages/admin/admin_guilds_add.jsp")).thenReturn(requestDispatcher);
        errors.add("Unable to create Character, contact an administrator");
        when(request.getParameter("name")).thenReturn("Guild's name");
        when(request.getParameter("description")).thenReturn("Guild's description");
        when(guildManager.isNameFree(any())).thenReturn(true);
        when(guildManager.addGuild(any())).thenReturn(false);
        servlet.doPost(request, response);
        verify(request, atLeastOnce()).setAttribute("errors", errors);
        verify(requestDispatcher, atLeastOnce()).forward(request, response);
    }
}