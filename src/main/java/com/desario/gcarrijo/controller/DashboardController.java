package com.desario.gcarrijo.controller;

import com.desario.gcarrijo.entity.dto.MediumTicketDTO;
import com.desario.gcarrijo.entity.dto.MonthlyBillingDTO;
import com.desario.gcarrijo.entity.dto.TopBuyerDTO;
import com.desario.gcarrijo.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/top-compradores")
    public List<TopBuyerDTO> getTopBuyers() {
        return dashboardService.getTopFiveBuyers();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/ticket-medio")
    public List<MediumTicketDTO> getMediumTickets() {
        return dashboardService.getMediumTickets();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/faturamento-mensal")
    public MonthlyBillingDTO getMonthlyBilling() {
        return dashboardService.getMonthlyBilling();
    }
}
