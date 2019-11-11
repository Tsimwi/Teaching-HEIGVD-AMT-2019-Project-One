package ch.heigvd.amt.projectOne.services.dao;

import ch.heigvd.amt.projectOne.model.Character;

import javax.ejb.Local;
import java.sql.SQLException;
import java.util.List;

@Local
public interface CharacterManagerLocal {

    /**
     * Count the number of rows in a table, following a pattern.
     */
    int countRows(String table, String pattern);

    /**
     * Return a list of character (USED ONLY TO TEST THE PAGINATION).
     */
    List<Character> getCharactersForPaginationTest();

    /**
     * Return a list of characters selected by a specific pattern.
     */
    List<Character> getCharactersByPattern(String letter, int pageNumber);

    /**
     * Return a list of characters selected in relation with the page number they are in.
     */
    List<Character> getCharactersByPage(int pageNumber);

    /**
     * Add a character in the database.
     */
    boolean addCharacter(String username, String password, boolean isAdmin);

    /**
     * Update a character in the database.
     */
    boolean updateCharacter(int id, String username, String password, boolean isAdmin, boolean updatePassword);

    /**
     * Return the character with the given ID.
     */
    Character getCharacterById(int id);

    /**
     * Return the character with the given username.
     */
    Character getCharacterByUsername(String username);

    /**
     * Check whether the given username is free or not in the database.
     */
    boolean isUsernameFree(String username) throws SQLException;

    /**
     * Check if the given password matches the one in the database.
     */
    boolean checkPassword(String username, String password);

    /**
     * Delete the given character from the database.
     */
    boolean deleteCharacter(int id);
}
