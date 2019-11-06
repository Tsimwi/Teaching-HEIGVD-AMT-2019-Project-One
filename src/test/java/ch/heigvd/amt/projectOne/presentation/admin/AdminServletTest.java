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

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminServletTest {

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;


    @Mock
    RequestDispatcher requestDispatcher;

    private AdminServlet servlet;

    @BeforeEach
    void setUp() {
        servlet = new AdminServlet();

    }

    @Test
    void itSHouldBePossibleToLoadThePage() throws ServletException, IOException {
        when(request.getRequestDispatcher("/WEB-INF/pages/admin/admin.jsp")).thenReturn(requestDispatcher);
        servlet.doGet(request, response);

        verify(requestDispatcher, atLeastOnce()).forward(request, response);
    }
}