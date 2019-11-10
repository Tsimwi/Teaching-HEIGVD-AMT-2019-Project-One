package ch.heigvd.amt.projectOne.services.dao;

import ch.heigvd.amt.projectOne.model.Character;

import javax.ejb.Local;
import java.sql.SQLException;
import java.util.List;

@Local
public interface CharacterManagerLocal {

    int countRows(String table, String pattern);

    List<Character> getAllCharacters();

    List<Character> getCharactersByPattern(String letter, int pageNumber);

    List<Character> getCharactersByPage(int pageNumber);

    boolean addCharacter(String username, String password, boolean isAdmin);

    boolean updateCharacter(int id, String username, String password, boolean isAdmin, boolean updatePassword);

    Character getCharacterById(int id);

    Character getCharacterByUsername(String username);

    boolean isUsernameFree(String username) throws SQLException;

    boolean checkPassword(String username, String password);

    boolean deleteCharacter(int id);
}
