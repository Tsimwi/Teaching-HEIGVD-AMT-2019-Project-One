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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminCharactersAddServletTest {

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    RequestDispatcher requestDispatcher;

    @Mock
    CharacterManagerLocal characterManager;

    private AdminCharactersAddServlet servlet;

    private List<String> errors;

    @BeforeEach
    void setUp() {
        servlet = new AdminCharactersAddServlet();
        servlet.characterManager = characterManager;
        errors = new ArrayList<>();
    }

    @Test
    void weShouldBeAbleToSeeThePage() throws ServletException, IOException {
        when(request.getRequestDispatcher("/WEB-INF/pages/admin/admin_characters_add.jsp")).thenReturn(requestDispatcher);
        servlet.doGet(request, response);
        verify(requestDispatcher, atLeastOnce()).forward(request, response);
    }

    @Test
    void WeShouldBeAbleToAddAdminCharacter() throws ServletException, IOException {
        when(request.getParameter("name")).thenReturn("test");
        when(request.getParameter("password")).thenReturn("password");
        when(request.getParameter("passwordVerify")).thenReturn("password");
        when(request.getParameter("isAdminCheckbox")).thenReturn("on");
        when(characterManager.isUsernameFree(any())).thenReturn(true);
        when(characterManager.addCharacter("test", "password", true)).thenReturn(true);
        servlet.doPost(request, response);

        verify(response, atLeastOnce()).sendRedirect(request.getContextPath() + "/admin/characters");
    }

    @Test
    void WeShouldBeAbleToAddCharacter() throws ServletException, IOException {
        when(request.getParameter("name")).thenReturn("test");
        when(request.getParameter("password")).thenReturn("password");
        when(request.getParameter("passwordVerify")).thenReturn("password");
        when(request.getParameter("isAdminCheckbox")).thenReturn(null);
        when(characterManager.isUsernameFree(any())).thenReturn(true);
        when(characterManager.addCharacter("test", "password", servlet.isAdminBool)).thenReturn(true);
        servlet.doPost(request, response);

        verify(response, atLeastOnce()).sendRedirect(request.getContextPath() + "/admin/characters");
    }

    @Test
    void addCharacterShouldFailedBecauseUsernameIsEmpty() throws ServletException, IOException {
        when(request.getRequestDispatcher("/WEB-INF/pages/admin/admin_characters_add.jsp")).thenReturn(requestDispatcher);
        errors.add("Username cannot be empty");
        when(request.getParameter("name")).thenReturn("");
        when(request.getParameter("password")).thenReturn("password");
        when(request.getParameter("passwordVerify")).thenReturn("password");
        when(request.getParameter("isAdminCheckbox")).thenReturn(null);
        when(characterManager.isUsernameFree(any())).thenReturn(true);
        servlet.doPost(request, response);

        verify(request, atLeastOnce()).setAttribute("errors", errors);
        verify(requestDispatcher, atLeastOnce()).forward(request, response);
    }

    @Test
    void addCharacterShouldFailedBecausePasswordIsEmpty() throws ServletException, IOException {
        when(request.getRequestDispatcher("/WEB-INF/pages/admin/admin_characters_add.jsp")).thenReturn(requestDispatcher);
        errors.add("Password cannot be empty");
        when(request.getParameter("name")).thenReturn("test");
        when(request.getParameter("password")).thenReturn("");
        when(request.getParameter("passwordVerify")).thenReturn("password");
        when(request.getParameter("isAdminCheckbox")).thenReturn(null);
        when(characterManager.isUsernameFree(any())).thenReturn(true);
        servlet.doPost(request, response);

        verify(request, atLeastOnce()).setAttribute("errors", errors);
        verify(requestDispatcher, atLeastOnce()).forward(request, response);
    }

    @Test
    void addCharacterShouldFailedBecausePasswordDoesNotMatch() throws ServletException, IOException {
        when(request.getRequestDispatcher("/WEB-INF/pages/admin/admin_characters_add.jsp")).thenReturn(requestDispatcher);
        errors.add("Password are not the same");
        when(request.getParameter("name")).thenReturn("test");
        when(request.getParameter("password")).thenReturn("password");
        when(request.getParameter("passwordVerify")).thenReturn("password1");
        when(request.getParameter("isAdminCheckbox")).thenReturn(null);
        when(characterManager.isUsernameFree(any())).thenReturn(true);
        servlet.doPost(request, response);

        verify(request, atLeastOnce()).setAttribute("errors", errors);
        verify(requestDispatcher, atLeastOnce()).forward(request, response);
    }

    @Test
    void addCharacterShouldFailedBecauseUsernameIsNotFree() throws ServletException, IOException {
        when(request.getRequestDispatcher("/WEB-INF/pages/admin/admin_characters_add.jsp")).thenReturn(requestDispatcher);
        errors.add("This name is already taken");
        when(request.getParameter("name")).thenReturn("test");
        when(request.getParameter("password")).thenReturn("password");
        when(request.getParameter("passwordVerify")).thenReturn("password");
        when(request.getParameter("isAdminCheckbox")).thenReturn(null);
        when(characterManager.isUsernameFree(any())).thenReturn(false);
        servlet.doPost(request, response);

        verify(request, atLeastOnce()).setAttribute("errors", errors);
        verify(requestDispatcher, atLeastOnce()).forward(request, response);
    }

    @Test
    void addCharacterShouldFailedBecauseUnableToAddInDatabase() throws ServletException, IOException {
        when(request.getRequestDispatcher("/WEB-INF/pages/admin/admin_characters_add.jsp")).thenReturn(requestDispatcher);
        errors.add("Unable to create Character, contact an administrator");
        when(request.getParameter("name")).thenReturn("test");
        when(request.getParameter("password")).thenReturn("password");
        when(request.getParameter("passwordVerify")).thenReturn("password");
        when(request.getParameter("isAdminCheckbox")).thenReturn(null);
        when(characterManager.isUsernameFree(any())).thenReturn(true);
        when(characterManager.addCharacter("test", "password", servlet.isAdminBool)).thenReturn(false);
        servlet.doPost(request, response);

        verify(request, atLeastOnce()).setAttribute("errors", errors);
        verify(requestDispatcher, atLeastOnce()).forward(request, response);
    }
}