package api_gestao_estacionamento.projeto.model;

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

@Entity
@Table(name = "addresses")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@EntityListeners(AuditingEntityListener.class)
public class Address implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "street", nullable = false)
    private String street;
    @Column(name = "number", nullable = false, length = 8)
    private String number;
    @Column(name = "city", nullable = false, length = 100)
    private String city;
    @Column(name = "state", nullable = false, length = 2)
    private String state;
    @Column(name = "zip_code", nullable = false, length = 8)
    private String zipCode;
    @Column(name = "country", nullable = false, length = 2)
    private String country;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(id, address.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}
