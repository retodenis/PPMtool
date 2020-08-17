package com.github.ppmtool.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@ToString
public class ProjectTask {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(updatable = false)
    private String projectSequence;
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
    private String projectIdentifier;
    @Column(updatable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "backlog_id", updatable = false, nullable = false)
    @JsonIgnore
    @ToString.Exclude
    private Backlog backlog;

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
    }
}
