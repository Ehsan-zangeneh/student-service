package com.student.sample.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "students",
 indexes = @Index(name = "national_code_index", columnList = "nationalCode"))
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String name;
    String family;
    String birthPlace;
    @Column(unique = true)
    String nationalCode;
    @Enumerated(EnumType.STRING)
    Major field;
    @Builder.Default
    LocalDateTime createdDate = LocalDateTime.now();
    LocalDateTime registeredDate;

    @PrePersist
    protected void prepersist() {
        this.registeredDate = LocalDateTime.now();
    }

}
