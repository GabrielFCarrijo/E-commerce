package com.desario.gcarrijo.repository;

import com.desario.gcarrijo.entity.Order;
import com.desario.gcarrijo.entity.dto.MediumTicketDTO;
import com.desario.gcarrijo.entity.dto.MonthlyBillingDTO;
import com.desario.gcarrijo.entity.dto.TopBuyerDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DashboardRepository extends JpaRepository<Order, UUID> {

    @Query("""
        SELECT new com.desario.gcarrijo.entity.dto.TopBuyerDTO(
            u.id, u.nome, u.email, SUM(o.total))
        FROM Order o
        JOIN o.user u
        WHERE o.status = 'PAGO'
        GROUP BY u.id, u.nome, u.email
        ORDER BY SUM(o.total) DESC
    """)
    List<TopBuyerDTO> findTopFiveBuyers();

    @Query("""
        SELECT new com.desario.gcarrijo.entity.dto.MediumTicketDTO(
            u.id, u.nome, u.email, AVG(o.total))
        FROM Order o
        JOIN o.user u
        WHERE o.status = 'PAGO'
        GROUP BY u.id, u.nome, u.email
    """)
    List<MediumTicketDTO> findMediumTicketByUser();

    @Query("""
        SELECT new com.desario.gcarrijo.entity.dto.MonthlyBillingDTO(
            SUM(o.total))
        FROM Order o
        WHERE o.status = 'PAGO'
        AND MONTH(o.dataCriacao) = MONTH(CURRENT_DATE)
        AND YEAR(o.dataCriacao) = YEAR(CURRENT_DATE)
    """)
    MonthlyBillingDTO findMonthlyBilling();
}
