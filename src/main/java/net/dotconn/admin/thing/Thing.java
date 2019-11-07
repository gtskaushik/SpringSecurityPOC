package net.dotconn.admin.thing;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.dotconn.admin.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Thing {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Convert(converter = ListParameterToStringConverter.class)
    @Lob
    @NotNull
    private List<Parameter> params;

    @OneToOne
    @JoinTable(
            name = "user_thing",
            joinColumns = @JoinColumn(
                    name = "thing_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"))
    @JsonIgnore
    private User user;
}

