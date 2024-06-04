package org.aspire.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class LoanDTO {

    private String loanId;
    private String applicant;
    private double amount;
    private int terms;
    private String status;
    private List<RepaymentDTO> repayments;
}
