package net.brifboy.levelup.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity(name = "Users")
public class User {

    @Id
    private long userid;
    private String username;
    public int level;
    public int xp;
    @ManyToOne
    private Guild Guild;

    public User(long userid, String username, int level, int xp, net.brifboy.levelup.model.Guild guild) {
        this.userid = userid;
        this.username = username;
        this.level = level;
        this.xp = xp;
        this.Guild = guild;
    }

    public User() {}

}
