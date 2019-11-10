package ch.heigvd.amt.projectOne.presentation;

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

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegistrationServletTest {

    @Mock
    HttpServletRequest request;;

    @Mock
    HttpServletResponse response;

    @Mock
    CharacterManagerLocal characterManager;

    @Mock
    RequestDispatcher requestDispatcher;

    private RegistrationServlet servlet;

    private List<String> errors;



    @BeforeEach
    void setUp() {
        servlet = new RegistrationServlet();
        servlet.characterManager = characterManager;
        errors = new ArrayList<>();
    }

    @Test
    void WeShouldGetTheRegistrationPage() throws ServletException, IOException {
        when(request.getRequestDispatcher("/WEB-INF/pages/register.jsp")).thenReturn(requestDispatcher);
        servlet.doGet(request, response);
        verify(requestDispatcher, atLeastOnce()).forward(request, response);
    }

    @Test
    void WeShouldBeAbleToRegister() throws ServletException, IOException, SQLException {
        when(request.getParameter("name")).thenReturn("test");
        when(request.getParameter("password")).thenReturn("password");
        when(request.getParameter("passwordVerify")).thenReturn("password");
        when(characterManager.isUsernameFree(any())).thenReturn(true);
        when(characterManager.addCharacter("test", "password", false)).thenReturn(true);
        servlet.doPost(request, response);

        verify(response, atLeastOnce()).sendRedirect(request.getContextPath() + "/login");
    }

    @Test
    void registerShouldFailedBecauseUsernameIsEmpty() throws ServletException, IOException, SQLException {
        when(request.getRequestDispatcher("/WEB-INF/pages/register.jsp")).thenReturn(requestDispatcher);
        errors.add("Username cannot be empty");
        when(request.getParameter("name")).thenReturn("");
        when(request.getParameter("password")).thenReturn("password");
        when(request.getParameter("passwordVerify")).thenReturn("password");
        when(characterManager.isUsernameFree(any())).thenReturn(true);
        servlet.doPost(request, response);

        verify(request, atLeastOnce()).setAttribute("errors", errors);
        verify(requestDispatcher, atLeastOnce()).forward(request, response);
    }

    @Test
    void registerShouldFailedBecausePasswordIsEmpty() throws ServletException, IOException, SQLException {
        when(request.getRequestDispatcher("/WEB-INF/pages/register.jsp")).thenReturn(requestDispatcher);
        errors.add("Password cannot be empty");
        when(request.getParameter("name")).thenReturn("test");
        when(request.getParameter("password")).thenReturn("");
        when(request.getParameter("passwordVerify")).thenReturn("password");
        when(characterManager.isUsernameFree(any())).thenReturn(true);
        servlet.doPost(request, response);

        verify(request, atLeastOnce()).setAttribute("errors", errors);
        verify(requestDispatcher, atLeastOnce()).forward(request, response);
    }

    @Test
    void registerShouldFailedBecausePasswordDoesNotMatch() throws ServletException, IOException, SQLException {
        when(request.getRequestDispatcher("/WEB-INF/pages/register.jsp")).thenReturn(requestDispatcher);
        errors.add("Password are not the same");
        when(request.getParameter("name")).thenReturn("test");
        when(request.getParameter("password")).thenReturn("password");
        when(request.getParameter("passwordVerify")).thenReturn("password1");
        when(characterManager.isUsernameFree(any())).thenReturn(true);
        servlet.doPost(request, response);

        verify(request, atLeastOnce()).setAttribute("errors", errors);
        verify(requestDispatcher, atLeastOnce()).forward(request, response);
    }

    @Test
    void registerShouldFailedBecauseUsernameIsNotFree() throws ServletException, IOException, SQLException {
        when(request.getRequestDispatcher("/WEB-INF/pages/register.jsp")).thenReturn(requestDispatcher);
        errors.add("This name is already taken");
        when(request.getParameter("name")).thenReturn("test");
        when(request.getParameter("password")).thenReturn("password");
        when(request.getParameter("passwordVerify")).thenReturn("password");
        when(characterManager.isUsernameFree(any())).thenReturn(false);
        servlet.doPost(request, response);

        verify(request, atLeastOnce()).setAttribute("errors", errors);
        verify(requestDispatcher, atLeastOnce()).forward(request, response);
    }

    @Test
    void registerShouldFailedBecauseUnableToAddInDatabase() throws ServletException, IOException, SQLException {
        when(request.getRequestDispatcher("/WEB-INF/pages/register.jsp")).thenReturn(requestDispatcher);
        errors.add("Unable to create Character, contact an administrator");
        when(request.getParameter("name")).thenReturn("test");
        when(request.getParameter("password")).thenReturn("password");
        when(request.getParameter("passwordVerify")).thenReturn("password");
        when(characterManager.isUsernameFree(any())).thenReturn(true);
        when(characterManager.addCharacter("test", "password", false)).thenReturn(false);
        servlet.doPost(request, response);

        verify(request, atLeastOnce()).setAttribute("errors", errors);
        verify(requestDispatcher, atLeastOnce()).forward(request, response);
    }


}