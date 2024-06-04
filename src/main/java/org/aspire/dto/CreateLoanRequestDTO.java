package org.aspire.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class CreateLoanRequestDTO {

    private double amount;
    private int terms;
}
