package com.example.habittracker.model;

//import jakarta.persistence.*;
//import lombok.EqualsAndHashCode;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.springframework.boot.autoconfigure.task.TaskExecutionProperties;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Collection;
//import java.util.List;
//import java.util.UUID;

//@Entity
//@Getter
//@Setter
//@NoArgsConstructor
//@Table(name = "APP_USER")
//public class User implements UserDetails {
//    public User(Long id, String uuid, String login, String email, String username, String password, Role role, boolean isLock, boolean isEnable) {
//        this.id = id;
//        this.uuid = uuid;
//        this.login = login;
//        this.email = email;
//        this.username = username;
//        this.password = password;
//        this.role = role;
//        this.isLock = isLock;
//        this.isEnable = isEnable;
//        generateUuid();
//    }
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String uuid;
//
//    private String login;
//
//    @Column(name = "Email")
//    private String email;
//
//    @Column(name = "USER_NAME")
//    private String username;
//
//    @Column(name = "PASSWORD")
//    private String password;
//
//    @Column(name = "ROLE")
//    @Enumerated(EnumType.STRING)
//    private Role role;
//
//    private boolean isLock;
//
//    private boolean isEnable;
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return List.of(new SimpleGrantedAuthority(role.name()));
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return false;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return !isLock;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() { //data przydatności hasła
//        return false;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return isEnable;
//    }
//    private void generateUuid(){
//        if (uuid == null || uuid.isEmpty()){
//            setUuid(UUID.randomUUID().toString());
//        }
//    }
//}
//