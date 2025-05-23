package com.jerzymaj.budgetmanagement.budget_management_app.services;

import com.jerzymaj.budgetmanagement.budget_management_app.DTOs.MonthlyCostsDTO;
import com.jerzymaj.budgetmanagement.budget_management_app.DTOs.MonthlyCostsSummaryDTO;
import com.jerzymaj.budgetmanagement.budget_management_app.jpa_repositories.MonthlyCostsRepository;
import com.jerzymaj.budgetmanagement.budget_management_app.jpa_repositories.MonthlyCostsSummaryRepository;
import com.jerzymaj.budgetmanagement.budget_management_app.models.MonthlyCosts;
import com.jerzymaj.budgetmanagement.budget_management_app.models.MonthlyCostsSummary;
import com.jerzymaj.budgetmanagement.budget_management_app.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MonthlyCostsServiceTest {

    @Mock
    private MonthlyCostsRepository monthlyCostsRepository;

    @Mock
    private MonthlyCostsSummaryRepository monthlyCostsSummaryRepository;

    @InjectMocks
    private MonthlyCostsService monthlyCostsService;

    @Mock
    private User user;

    private MonthlyCosts mockMonthlyCosts;

    private MonthlyCostsSummary mockMonthlyCostsSummary;

    @BeforeEach
    public void setUp(){
        mockMonthlyCosts = new MonthlyCosts();
        mockMonthlyCosts.setId(1L);
        mockMonthlyCosts.setRent(100.0);
        mockMonthlyCosts.setFoodCosts(100.0);
        mockMonthlyCosts.setCurrentElectricityBill(100.0);
        mockMonthlyCosts.setCurrentGasBill(100.0);
        mockMonthlyCosts.setTotalCarServiceCosts(null);
        mockMonthlyCosts.setCarInsuranceCosts(100.0);
        mockMonthlyCosts.setCarOperatingCosts(100.0);
        mockMonthlyCosts.setCreateDate(LocalDateTime.now());

        mockMonthlyCostsSummary = new MonthlyCostsSummary();
        mockMonthlyCostsSummary.setId(1);
        mockMonthlyCostsSummary.setMonthlyCostsSum(600.0);
        mockMonthlyCostsSummary.setRentPercentageOfUserSalary(BigDecimal.valueOf(10));
        mockMonthlyCostsSummary.setFoodCostsPercentageOfUserSalary(BigDecimal.valueOf(10));
        mockMonthlyCostsSummary.setCurrentElectricityBillPercentageOfUserSalary(BigDecimal.valueOf(10));
        mockMonthlyCostsSummary.setCurrentGasBillPercentageOfUserSalary(BigDecimal.valueOf(10));
        mockMonthlyCostsSummary.setTotalCarServicePercentageOfUserSalary(BigDecimal.valueOf(10));
        mockMonthlyCostsSummary.setCarInsuranceCostsPercentageOfUserSalary(BigDecimal.valueOf(10));
        mockMonthlyCostsSummary.setCarOperatingCostsPercentageOfUserSalary(BigDecimal.valueOf(10));
        mockMonthlyCostsSummary.setCostsPercentageOfUserSalary(BigDecimal.valueOf(70));
        mockMonthlyCostsSummary.setNetSalaryAfterCosts(BigDecimal.valueOf(3000));
        mockMonthlyCostsSummary.setCreateDate(LocalDateTime.now());
    }
    @Test
    public void testAddUpAllMonthlyCostsForUser(){

        MonthlyCostsService spyMonthlyCostsService = Mockito.spy(monthlyCostsService);

        doReturn(mockMonthlyCosts).when(spyMonthlyCostsService).getMonthlyCostsForUserByMonth(1L,1);

       double actualResult = spyMonthlyCostsService.addUpAllMonthlyCostsForUser(1L,1);

       assertEquals(600,actualResult);
    }

    @Test
    public void testConvertMonthlyCostsToDTO(){

        MonthlyCostsDTO expectedMonthlyCostsDTO = new MonthlyCostsDTO(mockMonthlyCosts.getId(),mockMonthlyCosts.getRent(),
                mockMonthlyCosts.getFoodCosts(),mockMonthlyCosts.getCurrentElectricityBill(),
                mockMonthlyCosts.getCurrentGasBill(),
                mockMonthlyCosts.getTotalCarServiceCosts() != null ? mockMonthlyCosts.getTotalCarServiceCosts() : 0.0,
                mockMonthlyCosts.getCarInsuranceCosts(),
                mockMonthlyCosts.getCarOperatingCosts(),
                mockMonthlyCosts.getCreateDate());

        MonthlyCostsService spyMonthlyCostsService = Mockito.spy(monthlyCostsService);

        doReturn(expectedMonthlyCostsDTO).when(spyMonthlyCostsService).convertMonthlyCostsToDTO(mockMonthlyCosts);

        MonthlyCostsDTO actualResultDTO = spyMonthlyCostsService.convertMonthlyCostsToDTO(mockMonthlyCosts);

        assertEquals(expectedMonthlyCostsDTO,actualResultDTO);
    }

    @Test
    public void testConvertMonthlyCostsSummaryToDTO(){

        MonthlyCostsSummaryDTO expectedMonthlyCostsSummaryDTO = new MonthlyCostsSummaryDTO(
                mockMonthlyCostsSummary.getId(),
                mockMonthlyCostsSummary.getMonthlyCostsSum(),
                mockMonthlyCostsSummary.getRentPercentageOfUserSalary(),
                mockMonthlyCostsSummary.getFoodCostsPercentageOfUserSalary(),
                mockMonthlyCostsSummary.getCurrentElectricityBillPercentageOfUserSalary(),
                mockMonthlyCostsSummary.getCurrentGasBillPercentageOfUserSalary(),
                mockMonthlyCostsSummary.getTotalCarServicePercentageOfUserSalary(),
                mockMonthlyCostsSummary.getCarInsuranceCostsPercentageOfUserSalary(),
                mockMonthlyCostsSummary.getCarOperatingCostsPercentageOfUserSalary(),
                mockMonthlyCostsSummary.getCostsPercentageOfUserSalary(),
                mockMonthlyCostsSummary.getNetSalaryAfterCosts(),
//                mockMonthlyCostsSummary.getFinancialAdvice(),
                mockMonthlyCostsSummary.getCreateDate()
        );

        MonthlyCostsService spyMonthlyCostsService = Mockito.spy(monthlyCostsService);

        doReturn(expectedMonthlyCostsSummaryDTO).when(spyMonthlyCostsService).convertMonthlyCostsSummaryToDTO(mockMonthlyCostsSummary);

        MonthlyCostsSummaryDTO actualResultDTO = spyMonthlyCostsService.convertMonthlyCostsSummaryToDTO(mockMonthlyCostsSummary);

        assertEquals(expectedMonthlyCostsSummaryDTO,actualResultDTO);

    }

    @Test
    void testCreateOrUpdateMonthlyCosts_NewMonthlyCosts() {
        MonthlyCostsDTO inputDTO = new MonthlyCostsDTO();
        inputDTO.setRent(1000.0);
        inputDTO.setFoodCosts(500.0);
        inputDTO.setCurrentElectricityBill(100.0);
        inputDTO.setCurrentGasBill(50.0);
        inputDTO.setTotalCarServiceCosts(200.0);
        inputDTO.setCarInsuranceCosts(150.0);
        inputDTO.setCarOperatingCosts(300.0);

        List<MonthlyCosts> costsFromDB = new ArrayList<>();

        MonthlyCosts newMonthlyCosts = new MonthlyCosts();
        newMonthlyCosts.setUser(user);
        newMonthlyCosts.setRent(1000.0);
        newMonthlyCosts.setFoodCosts(500.0);
        newMonthlyCosts.setCurrentElectricityBill(100.0);
        newMonthlyCosts.setCurrentGasBill(50.0);
        newMonthlyCosts.setTotalCarServiceCosts(200.0);
        newMonthlyCosts.setCarInsuranceCosts(150.0);
        newMonthlyCosts.setCarOperatingCosts(300.0);

        when(monthlyCostsRepository.save(any(MonthlyCosts.class))).thenReturn(newMonthlyCosts);

        MonthlyCostsDTO actualDTO = monthlyCostsService.createOrUpdateMonthlyCosts(user, inputDTO, costsFromDB);

        assertNotNull(actualDTO);
        assertEquals(1000.0, actualDTO.getRent());
        assertEquals(500.0, actualDTO.getFoodCosts());
        assertEquals(100.0, actualDTO.getCurrentElectricityBill());
        assertEquals(50.0, actualDTO.getCurrentGasBill());
        assertEquals(200.0, actualDTO.getTotalCarServiceCosts());
        assertEquals(150.0, actualDTO.getCarInsuranceCosts());
        assertEquals(300.0, actualDTO.getCarOperatingCosts());

        verify(monthlyCostsRepository, times(1)).save(any(MonthlyCosts.class));
    }

    @Test
    void testCreateOrUpdateMonthlyCosts_UpdateExistingCosts() {
        YearMonth currentYearMonth = YearMonth.now();

        MonthlyCosts existingMonthlyCosts = mockMonthlyCosts;

        List<MonthlyCosts> costsFromDB = new ArrayList<>();
        costsFromDB.add(existingMonthlyCosts);

        MonthlyCostsDTO inputDTO = new MonthlyCostsDTO();
        inputDTO.setRent(1200.0);
        inputDTO.setFoodCosts(500.0);
        inputDTO.setCurrentElectricityBill(100.0);
        inputDTO.setCurrentGasBill(50.0);
        inputDTO.setTotalCarServiceCosts(200.0);
        inputDTO.setCarInsuranceCosts(150.0);
        inputDTO.setCarOperatingCosts(300.0);

        when(monthlyCostsRepository.save(any(MonthlyCosts.class))).thenReturn(existingMonthlyCosts);

        MonthlyCostsDTO actualDTO = monthlyCostsService.createOrUpdateMonthlyCosts(user, inputDTO, costsFromDB);

        assertNotNull(actualDTO);
        assertEquals(1200.0, actualDTO.getRent());
        assertEquals(500.0, actualDTO.getFoodCosts());
        assertEquals(100.0, actualDTO.getCurrentElectricityBill());
        assertEquals(50.0, actualDTO.getCurrentGasBill());
        assertEquals(200.0, actualDTO.getTotalCarServiceCosts());
        assertEquals(150.0, actualDTO.getCarInsuranceCosts());
        assertEquals(300.0, actualDTO.getCarOperatingCosts());

        verify(monthlyCostsRepository, times(1)).save(existingMonthlyCosts);
    }

    @ParameterizedTest
    @CsvSource({
            "rent, 10.5",
            "food, 15.0",
            "electricity, 5.75",
            "gas, 8.25",
            "car_service, 12.0",
            "car_insurance, 7.5",
            "car_operating, 6.0",
            "net_salary, 2000.00",
            "total_costs, 50.00"})
    public void testUpdateMonthlyCostsSummary(String type, BigDecimal value){

        Long userId = 1L;
        int month = 1;

        MonthlyCostsService spyMonthlyCostsService = Mockito.spy(monthlyCostsService);

        doReturn(mockMonthlyCosts).when(spyMonthlyCostsService).getMonthlyCostsForUserByMonth(userId, month);
        doReturn(mockMonthlyCostsSummary).when(spyMonthlyCostsService).getOrCreateMonthlyCostsSummary(mockMonthlyCosts);

        spyMonthlyCostsService.updateMonthlyCostsSummary(userId, month, value, type);

        switch (type) {
            case "rent" -> assertEquals(value, mockMonthlyCostsSummary.getRentPercentageOfUserSalary());
            case "food" -> assertEquals(value, mockMonthlyCostsSummary.getFoodCostsPercentageOfUserSalary());
            case "electricity" -> assertEquals(value, mockMonthlyCostsSummary.getCurrentElectricityBillPercentageOfUserSalary());
            case "gas" -> assertEquals(value, mockMonthlyCostsSummary.getCurrentGasBillPercentageOfUserSalary());
            case "car_service" -> assertEquals(value, mockMonthlyCostsSummary.getTotalCarServicePercentageOfUserSalary());
            case "car_insurance" -> assertEquals(value, mockMonthlyCostsSummary.getCarInsuranceCostsPercentageOfUserSalary());
            case "car_operating" -> assertEquals(value, mockMonthlyCostsSummary.getCarOperatingCostsPercentageOfUserSalary());
            case "net_salary" -> assertEquals(value, mockMonthlyCostsSummary.getNetSalaryAfterCosts());
            case "total_costs" -> assertEquals(value, mockMonthlyCostsSummary.getCostsPercentageOfUserSalary());
            default -> fail("Unknown cost type: " + type);
        }

        verify(spyMonthlyCostsService).createMonthlyCostsSummaryForUser(mockMonthlyCostsSummary);
    }
}
