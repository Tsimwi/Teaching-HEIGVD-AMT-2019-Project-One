package ch.heigvd.amt.projectOne.services.dao;

import ch.heigvd.amt.projectOne.model.Character;
import ch.heigvd.amt.projectOne.model.Guild;

import javax.ejb.Local;
import java.sql.SQLException;
import java.util.List;

@Local
public interface GuildManagerLocal {

    /**
     * Return the number of guilds in the database.
     */
    int getNumberOfGuild();

    /**
     * Return a list of all guilds.
     */
    List<Guild> getAllGuilds();

    /**
     * Return the guild with the given ID.
     */
    Guild getGuildById(int id);

    /**
     * Return the guild with the given name.
     */
    Guild getGuildByName(String name);

    /**
     * Add a guild in the database.
     */
    boolean addGuild(Guild guild);

    /**
     * Update a guild in the database.
     */
    boolean updateGuild(Guild guild);

    /**
     * Delete a guild in the database.
     */
    boolean deleteGuild(Guild guild);

    /**
     * Check whether the given guild name is free in the database.
     */
    boolean isNameFree(String name);

}
