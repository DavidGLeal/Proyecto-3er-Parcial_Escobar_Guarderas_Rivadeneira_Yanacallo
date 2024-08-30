package com.espe.msvc.usuarios.service;

import com.espe.msvc.usuarios.clients.CursoClientRest;
import com.espe.msvc.usuarios.exceptions.ForeignKeyConstraintViolationException;
import com.espe.msvc.usuarios.models.entity.Usuario;
import com.espe.msvc.usuarios.repositories.UsuarioRepository;
import com.espe.msvc.usuarios.services.UsuarioServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
@Rollback
public class UsuarioServiceIntegrationTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Mock
    private CursoClientRest cursoClientRest;

    @InjectMocks
    @Autowired
    private UsuarioServiceImpl usuarioService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        usuarioRepository.deleteAll();  // Limpiar la base de datos antes de cada prueba
    }

    @Test
    public void testListarUsuarios() {
        usuarioRepository.save(new Usuario(null, "Usuario 1", "user1@mail.com", "pass1"));
        usuarioRepository.save(new Usuario(null, "Usuario 2", "user2@mail.com", "pass2"));

        List<Usuario> usuarios = usuarioService.listar();

        assertEquals(2, usuarios.size());
        assertEquals("Usuario 1", usuarios.get(0).getNombre());
        assertEquals("Usuario 2", usuarios.get(1).getNombre());
    }

    @Test
    public void testGuardarUsuario() {
        Usuario usuario = new Usuario(null, "Nuevo Usuario", "nuevo@mail.com", "password");
        Usuario usuarioGuardado = usuarioService.guardar(usuario);

        assertNotNull(usuarioGuardado.getId());
        assertEquals("Nuevo Usuario", usuarioGuardado.getNombre());
    }
}
