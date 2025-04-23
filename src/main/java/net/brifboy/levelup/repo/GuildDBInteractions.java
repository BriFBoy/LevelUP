package net.brifboy.levelup.repo;

import net.brifboy.levelup.model.Guild;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class GuildDBInteractions {

    @Autowired
    GuildRepository guildRepository;

    public void saveGuild(Guild guild) {
        guildRepository.save(guild);
    }
    public void deleteGuild(Guild guild) {
        guildRepository.delete(guild);
    }

    // Will return NULL if there is none in DB
    public Guild findById(long guildid) {
        return guildRepository.findById(guildid).orElse(null);

    }


}
