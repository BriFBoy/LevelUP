package net.brifboy.levelup.service;

import net.brifboy.levelup.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserClaclulations {
    Logger logger = LoggerFactory.getLogger(UserClaclulations.class);

    public boolean checkUserLevelUp(User user) {
        try {
            final int XPTOLEVELUP = getXptolevelup(user);
            if (user.xp >= XPTOLEVELUP) {
                user.level++;
                user.xp = 0;
                return true;
            }
        } catch (ClassCastException c) {
            logger.info("Failed to cast XPTOLEVELUP to int");
        }
        return false;
    }



    public void addXp(User user, String[] XP) {
        user.xp += XP.length;
    }

    // Formula for calculating the xp needed to level up is:
    // XP to level up = levels^1.6 + 25
    public int getXptolevelup(User user) {
        return (int) (Math.pow(user.level, 1.6) + 25);
    }

}
