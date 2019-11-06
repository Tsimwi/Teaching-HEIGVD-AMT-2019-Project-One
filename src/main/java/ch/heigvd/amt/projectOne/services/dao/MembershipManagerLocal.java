package ch.heigvd.amt.projectOne.services.dao;

import ch.heigvd.amt.projectOne.model.Character;
import ch.heigvd.amt.projectOne.model.Guild;
import ch.heigvd.amt.projectOne.model.Membership;

import javax.ejb.Local;
import java.util.List;

@Local
public interface MembershipManagerLocal {

    public int getNumberOfMembershipsForGuild(int id);

    public boolean addMembership(Membership membership);

    public List<Membership> getMembershipsByUserId(int id);

    public List<Membership> getMembershipsByGuildIdWithPage(int id, int pageNumber);

    public boolean removeMembership(int id);

    public boolean checkCharacterMembership(Character character, Guild guild);
}
