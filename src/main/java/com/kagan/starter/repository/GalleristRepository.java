package com.kagan.starter.repository;

import com.kagan.starter.entity.Gallerist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GalleristRepository  extends JpaRepository<Gallerist,Long> {
}
