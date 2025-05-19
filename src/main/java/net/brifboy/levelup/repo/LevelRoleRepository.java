package net.brifboy.levelup.repo;

import net.brifboy.levelup.model.LevelRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LevelRoleRepository extends JpaRepository<LevelRole, Integer> {

    @Query(value = "SELECT getatlevel FROM levelrole where guildid = :g", nativeQuery = true)
    int[] getLevelsForGuild(@Param("g") long guildid);

    @Query(value = "SELECT roleid FROM levelrole where guildid = :g AND getatlevel = :l", nativeQuery = true)
    long getRoleFromLevelAndGuild(@Param("g") long guildid, @Param("l") int level);

}
