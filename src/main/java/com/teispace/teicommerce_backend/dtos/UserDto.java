package com.teispace.teicommerce_backend.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.teispace.teicommerce_backend.models.Address;
import com.teispace.teicommerce_backend.models.Order;
import com.teispace.teicommerce_backend.models.Rating;
import com.teispace.teicommerce_backend.models.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

/**
 * A DTO for the {@link com.teispace.teicommerce_backend.models.User} entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements Serializable {
    private Set<Role> roles;
    private Long id;
    private String fullName;
    private String email;

    private String phoneNumber;
    private String userName;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Set<Address> addresses;
    private Set<Rating> ratings;
    private Set<Order> orders;
}