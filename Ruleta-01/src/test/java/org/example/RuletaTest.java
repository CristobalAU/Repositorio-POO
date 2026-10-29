
package org.example;

import Modelo.Ruleta;
import Modelo.ApuestaRojo;
import Modelo.ApuestaNegro;
import Modelo.ApuestaPar;
import Modelo.ApuestaImpar;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RuletaTest {

    private final Ruleta ruleta = new Ruleta();

    @Test
    void numeroRojoDebeRetornarTrue() {
        assertTrue(ruleta.esRojo(1));
    }

    @Test
    void numeroNegroDebeRetornarFalse() {
        assertFalse(ruleta.esRojo(2));
    }

    @Test
    void apuestaRojoDebeGanarConNumeroRojo() {
        assertTrue(
                ruleta.evaluarResultado(
                        1, "Rojo", new ApuestaRojo(100)
                )
        );
    }

    @Test
    void apuestaRojoDebePerderConNumeroNegro() {
        assertFalse(
                ruleta.evaluarResultado(
                        2, "Negro", new ApuestaRojo(100)
                )
        );
    }

    @Test
    void apuestaNegroDebeGanarConNumeroNegro() {
        assertTrue(
                ruleta.evaluarResultado(
                        2, "Negro", new ApuestaNegro(100)
                )
        );
    }

    @Test
    void apuestaParDebeGanarConNumeroPar() {
        assertTrue(
                ruleta.evaluarResultado(
                        8, "Negro", new ApuestaPar(100)
                )
        );
    }

    @Test
    void apuestaImparDebeGanarConNumeroImpar() {
        assertTrue(
                ruleta.evaluarResultado(
                        7, "Rojo", new ApuestaImpar(100)
                )
        );
    }

    @Test
    void numeroCeroDebePerderCualquierApuesta() {
        assertFalse(
                ruleta.evaluarResultado(
                        0, "Verde", new ApuestaRojo(100)
                )
        );

        assertFalse(
                ruleta.evaluarResultado(
                        0, "Verde", new ApuestaNegro(100)
                )
        );

        assertFalse(
                ruleta.evaluarResultado(
                        0, "Verde", new ApuestaPar(100)
                )
        );

        assertFalse(
                ruleta.evaluarResultado(
                        0, "Verde", new ApuestaImpar(100)
                )
        );
    }
}