package net.brifboy.levelup.service;

import net.brifboy.levelup.model.Guild;
import net.brifboy.levelup.repo.GuildDBInteractions;
import net.brifboy.levelup.repo.UserDBInteraction;
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
    private GuildDBInteractions guildDBInteractions;
    @Autowired
    private UserDBInteraction userDBInteraction;


    private static final Logger logger = LoggerFactory.getLogger(GuildSettup.class);
    @Override
    public void onGuildJoin(@NotNull GuildJoinEvent event) {

        Guild guild = new Guild(event.getGuild().getIdLong(), event.getGuild().getName());
        Guild guildDB = guildDBInteractions.findById(event.getGuild().getIdLong());

        if (guildDB == null) {
            guildDBInteractions.saveGuild(guild);
            logger.info("Saved Guild {}, {} to DB", guild.getGuildid(), guild.getName());
        } else {
            logger.warn("Guild {}, {}, was already in DB when joining", guild.getGuildid(), guild.getName());
        }
    }

    @Override
    public void onGuildLeave(@NotNull GuildLeaveEvent event) {
        Guild guild = guildDBInteractions.findById(event.getGuild().getIdLong());
        if (guild != null) {
            userDBInteraction.deleteUsers(userDBInteraction.getUsersFromGuildId(guild.getGuildid()));
            guildDBInteractions.deleteGuild(guild);
        }

    }
}
