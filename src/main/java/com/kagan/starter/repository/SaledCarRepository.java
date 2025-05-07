package com.kagan.starter.repository;

import com.kagan.starter.entity.SaledCar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SaledCarRepository extends JpaRepository<SaledCar,Long> {
}
