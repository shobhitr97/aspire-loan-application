package org.aspire.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.aspire.data.User;

@NoArgsConstructor
@Getter
@Setter
public class RequestMetadata {

    private String requestId;
    private User user;
}
