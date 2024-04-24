package com.maidscc.lms.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "patrons")
public class Patron {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotEmpty(message = "Name must be filled")
    @Size(max = 255, message = "Name should not exceed 255 characters ")
    @Column(name = "name")
    private String name;

    @NotEmpty(message = "Contact Info must be filled")
    @Size(max = 255, message = "Name should not exceed 255 characters ")
    @Column(name = "contact_Info")
    private String contactInfo;
}
