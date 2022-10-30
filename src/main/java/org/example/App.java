package org.example;

import org.example.models.User;
import org.example.service.UserService;

public class App {
    public static void main( String[] args ) {
        UserService userService = new UserService();

        User user = new User(
                "Asan",
                "Asanov",
                "+996702751525",
                "asan@gmail.com",
                "asan"
        );
        User user1 = new User(
                "Kasan",
                "Kasanov",
                "+996902751525",
                "kasan@gmail.com",
                "kasan"
        );

        userService.register(user);
        userService.register(user1);

    }
}
