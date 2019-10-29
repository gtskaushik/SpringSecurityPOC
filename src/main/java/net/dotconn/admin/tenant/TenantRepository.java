package net.dotconn.admin.tenant;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TenantRepository extends CrudRepository<Tenant, Long> {
    Optional<Tenant> findByDisplayName(String tenantDisplayName);
}
