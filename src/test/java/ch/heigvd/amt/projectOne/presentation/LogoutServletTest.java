package ch.heigvd.amt.projectOne.presentation;

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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LogoutServletTest {

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    HttpSession session;

    private LogoutServlet servlet;

    @BeforeEach
    void setUp() {
        servlet = new LogoutServlet();
        when(request.getSession()).thenReturn(session);
    }

    @Test
    void weShouldBeAbleToLogout() throws IOException, ServletException {
        servlet.doGet(request, response);
        verify(session, atLeastOnce()).invalidate();
        verify(response, atLeastOnce()).sendRedirect(request.getContextPath() + "/login");
    }
}