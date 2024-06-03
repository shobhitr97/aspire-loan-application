package org.aspire.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class MakeRepaymentDTO {

    private int amount;
    private String transactionRef;
}
