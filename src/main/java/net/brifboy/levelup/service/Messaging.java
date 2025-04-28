package net.brifboy.levelup.service;

import net.brifboy.levelup.model.User;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import org.hibernate.annotations.AttributeAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.awt.*;

@Service
public class Messaging {

    @Autowired
    private UserClaclulations claclulations;

    public MessageCreateData levelUpMessage(User user, MessageChannel channel) {
        String ping = "<@" + user.getUserid() + ">"; // For pinging the user that leveled up
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("You Level Up!").setColor(Color.cyan)
                .addField("Level", String.valueOf(user.level), true)
                .addField("Xp Until Next Level", String.valueOf(claclulations.getXptolevelup(user) - user.xp), true);
        MessageCreateBuilder messageCreateBuilder = new MessageCreateBuilder();
        messageCreateBuilder.addContent(ping).addEmbeds(embedBuilder.build());
        return messageCreateBuilder.build();
    }
    public MessageEmbed statMessage(User user, MessageChannel channel) {

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("Stats").setColor(Color.cyan).addField("Level", String.valueOf(user.level), true)
                .addField("Xp", String.valueOf(user.xp), true)
                .addField("Xp Until Next Level", String.valueOf(claclulations.getXptolevelup(user) - user.xp), true);
        return embedBuilder.build();

    }
}
