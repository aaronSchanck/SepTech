package com.septech.centauri.domain.repository;


import com.septech.centauri.domain.models.User;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * A domain-level interface to serve the ViewModels with information grabbed regarding the User
 * (domain-level) and UserEntity (data-level) models.
 *
 * @author adamg
 */
public interface UserRepository {

    /**
     * @param userid
     * @return
     */
    Single<User> getUserById(int userid);

    /**
     * Attempts to log in a user with the specified username, password, and passwordSalt. The
     * password will be hashed on the application side alongside the passwordSalt, then that
     * hashed password will be sent alongside the email address to the server to be validated
     * against. If the user is found, an observable Single object representing the User model
     * will be returned to the caller.
     *
     * @param email        The email address of the user logging in.
     * @param password     The password entered for the user logging in.
     * @param passwordSalt The password salt for the specified user. This is grabbed by calling
     *                     getUserByEmail(email) and returning the User model associated with the
     *                     corresponding email address. If a user doesn't exist for the given
     *                     email address, the function will break before reaching this stage.
     * @return An observable Single representing the User model that was found for the specified
     * email and password, if it exists.
     */
    Single<User> login(String email, String password, String passwordSalt);

    /**
     * Deletes the user in the remote API with the corresponding id. This function should only be
     * called as a testing purpose, and never in the production level. If a user is to be banned,
     * then the associated banUser function should be called.
     *
     * @param userid The id of the user to be banned.
     * @return An observable Single representing the User model that was deleted.
     */
    Single<User> deleteUser(int userid);

    /**
     * A function that creates a user object in the remote API based off of an existing User
     * (domain-level) model.
     *
     * @param user
     */
    @Deprecated
    void createUser(User user);

    /**
     * A testing function that will return all Users from the remote database
     *
     * @return An observable List of all User models found on the remote API.
     */
    Observable<List<User>> getAllUsers();


    /**
     * 
     * @param user
     * @return
     */
    Single<User> createAccount(User user);

    /**
     * An interface function to return a user based on their email address.
     *
     * @param email The email address to grab the user object off of.
     * @return An observable single with the User object, if it exists.
     */
    Single<User> getUserByEmail(String email);

    Single<String> checkUserExists(String email);

    Single<String> verifyPasswordCode(String code, String email);

    Single<String> forgotPassword(String email);

    Single<User> changePassword(User user, String email);
}
