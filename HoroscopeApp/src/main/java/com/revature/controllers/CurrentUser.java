package com.revature.controllers;

import com.revature.models.User;

public class CurrentUser {
    private static User currentUser;

    private CurrentUser() {
    }

    public CurrentUser(User user) {
        this.currentUser = user;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        CurrentUser.currentUser = currentUser;
    }
}
