package ge.idealab.kedi.model.user;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import ge.idealab.kedi.dto.PersonalInformationDTO;
import ge.idealab.kedi.model.BaseStatusAuditEntity;
import ge.idealab.kedi.model.enums.AuthProvider;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class User extends BaseStatusAuditEntity {
    @Column(nullable = false)
    private String name;

    @Email
    @Column(nullable = false)
    private String email;

    private String imageUrl;

    @Column(nullable = false)
    private Boolean emailVerified = false;

    @JsonIgnore
    private String password;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    private String providerId;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinTable(name = "user_authority",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name="fk_usrauth_user_id")),
            inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name="fk_usrauth_authority_id")))
    private List<Authority> authorities;

    @OneToOne(cascade = CascadeType.ALL, optional = true)
    @JoinColumn(name = "personal_information_id", nullable = true, foreignKey = @ForeignKey(name="fk_prsinf_personal_information_id"))
    private PersonalInformation personalInformation = new PersonalInformation();

    public User() {
    }

    public User(String name, @Email String email, String password, @NotNull AuthProvider provider, List<Authority> authorities) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.provider = provider;
        this.authorities = authorities;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Boolean getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AuthProvider getProvider() {
        return provider;
    }

    public void setProvider(AuthProvider provider) {
        this.provider = provider;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }

    public PersonalInformation getPersonalInformation() {
        return personalInformation;
    }

    public PersonalInformationDTO getPersonalInformationDTO(){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this.personalInformation, PersonalInformationDTO.class);
    }

    public void setPersonalInformation(PersonalInformation personalInformation) {
        this.personalInformation = personalInformation;
    }
}

