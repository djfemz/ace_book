package africa.semicolon.acebook.security;

import africa.semicolon.acebook.models.Account;
import africa.semicolon.acebook.models.Tier;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
public class SecureUser implements UserDetails {
    private final Account account;
    @Override
    public String getUsername() {
        return account.getAccountDetails().getEmail();
    }
    @Override
    public String getPassword() {
        return account.getAccountDetails().getPassword();
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        Tier tier =  account.getTier();
        authorities.add(new SimpleGrantedAuthority(tier.name()));
        return authorities;
    }


    @Override
    public boolean isEnabled() {
        return true;
    }


    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


}
