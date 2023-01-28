package com.example.authservice.entity;

//import com.sun.istack.NotNull;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Table;
import org.springframework.data.annotation.LastModifiedDate;

//import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
//@Table(name = "user")
public class User {
    public enum Role {ADMIN, USER, PROVIDER}

    private static final boolean DEFAULT_NON_LOCKED_FLAG = true;
    private static final boolean DEFAULT_ENABLED_FLAG = true;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password")
    private String password;

    private String avatar;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Set<Role> role;

    @Column(name = "non_locked")
    private Boolean nonLocked;

    @Column(name = "enabled")
    private Boolean enabled;

    @Column(name = "creation_time")
    private LocalDateTime creationTime;

    @LastModifiedDate
    @Column(name = "last_updated_timestamp")
    private LocalDateTime lastUpdatedTimestamp;
    @PrePersist
    public void prePersist() {
        if (creationTime == null) {
            creationTime = LocalDateTime.now();
        }
        if (lastUpdatedTimestamp == null) {
            lastUpdatedTimestamp = LocalDateTime.now();
        }
        if (getEnabled() == null) {
            setEnabled(DEFAULT_ENABLED_FLAG);
        }
        if (getNonLocked() == null) {
            setNonLocked(DEFAULT_NON_LOCKED_FLAG);
        }
    }

    @PreUpdate
    public void preUpdate() {
        lastUpdatedTimestamp = LocalDateTime.now();
    }
}
