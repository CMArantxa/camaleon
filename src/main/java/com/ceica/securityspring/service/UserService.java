package com.ceica.securityspring.service;

import com.ceica.securityspring.model.Authority;
import com.ceica.securityspring.model.User;
import com.ceica.securityspring.repository.AuthorityRepository;
import com.ceica.securityspring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;
    private AuthorityRepository authorityRepository;
    @Autowired
    public UserService(UserRepository userRepository,AuthorityRepository authorityRepository) {
        this.userRepository = userRepository;
        this.authorityRepository=authorityRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Usuario no encontrado con nombre de usuario: " + username);
        }
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                getAuthorities(user.getAuthorities())
        );
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Collection<Authority> authorities) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (Authority authority : authorities) {
            grantedAuthorities.add(new SimpleGrantedAuthority(authority.getAuthority()));
        }
        return grantedAuthorities;
    }

    public void crearUsuario(User user) {

       User newUser=userRepository.save(user);
       Authority authority=new Authority();
       authority.setAuthority("USER");
       authority.setUser_id(newUser.getId());
        authorityRepository.save(authority);
    }
}
