package net.brifboy.levelup.service;

import net.brifboy.levelup.model.Guild;
import net.brifboy.levelup.model.User;
import net.brifboy.levelup.repo.GuildDBInteractions;
import net.brifboy.levelup.repo.UserDBInteraction;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserGetXp extends ListenerAdapter {
    @Autowired
    GuildDBInteractions guildDBInteractions;

    @Autowired
    UserDBInteraction userDBInteraction;

    @Autowired
    UserClaclulations claclulations;

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            return;
        }

        long userid = event.getAuthor().getIdLong();
        long guildid = event.getGuild().getIdLong();
        User user = userDBInteraction.getUserFormIdAndGuildId(userid, guildid);

        if (user != null) {
            user.xp++;
            user = claclulations.checkUserLevelUp(user);
            userDBInteraction.saveUser(user);

        } else {
            Guild guild = guildDBInteractions.findById(event.getGuild().getIdLong());
            userDBInteraction.saveUser(
                    new User(userid, event.getAuthor().getName(), 0, 1, guild));
        }
    }


}
