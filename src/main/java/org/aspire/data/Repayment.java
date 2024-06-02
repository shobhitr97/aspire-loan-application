package org.aspire.data;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Repayment implements Serializable {

    private String transactionRef;
    private int amount;
    private String status;
    private long expectedByMillis;
    private long paidOnMillis;
}
