package net.brifboy.levelup.service.slashcommands;


import net.brifboy.levelup.LevelUPConfiguration;
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
        if (event.getName().equals(LevelUPConfiguration.STATCOMMAND_ID)) {

            User user = userDBInteraction.getUserFormUserIdAndGuildId(event.getUser().getIdLong(), Objects.requireNonNull(event.getGuild()).getIdLong());
            Guild guild = guildDBInteractions.findById(event.getGuild().getIdLong());

            if (guild == null) { // If guild Is null then save the guild and return
                guild = new Guild(event.getGuild().getIdLong(), event.getGuild().getName(), false);
                logger.warn("No guild found In DB when interacting with stat command. Guild: {}, {}", guild.getGuildid(), guild.getName());
                guildDBInteractions.saveGuild(guild);
                event.reply("Your guild was not found. Please try again").setEphemeral(true).queue();

                return;
            }  else if (user == null) { // if user is null then create a new user and save it
                user = new User(event.getUser().getIdLong(), event.getUser().getName(), 0, 0, null, guild);
                userDBInteraction.saveUser(user);
                logger.info("No user for command: levelstat, added user to DB");
            }

            event.replyEmbeds(messaging.statMessage(user, event.getChannel())).setEphemeral(true).queue();
        }
    }
}
