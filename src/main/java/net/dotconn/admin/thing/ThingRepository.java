package net.dotconn.admin.thing;

import net.dotconn.admin.user.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ThingRepository extends CrudRepository<Thing, Long> {
    List<Thing> findByUser(User user);
}
