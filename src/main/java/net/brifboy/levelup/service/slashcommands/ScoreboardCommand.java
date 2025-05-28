package net.brifboy.levelup.service.slashcommands;

import net.brifboy.levelup.LevelUPConfiguration;
import net.brifboy.levelup.model.User;
import net.brifboy.levelup.repo.UserDBInteraction;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;

@Service
public class ScoreboardCommand extends ListenerAdapter {
    @Autowired
    private UserDBInteraction userDBInteraction;

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (event.getName().equals(LevelUPConfiguration.SCOREBOARD_COMMAND)) {
            switch (event.getSubcommandName()) {
                case LevelUPConfiguration.SCOREBOARD_OPTION_GUILD -> event.replyEmbeds(getScoreEmbed(5, event.getGuild().getIdLong())).setEphemeral(true).queue();
                default -> {}
            }
        }

    }

    private MessageEmbed getScoreEmbed(int top, long guildid) {
        List<User> users = userDBInteraction.getUsersWithHighestLevel(top, guildid);
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("***ScoreBoard***");
        eb.setColor(Color.orange);
        String board = "**";

        for (int i = 0; i < users.size(); i++) {
            board += (i + 1) + ".    " + users.get(i).getUsername() + ", Level: " + users.get(i).level + " \n ";
        }

        board += "**";
        eb.setDescription(board);

        return eb.build();
    }
}
