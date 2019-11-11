package ch.heigvd.amt.projectOne.services.business;

import javax.ejb.Local;

@Local
public interface IAuthenticationService {

    /**
     * Hash a plain text password with BCrypt.
     */
    public String hashPassword(String plainTextPassword);

    /**
     * Hash the given password and check if it matches the hashed one in the database.
     */
    public boolean checkPassword(String plainTextPassword, String hashedPassword);
}