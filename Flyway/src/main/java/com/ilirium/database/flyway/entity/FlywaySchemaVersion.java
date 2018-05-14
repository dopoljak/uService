package com.ilirium.database.flyway.entity;

import com.ilirium.basic.db.*;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "FLYWAY_SCHEMA_HISTORY")
public class FlywaySchemaVersion implements SchemaVersion {

    @Id
    @Column(name = "version")
    private String version;
    @Column(name = "installed_rank")
    private int installedRank;
    @Column(name = "description")
    private String description;
    @Column(name = "type")
    private String type;
    @Column(name = "script")
    private String script;
    @Column(name = "checksum")
    private Integer checksum;
    @Column(name = "installed_by")
    private String installedBy;
    @Column(name = "installed_on")
    private Date installedOn;
    @Column(name = "execution_time")
    private int executionTime;
    @Column(name = "success")
    private boolean success;

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getInstalledRank() {
        return this.installedRank;
    }

    public void setInstalledRank(int installedRank) {
        this.installedRank = installedRank;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getScript() {
        return this.script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public Integer getChecksum() {
        return this.checksum;
    }

    public void setChecksum(Integer checksum) {
        this.checksum = checksum;
    }

    public String getInstalledBy() {
        return this.installedBy;
    }

    public void setInstalledBy(String installedBy) {
        this.installedBy = installedBy;
    }

    public Date getInstalledOn() {
        return this.installedOn;
    }

    public void setInstalledOn(Date installedOn) {
        this.installedOn = installedOn;
    }

    public int getExecutionTime() {
        return this.executionTime;
    }

    public void setExecutionTime(int executionTime) {
        this.executionTime = executionTime;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

}
