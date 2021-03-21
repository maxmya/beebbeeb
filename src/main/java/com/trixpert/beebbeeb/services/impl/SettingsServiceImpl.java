package com.trixpert.beebbeeb.services.impl;

import com.trixpert.beebbeeb.data.constants.AuditActions;
import com.trixpert.beebbeeb.data.repositories.*;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.to.SettingsDTO;
import com.trixpert.beebbeeb.services.ReporterService;
import com.trixpert.beebbeeb.services.SettingsService;
import org.springframework.stereotype.Service;

@Service
public class SettingsServiceImpl implements SettingsService {

    private final VendorRepository vendorRepository;
    private final BranchRepository branchRepository;
    private final CustomerRepository customerRepository;
    private final CarInstanceRepository carInstanceRepository;
    private final AuditRepository auditRepository;

    private final ReporterService reporterService;

    public SettingsServiceImpl(VendorRepository vendorRepository,
                               BranchRepository branchRepository,
                               CustomerRepository customerRepository,
                               CarInstanceRepository carInstanceRepository,
                               AuditRepository auditRepository,
                               ReporterService reporterService) {

        this.vendorRepository = vendorRepository;
        this.branchRepository = branchRepository;
        this.customerRepository = customerRepository;
        this.carInstanceRepository = carInstanceRepository;
        this.auditRepository = auditRepository;
        this.reporterService = reporterService;
    }

    @Override

    public ResponseWrapper<SettingsDTO> getSystemSettings() {
        try {
            long allVendorsCount = vendorRepository.count();
            long allActiveVendorsCount = vendorRepository.countAllByActive(true);

            long allBranchesCount = branchRepository.count();
            long allActiveBranchesCount = branchRepository.countAllByActive(true);

            long allCarsCount = carInstanceRepository.count();
            long allActiveCarsCount = carInstanceRepository.countAllByActive(true);

            long allActiveCustomersCount = customerRepository.countAllByActive(true);

            long numberOfAudits = auditRepository.count();
            long numberOfErrors = auditRepository.countAllByAction(AuditActions.ERROR);

            if (numberOfAudits == 0) numberOfAudits = 1;
            long healthStatus = Math.floorDiv(numberOfErrors, numberOfAudits) * 100;

            long systemOperationsCount = auditRepository.countAllByAction(AuditActions.INSERT) +
                    auditRepository.countAllByAction(AuditActions.UPDATE) +
                    auditRepository.countAllByAction(AuditActions.DELETE);

            return reporterService.reportSuccess(SettingsDTO.builder()
                    .errorsCount(numberOfErrors)
                    .numberOfActiveBranches(allActiveBranchesCount)
                    .numberOfActiveCars(allActiveCarsCount)
                    .numberOfActiveVendors(allActiveVendorsCount)
                    .numberOfAllBranches(allBranchesCount)
                    .numberOfAllCars(allCarsCount)
                    .numberOfAllVendors(allVendorsCount)
                    .numberOfCustomers(allActiveCustomersCount)
                    .numberOfSystemOperations(systemOperationsCount)
                    .systemHealthStatus(healthStatus)
                    .build());

        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }
}
