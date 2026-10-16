package org.example;

import Controlador.SessionController;
import Vista.VentanaLogin;

public class Main {

    public static void main(String [] args) {

        SessionController controller = new SessionController();
        VentanaLogin login = new VentanaLogin(controller);
        login.mostrarVentana();
    }
}