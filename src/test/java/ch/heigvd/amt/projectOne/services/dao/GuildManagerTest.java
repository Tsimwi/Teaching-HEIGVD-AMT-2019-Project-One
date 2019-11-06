package ch.heigvd.amt.projectOne.services.dao;

import ch.heigvd.amt.projectOne.model.Class;
import ch.heigvd.amt.projectOne.model.Guild;
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

public class GuildManagerTest {

    @EJB
    GuildManagerLocal guildManager;

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToCreateAGuild() {
        String name = "Meet your baker";
        String description = "The bread is a lie.";
        Guild myb = Guild.builder().name(name).description(description).build();

        boolean guildCreated = guildManager.addGuild(myb);

        assertTrue(guildCreated);
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToGetAGuildByItsName() {
        String name = "Meet your baker";
        String description = "The bread is a lie.";
        Guild myb = Guild.builder().name(name).description(description).build();

        guildManager.addGuild(myb);
        Guild createdGuild = guildManager.getGuildByName(name);

        assertNotNull(createdGuild);
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToGetAGuildByItsId() {
        String name = "Meet your baker";
        String description = "The bread is a lie.";
        Guild myb = Guild.builder().name(name).description(description).build();

        guildManager.addGuild(myb);
        Guild createdGuild = guildManager.getGuildByName(name);
        Guild createdGuildById = guildManager.getGuildById(createdGuild.getId());

        assertNotNull(createdGuildById);
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToUpdateAGuild() {
        String name = "Meet your baker";
        String description = "The bread is a lie.";
        Guild myb = Guild.builder().name(name).description(description).build();

        /* Guild creation */
        guildManager.addGuild(myb);
        Guild myGuild = guildManager.getGuildByName(name);

        /* Guild update */
        String newName = "In baguette we trust";
        Guild updatedGuild = Guild.builder().id(myGuild.getId()).name(newName).description(description).build();
        guildManager.updateGuild(updatedGuild);
        Guild newGuild = guildManager.getGuildByName(newName);

        assertEquals(newGuild.getName(), newName);
        assertEquals(newGuild.getId(), myGuild.getId());
        assertNotEquals(newGuild.getName(), name);
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToFetchMultipleGuilds() {
        String guildName1 = "Chef";
        String guildName2 = "Baker";
        String guildName3 = "Pastry Cook";
        String description = "Delicious and moist...";
        List<Guild> guilds;

        Guild chef = Guild.builder().name(guildName1).description(description).build();
        Guild baker = Guild.builder().name(guildName2).description(description).build();
        Guild pastrycook = Guild.builder().name(guildName3).description(description).build();

        guildManager.addGuild(chef);
        guildManager.addGuild(baker);
        guildManager.addGuild(pastrycook);
        guilds = guildManager.getAllGuilds();

        assertNotNull(guilds);
        assertFalse(guilds.isEmpty());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToDeleteAGuild() {
        String name = "Meet your baker";
        String description = "The bread is a lie.";
        Guild myb = Guild.builder().name(name).description(description).build();

        /* Guild creation */
        guildManager.addGuild(myb);
        Guild mybCreated = guildManager.getGuildByName(name);

        /* Guild deletion */
        boolean deletedGuild = guildManager.deleteGuild(mybCreated);
        myb = guildManager.getGuildById(mybCreated.getId());

        assertNull(myb);
        assertTrue(deletedGuild);
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToCheckIfAGuildNameIsFree() {
        String name = "Meet your baker";
        String description = "The bread is a lie.";
        Guild myb = Guild.builder().name(name).description(description).build();

        /* Guild creation */
        guildManager.addGuild(myb);
        myb = guildManager.getGuildByName(name);
        boolean freeName = guildManager.isNameFree(name);

        assertFalse(freeName);

        guildManager.deleteGuild(myb);
        freeName = guildManager.isNameFree(name);

        assertTrue(freeName);
    }
}
