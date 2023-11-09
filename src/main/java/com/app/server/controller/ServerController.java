package com.app.server.controller;

import com.app.server.model.Server;
import com.app.server.service.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/servers")
public class ServerController {

    private final ServerService serverService;

    @Autowired
    public ServerController(ServerService serverService) {
        this.serverService = serverService;
    }

    @GetMapping
    public ResponseEntity<List<Server>> getAllUserServers(@RequestParam("userId") Optional<Long> userId) {
        List<Server> servers = serverService.getUserServers(userId.orElse(null));
        return ResponseEntity.ok(servers);
    }

    @PostMapping
    public ResponseEntity<Server> createServer(@RequestBody Server server, @RequestParam("userId") Long userId) {
        Server newServer = serverService.createServer(server, userId);
        return ResponseEntity.ok(newServer);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Server> updateServer(@PathVariable Long id, @RequestBody Server server) {
        Server updatedServer = serverService.updateServer(id, server);
        return ResponseEntity.ok(updatedServer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Server> deleteServer(@PathVariable Long id) {
        serverService.deleteServer(id);
        return ResponseEntity.noContent().build();
    }

}
