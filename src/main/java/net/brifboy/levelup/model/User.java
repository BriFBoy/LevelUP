package net.brifboy.levelup.model;

import jakarta.persistence.*;
import net.brifboy.levelup.service.UserClaclulations;

@Entity(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "by1")
    @SequenceGenerator(name = "by1", sequenceName = "by1", allocationSize = 1)
    private int id;
    private long userid;
    private String username;
    public int level;
    public int xp;

    @ManyToOne
    @JoinColumn(name = "guildid")
    private Guild Guild;

    public User(long userid, String username, int level, int xp, net.brifboy.levelup.model.Guild guild) {
        this.userid = userid;
        this.username = username;
        this.level = level;
        this.xp = xp;
        this.Guild = guild;
    }

    public User() {}

    public boolean checkLevel(UserClaclulations claclulations) {
       return claclulations.checkUserLevelUp(this);
    }
    public void addXp(UserClaclulations claclulations, String[] words) {
        claclulations.addXp(this, words);
    }

    public long getUserid() {
        return userid;
    }

    public String getUsername() {
        return username;
    }
}
