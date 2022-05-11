// Written By Gregory Presser

package edu.cooper.ece366.Endpoints;
import static spark.Spark.*;

import edu.cooper.ece366.Mongo.User.UserHandler;
import edu.cooper.ece366.RouteInterfaces.UserBodyParser.AuthRoute;



public class UserAPI {

    public static void paths(UserHandler userHandler){
        path("/user", () -> {
            post("/me", (AuthRoute) (req, res, body, user) -> user.toJSONString());
        });
    }
    
}
