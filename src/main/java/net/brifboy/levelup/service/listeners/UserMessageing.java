package net.brifboy.levelup.service.listeners;

import net.brifboy.levelup.model.Guild;
import net.brifboy.levelup.model.User;
import net.brifboy.levelup.repo.GuildDBInteractions;
import net.brifboy.levelup.repo.LevelRoleRepository;
import net.brifboy.levelup.repo.UserDBInteraction;
import net.brifboy.levelup.service.Messaging;
import net.brifboy.levelup.service.Claclulations;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.HierarchyException;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class UserMessageing extends ListenerAdapter {

    GuildDBInteractions guildDBInteractions;
    UserDBInteraction userDBInteraction;
    LevelRoleRepository levelRoleRepository;
    Messaging messaging;
    private static final Logger logger = LoggerFactory.getLogger(UserMessageing.class);

    public UserMessageing(GuildDBInteractions guildDBInteractions, UserDBInteraction userDBInteraction, Messaging messaging, LevelRoleRepository levelRoleRepository) {
        this.guildDBInteractions = guildDBInteractions;
        this.userDBInteraction = userDBInteraction;
        this.messaging = messaging;
        this.levelRoleRepository = levelRoleRepository;
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            return;
        }

        final String[] XP = event.getMessage().getContentRaw().split(" ");
        long userid = event.getAuthor().getIdLong();
        long guildid = event.getGuild().getIdLong();
        User user = userDBInteraction.getUserFormUserIdAndGuildId(userid, guildid);
        try {
            if (user != null) {
                Claclulations.addXp(user, XP);
                if (ifUserLevelsUp(event, user)) {
                    checkNewRole(event, user);
                }
                userDBInteraction.saveUser(user);
            } else { // Adds user if there is no in DB
                addUser(event, userid, XP);
            }
        } catch (Exception e) {
            logger.warn("{} occurred in onMessage, saved guild as safety measure", e.getClass());
        }

    }

    private boolean ifUserLevelsUp(@NotNull MessageReceivedEvent event, User user) {
        if (Claclulations.checkUserLevelUp(user)) {
           event.getMessage().reply(messaging.levelUpMessage(user, event.getChannel())).queue(); // If user levels up it, then send message
            return true;
        }
        return false;
    }

    private void addUser(@NotNull MessageReceivedEvent event, long userid, String[] XP) throws DataIntegrityViolationException {
        Guild guild = guildDBInteractions.findById(event.getGuild().getIdLong());

        User NewUser = new User(userid, event.getAuthor().getName(), 0, XP.length, null, guild);
        Claclulations.checkUserLevelUp(NewUser);
        userDBInteraction.saveUser(NewUser);
        logger.info("Added new User to DB");
    }

    private void checkNewRole(MessageReceivedEvent event, User user) throws HierarchyException {
        int[] levels = levelRoleRepository.getLevelsForGuild(event.getGuild().getIdLong());
        for (int i : levels) {
            if (i == user.level) {
                long roleid = levelRoleRepository.getRoleFromLevelAndGuild(event.getGuild().getIdLong(), i);
                if (user.getLevelRole() != null) {
                    event.getGuild().removeRoleFromMember(event.getMember(),
                            event.getGuild().getRoleById(user.getLevelRole().getRoleid())).queue();
                }
                event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(roleid)).queue();
            }
        }
    }

}
