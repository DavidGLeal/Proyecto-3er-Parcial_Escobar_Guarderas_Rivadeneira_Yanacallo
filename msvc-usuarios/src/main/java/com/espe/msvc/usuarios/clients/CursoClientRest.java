package com.espe.msvc.usuarios.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-cursos", url = "http://localhost:8002/api/cursos")
public interface CursoClientRest {

    @GetMapping("/usuario-asociado/{idUsuario}")
    boolean isUsuarioAsociado(@PathVariable Long idUsuario);
}
