package cal.projeteq3.glucose.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public abstract class User<U extends User<?>>{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Embedded
    private Person person;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "credentials", referencedColumnName = "id", nullable = false)
    private Credentials<U> credentials;

    @Builder
    public User(long id, String firstName, String lastName, String email, String password) {
        this.id = id;
        this.person = new Person(firstName, lastName);
        this.credentials = new Credentials<U>(email, password, Role.STUDENT);
    }

    public String getFirstName(){
        return this.person.getFirstName();
    }

    public String getLastName(){
        return this.person.getLastName();
    }

    public String getEmail(){
        return this.credentials.getEmail();
    }

    public String getPassword(){
        return this.credentials.getPassword();
    }

    public Role getRole(){
        return this.credentials.getRole();
    }

    public void setEmail(String email){
        this.credentials.setEmail(email);
    }

    public void setPassword(String password){
        this.credentials.setPassword(password);
    }

    public void setRole(Role role){
        this.credentials.setRole(role);
    }

}
