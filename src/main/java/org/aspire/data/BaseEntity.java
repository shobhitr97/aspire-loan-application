package org.aspire.data;

import lombok.*;
import org.springframework.data.annotation.Version;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class BaseEntity implements Serializable {

    // TODO : populate these for all create/update operations

    private long createdTimeMillis;
    private long updatedTimeMillis;
    private long lastUpdatedBy;
    private boolean deleted;
    @Version
    private int version;
}
