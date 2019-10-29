package net.dotconn.admin.user.repo;

import net.dotconn.admin.tenant.Tenant;
import net.dotconn.admin.user.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByTenant(Tenant tenant);
}
