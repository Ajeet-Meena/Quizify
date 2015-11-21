/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.quizify.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import java.sql.SQLException;

import javax.inject.Named;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "myApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend.quizify.com",
                ownerName = "backend.quizify.com",
                packagePath = ""
        )
)
public class Endpoint {

    DatabaseController databaseController;

    /**
     * Insert User
     */
    @ApiMethod(name = "insertNewUser")
    public Quizify.User insertNewUser(@Named("userName") String userName, @Named("email") String email, @Named("facebookId") String facebookId, @Named("gcmId") String gcmId) {
        Quizify.User user = new Quizify.User(facebookId, gcmId, email, userName);
        try {
            databaseController = new DatabaseController("jdbc:mysql://127.0.0.1:3306/mydb", "root", "ben10som");
            user.setUserId(databaseController.insertNewUser(user));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return user;
    }

}
