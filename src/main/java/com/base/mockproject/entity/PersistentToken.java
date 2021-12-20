package com.base.mockproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Persistent tokens are used by Spring Security to automatically log in users.
 *
 * @see com.base.mock.project.security.PersistentTokenRememberMeServices
 */
@Getter
@Setter
@Entity
@Table(name = "persistent_token")
public class PersistentToken implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String series;

    @JsonIgnore
    @NotNull
    @Column(name = "token_value", nullable = false)
    private String tokenValue;

    @Column(name = "token_date")
    private LocalDate tokenDate;

    //an IPV6 address max length is 39 characters
    @Size(min = 0, max = 39)
    @Column(name = "ip_address", length = 39)
    private String ipAddress;

    @Column(name = "user_agent")
    private String userAgent;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
