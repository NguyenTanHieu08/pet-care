package com.hieusrpingboot.petcare.repository;

import com.hieusrpingboot.petcare.entity.Order;
import com.hieusrpingboot.petcare.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByOwnerId(Long ownerId);
    List<Order> findByStatus(OrderStatus status);
    List<Order> findByOrderDateBetween(LocalDateTime start, LocalDateTime end);
    List<Order> findByOwnerIdAndStatus(Long ownerId, OrderStatus status);
}
