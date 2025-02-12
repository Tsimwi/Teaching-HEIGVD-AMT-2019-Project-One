package ch.heigvd.amt.projectOne.services.dao;

import ch.heigvd.amt.projectOne.model.Guild;
import ch.heigvd.amt.projectOne.model.Character;
import ch.heigvd.amt.projectOne.model.Membership;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
public class MembershipManager implements MembershipManagerLocal {

    @Resource(lookup = "jdbc/amt")
    private DataSource dataSource;

    public int getNumberOfMembershipsForGuild(int id) {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement pstmt = connection.prepareStatement("SELECT COUNT(*) AS counter FROM membership WHERE guild_id=?");
            pstmt.setObject(1, id);
            ResultSet rs = pstmt.executeQuery();

            rs.next();
            connection.close();

            return rs.getInt("counter");

        } catch (SQLException ex) {
            Logger.getLogger(CharacterManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection(connection);
        }

        return -1;

    }

    @Override
    public boolean addMembership(Membership membership) {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement pstmt = connection.prepareStatement("INSERT INTO membership(character_id, guild_id, rank) VALUES (?, ?, ?);");
            pstmt.setObject(1, membership.getCharacter().getId());
            pstmt.setObject(2, membership.getGuild().getId());
            pstmt.setObject(3, "Apprentice");

            int row = pstmt.executeUpdate();
            connection.close();

            return row > 0;

        } catch (SQLException ex) {
            Logger.getLogger(CharacterManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection(connection);
        }

        return false;
    }

    @Override
    public List<Membership> getMembershipsByUserId(int id) {
        Connection connection = null;
        List<Membership> memberships = new ArrayList<>();

        try {
            connection = dataSource.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(
                    "SELECT membership.id, guild_id, rank, name, character_id FROM membership INNER JOIN guild on membership.guild_id = guild.id WHERE character_id=?");
            pstmt.setObject(1, id);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int membershipId = rs.getInt("id");
                int guildId = rs.getInt("guild_id");
                int characterId = rs.getInt("character_id");
                String rank = rs.getString("rank");
                String guildName = rs.getString("name");

                memberships.add(Membership.builder()
                        .id(membershipId)
                        .character(Character.builder()
                                .id(characterId)
                                .build())
                        .guild(Guild.builder()
                                .id(guildId)
                                .name(guildName)
                                .build())
                        .rank(rank).build());
            }
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(CharacterManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection(connection);
        }
        return memberships;
    }

    @Override
    public List<Membership> getMembershipsByGuildIdWithPage(int id, int pageNumber) {
        Connection connection = null;
        List<Membership> memberships = new ArrayList<>();

        try {
            connection = dataSource.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(
                    "SELECT membership.id, rank, character.name, guild_id, character_id FROM membership INNER JOIN character on membership.character_id = character.id WHERE guild_id=? ORDER BY character.name LIMIT 25 OFFSET ?");
            pstmt.setObject(1, id);
            pstmt.setObject(2, pageNumber * 25);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int membershipId = rs.getInt("id");
                int guildId = rs.getInt("guild_id");
                int characterId = rs.getInt("character_id");
                String rank = rs.getString("rank");
                String characterName = rs.getString("name");

                memberships.add(Membership.builder()
                        .id(membershipId)
                        .character(Character.builder()
                                .id(characterId)
                                .name(characterName)
                                .build())
                        .guild(Guild.builder()
                                .id(guildId)
                                .build())
                        .rank(rank).build());
            }
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(CharacterManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection(connection);
        }
        return memberships;
    }

    @Override
    public boolean removeMembership(int id) {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement pstmt = connection.prepareStatement("DELETE FROM membership WHERE id=?");
            pstmt.setObject(1, id);

            int row = pstmt.executeUpdate();
            connection.close();
            return row > 0;

        } catch (SQLException ex) {
            Logger.getLogger(CharacterManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection(connection);
        }

        return false;
    }

    @Override
    public boolean checkCharacterMembership(Character character, Guild guild) {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement pstmt = connection.prepareStatement("SELECT id FROM membership WHERE character_id=? AND guild_id=?");
            pstmt.setObject(1, character.getId());
            pstmt.setObject(2, guild.getId());

            ResultSet rs = pstmt.executeQuery();

            connection.close();

            if (rs.next()) {
                return true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(CharacterManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection(connection);
        }

        return false;
    }

    private void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
