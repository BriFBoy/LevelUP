package net.brifboy.levelup.repo;

import net.brifboy.levelup.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDBInteraction {

    @Autowired
    UserRepository userRepository;
    public void deleteUsers(List<User> users) {

        userRepository.deleteAll(users);

    }
}
