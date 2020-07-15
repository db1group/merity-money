package br.com.db1.meritmoney.service;

import br.com.db1.meritmoney.domain.Pessoa;
import br.com.db1.meritmoney.repository.PessoaRepository;
import br.com.db1.meritmoney.security.UserSS;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final PessoaRepository pessoaRepository;

    public UserDetailsServiceImpl(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Pessoa usuario = pessoaRepository.findByEmail(email);
        if (usuario == null){
            throw new UsernameNotFoundException(email);
        }
        return new UserSS(usuario.getId(), usuario.getEmail(), usuario.getSenha(), usuario.getPerfis());
    }
}
