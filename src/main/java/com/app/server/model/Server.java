package com.app.server.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Map;

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

    @Column(nullable=false, unique = true)
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

    private static final Map<Type, String> typeToImageUrlMap = Map.of(
            Type.WEB, "https://i.ibb.co/rQc4YkD/Pngtree-modern-isometric-server-illustrated-on-6202098.png",
            Type.DATABASE, "https://i.ibb.co/Dkh6Zv0/vecteezy-database-data-analytics-icon-monitoring-big-data-analysis-29566628.jpg",
            Type.APPLICATION, "https://i.ibb.co/LdsSbRR/Lovepik-com-450037338-web-Servers.png",
            Type.FILE, "https://i.ibb.co/2yZLPXT/Lovepik-com-450005961-isometric-server-illustrated-in-vector.png",
            Type.MAIL, "https://i.ibb.co/FwjStvk/Lovepik-com-400495986-mail.png",
            Type.CLOUD, "https://i.ibb.co/WWLPMyS/Lovepik-com-648397958-Vector-cloud-computing-security-shield.png",
            Type.VPN, "https://i.ibb.co/rQc4YkD/Pngtree-modern-isometric-server-illustrated-on-6202098.png",
            Type.PERSONAL, "https://i.ibb.co/DGHB0SJ/Lovepik-com-400219397-computer.png"
    );

    public void setImageUrlBasedOnType() {
        this.setImage_url(typeToImageUrlMap.getOrDefault(this.type, "https://i.ibb.co/rQc4YkD/Pngtree-modern-isometric-server-illustrated-on-6202098.png"));
    }

}
