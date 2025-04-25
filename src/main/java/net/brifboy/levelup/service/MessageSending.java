package net.brifboy.levelup.service;

import net.brifboy.levelup.model.User;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import org.springframework.stereotype.Service;


import java.awt.*;

@Service
public class MessageSending {


    public void sendLevelupMessage(User user, MessageChannel channel) {
        String ping = "<@" + user.getUserid() + ">";
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("You Level Up!").setColor(Color.cyan).addField("Level", String.valueOf(user.level), true);
        MessageCreateBuilder messageCreateBuilder = new MessageCreateBuilder();
        messageCreateBuilder.addContent(ping).addEmbeds(embedBuilder.build());
        channel.sendMessage(messageCreateBuilder.build()).queue();
    }
    public void sendStatMessage(User user, MessageChannel channel) {

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("Stats").setColor(Color.cyan).addField("Level", String.valueOf(user.level), true)
                .addField("Xp", String.valueOf(user.xp), true);

        channel.sendMessageEmbeds(embedBuilder.build()).queue();
    }
}
