package com.desario.gcarrijo.service;

import com.desario.gcarrijo.entity.dto.MediumTicketDTO;
import com.desario.gcarrijo.entity.dto.MonthlyBillingDTO;
import com.desario.gcarrijo.entity.dto.TopBuyerDTO;
import com.desario.gcarrijo.repository.DashboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DashboardService {

    @Autowired
    private DashboardRepository dashboardRepository;

    public List<TopBuyerDTO> getTopFiveBuyers() {
        return dashboardRepository.findTopFiveBuyers();
    }

    public List<MediumTicketDTO> getMediumTickets() {
        return dashboardRepository.findMediumTicketByUser();
    }

    public MonthlyBillingDTO getMonthlyBilling() {
        return dashboardRepository.findMonthlyBilling();
    }
}