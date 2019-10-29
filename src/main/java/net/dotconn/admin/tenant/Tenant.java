package net.dotconn.admin.tenant;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import net.dotconn.admin.user.User;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Tenant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String displayName;
    @OneToMany(mappedBy = "tenant")
    @JsonIgnore
    private List<User> users;
    private String ph;
    private String email;
}
