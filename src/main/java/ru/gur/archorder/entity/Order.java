package ru.gur.archorder.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "profile_id", nullable = false)
    private UUID profileId;

    @Generated(value = GenerationTime.INSERT)
    @GenericGenerator(name = "fieldGenerator", strategy = "increment")
    @Column(name = "order_number", insertable = false)
    private Long orderNumber;

    @Column(name = "product_quantity")
    private Long productQuantity;

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private State state;
}