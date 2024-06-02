package org.aspire.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class RepaymentDTO {
    private String transactionRef;
    private int amount;
    private String status;
    private long expectedByMillis;
    private long paidOnMillis;
}
