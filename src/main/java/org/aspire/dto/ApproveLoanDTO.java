package org.aspire.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ApproveLoanDTO {

    private boolean approved;
    private String loanId;
}
