package com.ilirium.database.flyway;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "\"flyway_schema_history\"")
public class SchemaVersion implements java.io.Serializable {

    private String version;
    private int installedRank;
    private String description;
    private String type;
    private String script;
    private Integer checksum;
    private String installedBy;
    private Date installedOn;
    private int executionTime;
    private boolean success;

    @Id
    @Column(name = "\"version\"")
    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Column(name = "\"installed_rank\"")
    public int getInstalledRank() {
        return this.installedRank;
    }

    public void setInstalledRank(int installedRank) {
        this.installedRank = installedRank;
    }

    @Column(name = "\"description\"")
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "\"type\"")
    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "\"script\"")
    public String getScript() {
        return this.script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    @Column(name = "\"checksum\"")
    public Integer getChecksum() {
        return this.checksum;
    }

    public void setChecksum(Integer checksum) {
        this.checksum = checksum;
    }

    @Column(name = "\"installed_by\"")
    public String getInstalledBy() {
        return this.installedBy;
    }

    public void setInstalledBy(String installedBy) {
        this.installedBy = installedBy;
    }

    @Column(name = "\"installed_on\"")
    public Date getInstalledOn() {
        return this.installedOn;
    }

    public void setInstalledOn(Date installedOn) {
        this.installedOn = installedOn;
    }

    @Column(name = "\"execution_time\"")
    public int getExecutionTime() {
        return this.executionTime;
    }

    public void setExecutionTime(int executionTime) {
        this.executionTime = executionTime;
    }

    @Column(name = "\"success\"")
    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

}
