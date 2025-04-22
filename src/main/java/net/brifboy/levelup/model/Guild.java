package net.brifboy.levelup.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity(name = "Guild")
public class Guild {

    @Id
    private Long guildid;
    private String name;

    public Guild(long guildid, String name) {
        this.guildid = guildid;
        this.name = name;
    }

    public Guild() {
    }

    public String getName() {
        return name;
    }

    public Long getGuildid() {
        return guildid;
    }
}
