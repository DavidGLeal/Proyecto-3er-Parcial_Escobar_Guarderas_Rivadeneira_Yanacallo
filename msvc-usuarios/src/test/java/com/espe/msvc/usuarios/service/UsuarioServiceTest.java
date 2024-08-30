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
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private CursoClientRest cursoClientRest;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testListar() {
        when(usuarioRepository.findAll()).thenReturn(List.of(new Usuario(1L, "Usuario 1", "user1@mail.com", "pass1"),
                new Usuario(2L, "Usuario 2", "user2@mail.com", "pass2")));

        List<Usuario> usuarios = usuarioService.listar();

        assertEquals(2, usuarios.size());
        assertEquals("Usuario 1", usuarios.get(0).getNombre());
        assertEquals("Usuario 2", usuarios.get(1).getNombre());
    }

    @Test
    public void testPorId() {
        Long id = 1L;
        Usuario usuario = new Usuario(id, "Usuario 1", "user1@mail.com", "pass1");
        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));

        Optional<Usuario> result = usuarioService.porId(id);

        assertTrue(result.isPresent());
        assertEquals("Usuario 1", result.get().getNombre());
    }

    @Test
    public void testGuardar() {
        Usuario usuario = new Usuario(null, "Usuario 1", "user1@mail.com", "pass1");
        Usuario usuarioGuardado = new Usuario(1L, "Usuario 1", "user1@mail.com", "pass1");
        when(usuarioRepository.save(usuario)).thenReturn(usuarioGuardado);

        Usuario result = usuarioService.guardar(usuario);

        assertNotNull(result.getId());
        assertEquals("Usuario 1", result.getNombre());
    }

    @Test
    public void testEliminar() {
        Long id = 1L;
        when(cursoClientRest.isUsuarioAsociado(id)).thenReturn(false);
        doNothing().when(usuarioRepository).deleteById(id);

        usuarioService.eliminar(id);

        verify(usuarioRepository, times(1)).deleteById(id);
    }
}