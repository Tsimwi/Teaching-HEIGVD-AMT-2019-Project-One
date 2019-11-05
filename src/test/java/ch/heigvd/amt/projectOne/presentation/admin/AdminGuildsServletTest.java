package ch.heigvd.amt.projectOne.presentation.admin;

import ch.heigvd.amt.projectOne.model.Guild;
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
class AdminGuildsServletTest {

    @Mock
    Guild guild;

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    GuildManagerLocal guildManager;

    @Mock
    RequestDispatcher requestDispatcher;

    private AdminGuildsServlet servlet;

    private List<Guild> guilds;

    @BeforeEach
    void setUp() {
        servlet = new AdminGuildsServlet();
        servlet.guildManager = guildManager;
        guilds = new ArrayList<>();
        guilds.add(guild);
        guilds.add(guild);
    }

    @Test
    void itShouldBePossibleToLoadThePage() throws ServletException, IOException {
        when(request.getRequestDispatcher("/WEB-INF/pages/admin/admin_guilds.jsp")).thenReturn(requestDispatcher);
        when(guildManager.getAllGuilds()).thenReturn(guilds);
        servlet.doGet(request, response);
        verify(request, atLeastOnce()).setAttribute("guilds", guilds);
        verify(requestDispatcher, atLeastOnce()).forward(request, response);
    }
}