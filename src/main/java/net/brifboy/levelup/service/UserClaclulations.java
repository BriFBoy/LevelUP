package net.brifboy.levelup.service;

import net.brifboy.levelup.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserClaclulations {
    Logger logger = LoggerFactory.getLogger(UserClaclulations.class);

    public User checkUserLevelUp(User user) {
        try {
            final int XPTOLEVELUP = ((int) (Math.pow(user.level, 1.4) + 20));
            if (user.xp >= XPTOLEVELUP) {
                user.level++;
                user.xp = 0;
            }
        } catch (ClassCastException c) {
            logger.info("Failed to cast XPTOLEVELUP to int");
        }
        return user;
    }
    public User addXp(User user, String[] XP) {
        user.xp += XP.length;
        return user;
    }

}
