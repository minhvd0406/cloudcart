package com.cloudcart.orderservice.modals;

import com.cloudcart.orderservice.enums.EOrderPaymentStatus;
import com.cloudcart.orderservice.enums.EOrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@Entity
@Table(name = "customer_orders")
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue
    @UuidGenerator
    private String id;

    private String userId;

    private String firstName;

    private String lastName;

    private String addressLine1;

    private String addressLine2;

    private String city;

    private String phoneNo;

    private double orderAmt;

    private LocalDateTime placedOn;

    @Enumerated(EnumType.STRING)
    private EOrderStatus orderStatus;

    @Enumerated(EnumType.STRING)
    private EOrderPaymentStatus paymentStatus;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "order_items", joinColumns = @JoinColumn(name = "order_id"))
    private Set<OrderItem> orderItems;

}
