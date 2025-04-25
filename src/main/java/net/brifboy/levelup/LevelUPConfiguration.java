package net.brifboy.levelup;


import net.brifboy.levelup.service.GuildSettup;
import net.brifboy.levelup.service.UserGetXp;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;


@Configuration
public class LevelUPConfiguration {
    @Value("${DISCORD_TOKEN}")
    private String DISCORDTOKEN;
    @Autowired
    private final GuildSettup guildSettup;
    @Autowired
    private final UserGetXp userGetXp;


    public LevelUPConfiguration(GuildSettup guildSettup, UserGetXp userGetXp) {
        this.guildSettup = guildSettup;
        this.userGetXp = userGetXp;
    }


    @Bean
    public JDA jda() {
        JDA jda = JDABuilder.create(DISCORDTOKEN, getGatewayIntent())
                .addEventListeners(guildSettup)
                .addEventListeners(userGetXp)
                .disableCache(CacheFlag.VOICE_STATE, CacheFlag.SCHEDULED_EVENTS)
                .build();
        jda.updateCommands()
                .addCommands(net.dv8tion.jda.api.interactions.commands.build.Commands.slash("levelstat", "See stats for the levels")
                        .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MESSAGE_SEND)));
        return jda;
    }
    private static List<GatewayIntent> getGatewayIntent() {
        return List.of(
                GatewayIntent.MESSAGE_CONTENT,
                GatewayIntent.GUILD_MEMBERS,
                GatewayIntent.GUILD_MESSAGES,
                GatewayIntent.GUILD_PRESENCES,
                GatewayIntent.GUILD_EXPRESSIONS

        );
    }

}
