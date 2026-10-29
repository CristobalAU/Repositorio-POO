package Modelo;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;



public class PersistenciaHistorial {

    private static Path obtenerArchivo(String username) {

        if (username == null ||
                !username.matches("[a-zA-Z0-9_-]+")) {
            throw new IllegalArgumentException(
                    "Nombre de usuario inválido para el archivo"
            );
        }

        return Paths.get(
                "data",
                "historial_" + username + ".csv"
        );
    }

    public static void guardar(String username, List<Resultado> historial)
            throws IOException {

        Path archivo = obtenerArchivo(username);

        Files.createDirectories(archivo.getParent());

        try (BufferedWriter writer =
                Files.newBufferedWriter(archivo)) {

            writer.write("numero,tipoApuesta,monto,gano,ganancia");
            writer.newLine();

            for (Resultado resultado : historial) {

                writer.write(
                        resultado.getNumero() + "," +
                                resultado.getTipoApuesta().getEtiqueta() + "," +
                                resultado.getMontoApostado() + "," +
                                resultado.isGano() + "," +
                                resultado.getGanancia()
                );

                writer.newLine();
            }
        }
    }

    public static List<Resultado> cargar(String username)
        throws IOException {

        Path archivo = obtenerArchivo(username);

        List<Resultado> historial = new ArrayList<>();

        if (!Files.exists(archivo)) {
            return historial;
        }

        try (BufferedReader reader =
                Files.newBufferedReader(archivo)) {

            reader.readLine();

            String linea;

            while ((linea = reader.readLine()) != null) {

                String[] datos = linea.split(",");

                if (datos.length != 5) {
                    continue;
                }

                int numero = Integer.parseInt(datos[0]);
                String tipo = datos[1];
                int monto = Integer.parseInt(datos[2]);
                boolean gano = Boolean.parseBoolean(datos[3]);
                int ganancia = Integer.parseInt(datos[4]);

                ApuestaBase apuesta;

                switch (tipo) {
                    case "Rojo":
                        apuesta = new ApuestaRojo(monto);
                        break;

                    case "Negro":
                        apuesta = new ApuestaNegro(monto);
                        break;

                    case "Par":
                        apuesta = new ApuestaPar(monto);
                        break;

                    case "Impar":
                        apuesta = new ApuestaImpar(monto);
                        break;

                    default:
                        continue;
                }

                Resultado resultado = new Resultado(
                        numero,
                        apuesta,
                        monto,
                        gano,
                        ganancia
                );

                historial.add(resultado);
            }
        }

        return historial;
    }
}
