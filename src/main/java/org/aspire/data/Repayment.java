package org.aspire.data;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Repayment implements Serializable {

    private List<String> transactionIds;
    private double amount;
    private double pendingAmount;
    private String status;
    private long expectedByMillis;
    private Long paidOnMillis;
}
