package Modelo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PersistenciaSaldo {

    private static Path obtenerArchivo(String username) {

        if (username == null ||
                !username.matches("[a-zA-Z0-9_-]+")) {

            throw new IllegalArgumentException(
                    "Nombre de usuario inválido"
            );
        }

        return Paths.get(
                "data",
                "saldo_" + username + ".txt"
        );
    }

    public static void guardar(String username, int saldo)
        throws IOException {

        if (saldo < 0) {
            throw new IllegalArgumentException(
                    "El saldo no puede ser negativo"
            );
        }

        Path archivo = obtenerArchivo(username);

        Files.createDirectories(archivo.getParent());

        Files.writeString(
                archivo,
                String.valueOf(saldo)
        );
    }

    public static int cargar(String username)
        throws IOException {

        Path archivo = obtenerArchivo(username);

        if (!Files.exists(archivo)) {
            return 0;
        }

        String contenido = Files.readString(archivo).trim();

        try {
            int saldo = Integer.parseInt(contenido);

            if (saldo < 0) {
                throw new IOException(
                        "El archivo contiene un saldo negativo"
                );
            }

            return saldo;

        } catch (NumberFormatException e) {
            throw new IOException(
                    "El archivo contiene un saldo inválido",
                    e
            );
        }
    }
}
