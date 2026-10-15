package org.example;

import java.util.Collections;
import java.util.List;

public class ResultadoController {

    private final SessionController session;

    public ResultadoController(SessionController session) {
        this.session = session;
    }

    public List<Resultado> obtenerHistorial() {

        Usuario usuario = session.getUsuarioActual();

        if (usuario == null) {
            return Collections.emptyList();
        }

        return usuario.getHistorial();
    }
}
