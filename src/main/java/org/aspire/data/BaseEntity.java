package org.aspire.data;

import lombok.*;
import org.springframework.data.annotation.Version;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class BaseEntity implements Serializable {

    private long createdTimeMillis;
    private long updatedTimeMillis;
    private String lastUpdatedBy;
    private boolean deleted;
    @Version
    private int version;
}
