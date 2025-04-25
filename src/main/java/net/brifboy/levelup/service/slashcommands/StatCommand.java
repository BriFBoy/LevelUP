package net.brifboy.levelup.service.slashcommands;


import net.brifboy.levelup.model.User;
import net.brifboy.levelup.repo.UserDBInteraction;
import net.brifboy.levelup.service.MessageSending;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;


public class StatCommand extends ListenerAdapter {
    @Autowired
    MessageSending messageSending;
    @Autowired
    UserDBInteraction userDBInteraction;

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        User user = userDBInteraction.getUserFormUserIdAndGuildId(event.getUser().getIdLong(), Objects.requireNonNull(event.getGuild()).getIdLong());

        if (event.getName().equals("LevelStat")) {
            messageSending.sendStatMessage(user, event.getChannel());
        }
    }
}
