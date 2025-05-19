package net.brifboy.levelup.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity(name = "Guild")
public class Guild {

    @Id
    private Long guildid;
    private String name;
    private boolean LevelRoles;

    public Guild(long guildid, String name, boolean levelRoles) {
        this.guildid = guildid;
        this.name = name;
        LevelRoles = levelRoles;
    }

    public Guild() {
    }

    public String getName() {
        return name;
    }

    public Long getGuildid() {
        return guildid;
    }

    public boolean isLevelRoles() {
        return LevelRoles;
    }

    public void setLevelRoles(boolean levelRoles) {
        LevelRoles = levelRoles;
    }

}
