package com.app.server.repository;

import com.app.server.model.Server;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ServerRepository extends JpaRepository<Server, Long> {
}
