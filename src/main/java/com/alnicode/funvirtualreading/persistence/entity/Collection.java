package com.alnicode.funvirtualreading.persistence.entity;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;


import static com.alnicode.funvirtualreading.constants.DateFormatConstants.DATE_TIME_FORMAT;

/**
 * The collection entity model.
 *
 * @author Alben Bustamante
 * @version 1.0
 * @since 1.0
 */
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "collections")
public class Collection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "collection_id")
    private Long collectionId;

    @Column(nullable = false, length = 200)
    private String name;

    @DateTimeFormat(iso = ISO.DATE_TIME, pattern = DATE_TIME_FORMAT)
    @Column(name = "creation_date", nullable = false)
    private LocalDateTime date;

    @Min(1L)
    @Max(Long.MAX_VALUE)
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @OneToMany(mappedBy = "collection", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    private Set<CollectionsBook> books = Collections.emptySet();

    /**
     * Set the creation date before being registered.
     */
    @PrePersist
    private void setCreationDate() {
        if (this.date == null) {
            this.date = LocalDateTime.now();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Collection that = (Collection) o;
        return collectionId != null && Objects.equals(collectionId, that.collectionId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
