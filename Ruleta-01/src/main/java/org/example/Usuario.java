package org.example;

public class Usuario {

    private String username;
    private String password;
    private String nombre;
    private int saldo;

    public Usuario() {
        this.username = "invitado";
        this.password = "";
        this.nombre = "Invitado";
        this.saldo = 0;
    }

    public Usuario(String username, String password, String nombre) {
        this.username = username;
        this.password = password;
        this.nombre = "Invitado";
        setNombre(nombre);
        this.saldo = 0;
    }

    public String getUsername() {
        return username;
    }

    public String getNombre() {
        return nombre;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setNombre(String nombre) {
        if (nombre != null && !nombre.trim().isEmpty()) {
            this.nombre = nombre.trim();
        }
    }

    public void setSaldo(int saldo) {
        if (saldo >= 0) {
            this.saldo = saldo;
        }
    }
    public boolean validarCredenciales(String username, String password) {
        return this.username.equals(username)
                && this.password.equals(password);
    }

    public boolean depositar(int monto) {
        if (monto <= 0) {
            return false;
        }

        saldo += monto;
        return true;
    }
}
