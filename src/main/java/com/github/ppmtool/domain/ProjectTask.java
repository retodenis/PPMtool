package com.github.ppmtool.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@ToString(of = {"id", "summary"})
@EqualsAndHashCode(of = {"id"})
public class ProjectTask {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(updatable = false, unique = true)
    private String ptSeq;
    @NotNull(message = "Must not be null")
    @NotBlank(message = "Please include a project summary")
    private String summary;
    private String acceptanceCriteria;
    @Enumerated(EnumType.STRING)
    private PTStatus status;
    @Basic
    private int priority;
    @Transient
    private PTPriority ptPriority;
    private LocalDateTime dueDate;
    @Column(updatable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", updatable = false, nullable = false)
    @JsonIgnore
    private Project project;

    @PostLoad
    private void postLoad() {
        if (priority > 0) {
            this.ptPriority = PTPriority.of(priority);
        }
    }

    @PrePersist
    private void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();

        if (ptPriority != null) {
            priority = ptPriority.getPriority();
        }
    }

    @PreUpdate
    private void onUpdate() {
        updatedAt = LocalDateTime.now();
        if (priority > 0) {
            this.ptPriority = PTPriority.of(priority);
        }
    }
}
