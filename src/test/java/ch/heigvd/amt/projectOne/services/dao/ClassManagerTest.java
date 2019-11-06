package ch.heigvd.amt.projectOne.services.dao;

import ch.heigvd.amt.projectOne.model.Class;
import org.arquillian.container.chameleon.deployment.api.DeploymentParameters;
import org.arquillian.container.chameleon.deployment.maven.MavenBuild;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
@MavenBuild
@DeploymentParameters(testable = true)

public class ClassManagerTest {

    @EJB
    ClassManagerLocal classManager;

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToCreateAClass() {
        String name = "Chef";
        String weapon = "Spoon";
        String armor = "Hat";
        String description = "Delicious and moist...";
        Class chef = Class.builder().name(name).weapon(weapon).armor(armor).description(description).build();

        boolean ClassCreated = classManager.addClass(chef);

        assertTrue(ClassCreated);
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToGetAClassByItsName() {
        String name = "Chef";
        String weapon = "Spoon";
        String armor = "Hat";
        String description = "Delicious and moist...";
        Class chef = Class.builder().name(name).weapon(weapon).armor(armor).description(description).build();

        classManager.addClass(chef);
        Class myClass = classManager.getClassByName(name);

        assertNotNull(myClass);
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToGetAClassByItsId() {
        String name = "Chef";
        String weapon = "Spoon";
        String armor = "Hat";
        String description = "Delicious and moist...";
        Class chef = Class.builder().name(name).weapon(weapon).armor(armor).description(description).build();

        classManager.addClass(chef);
        Class myClass = classManager.getClassByName(name);
        Class myClassbyId = classManager.getClassById(myClass.getId());
        assertNotNull(myClassbyId);
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToFetchMultipleClasses() {
        String className1 = "Chef";
        String className2 = "Baker";
        String className3 = "Pastry Cook";
        String weapon = "Spoon";
        String armor = "Hat";
        String description = "Delicious and moist...";
        List<Class> goodGuys;

        Class chef = Class.builder().name(className1).weapon(weapon).armor(armor).description(description).build();
        Class baker = Class.builder().name(className2).weapon(weapon).armor(armor).description(description).build();
        Class pastrycook = Class.builder().name(className3).weapon(weapon).armor(armor).description(description).build();

        classManager.addClass(chef);
        classManager.addClass(baker);
        classManager.addClass(pastrycook);
        goodGuys = classManager.fetchAllClasses();

        /* Tests multiple assertions */
        assertNotNull(goodGuys);
        assertFalse(goodGuys.isEmpty());
    }
}
