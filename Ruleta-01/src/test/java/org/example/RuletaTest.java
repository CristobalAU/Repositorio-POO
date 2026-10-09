
package org.example;

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
                ruleta.evaluarResultado(1, TipoApuesta.ROJO)
        );
    }

    @Test
    void apuestaRojoDebePerderConNumeroNegro() {
        assertFalse(
                ruleta.evaluarResultado(2, TipoApuesta.ROJO)
        );
    }

    @Test
    void apuestaParDebeGanarConNumeroPar() {
        assertTrue(
                ruleta.evaluarResultado(8, TipoApuesta.PAR)
        );
    }

    @Test
    void apuestaImparDebeGanarConNumeroImpar() {
        assertTrue(
                ruleta.evaluarResultado(7, TipoApuesta.IMPAR)
        );
    }

    @Test
    void numeroCeroDebePerderCualquierApuesta() {
        assertFalse(
                ruleta.evaluarResultado(0, TipoApuesta.ROJO)
        );
    }
}