package net.brifboy.levelup.service.slashcommands;


import net.brifboy.levelup.model.Guild;
import net.brifboy.levelup.model.User;
import net.brifboy.levelup.repo.GuildDBInteractions;
import net.brifboy.levelup.repo.UserDBInteraction;
import net.brifboy.levelup.service.Messaging;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class StatCommand extends ListenerAdapter {
    @Autowired
    Messaging messaging;
    @Autowired
    UserDBInteraction userDBInteraction;
    @Autowired
    GuildDBInteractions guildDBInteractions;

    static final Logger logger = LoggerFactory.getLogger(StatCommand.class);

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        User user = userDBInteraction.getUserFormUserIdAndGuildId(event.getUser().getIdLong(), Objects.requireNonNull(event.getGuild()).getIdLong());
        Guild guild = guildDBInteractions.findById(event.getGuild().getIdLong());
        if (user == null) {
            user = new User(event.getUser().getIdLong(), event.getUser().getName(), 0, 0, guild);
            userDBInteraction.saveUser(user);
            logger.info("No user for command: levelstat, added user to DB");
        }

        if (event.getName().equals("levelstat")) {
            event.replyEmbeds(messaging.statMessage(user, event.getChannel())).queue();
        }
        logger.info("Interacted with slash command: levelstat");
    }
}
