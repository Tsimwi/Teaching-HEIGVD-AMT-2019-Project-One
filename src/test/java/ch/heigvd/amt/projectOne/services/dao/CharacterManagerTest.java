package ch.heigvd.amt.projectOne.services.dao;

import ch.heigvd.amt.projectOne.model.Character;
import org.arquillian.container.chameleon.deployment.api.DeploymentParameters;
import org.arquillian.container.chameleon.deployment.maven.MavenBuild;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
@MavenBuild
@DeploymentParameters(testable = true)
public class CharacterManagerTest {

    @EJB
    CharacterManagerLocal characterManager;

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToCreateCharacter() {
        String name = "Cake";
        String password = "test";
        boolean isAdmin = false;

        boolean characterCreated = characterManager.addCharacter(name, password, isAdmin);
        assertTrue(characterCreated);
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToGetACharacterByItsName() {
        String name = "Cake";
        String password = "test";
        boolean isAdmin = false;

        /* Character creation */
        characterManager.addCharacter(name, password, isAdmin);
        Character cake = characterManager.getCharacterByUsername(name);

        assertNotNull(cake);
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToGetACharacterByItsId() {
        String name = "Cake";
        String password = "test";
        boolean isAdmin = false;

        /* Character creation */
        characterManager.addCharacter(name, password, isAdmin);
        Character cakeByName = characterManager.getCharacterByUsername(name);
        int id = cakeByName.getId();
        Character cakeById = characterManager.getCharacterById(id);

        assertEquals(cakeByName, cakeById);
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToUpdateACharacter() {
        String name = "Cake";
        String password = "test";
        boolean isAdmin = false;

        /* Character creation */
        characterManager.addCharacter(name, password, isAdmin);
        Character cake = characterManager.getCharacterByUsername(name);
        String newName = "Lie";
        String newPassword = "bye";

        /* Character update */
        characterManager.updateCharacter(cake.getId(), newName, newPassword, isAdmin, true);
        Character newCake = characterManager.getCharacterByUsername("Lie");

        assertEquals(newCake.getName(), newName);
        assertNotEquals(newCake.getName(), name);
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToCheckIfAPasswordIsCorrect() {
        String name = "Cake";
        String password = "test";
        boolean isAdmin = false;

        /* Character creation */
        characterManager.addCharacter(name, password, isAdmin);
        Character cake = characterManager.getCharacterByUsername("Cake");
        boolean checkSuccess = characterManager.checkPassword(cake.getName(), password);

        assertTrue(checkSuccess);
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToCheckIfAUsernameIsFree() {
        String name = "Cake";
        String password = "test";
        boolean isAdmin = false;

        /* Character creation */
        characterManager.addCharacter(name, password, isAdmin);
        Character cake = characterManager.getCharacterByUsername(name);
        boolean freeUsername = characterManager.isUsernameFree(name);

        assertFalse(freeUsername);

        /* Character deletion */
        characterManager.deleteCharacter(cake.getId());
        freeUsername = characterManager.isUsernameFree(name);

        assertTrue(freeUsername);
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToDeleteACharacter() {
        String name = "Cake";
        String password = "test";
        boolean isAdmin = false;

        /* Character creation */
        characterManager.addCharacter(name, password, isAdmin);
        Character cake = characterManager.getCharacterByUsername(name);

        /* Character deletion */
        boolean deletedCharacter = characterManager.deleteCharacter(cake.getId());
        cake = characterManager.getCharacterById(cake.getId());

        assertNull(cake);
        assertTrue(deletedCharacter);
    }
}
