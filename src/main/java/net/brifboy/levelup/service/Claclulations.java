package net.brifboy.levelup.service;

import net.brifboy.levelup.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public abstract class Claclulations {
    private static Logger logger = LoggerFactory.getLogger(Claclulations.class);

    public static boolean checkUserLevelUp(User user) {
        try {
            final int XPTOLEVELUP = getXptolevelup(user);
            if (user.xp >= XPTOLEVELUP) {
                user.level++;
                user.xp = 0;
                return true;
            }
        } catch (ClassCastException c) {
            logger.warn("Failed to cast XPTOLEVELUP to int");
        }
        return false;
    }
    // Formula for calculating the xp needed to level up is:
    // XP to level up = levels^1.6 + 25
    public static int getXptolevelup(User user) {
        return (int) (Math.pow(user.level, 1.6) + 25);
    }



    public static void addXp(User user, String[] XP) {
        user.xp += XP.length;
    }




}
