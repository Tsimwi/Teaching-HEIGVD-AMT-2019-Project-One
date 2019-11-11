package ch.heigvd.amt.projectOne.services.dao;

import ch.heigvd.amt.projectOne.model.Class;

import javax.ejb.Local;
import java.util.List;

@Local
public interface ClassManagerLocal {

    /**
     * Return a list of all classes.
     */
    public List<Class> fetchAllClasses();

    /**
     * Return the class with the given ID.
     */
    public Class getClassById(int id);

    /**
     * Return the class with the given name.
     */
    public Class getClassByName(String name);

    /**
     * Get the number of classes in the database.
     */
    int getNumberOfClasses();

    /**
     * Add a class in the database.
     */
    public boolean addClass(Class myClass);
}
