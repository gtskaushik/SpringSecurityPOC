package net.dotconn.admin.thing;

import lombok.*;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Parameter {
    @NonNull private String name;
    @NonNull private String displayName;
}
