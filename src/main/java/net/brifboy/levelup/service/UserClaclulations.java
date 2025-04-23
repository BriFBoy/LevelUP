package net.brifboy.levelup.service;

import net.brifboy.levelup.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserClaclulations {

    User checkUserLevelUp(User user) {
        if (user.xp >= 10) {
            user.level++;
            user.xp = 0;
        }
        return user;
    }

}
