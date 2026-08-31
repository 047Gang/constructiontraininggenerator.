package com.construction.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class TrainingCertificate {
    private String employeeName;
    private String toolName;
    private LocalDate trainingDate;
    private String certificateNumber;
    private String trainer;
}
