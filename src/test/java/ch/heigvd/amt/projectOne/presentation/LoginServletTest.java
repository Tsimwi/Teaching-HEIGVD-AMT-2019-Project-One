package ch.heigvd.amt.projectOne.presentation;

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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginServletTest {

    @Mock
    ch.heigvd.amt.projectOne.model.Character character;

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    CharacterManagerLocal characterManager;

    @Mock
    RequestDispatcher requestDispatcher;

    @Mock
    HttpSession session;

    private List<String> errors;

    private LoginServlet servlet;

    @BeforeEach
    public void setup() throws IOException {
        errors = new ArrayList<>();
        servlet = new LoginServlet();
        servlet.characterManager = characterManager;
    }

    @Test
    void doGet() throws ServletException, IOException {
        when(request.getRequestDispatcher("/WEB-INF/pages/login.jsp")).thenReturn(requestDispatcher);

        servlet.doGet(request, response);
        verify(requestDispatcher, atLeastOnce()).forward(request, response);
    }

    @Test
    void weShouldBEAbleToLogin() throws ServletException, IOException, SQLException {
        when(request.getParameter("username")).thenReturn("test");
        when(request.getParameter("password")).thenReturn("test");
        when(characterManager.isUsernameFree(any())).thenReturn(false);
        when(characterManager.checkPassword(any(), any())).thenReturn(true);
        when(characterManager.getCharacterByUsername(any())).thenReturn(character);
        when(request.getSession()).thenReturn(session);
        servlet.doPost(request, response);
        verify(session, atLeastOnce()).setAttribute("character", character);
        verify(response, atLeastOnce()).sendRedirect(request.getContextPath() + "/home");
    }


    @Test
    void loginShouldFailBecauseUsernameIsEmpty() throws ServletException, IOException {

        errors.add("Username cannot be empty.");
        when(request.getRequestDispatcher("/WEB-INF/pages/login.jsp")).thenReturn(requestDispatcher);
        when(request.getParameter("username")).thenReturn("");
        when(request.getParameter("password")).thenReturn("test");
        servlet.doPost(request, response);
        verify(request, atLeastOnce()).setAttribute("errors", errors);
        verify(requestDispatcher, atLeastOnce()).forward(request, response);
    }

    @Test
    void loginShouldFailBecausePasswordIsEmpty() throws ServletException, IOException {
        errors.add("Password cannot be empty.");
        when(request.getRequestDispatcher("/WEB-INF/pages/login.jsp")).thenReturn(requestDispatcher);
        when(request.getParameter("username")).thenReturn("test");
        when(request.getParameter("password")).thenReturn("");
        servlet.doPost(request, response);
        verify(request, atLeastOnce()).setAttribute("errors", errors);
        verify(requestDispatcher, atLeastOnce()).forward(request, response);
    }

    @Test
    void loginShouldFailedBecauseUsernameNotInBd() throws ServletException, IOException, SQLException {
        errors.add("Wrong username or password.");
        when(request.getRequestDispatcher("/WEB-INF/pages/login.jsp")).thenReturn(requestDispatcher);
        when(request.getParameter("username")).thenReturn("test");
        when(request.getParameter("password")).thenReturn("test");
        when(characterManager.isUsernameFree(any())).thenReturn(true);
        servlet.doPost(request, response);
        verify(request, atLeastOnce()).setAttribute("errors", errors);
        verify(requestDispatcher, atLeastOnce()).forward(request, response);
    }

    @Test
    void loginShouldFailedBecausePasswordIsNotCorrect() throws ServletException, IOException, SQLException {
        errors.add("Wrong username or password.");
        when(request.getRequestDispatcher("/WEB-INF/pages/login.jsp")).thenReturn(requestDispatcher);
        when(request.getParameter("username")).thenReturn("test");
        when(request.getParameter("password")).thenReturn("test");
        when(characterManager.isUsernameFree(any())).thenReturn(false);
        when(characterManager.checkPassword(any(), any())).thenReturn(false);
        servlet.doPost(request, response);
        verify(request, atLeastOnce()).setAttribute("errors", errors);
        verify(requestDispatcher, atLeastOnce()).forward(request, response);
    }
}