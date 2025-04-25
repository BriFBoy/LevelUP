package net.brifboy.levelup;


import net.brifboy.levelup.service.GuildSettup;
import net.brifboy.levelup.service.UserGetXp;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
        return JDABuilder.create(DISCORDTOKEN, getGatewayIntent())
                .addEventListeners(guildSettup)
                .addEventListeners(userGetXp)
                .build();
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
