package ch.heigvd.amt.projectOne.presentation;

import ch.heigvd.amt.projectOne.model.Character;
import ch.heigvd.amt.projectOne.model.Class;
import ch.heigvd.amt.projectOne.services.dao.CharacterManagerLocal;
import ch.heigvd.amt.projectOne.services.dao.ClassManagerLocal;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClassesServletTest {

    @Mock
    Class myClass;

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    ClassManagerLocal classManager;

    @Mock
    RequestDispatcher requestDispatcher;


    private ClassesServlet servlet;

    private List<Class> classes;

    @BeforeEach
    void setup() {

        servlet = new ClassesServlet();
        servlet.classManager = classManager;
        classes = new ArrayList<>();
        when(request.getRequestDispatcher("/WEB-INF/pages/classes.jsp")).thenReturn(requestDispatcher);
        classes.add(myClass);
        classes.add(myClass);
        classes.add(myClass);
        classes.add(myClass);
        classes.add(myClass);
        classes.add(myClass);
    }

    @Test
    void weShouldBeAbleToGetAllClasses() throws ServletException, IOException {
        when(classManager.fetchAllClass()).thenReturn(classes);

        servlet.doGet(request, response);
        verify(request, atLeastOnce()).setAttribute("classes", classes);
        verify(requestDispatcher, atLeastOnce()).forward(request, response);

    }
}