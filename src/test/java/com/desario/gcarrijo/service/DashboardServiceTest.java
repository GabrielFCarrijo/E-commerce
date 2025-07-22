package com.desario.gcarrijo.service;

import com.desario.gcarrijo.entity.dto.MediumTicketDTO;
import com.desario.gcarrijo.entity.dto.MonthlyBillingDTO;
import com.desario.gcarrijo.entity.dto.TopBuyerDTO;
import com.desario.gcarrijo.repository.DashboardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DashboardServiceTest {

    @InjectMocks
    private DashboardService dashboardService;

    @Mock
    private DashboardRepository dashboardRepository;

    private List<TopBuyerDTO> topBuyersMock;
    private List<MediumTicketDTO> ticketMock;
    private MonthlyBillingDTO billingMock;

    @BeforeEach
    void setUp() {
        topBuyersMock = List.of(
                new TopBuyerDTO(UUID.randomUUID(), "Gabriel Confiavel", "gabriel@test.com", BigDecimal.valueOf(150))
        );

        ticketMock = List.of(
                new MediumTicketDTO(UUID.randomUUID(), "Gabriel dos ticket", "gabriel@test.com", 75.0)
        );

        billingMock = new MonthlyBillingDTO(BigDecimal.valueOf(225));
    }

    @Test
    void testGetTopFiveBuyers() {
        when(dashboardRepository.findTopFiveBuyers()).thenReturn(topBuyersMock);

        List<TopBuyerDTO> result = dashboardService.getTopFiveBuyers();

        assertEquals(1, result.size());
        assertEquals("Gabriel Confiavel", result.get(0).getNome());
        verify(dashboardRepository).findTopFiveBuyers();
    }

    @Test
    void testGetMediumTickets() {
        when(dashboardRepository.findMediumTicketByUser()).thenReturn(ticketMock);

        List<MediumTicketDTO> result = dashboardService.getMediumTickets();

        assertEquals(1, result.size());
        assertEquals("Gabriel dos ticket", result.get(0).getNome());
        assertEquals(75.0, result.get(0).getTicketMedio());
        verify(dashboardRepository).findMediumTicketByUser();
    }

    @Test
    void testGetMonthlyBilling() {
        when(dashboardRepository.findMonthlyBilling()).thenReturn(billingMock);

        MonthlyBillingDTO result = dashboardService.getMonthlyBilling();

        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(225), result.getValorTotal());
        verify(dashboardRepository).findMonthlyBilling();
    }
}
