package ch.heigvd.amt.projectOne.services.dao;

import ch.heigvd.amt.projectOne.model.Character;
import ch.heigvd.amt.projectOne.model.Guild;
import ch.heigvd.amt.projectOne.model.Membership;

import javax.ejb.Local;
import java.util.List;

@Local
public interface MembershipManagerLocal {

    /**
     * Return the number of members in the given guild.
     */
    int getNumberOfMembershipsForGuild(int id);

    /**
     * Add a new member in a guild.
     */
    boolean addMembership(Membership membership);

    /**
     * Return the memberships of a user given its ID.
     */
    List<Membership> getMembershipsByUserId(int id);

    /**
     * Return the memberships of a guild, in relation with the page number.
     */
    List<Membership> getMembershipsByGuildIdWithPage(int id, int pageNumber);

    /**
     * Remove a member from a guild.
     */
    boolean removeMembership(int id);

    /**
     * Check if a character is member of a given guild.
     */
    boolean checkCharacterMembership(Character character, Guild guild);
}
