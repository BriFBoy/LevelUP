package net.brifboy.levelup.service.listeners;

import net.brifboy.levelup.model.Guild;
import net.brifboy.levelup.model.User;
import net.brifboy.levelup.repo.GuildRepository;
import net.brifboy.levelup.repo.UserRepository;
import net.brifboy.levelup.service.Claclulations;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.awt.*;

@Service
public class UserMessaging extends ListenerAdapter {

    GuildRepository guildRepository;
    UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserMessaging.class);

    public UserMessaging(GuildRepository guildRepository, UserRepository userRepository) {
        this.guildRepository = guildRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            return;
        }

        final String[] XP = event.getMessage().getContentRaw().split(" ");
        long userid = event.getAuthor().getIdLong();
        long guildid = event.getGuild().getIdLong();
        User user = userRepository.getUserFromIdAndGuildId(userid, guildid);

        if (user != null) {
            Claclulations.addXp(user, XP);
            ifUserLevelsUp(event, user);
            userRepository.save(user);
        } else { // Adds user if there is no in DB
            addUser(event, userid, XP);
        }
    }

    private void ifUserLevelsUp(@NotNull MessageReceivedEvent event, User user) {
        if (Claclulations.checkUserLevelUp(user)) {
           event.getMessage().reply(levelUpMessage(user)).queue(); // If user levels up it, then send message
        }
    }

    private void addUser(@NotNull MessageReceivedEvent event, long userid, String[] XP) {
        Guild guild = guildRepository.findById(event.getGuild().getIdLong()).orElse(null);
        User NewUser = new User(userid, event.getAuthor().getName(), 0, XP.length, guild);
        Claclulations.checkUserLevelUp(NewUser);
        userRepository.save(NewUser);
        logger.info("Added new user to DB, User: {}, {}", NewUser.getUserid(), NewUser.getUsername());
    }
    public MessageCreateData levelUpMessage(User user) {
        String ping = "<@" + user.getUserid() + ">"; // For pinging the user that leveled up
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("You Level Up!").setColor(Color.cyan)
                .addField("Level", String.valueOf(user.level), true)
                .addField("Xp Until Next Level", String.valueOf(Claclulations.getXptolevelup(user) - user.xp), true);
        MessageCreateBuilder messageCreateBuilder = new MessageCreateBuilder();
        messageCreateBuilder.addContent(ping).addEmbeds(embedBuilder.build());
        return messageCreateBuilder.build();
    }


}
