package com.simpletask.security.auth;

import com.simpletask.security.user.User;

import java.security.Principal;

public interface UserService {
    User getUserById(Long userId);
    User getUserByUsername(String username);
    User getUserByPrincipal(Principal principal);
}
