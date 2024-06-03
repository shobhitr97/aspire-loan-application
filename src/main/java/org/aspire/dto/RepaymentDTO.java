package org.aspire.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class RepaymentDTO {
    private int amount;
    private int pendingAmount;
    private String status;
    private long expectedByMillis;
    private long paidOnMillis;
}
