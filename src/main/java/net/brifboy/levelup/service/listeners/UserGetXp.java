package net.brifboy.levelup.service.listeners;

import net.brifboy.levelup.model.Guild;
import net.brifboy.levelup.model.User;
import net.brifboy.levelup.repo.GuildDBInteractions;
import net.brifboy.levelup.repo.UserDBInteraction;
import net.brifboy.levelup.service.Messaging;
import net.brifboy.levelup.service.Claclulations;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.CachedIntrospectionResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserGetXp extends ListenerAdapter {

    GuildDBInteractions guildDBInteractions;
    UserDBInteraction userDBInteraction;
    Messaging messaging;
    private static final Logger logger = LoggerFactory.getLogger(UserGetXp.class);

    public UserGetXp(GuildDBInteractions guildDBInteractions, UserDBInteraction userDBInteraction, Messaging messaging) {
        this.guildDBInteractions = guildDBInteractions;
        this.userDBInteraction = userDBInteraction;
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
            Claclulations.addXp(user, XP);
            ifUserLevelsUp(event, user);
            userDBInteraction.saveUser(user);
        } else { // Adds user if there is no in DB
            addUser(event, userid, XP);
        }
    }

    private void ifUserLevelsUp(@NotNull MessageReceivedEvent event, User user) {
        if (Claclulations.checkUserLevelUp(user)) {
           event.getMessage().reply(messaging.levelUpMessage(user, event.getChannel())).queue(); // If user levels up it, then send message
        }
    }

    private void addUser(@NotNull MessageReceivedEvent event, long userid, String[] XP) {
        Guild guild = guildDBInteractions.findById(event.getGuild().getIdLong());
        User NewUser = new User(userid, event.getAuthor().getName(), 0, XP.length, guild);
        Claclulations.checkUserLevelUp(NewUser);
        userDBInteraction.saveUser(NewUser);
        logger.info("Added new user to DB, User: {}, {}", NewUser.getUserid(), NewUser.getUsername());
    }


}
