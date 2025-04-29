package net.brifboy.levelup.repo;

import net.brifboy.levelup.model.Guild;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class GuildDBInteractions {

    private static final Logger logger = LoggerFactory.getLogger(GuildDBInteractions.class);
    @Autowired
    GuildRepository guildRepository;

    public void saveGuild(Guild guild) {
        guildRepository.save(guild);
        logger.info("Saved or Updated Guild: {}, {}", guild.getName(), guild.getGuildid());
    }
    public void deleteGuild(Guild guild) {
        guildRepository.delete(guild);
        logger.info("Deleted Guild: {}, {}", guild.getName(), guild.getGuildid());
    }

    // Will return NULL if there is none in DB
    public Guild findById(long guildid) {
        return guildRepository.findById(guildid).orElse(null);

    }


}
