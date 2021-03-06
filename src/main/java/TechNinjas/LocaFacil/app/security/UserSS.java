package TechNinjas.LocaFacil.app.security;

import TechNinjas.LocaFacil.app.models.enums.Profile;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Essa classe serve para fornecer informações básicas do usuário
 * As implementações não são usadas diretamente pelo Spring Security
 * para fins de segurança. Eles simplesmente armazenam informações
 * do usuário que são posteriormente encapsuladas em Authentication
 */
public class UserSS implements UserDetails {

    @Getter
    private Integer id;
    private String name;
    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public UserSS(Integer id, String name, String email, String password, Set<Profile> profile) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.authorities = profile.stream().map(x -> new SimpleGrantedAuthority(x.getDescricao())).collect(Collectors.toSet());
    }

    public Integer getId() {
        return id;
    }

    public String getName(){
        return name;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public boolean hasRole(Profile profile) {
        return getAuthorities().contains(new SimpleGrantedAuthority(profile.getDescricao()));
    }
}
