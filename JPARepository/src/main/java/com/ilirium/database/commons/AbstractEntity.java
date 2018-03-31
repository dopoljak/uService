package com.ilirium.database.commons;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

public abstract class AbstractEntity implements Serializable {

    protected final String sequenceName = "SEQ_DEFAULT";

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = sequenceName)
    @SequenceGenerator(name = sequenceName, sequenceName = sequenceName, allocationSize = 1)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // TODO doraditi equals i hash metode; vidjet dal se može vratiti lista membera koji bi se extractali i s njima se onda radilo

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractEntity that = (AbstractEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
