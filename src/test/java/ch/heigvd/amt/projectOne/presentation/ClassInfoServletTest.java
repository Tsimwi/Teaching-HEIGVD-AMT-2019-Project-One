package ch.heigvd.amt.projectOne.presentation;

import ch.heigvd.amt.projectOne.model.Class;
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
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClassInfoServletTest {

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

    @Mock
    Map<String, String[]> map;

    private ClassInfoServlet servlet;


    @BeforeEach
    void setup() {

        servlet = new ClassInfoServlet();
        servlet.classManager = classManager;
        when(request.getRequestDispatcher("/WEB-INF/pages/class_info.jsp")).thenReturn(requestDispatcher);
    }

    @Test
    void classShouldBeDisplayedWithDetails() throws ServletException, IOException {
        when(request.getParameterMap()).thenReturn(map);
        when(request.getParameterMap().containsKey("id")).thenReturn(true);
        when(classManager.getNumberOfClasses()).thenReturn(3);
        when(request.getParameter("id")).thenReturn("1");

        when(classManager.getClassById(1)).thenReturn(myClass);
        when(myClass.getName()).thenReturn("image");

        servlet.doGet(request, response);

        verify(request, atLeastOnce()).setAttribute("class", myClass);
        verify(request, atLeastOnce()).setAttribute("image", myClass.getName());
        verify(requestDispatcher, atLeastOnce()).forward(request, response);

    }
}