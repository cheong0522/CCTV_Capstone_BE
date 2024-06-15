package com.simcheong.cctv._core.utils;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingFields.AuditingEntityListener.class)
public abstract class AuditingFields {

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.modifiedAt = now;
    }

    @PreUpdate
    public void preUpdate() {
        this.modifiedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public static class AuditingEntityListener {
        @PrePersist
        public void touchForCreate(Object target) {
            if (target instanceof AuditingFields) {
                ((AuditingFields) target).prePersist();
            }
        }

        @PreUpdate
        public void touchForUpdate(Object target) {
            if (target instanceof AuditingFields) {
                ((AuditingFields) target).preUpdate();
            }
        }
    }
}
