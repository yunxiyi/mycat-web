package cn.edu.nwsuaf.model;

public class UserSchema {
    private Long id;

    private Long userid;

    private Boolean hasschema;

    private String schemaUserName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Boolean getHasschema() {
        return hasschema;
    }

    public void setHasschema(Boolean hasschema) {
        this.hasschema = hasschema;
    }

    public String getSchemaUserName() {
        return schemaUserName;
    }

    public void setSchemaUserName(String schemaUserName) {
        this.schemaUserName = schemaUserName == null ? null : schemaUserName.trim();
    }
}