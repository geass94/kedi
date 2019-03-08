package ge.idealab.kedi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "basic_pages")
public class BasicPage extends BaseStatusAuditEntity {
    @NotNull
    @Column
    private String name;
    @Column
    private String alias;
    @Column(columnDefinition = "TEXT")
    private String body;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
