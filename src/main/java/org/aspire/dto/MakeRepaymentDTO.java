package org.aspire.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class MakeRepaymentDTO {

    private double amount;
    private String transactionRef;
}
