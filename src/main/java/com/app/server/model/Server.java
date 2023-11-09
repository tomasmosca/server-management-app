package com.app.server.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "servers")
public class Server {

    @Id
    @SequenceGenerator(
            name = "server_sequence",
            sequenceName = "server_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "server_sequence")
    private Long id;

    @Column(nullable=false)
    @NotBlank(message = "IP cannot be blank")
    private String ip;

    @Column(nullable=false)
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @Column(nullable=false)
    @NotBlank(message = "Memory cannot be blank")
    private String memory;

    private String image_url;

    @Column(nullable=false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(nullable=false)
    @Enumerated(EnumType.STRING)
    private Type type;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public enum Status {
        SERVER_UP,
        SERVER_DOWN
    }

    public enum Type {
        WEB,        // For hosting websites
        DATABASE,   // Dedicated to database services
        APPLICATION,// Runs specific applications
        FILE,       // Stores and shares files
        MAIL,       // Manages email hosting
        CLOUD,      // Offers cloud services
        VPN,        // Provides VPN functionalities
        PERSONAL    // For personal or home use, multipurpose
    }


}
