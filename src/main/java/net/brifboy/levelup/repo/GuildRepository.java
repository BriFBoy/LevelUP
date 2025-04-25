package net.brifboy.levelup.repo;

import net.brifboy.levelup.model.Guild;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
interface GuildRepository extends JpaRepository<Guild, Long> {
}
