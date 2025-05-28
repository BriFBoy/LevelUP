package net.brifboy.levelup.service.listeners;

import net.brifboy.levelup.model.Guild;
import net.brifboy.levelup.repo.GuildRepository;
import net.brifboy.levelup.repo.UserRepository;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class GuildSettup extends ListenerAdapter {

    private final GuildRepository guildRepository;
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(GuildSettup.class);

    public GuildSettup(GuildRepository guildRepository, UserRepository userRepository) {
        this.guildRepository = guildRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void onGuildJoin(@NotNull GuildJoinEvent event) {
        Guild guild = new Guild(event.getGuild().getIdLong(), event.getGuild().getName());
        Guild guildDB = guildRepository.findById(event.getGuild().getIdLong()).orElse(null);

        if (guildDB == null) {
            guildRepository.save(guild);
        } else {
            logger.warn("Guild {}, {}, was already in DB when joining", guild.getGuildid(), guild.getName());
        }
    }

    @Override
    public void onGuildLeave(@NotNull GuildLeaveEvent event) {
        Guild guild = guildRepository.findById(event.getGuild().getIdLong()).orElse(null);
        if (guild != null) {
            userRepository.deleteAll(userRepository.getUsersByGuildId(guild.getGuildid()));
            guildRepository.delete(guild);
        }
        logger.info("Bot Left a Guild");

    }
}
