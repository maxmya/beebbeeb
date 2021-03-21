package com.trixpert.beebbeeb.data.to;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SettingsDTO {
    private long numberOfAllVendors;
    private long numberOfActiveVendors;
    private long numberOfAllBranches;
    private long numberOfActiveBranches;
    private long numberOfAllCars;
    private long numberOfActiveCars;
    private long numberOfCustomers;
    private double systemHealthStatus;
    private long errorsCount;
    private long numberOfInserts;
    private long numberOfUpdates;
    private long numberOfDeletes;
}
