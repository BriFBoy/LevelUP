package net.brifboy.levelup;


import net.brifboy.levelup.service.listeners.GuildSettup;
import net.brifboy.levelup.service.listeners.UserMessageing;
import net.brifboy.levelup.service.slashcommands.SetLevelRoles;
import net.brifboy.levelup.service.slashcommands.StatCommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;


@Configuration
public class LevelUPConfiguration {

    public static final String STATCOMMAND_ID = "levelstat";
    public static final String SETLEVELROLES_ID = "setlevelstat";

    @Value("${DISCORD_TOKEN}")
    private String DISCORDTOKEN;
    private final GuildSettup guildSettup;
    private final UserMessageing userMessageing;
    private final StatCommand statCommand;
    private final SetLevelRoles setLevelRoles;

    public LevelUPConfiguration(GuildSettup guildSettup, UserMessageing userMessageing, StatCommand statCommand, SetLevelRoles setLevelRoles) {
        this.guildSettup = guildSettup;
        this.userMessageing = userMessageing;
        this.statCommand = statCommand;
        this.setLevelRoles = setLevelRoles;
    }

    @Bean
    public JDA jda() throws InterruptedException {
        JDA jda = JDABuilder.create(DISCORDTOKEN, getGatewayIntent())
                .addEventListeners(guildSettup)
                .addEventListeners(userMessageing)
                .addEventListeners(statCommand)
                .addEventListeners(setLevelRoles)
                .disableCache(getCacheFlags())
                .build();
        jda.awaitReady();
        jda.updateCommands().addCommands(Commands.slash(STATCOMMAND_ID, "View you level stat"),
                Commands.slash(SETLEVELROLES_ID, "Set Roles you want the user to get")).queue();
        return jda;
    }

    private List<GatewayIntent> getGatewayIntent() {
        return List.of(
                GatewayIntent.MESSAGE_CONTENT,
                GatewayIntent.GUILD_MEMBERS,
                GatewayIntent.GUILD_MESSAGES
        );
    }
    private List<CacheFlag> getCacheFlags() {
        return List.of(
                CacheFlag.ACTIVITY,
                CacheFlag.EMOJI,
                CacheFlag.STICKER,
                CacheFlag.CLIENT_STATUS,
                CacheFlag.ONLINE_STATUS,
                CacheFlag.VOICE_STATE,
                CacheFlag.SCHEDULED_EVENTS);
    }

}
