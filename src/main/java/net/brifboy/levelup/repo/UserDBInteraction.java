package net.brifboy.levelup.repo;

import net.brifboy.levelup.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDBInteraction {

    private static final Logger logger = LoggerFactory.getLogger(UserDBInteraction.class);
    @Autowired
    UserRepository userRepository;

    public void deleteUsers(List<User> users) {
        if (!users.isEmpty()) {
            userRepository.deleteAll(users);
            logger.info("Deleted users from DB");
        }

    }

    public void saveUser(User user) {
        userRepository.save(user);
        logger.info("Saved or Updated user in DB");
    }
    public User getUserFormUserIdAndGuildId(long userid, long guildid) {
       return userRepository.getUserFromIdAndGuildId(userid, guildid);
    }
    public List<User> getUsersFromGuildId(long guildid) {
        return userRepository.getUsersByGuildId(guildid);
    }

    public List<User> getUsersWithHighestLevel(int top, long guildid) {
        return userRepository.getUsersWithHighestLevel(top, guildid);
    }
}
