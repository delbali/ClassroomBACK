package it.itresources.springtut.springtutorial.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "users", uniqueConstraints = {
    @UniqueConstraint(columnNames = "username")
})
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column()
    private String username;

    @Column()
    private String password;
    
    @Column()
    private String firstName;
    
    @Column()
    private String lastName;

    @Column()
    private String address;
    
    @Column()
    private LocalDate subscriptionDate;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",  joinColumns = @JoinColumn(name = "userid"),  inverseJoinColumns = @JoinColumn(name = "roleid")) 
    private List<RoleEntity> roles = new ArrayList<>();
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_classrooms",  joinColumns = @JoinColumn(name = "userid"),  inverseJoinColumns = @JoinColumn(name = "classroomid")) 
    private List<ClassroomEntity> classrooms = new ArrayList<>();

    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="creator")
    List<DocumentEntity> uploads = new ArrayList<>();
    
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public LocalDate getSubscriptionDate() {
		return subscriptionDate;
	}

	public void setSubscriptionDate(LocalDate subscriptionDate) {
		this.subscriptionDate = subscriptionDate;
	}

	public UserEntity(Long id, String username, String password, String firstName, String lastName, String address,
			LocalDate subscriptionDate, List<RoleEntity> roles, List<ClassroomEntity> classrooms) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.subscriptionDate = subscriptionDate;
		this.roles = roles;
		this.classrooms = classrooms;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public List<ClassroomEntity> getClassrooms() {
		return classrooms;
	}

	public void setClassrooms(List<ClassroomEntity> classrooms) {
		this.classrooms = classrooms;
	}

	public UserEntity() {
    }

    public UserEntity(Long id, String username, String password, List<RoleEntity> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public UserEntity(String username, String password, List<RoleEntity> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public UserEntity(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<RoleEntity> getRoles() {
        return this.roles;
    }

    public void setRoles(List<RoleEntity> roles) {
        this.roles = roles;
    }

    public UserEntity id(Long id) {
        setId(id);
        return this;
    }

    public UserEntity username(String username) {
        setUsername(username);
        return this;
    }

    public UserEntity password(String password) {
        setPassword(password);
        return this;
    }

    public UserEntity roles(List<RoleEntity> roles) {
        setRoles(roles);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof UserEntity)) {
            return false;
        }
        UserEntity user = (UserEntity) o;
        return Objects.equals(id, user.id) && Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(roles, user.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, roles);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", username='" + getUsername() + "'" +
            ", password='" + getPassword() + "'" +
            ", roles='" + getRoles() + "'" +
            "}";
    }

}