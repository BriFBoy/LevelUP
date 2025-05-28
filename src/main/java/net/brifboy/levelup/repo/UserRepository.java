package net.brifboy.levelup.repo;

import net.brifboy.levelup.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM users WHERE guildid = :g", nativeQuery = true)
    List<User> getUsersByGuildId(@Param("g") long guildid);

    @Query(value = "SELECT * FROM users WHERE userid = :u AND guildid = :g", nativeQuery = true)
    User getUserFromIdAndGuildId(@Param("u") long userid, @Param("g") long guildid);

    @Query(value = "SELECT * FROM users WHERE guildid = :g ORDER BY level DESC LIMIT :t", nativeQuery = true)
    List<User> getUsersWithHighestLevel(@Param("t") int top, @Param("g") long guildid);
}
