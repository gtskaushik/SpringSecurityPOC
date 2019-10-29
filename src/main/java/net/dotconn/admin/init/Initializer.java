package net.dotconn.admin.init;

import net.dotconn.admin.tenant.Tenant;
import net.dotconn.admin.tenant.TenantRepository;
import net.dotconn.admin.thing.Parameter;
import net.dotconn.admin.thing.ParameterRepository;
import net.dotconn.admin.thing.Thing;
import net.dotconn.admin.thing.ThingRepository;
import net.dotconn.admin.user.Privilege;
import net.dotconn.admin.user.Role;
import net.dotconn.admin.user.User;
import net.dotconn.admin.user.repo.PrivilegeRepository;
import net.dotconn.admin.user.repo.RoleRepository;
import net.dotconn.admin.user.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class Initializer implements ApplicationListener<ContextRefreshedEvent> {
    private boolean alreadySetup = false;
    @Autowired private UserRepository userRepository;
    @Autowired private RoleRepository roleRepository;
    @Autowired private PrivilegeRepository privilegeRepository;
    @Autowired private ThingRepository thingRepository;
    @Autowired private ParameterRepository parameterRepository;
    @Autowired private TenantRepository tenantRepository;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (alreadySetup)
            return; // Already called. This method will be called multiple times
                    // whenever context is refreshed

        Tenant tenant = new Tenant();
//        tenant.setUsers(List.of(user, user1));
        tenant.setDisplayName("TENANT");
        tenant.setEmail("ops@dotconn.net");
        tenantRepository.save(tenant);

        Set<Privilege> privileges = Set.of(
                createPrivilegeIfNotFound("READ_PRIVILEGE"),
                createPrivilegeIfNotFound("WRITE_PRIVILEGE")
        );
        Set<Role> roles = Set.of(
                createRoleIfNotFound("ADMIN", privileges)
        );

        User user = new User();
        user.setTenant(tenant);
        user.setFirstName("Kaushik");
        user.setLastName("Gunasekaran");
        user.setUsername("gtskaushik");
        user.setPh("9538140126");
        user.setEnabled(true);
        user.setEmail("gtskaushik@gmail.com");
        user.setRoles(roles);
        user.setPassword("gtskaushik");
        userRepository.save(user);
        User user1 = new User();
        user1.setTenant(tenant);
        user1.setFirstName("Leela");
        user1.setLastName("Kaushik");
        user1.setUsername("gtsleela");
        user1.setPh("7411802748");
        user1.setEnabled(true);
        user1.setEmail("monisorna@gmail.com");
        user1.setRoles(roles);
        user1.setPassword("gtsleela");
        userRepository.save(user1);

        List<Parameter> params = List.of(
                new Parameter("s001", "act_p"),
                new Parameter("s002", "app_p")
        );
        parameterRepository.saveAll(params);
        Thing thing = new Thing();
        thing.setParams(params);
        thing.setUser(user);
        thingRepository.save(thing);

        List<Parameter> params1 = List.of(
                new Parameter("s003", "act_p"),
                new Parameter("s004", "app_p")
        );
        parameterRepository.saveAll(params1);
        Thing thing1 = new Thing();
        thing1.setParams(params1);
        thing1.setUser(user1);
        thingRepository.save(thing1);

//        Tenant tenant = new Tenant();
//        tenant.setUsers(List.of(user, user1));
//        tenant.setDisplayName("TENANT");
//        tenant.setEmail("ops@dotconn.net");
//        tenantRepository.save(tenant);

        alreadySetup = true;
    }

    @Transactional
    Privilege createPrivilegeIfNotFound(String name) {
        Optional<Privilege> optionalPrivilege = privilegeRepository.findByName(name);
        if (optionalPrivilege.isEmpty())
            return privilegeRepository.save(new Privilege(name));
        else
            return optionalPrivilege.get();
    }

    @Transactional
    Role createRoleIfNotFound(String name, Set<Privilege> privileges) {
        Optional<Role> optionalRole = roleRepository.findByName(name);
        if (optionalRole.isEmpty()) {
            Role role = new Role(name);
            role.setPrivileges(privileges);
            return roleRepository.save(role);
        } else
            return optionalRole.get();
    }
}
