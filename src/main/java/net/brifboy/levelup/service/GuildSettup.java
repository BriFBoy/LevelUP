package net.brifboy.levelup.service;

import net.brifboy.levelup.model.Guild;
import net.brifboy.levelup.repo.GuildRepository;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GuildSettup extends ListenerAdapter {

    @Autowired
    private GuildRepository guildrepo;

    private static final Logger logger = LoggerFactory.getLogger(GuildSettup.class);
    @Override
    public void onGuildJoin(@NotNull GuildJoinEvent event) {

        Guild guild = new Guild(event.getGuild().getIdLong(), event.getGuild().getName());
        Guild guildDB = guildrepo.findById(event.getGuild().getIdLong()).orElse(null);

        if (guildDB == null) {
            guildrepo.save(guild);
            logger.info("Saved Guild {}, {} to DB", guild.getGuildid(), guild.getName());
        } else {
            logger.warn("Guild {}, {}, was already in DB when joining", guild.getGuildid(), guild.getName());
        }
    }

    @Override
    public void onGuildLeave(@NotNull GuildLeaveEvent event) {
        guildrepo.findById(event.getGuild().getIdLong())
                .ifPresent(guildDB -> {
                    guildrepo.delete(guildDB);
                    logger.info("Deleted Guild {}, {} to DB", guildDB.getGuildid(), guildDB.getName());

                });

    }
}
