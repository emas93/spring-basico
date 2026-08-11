package com.emas.spring_basico.business;

import com.emas.spring_basico.infrastructure.entity.Usuario;
import com.emas.spring_basico.infrastructure.exceptions.ConflictException;
import com.emas.spring_basico.infrastructure.exceptions.ResourceNotFoundException;
import com.emas.spring_basico.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;


    public Usuario salvaUsuario(Usuario usuario) {
        try {
            emailExiste(usuario.getEmail());
            usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
            return usuarioRepository.save(usuario);
        } catch (ConflictException e) {
            throw new ConflictException("E-mail já cadastrado");
        }
    }

    public void emailExiste(String email) {
        try {
            boolean emailExiste = verificaEmailExistente(email);
            if (emailExiste) {
                throw new ConflictException("E-mail já cadastrado " + email);
            }
        } catch (ConflictException e) {
            throw new ConflictException("Email já cadastrado " + e.getCause());
        }
    }


    public boolean verificaEmailExistente(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    public Usuario buscarUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("E-mail não encontrado." + email));
    }

    public void deletaUsuarioPorEmail(String email) {
        usuarioRepository.deleteByEmail(email);
    }

    public Usuario atualizaUsuario(Usuario usuario) {
        //Se o email existe, salva a alteração, senão.. informa que o usuario não existe
        Optional<Usuario> emailExiste = usuarioRepository.findByEmail(usuario.getEmail());

        if (emailExiste.isPresent()) {
            usuario.setId(emailExiste.get().getId());
            return usuarioRepository.save(usuario);

        } else {
            throw new ConflictException("Alteração não realizada, o usuário" + usuario.getEmail() + " não existe na base de dados.");
        }
    }


}

