package com.espe.msvc.usuarios.services;

import com.espe.msvc.usuarios.clients.CursoClientRest;
import com.espe.msvc.usuarios.exceptions.ForeignKeyConstraintViolationException;
import com.espe.msvc.usuarios.models.entity.Usuario;
import com.espe.msvc.usuarios.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {
    @Autowired
    private UsuarioRepository repository;
    @Autowired
    private CursoClientRest cursoClientRest;

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> listar() {
        return (List<Usuario>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> porId(long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Usuario guardar(Usuario usuario) {
        return repository.save(usuario);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        System.out.println("Verificando si el usuario está asociado...");
        if (cursoClientRest.isUsuarioAsociado(id)) {
            System.out.println("Usuario está asociado. Lanzando excepción...");
            throw new ForeignKeyConstraintViolationException("No se puede eliminar el estudiante porque está matriculado en un curso.");
        }
        System.out.println("Usuario no está asociado. Procediendo a eliminar...");
        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> porEmail(String email) {
        return repository.findByEmail(email);
    }
}
