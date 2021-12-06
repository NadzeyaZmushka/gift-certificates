package com.epam.esm.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@Entity
@Table(name = "order", schema = "gifts")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
//@EntityListeners(AuditingEntityListener.class)
public class Order extends BaseEntity {

    @Column(name = "cost")
    private BigDecimal cost;
    @Column(name = "create_date", columnDefinition = "TIMESTAMP")
//    @CreatedDate
    private LocalDateTime createDate;
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @ManyToOne(targetEntity = Certificate.class)
    @JoinColumn(name = "certificate_id", referencedColumnName = "id")
    private Certificate certificate;

    public Order(Long id, BigDecimal cost, LocalDateTime createDate, User user, Certificate certificate) {
        super(id);
        this.cost = cost;
        this.createDate = createDate;
        this.user = user;
        this.certificate = certificate;
    }

    @PrePersist
    public void prePersist() {
        createDate = LocalDateTime.now();
        cost = certificate.getPrice();
    }

}
