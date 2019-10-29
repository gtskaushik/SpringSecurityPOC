package net.dotconn.admin.controller;

import net.dotconn.admin.tenant.Tenant;
import net.dotconn.admin.tenant.TenantRepository;
import net.dotconn.admin.thing.Thing;
import net.dotconn.admin.thing.ThingRepository;
import net.dotconn.admin.user.User;
import net.dotconn.admin.user.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class TestController {
    @Autowired ThingRepository thingRepository;
    @Autowired UserRepository userRepository;
    @Autowired TenantRepository tenantRepository;

    @GetMapping("/things")
    public Iterable<Thing> getAllThings() {
        return thingRepository.findAll();
    }

    @GetMapping("/things/{username}")
    public Iterable<Thing> getThingsForUser(@PathVariable String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            return thingRepository.findByUser(optionalUser.get());
        } else return List.of();
    }

    @GetMapping("/users")
    public Iterable<User> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/users/{tenantName}")
    public List<User> getUsers(@PathVariable String tenantName) {
        Tenant tenant = tenantRepository.findByDisplayName(tenantName).get();
//        return userRepository.findByTenant(tenant).get();
        return tenant.getUsers();
    }

    @GetMapping("/tenants")
    public Iterable<Tenant> getTenants() {
        return tenantRepository.findAll();
    }

    @GetMapping("/tenants/{tenantName}")
    public Tenant getTenants(@PathVariable String tenantName) {
        return tenantRepository.findByDisplayName(tenantName).get();
    }

    @GetMapping("/tenant/{tenantName}/things")
    public List<Thing> getThings(@PathVariable String tenantName) {
        List<Thing> things = new ArrayList<>();
        tenantRepository.findByDisplayName(tenantName)
                .get().getUsers().forEach(user -> {
            User userEntity = userRepository.findByUsername(user.getUsername()).get();
            things.addAll(thingRepository.findByUser(userEntity));
        });

        return things;
    }
}
