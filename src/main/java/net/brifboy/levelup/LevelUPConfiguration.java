package net.brifboy.levelup;


import net.brifboy.levelup.service.listeners.GuildSettup;
import net.brifboy.levelup.service.listeners.UserGetXp;
import net.brifboy.levelup.service.slashcommands.ScoreboardCommand;
import net.brifboy.levelup.service.slashcommands.StatCommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
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
    private final ScoreboardCommand scoreboardCommand;
    public static final String LEVELSTAT_COMMAND = "levelstat";
    public static final String SCOREBOARD_COMMAND = "scoreboard";
    public static final String SCOREBOARD_OPTION_GUILD = "guild";

    public LevelUPConfiguration(GuildSettup guildSettup, UserGetXp userGetXp, StatCommand statCommand, ScoreboardCommand scoreboardCommand) {
        this.guildSettup = guildSettup;
        this.userGetXp = userGetXp;
        this.statCommand = statCommand;
        this.scoreboardCommand = scoreboardCommand;
    }

    @Bean
    public JDA jda() throws InterruptedException {
        JDA jda = JDABuilder.create(DISCORDTOKEN, getGatewayIntent())
                .addEventListeners(guildSettup)
                .addEventListeners(userGetXp)
                .addEventListeners(statCommand)
                .addEventListeners(scoreboardCommand)
                .disableCache(CacheFlag.VOICE_STATE, CacheFlag.SCHEDULED_EVENTS)
                .build();
        jda.awaitReady();
        jda.updateCommands().addCommands(Commands.slash(LEVELSTAT_COMMAND, "View you level stat"),
                Commands.slash(SCOREBOARD_COMMAND, "See the Scoreboard")
                        .addSubcommands(new SubcommandData(SCOREBOARD_OPTION_GUILD, "See guild scoreboard"))).queue();
        return jda;
    }

    private static List<GatewayIntent> getGatewayIntent() {
        return List.of(
                GatewayIntent.MESSAGE_CONTENT,
                GatewayIntent.GUILD_MEMBERS,
                GatewayIntent.GUILD_MESSAGES
        );
    }

}
