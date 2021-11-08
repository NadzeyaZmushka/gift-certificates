package com.epam.esm.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "order", schema = "gifts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "cost")
    private BigDecimal cost;
    @Column(name = "create_date", columnDefinition = "TIMESTAMP")
    private LocalDateTime createDate;
    @ManyToOne(targetEntity = User.class, cascade = {CascadeType.REMOVE, CascadeType.MERGE})
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
//    private List<Certificate> certificates;
    @ManyToOne(targetEntity = Certificate.class)
    @JoinColumn(name = "certificate_id", referencedColumnName = "id")
    private Certificate certificate;

    public Order(BigDecimal cost, LocalDateTime createDate, User user, Certificate certificate) {
        this.cost = cost;
        this.createDate = createDate;
        this.user = user;
        this.certificate = certificate;
    }
}
