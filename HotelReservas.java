import java.io.*;
import java.util.*;

public class HotelReservas {
    static final String USUARIOS_FILE = "usuarios.txt";
    static final String RESERVAS_FILE = "reservas.txt";

    static Scanner scanner = new Scanner(System.in);
    static String usuarioActual = null;

    public static void main(String[] args) {
        while (true) {
            System.out.println("1. Iniciar sesión");
            System.out.println("2. Registrar usuario");
            System.out.println("3. Salir");
            System.out.print("Elige una opción: ");
            int opcion = Integer.parseInt(scanner.nextLine());
            switch (opcion) {
                case 1:
                    if (login()) {
                        menuReservas();
                    }
                    break;
                case 2:
                    registrarUsuario();
                    break;
                case 3:
                    System.out.println("¡Hasta luego!");
                    return;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    static boolean login() {
        System.out.print("Usuario: ");
        String usuario = scanner.nextLine();
        System.out.print("Contraseña: ");
        String contrasena = scanner.nextLine();
        try (BufferedReader br = new BufferedReader(new FileReader(USUARIOS_FILE))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes[0].equals(usuario) && partes[1].equals(contrasena)) {
                    usuarioActual = usuario;
                    System.out.println("Inicio de sesión exitoso");
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer usuarios");
        }
        System.out.println("Usuario o contraseña incorrectos");
        return false;
    }

    static void registrarUsuario() {
        System.out.print("Nuevo usuario: ");
        String usuario = scanner.nextLine();
        System.out.print("Contraseña: ");
        String contrasena = scanner.nextLine();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(USUARIOS_FILE, true))) {
            bw.write(usuario + "," + contrasena);
            bw.newLine();
            System.out.println("Usuario registrado correctamente");
        } catch (IOException e) {
            System.out.println("Error al guardar usuario");
        }
    }

    static void menuReservas() {
        while (true) {
            System.out.println("\n--- Menú de Reservas ---");
            System.out.println("1. Crear reserva");
            System.out.println("2. Ver mis reservas");
            System.out.println("3. Cerrar sesión");
            System.out.print("Elige una opción: ");
            int opcion = Integer.parseInt(scanner.nextLine());
            switch (opcion) {
                case 1:
                    crearReserva();
                    break;
                case 2:
                    verReservas();
                    break;
                case 3:
                    usuarioActual = null;
                    return;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    static void crearReserva() {
        System.out.print("Fecha de entrada (YYYY-MM-DD): ");
        String entrada = scanner.nextLine();
        System.out.print("Fecha de salida (YYYY-MM-DD): ");
        String salida = scanner.nextLine();
        System.out.print("Tipo de habitación: ");
        String tipo = scanner.nextLine();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RESERVAS_FILE, true))) {
            bw.write(usuarioActual + "," + entrada + "," + salida + "," + tipo);
            bw.newLine();
            System.out.println("Reserva creada correctamente");
        } catch (IOException e) {
            System.out.println("Error al guardar reserva");
        }
    }

    static void verReservas() {
        System.out.println("\nTus reservas:");
        try (BufferedReader br = new BufferedReader(new FileReader(RESERVAS_FILE))) {
            String linea;
            boolean hayReservas = false;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes[0].equals(usuarioActual)) {
                    System.out.println("Entrada: " + partes[1] + ", Salida: " + partes[2] + ", Habitación: " + partes[3]);
                    hayReservas = true;
                }
            }
            if (!hayReservas) {
                System.out.println("No tienes reservas");
            }
        } catch (IOException e) {
            System.out.println("Error al leer reservas");
        }
    }
}
