package com.example.battlesimulator.repository;

import com.example.battlesimulator.domain.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {
}