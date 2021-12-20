package com.base.mockproject;

import com.base.mockproject.config.ApplicationProperties;
import com.base.mockproject.entity.Role;
import com.base.mockproject.entity.User;
import com.base.mockproject.entity.UserRole;
import com.base.mockproject.enums.DeleteFlgEnum;
import com.base.mockproject.enums.StatusEnum;
import com.base.mockproject.security.MasterRoleEnum;
import com.base.mockproject.service.RoleService;
import com.base.mockproject.service.UserService;
import com.base.mockproject.util.DefaultProfileUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.util.stream.Collectors;

@SpringBootApplication
@Slf4j
@EnableConfigurationProperties({ApplicationProperties.class})
@AllArgsConstructor
@EnableJpaAuditing
public class MockProjectApplication {

    private final RoleService roleService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(MockProjectApplication.class);
        DefaultProfileUtil.addDefaultProfile(app);
        Environment env = app.run(args).getEnvironment();
        logApplicationStartup(env);
    }

    private static void logApplicationStartup(Environment env) {
        String protocol = "http";
        if (env.getProperty("server.ssl.key-store") != null) {
            protocol = "https";
        }
        String serverPort = env.getProperty("server.port");
        String contextPath = env.getProperty("server.servlet.context-path");
        if (StringUtils.isBlank(contextPath)) {
            contextPath = "/";
        }
        String hostAddress = "localhost";
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.warn("The host name could not be determined, using `localhost` as fallback");
        }
        log.info("\n----------------------------------------------------------\n\t" +
                        "Application '{}' is running! Access URLs:\n\t" +
                        "Local: \t\t{}://localhost:{}{}\n\t" +
                        "External: \t{}://{}:{}{}\n\t" +
                        "Profile(s): \t{}\n----------------------------------------------------------",
                env.getProperty("pizsoft.client-app.name"),
                protocol,
                serverPort,
                contextPath,
                protocol,
                hostAddress,
                serverPort,
                contextPath,
                env.getActiveProfiles());
    }

    /**
     * Init master data of role if not exist
     */
    @Bean
    public CommandLineRunner initDefaultRole() {
        return args -> {
            List<String> existMasterRoleNames = roleService.getMasterRoleName(MasterRoleEnum.class);
            List<String> missMasterRoleNames = Arrays.stream(MasterRoleEnum.values()).
                    filter(r -> !existMasterRoleNames.contains(r.name()))
                    .map(Objects::toString)
                    .collect(Collectors.toList());

            if (CollectionUtils.isNotEmpty(missMasterRoleNames)) {
                List<Role> masterRole = missMasterRoleNames.stream().map(Role::new)
                        .collect(Collectors.toList());
                roleService.save(masterRole);
            }
        };
    }

    @Bean
    public CommandLineRunner initDefaultUser() {
        return args -> {
            Role adminRole = new Role(MasterRoleEnum.ROLE_ADMIN.name());
            User user = User.builder()
                    .email("admin@test.com")
                    .password(passwordEncoder.encode("abcd1234"))
                    .firstName("Admin")
                    .lastName("")
                    .status(StatusEnum.ACTIVE)
                    .build();
            user.setDeleteFlg(DeleteFlgEnum.UNDELETED);

            UserRole userRole = new UserRole();
            userRole.setUser(user);
            userRole.setRole(adminRole);

            user.setAuthorities(new HashSet<>(Collections.singleton(userRole)));

            //userService.save(user);

        };
    };
}
