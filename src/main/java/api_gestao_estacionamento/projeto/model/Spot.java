package api_gestao_estacionamento.projeto.model;

import api_gestao_estacionamento.projeto.web.dto.spot.SpotCreateDto;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "parking_spots")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class Spot implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", nullable = false, length = 8, unique = true)
    private String code;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ParkingSpotStatus parkingSpotStatus = ParkingSpotStatus.AVAIABLE;

    @CreatedBy
    private String createdBy;

    @CreatedDate
    private LocalDate createdAt;

    @LastModifiedBy
    private String lastModifiedBy;

    @LastModifiedDate
    private LocalDate lastModifiedAt;

    public enum ParkingSpotStatus {
        AVAIABLE, OCCUPIED
    }

    public Spot(SpotCreateDto dto) {
        this.code = dto.getCode();
        this.parkingSpotStatus = ParkingSpotStatus.valueOf(dto.getStatus());
    }


}
