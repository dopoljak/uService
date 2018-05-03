package com.example.demo.database.entities;

import com.ilirium.database.templates.AbstractEntity;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author dpoljak
 */
@NamedQueries({
    @NamedQuery(name = "EndUser.findAll", query = "SELECT e FROM EndUser e")
})
@Entity
@Table(name = "ENDUSER")
public class EndUser extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ENDUSER_SEQ_GENERATOR")
    @SequenceGenerator(name = "ENDUSER_SEQ_GENERATOR", sequenceName = "ENDUSER_SEQ", initialValue = 10, allocationSize = 1)
    private Long id;

    @Column
    private String username;

    @Column
    private String password;

    @OneToMany(mappedBy = "endUser")
    private Collection<Device> devices;

    public Collection<Device> getDevices() {
        return devices;
    }

    public void setDevices(Collection<Device> devices) {
        this.devices = devices;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
