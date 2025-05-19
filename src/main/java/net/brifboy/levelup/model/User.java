package net.brifboy.levelup.model;

import jakarta.persistence.*;
import net.brifboy.levelup.service.Claclulations;

@Entity(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "by1")
    @SequenceGenerator(name = "by1", sequenceName = "by1", allocationSize = 1)
    private int id;
    @Column(nullable = false)
    private long userid;
    private String username;
    public int level;
    public int xp;

    @ManyToOne
    @JoinColumn(name = "roleid")
    private LevelRole levelRole;

    @ManyToOne
    @JoinColumn(name = "guildid", nullable = false)
    private Guild Guild;

    public User(long userid, String username, int level, int xp, LevelRole levelRole, Guild guild) {
        this.userid = userid;
        this.username = username;
        this.level = level;
        this.xp = xp;
        this.levelRole = levelRole;
        this.Guild = guild;
    }

    public User() {}

    public long getUserid() {
        return userid;
    }

    public String getUsername() {
        return username;
    }

    public LevelRole getLevelRole() {
        return levelRole;
    }

    public void setLevelRole(LevelRole levelRole) {
        this.levelRole = levelRole;
    }
}
