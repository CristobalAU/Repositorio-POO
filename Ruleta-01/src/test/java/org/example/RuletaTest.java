package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RuletaTest {

    @Test
    void numeroRojoDebeRetornarTrue() {

        boolean resultado = Ruleta.esRojo(1);

        assertTrue(resultado);
    }

    @Test
    void numeroNegroDebeRetornarFalse() {

        boolean resultado = Ruleta.esRojo(2);

        assertFalse(resultado);
    }

    @Test
    void apuestaRojoDebeGanarConNumeroRojo() {

        boolean resultado = Ruleta.evaluarResultado(1, 'R');

        assertTrue(resultado);
    }

    @Test
    void apuestaRojoDebePerderConNumeroNegro() {

        boolean resultado = Ruleta.evaluarResultado(2, 'R');

        assertFalse(resultado);
    }

    @Test
    void apuestaParDebeGanarConNumeroPar() {

        boolean resultado = Ruleta.evaluarResultado(8, 'P');

        assertTrue(resultado);
    }

    @Test
    void apuestaImparDebeGanarConNumeroImpar () {

        boolean resultado = Ruleta.evaluarResultado(7, 'I');

        assertTrue(resultado);
    }

    @Test
    void ceroDebePerderEnApuestaPar() {

        boolean resultado = Ruleta.evaluarResultado(0, 'P');

        assertFalse(resultado);
    }
}
