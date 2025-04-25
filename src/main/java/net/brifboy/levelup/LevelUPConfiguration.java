package net.brifboy.levelup;


import net.brifboy.levelup.service.GuildSettup;
import net.brifboy.levelup.service.UserGetXp;
import net.brifboy.levelup.service.slashcommands.StatCommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;


@Configuration
public class LevelUPConfiguration {
    @Value("${DISCORD_TOKEN}")
    private String DISCORDTOKEN;
    private final GuildSettup guildSettup;
    private final UserGetXp userGetXp;
    private final StatCommand statCommand;


    public LevelUPConfiguration(GuildSettup guildSettup, UserGetXp userGetXp, StatCommand statCommand) {
        this.guildSettup = guildSettup;
        this.userGetXp = userGetXp;
        this.statCommand = statCommand;
    }


    @Bean
    public JDA jda() throws InterruptedException {
        JDA jda = JDABuilder.create(DISCORDTOKEN, getGatewayIntent())
                .addEventListeners(guildSettup)
                .addEventListeners(userGetXp)
                .addEventListeners(statCommand)
                .disableCache(CacheFlag.VOICE_STATE, CacheFlag.SCHEDULED_EVENTS)
                .build();
        jda.awaitReady();
        jda.getGuildById("1302710445139951657").upsertCommand(Commands.slash("levelstat", "See level stats")).queue();
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
