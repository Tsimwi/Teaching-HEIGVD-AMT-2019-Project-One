package ch.heigvd.amt.projectOne.presentation;

import ch.heigvd.amt.projectOne.model.Class;
import ch.heigvd.amt.projectOne.services.dao.ClassManagerLocal;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Manage classes, it will display all classes
 */
@WebServlet(urlPatterns = "/classes")
public class ClassesServlet extends HttpServlet {

    @EJB
    ClassManagerLocal classManager;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Class> classes = classManager.fetchAllClasses();
        req.setAttribute("classes", classes);
        req.getRequestDispatcher("/WEB-INF/pages/classes.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
