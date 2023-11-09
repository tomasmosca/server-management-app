package com.app.server.service;

import com.app.server.model.Server;
import com.app.server.model.User;
import com.app.server.repository.ServerRepository;
import com.app.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ServerService {

    private final ServerRepository serverRepository;
    private final UserRepository userRepository;

    @Autowired
    public ServerService(ServerRepository serverRepository, UserRepository userRepository) {
        this.serverRepository = serverRepository;
        this.userRepository = userRepository;
    }

    public Server createServer(Server server, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        server.setUser(user);
        return serverRepository.save(server);
    }

    public List<Server> getUserServers(Long userId) {
        return userId != null ? serverRepository.findAll().stream().filter(server -> server.getUser().getId().equals(userId)).toList() : serverRepository.findAll();
    }

    public Server updateServer(Long id, Server serverUpdates) {
        Server existingServer = serverRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Server not found with id " + id));

        Server updatedServer = existingServer.toBuilder()
                .ip(serverUpdates.getIp() != null ? serverUpdates.getIp() : existingServer.getIp())
                .name(serverUpdates.getName() != null ? serverUpdates.getName() : existingServer.getName())
                .memory(serverUpdates.getMemory() != null ? serverUpdates.getMemory() : existingServer.getMemory())
                .status(serverUpdates.getStatus() != null ? serverUpdates.getStatus() : existingServer.getStatus())
                .type(serverUpdates.getType() != null ? serverUpdates.getType() : existingServer.getType())
                .build();

        return serverRepository.save(updatedServer);
    }

    public void deleteServer(Long id) {
        if (serverRepository.existsById(id)) {
            serverRepository.deleteById(id);
        }
    }

}
