package net.brifboy.levelup.repo;

import net.brifboy.levelup.model.LevelRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LevelRoleRepository extends JpaRepository<LevelRole, Integer> {

}
