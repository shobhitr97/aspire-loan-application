package org.aspire.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class RepaymentDTO {
    private double amount;
    private double pendingAmount;
    private String status;
    private long expectedByMillis;
    private Long paidOnMillis;
}
