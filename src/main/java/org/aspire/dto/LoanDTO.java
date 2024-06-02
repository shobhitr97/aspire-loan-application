package org.aspire.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class LoanDTO {

    private String loanId;
    private String applicant;
    private int amount;
    private int terms;
    private List<RepaymentDTO> repayments;
}
