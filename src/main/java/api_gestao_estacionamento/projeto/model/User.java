package api_gestao_estacionamento.projeto.model;

import api_gestao_estacionamento.projeto.web.dto.user.UserCreateDto;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
@ToString
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    @Column(name = "username", nullable = false, unique = true, length = 150)
    private String username;
    @Column(name = "password", nullable = false, length = 200)
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role = Role.ROLE_CLIENT;

    public enum Role {
        ROLE_CLIENT, ROLE_ADMIN
    }

    @Column(name = "created_by")
    @CreatedBy
    private String createdBy;
    @Column(name = "created_at")
    @CreatedDate
    private LocalDateTime createdAt;
    @Column(name = "last_modified_by")
    @LastModifiedBy
    private String lastModifiedBy;
    @Column(name = "last_modified_at")
    @LastModifiedDate
    private LocalDateTime lastModifiedAt;

    @Column(name = "activation_token")
    private String activationToken;
    @Column(name = "is_active")
    private boolean isActive = false;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public User(UserCreateDto dto) {
        this.name = dto.getName();
        this.username = dto.getUsername();
        this.password = dto.getPassword();
    }


}
