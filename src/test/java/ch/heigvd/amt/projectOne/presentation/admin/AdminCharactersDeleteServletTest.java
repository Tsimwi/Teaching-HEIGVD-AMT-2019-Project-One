package ch.heigvd.amt.projectOne.presentation.admin;

import ch.heigvd.amt.projectOne.services.dao.CharacterManagerLocal;
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
class AdminCharactersDeleteServletTest {

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    RequestDispatcher requestDispatcher;

    @Mock
    CharacterManagerLocal characterManager;
    @Mock
    Map<String, String[]> map;

    @Mock
    HttpSession session;

    private AdminCharactersDeleteServlet servlet;

    @BeforeEach
    void setUp() {
        servlet = new AdminCharactersDeleteServlet();
        servlet.characterManager = characterManager;
        when(request.getParameterMap()).thenReturn(map);
    }

    @Test
    void weShouldBeAbleToDeleteCharacter() throws ServletException, IOException {
        when(request.getParameterMap().containsKey("id")).thenReturn(true);
        when(request.getParameter("id")).thenReturn("1");
        when(characterManager.deleteCharacter(1)).thenReturn(true);
        when(request.getSession()).thenReturn(session);
        servlet.doGet(request, response);

        verify(session, atLeastOnce()).setAttribute("deleteStatus", "User deleted");
        verify(response, atLeastOnce()).sendRedirect(request.getContextPath() + "/admin/characters");

    }

    @Test
    void weShouldNotBeAbleToDeleteCharacterBecauseQueryFailed() throws ServletException, IOException {
        when(request.getParameterMap().containsKey("id")).thenReturn(true);
        when(request.getParameter("id")).thenReturn("1");
        when(characterManager.deleteCharacter(1)).thenReturn(false);
        when(request.getSession()).thenReturn(session);
        servlet.doGet(request, response);

        verify(session, atLeastOnce()).setAttribute("deleteStatus", "Unable to delete the user");
        verify(response, atLeastOnce()).sendRedirect(request.getContextPath() + "/admin/characters");

    }
    @Test
    void weShouldNotBeAbleToDeleteCharacterBecauseIdMissing() throws ServletException, IOException {
        when(request.getParameterMap().containsKey("id")).thenReturn(false);
        servlet.doGet(request, response);

        verify(response, atLeastOnce()).sendRedirect(request.getContextPath() + "/admin/characters");

    }
}