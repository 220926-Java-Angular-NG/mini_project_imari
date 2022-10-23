package com.revature.controllers;

import com.revature.models.User;
import com.revature.services.UserService;
import io.javalin.http.Handler;

public class UserController {
    private UserService userService;

    public UserController() {
        userService = new UserService();
    }

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public Handler createNewUser = context -> {
        User user = context.bodyAsClass(User.class);
        int id = userService.createUser(user);

        if (id > 0) {
            user.setId(id);
            context.json(user).status(200);
        } else {
            context.result("User not created").status(400);
        }
    };

    public Handler getAllUsers = context -> {
        context.json(userService.getAllUsers());
    };

    public Handler getUserById = context -> {
        String param = context.pathParam("id");

        try {
            int id = Integer.parseInt(param);
            User user = userService.getUserById(id);

            if (user != null) {
                context.json(user);
            } else {
                context.result("User not found").status(400);
            }

        } catch (NumberFormatException numberFormatException) {
            System.out.println(numberFormatException.getMessage());
        }
    };

    public Handler updateUser = context -> {
        User user = context.bodyAsClass(User.class);
        user = userService.updateUser(user);

        if (user != null) {
            context.json(user).status(202);
        } else {
            context.result("Could not update user").status(400);
        }
    };

    public Handler deleteUser = context -> {
        User user = context.bodyAsClass(User.class);

        if (user != null) {
            context.status(200).json(userService.deleteUser(user));
        } else {
            context.status(400).result("Could not delete user");
        }
    };

    public Handler deleteUserById = context -> {
        String param = context.pathParam("id");

        try {
            int id = Integer.parseInt(param);

            context.json(userService.deleteUserById(id)).status(202);

        } catch (NumberFormatException numberFormatException) {
            System.out.println(numberFormatException.getMessage());
        }
    };

    public Handler loginUser = context -> {
        User user = context.bodyAsClass(User.class);

        user = userService.loginUser(user);

        if (user != null) {
            CurrentUser.setCurrentUser(user);
            System.out.println(CurrentUser.getCurrentUser().getFirstname());

            context.json(user);
            context.result("Successfully logged in as " + CurrentUser.getCurrentUser().getFirstname() + " " +
                    CurrentUser.getCurrentUser().getLastname()).status(202);

        } else {
            context.result("Invalid email address or password").status(404);
        }

    };
}
