package ch.heigvd.amt.projectOne.services.dao;

import ch.heigvd.amt.projectOne.model.Class;

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
public class ClassManager implements ClassManagerLocal {

    @Resource(lookup = "jdbc/amt")
    private DataSource dataSource;

    @Override
    public int getNumberOfClasses() {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement pstmt = connection.prepareStatement("SELECT COUNT(*) AS counter FROM class");
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
    public boolean addClass(Class myClass) {

        Connection connection = null;

        try {
            connection = dataSource.getConnection();
            PreparedStatement pstmt = connection.prepareStatement("INSERT INTO class (name, weapon, armor, description) VALUES (?, ?, ?, ?)");
            pstmt.setObject(1, myClass.getName());
            pstmt.setObject(2, myClass.getWeapon());
            pstmt.setObject(3, myClass.getArmor());
            pstmt.setObject(4, myClass.getDescription());

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
    public Class getClassByName(String name) {

        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM class WHERE name=?");
            pstmt.setObject(1, name);

            ResultSet rs = pstmt.executeQuery();

            rs.next();
            int id = rs.getInt("id");
            String weapon = rs.getString("weapon");
            String armor = rs.getString("armor");
            String description = rs.getString("description");

            connection.close();
            return Class.builder().id(id).name(name).weapon(weapon).armor(armor).description(description).build();

        } catch (SQLException ex) {
            Logger.getLogger(CharacterManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection(connection);
        }

        return null;
    }

    @Override
    public Class getClassById(int id) {

        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM class WHERE id=?");
            pstmt.setObject(1, id);

            ResultSet rs = pstmt.executeQuery();

            rs.next();
            String name = rs.getString("name");
            String weapon = rs.getString("weapon");
            String armor = rs.getString("armor");
            String description = rs.getString("description");

            connection.close();
            return Class.builder().id(id).name(name).weapon(weapon).armor(armor).description(description).build();

        } catch (SQLException ex) {
            Logger.getLogger(CharacterManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection(connection);
        }

        return null;
    }

    @Override
    public List<Class> fetchAllClasses() {

        Connection connection = null;

        List<Class> classes = new ArrayList<>();
        try {
            connection = dataSource.getConnection();
            PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM class");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String weapon = rs.getString("weapon");
                String armor = rs.getString("armor");
                String description = rs.getString("description");

                classes.add(Class.builder().id(id).name(name).weapon(weapon).armor(armor).description(description).build());
            }
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(CharacterManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection(connection);
        }
        return classes;
    }

    private void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
