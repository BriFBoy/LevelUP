package net.brifboy.levelup.service.slashcommands;


import net.brifboy.levelup.LevelUPConfiguration;
import net.brifboy.levelup.model.Guild;
import net.brifboy.levelup.model.User;
import net.brifboy.levelup.repo.GuildRepository;
import net.brifboy.levelup.repo.UserRepository;
import net.brifboy.levelup.service.Claclulations;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.Objects;

@Service
public class StatCommand extends ListenerAdapter {
    @Autowired
    UserRepository userRepository;
    @Autowired
    GuildRepository guildRepository;

    static final Logger logger = LoggerFactory.getLogger(StatCommand.class);

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (event.getName().equals(LevelUPConfiguration.LEVELSTAT_COMMAND)) {

            User user = userRepository.getUserFromIdAndGuildId(event.getUser().getIdLong(), Objects.requireNonNull(event.getGuild()).getIdLong());
            Guild guild = guildRepository.findById(event.getGuild().getIdLong()).orElse(null);

            if (guild == null) { // If guild Is null then save the guild and return
                guild = new Guild(event.getGuild().getIdLong(), event.getGuild().getName());
                logger.warn("No guild found In DB when interacting with stat command. Guild: {}, {}", guild.getGuildid(), guild.getName());
                guildRepository.save(guild);
                event.reply("Your guild was not found. Please try again").setEphemeral(true).queue();

                return;
            }  else if (user == null) { // if user is null then create a new user and save it
                user = new User(event.getUser().getIdLong(), event.getUser().getName(), 0, 0, guild);
                userRepository.save(user);
                logger.info("No user for command: levelstat, added user to DB");
            }

            if (event.getName().equals(LevelUPConfiguration.LEVELSTAT_COMMAND)) {
                event.replyEmbeds(statMessage(user)).setEphemeral(true).queue();
            }
        }
    }

    public MessageEmbed statMessage(User user) {

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("Stats").setColor(Color.yellow).addField("Level", String.valueOf(user.level), true)
                .addField("Xp", String.valueOf(user.xp), true)
                .addField("Xp Until Next Level", String.valueOf(Claclulations.getXptolevelup(user) - user.xp), true);
        return embedBuilder.build();

    }


}
