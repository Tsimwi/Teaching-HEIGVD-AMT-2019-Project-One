package ch.heigvd.amt.projectOne.services.dao;

import ch.heigvd.amt.projectOne.model.Character;
import ch.heigvd.amt.projectOne.model.Guild;
import ch.heigvd.amt.projectOne.model.Membership;

import javax.ejb.Local;
import java.util.List;

@Local
public interface MembershipManagerLocal {

    int getNumberOfMembershipsForGuild(int id);

    boolean addMembership(Membership membership);

    List<Membership> getMembershipsByUserId(int id);

    List<Membership> getMembershipsByGuildIdWithPage(int id, int pageNumber);

    List<Membership> getMembershipsByGuildId(int id);

    boolean removeMembership(int id);

    boolean checkCharacterMembership(Character character, Guild guild);
}
