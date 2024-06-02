package org.aspire.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class CreateLoanRequestDTO {

    private int amount;
    private int terms;
}
