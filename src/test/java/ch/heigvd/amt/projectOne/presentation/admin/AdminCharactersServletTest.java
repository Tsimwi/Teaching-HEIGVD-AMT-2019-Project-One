package ch.heigvd.amt.projectOne.presentation.admin;

import ch.heigvd.amt.projectOne.model.Character;
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
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminCharactersServletTest {

    @Mock
    Character character;

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    CharacterManagerLocal characterManager;

    @Mock
    RequestDispatcher requestDispatcher;

    @Mock
    Map<String, String[]> map;

    private AdminCharactersServlet servlet;

    private List<Character> characters;

    @BeforeEach
    void setup() {

        servlet = new AdminCharactersServlet();
        servlet.characterManager = characterManager;
        characters = new ArrayList<>();
        when(request.getParameterMap()).thenReturn(map);
        when(request.getRequestDispatcher("/WEB-INF/pages/admin/admin_characters.jsp")).thenReturn(requestDispatcher);
        characters.add(character);
        characters.add(character);
        characters.add(character);
        characters.add(character);
        characters.add(character);
    }

    @Test
    void charactersShouldBeListedDefault() throws ServletException, IOException {

        when(characterManager.countRows(any(), any())).thenReturn(5);
        when(characterManager.getCharactersByPage(0)).thenReturn(characters);

        servlet.doGet(request, response);

        verify(request, atLeastOnce()).setAttribute("numberOfPage", ((servlet.numberOfUser - 1) / 25) + 1);
        verify(request, atLeastOnce()).setAttribute("characters", characters);
        verify(requestDispatcher, atLeastOnce()).forward(request, response);

    }

    @Test
    void charactersShouldBeListedWithPageNumber() throws ServletException, IOException {
        when(request.getParameterMap().containsKey("page")).thenReturn(true);
        when(request.getParameter("page")).thenReturn("5");
        when(characterManager.countRows(any(), any())).thenReturn(5);
        when(characterManager.getCharactersByPage(4)).thenReturn(characters);

        servlet.doGet(request, response);

        verify(request, atLeastOnce()).setAttribute("numberOfPage", ((servlet.numberOfUser - 1) / 25) + 1);
        verify(request, atLeastOnce()).setAttribute("characters", characters);
        verify(requestDispatcher, atLeastOnce()).forward(request, response);

    }

    @Test
    void charactersShouldBeListedWithLetter() throws ServletException, IOException {

        when(request.getParameterMap().containsKey("page")).thenReturn(false);
        when(request.getParameterMap().containsKey("letter")).thenReturn(true);
        when(request.getParameter("letter")).thenReturn("a");
        when(characterManager.getCharactersByPattern("a",0)).thenReturn(characters);
        when(characterManager.countRows(any(), any())).thenReturn(5);

        servlet.doGet(request, response);

        verify(request, atLeastOnce()).setAttribute("numberOfPage", ((servlet.numberOfUser - 1) / 25) + 1);
        verify(request, atLeastOnce()).setAttribute("characters", characters);
        verify(requestDispatcher, atLeastOnce()).forward(request, response);

    }

    @Test
    void charactersShouldBeListedWithLetterAndPageNumber() throws ServletException, IOException {

        when(request.getParameterMap().containsKey("page")).thenReturn(true);
        when(request.getParameter("page")).thenReturn("5");

        when(request.getParameterMap().containsKey("letter")).thenReturn(true);
        when(request.getParameter("letter")).thenReturn("a");

        when(characterManager.getCharactersByPattern("a",4)).thenReturn(characters);
        when(characterManager.countRows(any(), any())).thenReturn(5);

        servlet.doGet(request, response);

        verify(request, atLeastOnce()).setAttribute("numberOfPage", ((servlet.numberOfUser - 1) / 25) + 1);
        verify(request, atLeastOnce()).setAttribute("characters", characters);
        verify(requestDispatcher, atLeastOnce()).forward(request, response);

    }

    @Test
    void charactersShouldBeListedWithSearchBar() throws ServletException, IOException {


        when(request.getParameterMap().containsKey("page")).thenReturn(false);
        when(request.getParameterMap().containsKey("letter")).thenReturn(false);

        when(request.getParameterMap().containsKey("searchBar")).thenReturn(true);
        when(request.getParameter("searchBar")).thenReturn("abc");

        when(characterManager.getCharactersByPattern("abc",0)).thenReturn(characters);
        when(characterManager.countRows(any(), any())).thenReturn(5);

        servlet.doGet(request, response);

        verify(request, atLeastOnce()).setAttribute("numberOfPage", ((servlet.numberOfUser - 1) / 25) + 1);
        verify(request, atLeastOnce()).setAttribute("characters", characters);
        verify(requestDispatcher, atLeastOnce()).forward(request, response);

    }

    @Test
    void charactersShouldBeListedWithSearchBarWithPage() throws ServletException, IOException {

        when(request.getParameterMap().containsKey("page")).thenReturn(true);
        when(request.getParameter("page")).thenReturn("5");

        when(request.getParameterMap().containsKey("letter")).thenReturn(false);

        when(request.getParameterMap().containsKey("searchBar")).thenReturn(true);
        when(request.getParameter("searchBar")).thenReturn("abc");

        when(characterManager.getCharactersByPattern("abc",4)).thenReturn(characters);
        when(characterManager.countRows(any(), any())).thenReturn(5);

        servlet.doGet(request, response);

        verify(request, atLeastOnce()).setAttribute("numberOfPage", ((servlet.numberOfUser - 1) / 25) + 1);
        verify(request, atLeastOnce()).setAttribute("characters", characters);
        verify(requestDispatcher, atLeastOnce()).forward(request, response);

    }

}