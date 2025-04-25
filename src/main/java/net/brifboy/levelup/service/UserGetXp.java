package net.brifboy.levelup.service;

import net.brifboy.levelup.model.Guild;
import net.brifboy.levelup.model.User;
import net.brifboy.levelup.repo.GuildDBInteractions;
import net.brifboy.levelup.repo.UserDBInteraction;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserGetXp extends ListenerAdapter {

    GuildDBInteractions guildDBInteractions;
    UserDBInteraction userDBInteraction;
    UserClaclulations claclulations;
    Messaging messaging;
    private static final Logger logger = LoggerFactory.getLogger(UserGetXp.class);

    @Autowired
    public UserGetXp(GuildDBInteractions guildDBInteractions, UserDBInteraction userDBInteraction, UserClaclulations claclulations, Messaging messaging) {
        this.guildDBInteractions = guildDBInteractions;
        this.userDBInteraction = userDBInteraction;
        this.claclulations = claclulations;
        this.messaging = messaging;
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

        if (user != null) {
            user.addXp(claclulations, XP);
            if (user.checkLevel(claclulations)) {
                messaging.levelUpMessage(user, event.getChannel());
            }
            userDBInteraction.saveUser(user);
            logger.info("Updated user in DB, User: {}, {}", user.getUserid(), user.getUsername());

        } else {
            Guild guild = guildDBInteractions.findById(event.getGuild().getIdLong());
            User NewUser = new User(userid, event.getAuthor().getName(), 0, XP.length, guild);
            NewUser.checkLevel(claclulations);
            userDBInteraction.saveUser(NewUser);
            logger.info("Added new user to DB, User: {}, {}", NewUser.getUserid(), NewUser.getUsername());
        }
    }


}
