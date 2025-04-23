package net.brifboy.levelup.service;

import net.brifboy.levelup.model.Guild;
import net.brifboy.levelup.model.User;
import net.brifboy.levelup.repo.GuildRepository;
import net.brifboy.levelup.repo.UserRepository;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserGetXp extends ListenerAdapter {
    @Autowired
    GuildRepository guildRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            return;
        }
        Guild guild = guildRepository.findById(event.getGuild().getIdLong()).orElseGet(() -> {
            Guild guild1 = new Guild(event.getGuild().getIdLong(), event.getGuild().getName());
            guildRepository.save(guild1);
            return guild1;
        });
        User user = userRepository.getUserFromIdAndGuildId(event.getAuthor().getIdLong(), event.getGuild().getIdLong());
        if (user != null) {
            user.xp++;
            if (user.xp >= 10) {
                user.level++;
                user.xp = 0;
            }
            userRepository.save(user);
        } else {
            userRepository.save(new User(event.getAuthor().getIdLong(), event.getAuthor().getName(), 0, 0, guild));
        }
    }


}
