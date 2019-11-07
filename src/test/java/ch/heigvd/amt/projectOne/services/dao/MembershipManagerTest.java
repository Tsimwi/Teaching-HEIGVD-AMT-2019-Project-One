package ch.heigvd.amt.projectOne.services.dao;

import ch.heigvd.amt.projectOne.model.Character;
import ch.heigvd.amt.projectOne.model.Guild;
import ch.heigvd.amt.projectOne.model.Membership;
import org.arquillian.container.chameleon.deployment.api.DeploymentParameters;
import org.arquillian.container.chameleon.deployment.maven.MavenBuild;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;

import javax.ejb.EJB;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
@MavenBuild
@DeploymentParameters(testable = true)

public class MembershipManagerTest {

    @EJB
    MembershipManagerLocal membershipManager;

    @EJB
    CharacterManagerLocal characterManager;

    @EJB
    GuildManagerLocal guildManager;

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToCreateAMembership() {
        String characterName = "Cake";
        String password = "test";
        boolean isAdmin = false;
        characterManager.addCharacter(characterName, password, isAdmin);
        Character character = characterManager.getCharacterByUsername(characterName);

        String guildName = "Meet your baker";
        String description = "The bread is a lie.";
        Guild myb = Guild.builder().name(guildName).description(description).build();
        guildManager.addGuild(myb);
        Guild guild = guildManager.getGuildByName(guildName);

        Membership membership = Membership.builder().character(character).guild(guild).build();

        boolean membershipAdded = membershipManager.addMembership(membership);

        assertTrue(membershipAdded);
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToGetTheNumberOfMembershipsForAGuild() {
        String characterName1 = "Cake";
        String characterName2 = "Biscuit";
        String password = "test";
        boolean isAdmin = false;
        List<Character> characters = new ArrayList<>();

        characterManager.addCharacter(characterName1, password, isAdmin);
        characterManager.addCharacter(characterName2, password, isAdmin);
        Character character1 = characterManager.getCharacterByUsername(characterName1);
        Character character2 = characterManager.getCharacterByUsername(characterName2);
        characters.add(character1);
        characters.add(character2);

        String guildName = "Meet your baker";
        String description = "The bread is a lie.";
        Guild myb = Guild.builder().name(guildName).description(description).build();
        guildManager.addGuild(myb);
        Guild guild = guildManager.getGuildByName(guildName);

        Membership membership1 = Membership.builder().character(character1).guild(guild).build();
        Membership membership2 = Membership.builder().character(character2).guild(guild).build();

        membershipManager.addMembership(membership1);
        membershipManager.addMembership(membership2);
        int numberOfMemberships = membershipManager.getNumberOfMembershipsForGuild(guild.getId());

        assertNotEquals(numberOfMemberships, -1);
        assertEquals(numberOfMemberships, characters.size());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToGetMembershipsOfAUserByItsId() {
        String characterName = "Cake";
        String password = "test";
        boolean isAdmin = false;

        characterManager.addCharacter(characterName, password, isAdmin);
        Character character = characterManager.getCharacterByUsername(characterName);

        String guildName1 = "Meet your baker";
        String guildName2 = "Ol' bakery";
        String description1 = "All your cupcakes are belong to us";
        String description2 = "The bread is a lie.";
        List<Guild> guilds = new ArrayList<>();

        guildManager.addGuild(Guild.builder().name(guildName1).description(description1).build());
        guildManager.addGuild(Guild.builder().name(guildName2).description(description2).build());
        Guild guild1 = guildManager.getGuildByName(guildName1);
        Guild guild2 = guildManager.getGuildByName(guildName2);
        guilds.add(guild1);
        guilds.add(guild2);

        membershipManager.addMembership(Membership.builder().character(character).guild(guild1).build());
        membershipManager.addMembership(Membership.builder().character(character).guild(guild2).build());
        List<Membership> memberships = membershipManager.getMembershipsByUserId(character.getId());

        assertFalse(memberships.isEmpty());
        assertEquals(memberships.size(), guilds.size());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToGetMembershipsOfAGuildsByItsId() {
        String characterName1 = "Cake";
        String characterName2 = "Biscuit";
        String password = "test";
        boolean isAdmin = false;
        List<Character> characters = new ArrayList<>();

        characterManager.addCharacter(characterName1, password, isAdmin);
        characterManager.addCharacter(characterName2, password, isAdmin);
        Character character1 = characterManager.getCharacterByUsername(characterName1);
        Character character2 = characterManager.getCharacterByUsername(characterName2);
        characters.add(character1);
        characters.add(character2);

        String guildName = "Meet your baker";
        String description = "The bread is a lie.";
        Guild myb = Guild.builder().name(guildName).description(description).build();
        guildManager.addGuild(myb);
        Guild guild = guildManager.getGuildByName(guildName);

        Membership membership1 = Membership.builder().character(character1).guild(guild).build();
        Membership membership2 = Membership.builder().character(character2).guild(guild).build();

        membershipManager.addMembership(membership1);
        membershipManager.addMembership(membership2);
        List<Membership> memberships = membershipManager.getMembershipsByGuildId(guild.getId());

        assertFalse(memberships.isEmpty());
        assertEquals(memberships.size(), characters.size());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToDeleteAMembership() {
        String characterName = "Cake";
        String password = "test";
        boolean isAdmin = false;
        characterManager.addCharacter(characterName, password, isAdmin);
        Character character = characterManager.getCharacterByUsername(characterName);

        String guildName = "Meet your baker";
        String description = "The bread is a lie.";
        Guild myb = Guild.builder().name(guildName).description(description).build();
        guildManager.addGuild(myb);
        Guild guild = guildManager.getGuildByName(guildName);

        Membership membership = Membership.builder().character(character).guild(guild).build();

        membershipManager.addMembership(membership);
        List<Membership> memberships = membershipManager.getMembershipsByUserId(character.getId());

        boolean membershipDeleted = membershipManager.removeMembership(memberships.get(0).getId());
        memberships = membershipManager.getMembershipsByUserId(character.getId());

        assertTrue(membershipDeleted);
        assertTrue(memberships.isEmpty());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToCheckIfAMembershipExists() {
        String characterName = "Cake";
        String password = "test";
        boolean isAdmin = false;
        characterManager.addCharacter(characterName, password, isAdmin);
        Character character = characterManager.getCharacterByUsername(characterName);

        String guildName = "Meet your baker";
        String description = "The bread is a lie.";
        Guild myb = Guild.builder().name(guildName).description(description).build();
        guildManager.addGuild(myb);
        Guild guild = guildManager.getGuildByName(guildName);

        Membership membership = Membership.builder().character(character).guild(guild).build();

        membershipManager.addMembership(membership);
        boolean membershipExists = membershipManager.checkCharacterMembership(character, guild);

        assertTrue(membershipExists);
    }

}
