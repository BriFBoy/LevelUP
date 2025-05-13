package net.brifboy.levelup.model;

import jakarta.persistence.*;

@Entity
@Table(name = "levelrole")
public class LevelRole {

    @Id
    @SequenceGenerator(name = "by1", sequenceName = "by1", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "by1")
    private int id;
    private  long roleid;
    @ManyToOne
    @JoinColumn(nullable = false, name = "guildid")
    private Guild guild;
    private int getatlevel;
    private String name;

    public LevelRole(long roleid, Guild guild, int getatlevel, String name) {

        this.roleid = roleid;
        this.guild = guild;
        this.getatlevel = getatlevel;
        this.name = name;
    }

    public LevelRole() {
    }
}
