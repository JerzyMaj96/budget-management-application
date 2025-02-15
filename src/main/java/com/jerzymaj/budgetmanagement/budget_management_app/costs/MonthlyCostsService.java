package com.jerzymaj.budgetmanagement.budget_management_app.costs;

import com.jerzymaj.budgetmanagement.budget_management_app.jpa_repositories.MonthlyCostsRepository;
import com.jerzymaj.budgetmanagement.budget_management_app.exceptions.MonthlyCostsNotFoundException;
import com.jerzymaj.budgetmanagement.budget_management_app.jpa_repositories.MonthlyCostsResultsRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MonthlyCostsService {

    private final MonthlyCostsRepository monthlyCostsRepository;
    private final MonthlyCostsResultsRepository monthlyCostsResultsRepository;

    public MonthlyCostsService(MonthlyCostsRepository monthlyCostsRepository, MonthlyCostsResultsRepository monthlyCostsResultsRepository) {
        this.monthlyCostsRepository = monthlyCostsRepository;
        this.monthlyCostsResultsRepository = monthlyCostsResultsRepository;
    }

    public Optional<MonthlyCosts> getMonthlyCostsByUserId(Long userId) {
        return monthlyCostsRepository.findByUserId(userId);
    }

    public MonthlyCosts createMonthlyCostsForUser(MonthlyCosts monthlyCosts) {
        return monthlyCostsRepository.save(monthlyCosts);
    }

    public Optional<MonthlyCostsResults> getMonthlyCostsResultsByMonthlyCostsId(int monthlyCostsId){
        return monthlyCostsResultsRepository.findByMonthlyCostsId(monthlyCostsId);
    }

    public MonthlyCostsResults createMonthlyCostsResultsForUser(MonthlyCostsResults monthlyCostsResults){
        return monthlyCostsResultsRepository.save(monthlyCostsResults);
    };

    public double addUpAllMonthlyCostsForUser(Long userId){
       Optional<MonthlyCosts> monthlyCosts = monthlyCostsRepository.findByUserId(userId);

       if(monthlyCosts.isEmpty())
           throw new MonthlyCostsNotFoundException("No monthly costs found for user with id " + userId);

       return monthlyCosts.get().getRent() + monthlyCosts.get().getFoodCosts() +
               monthlyCosts.get().getCurrentElectricityBill() + monthlyCosts.get().getCurrentGasBill();
    }


}
