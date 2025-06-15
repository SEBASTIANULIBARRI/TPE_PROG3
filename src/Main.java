import java.io.*;
import java.util.*;

public class Main {
    public static int CANTIDAD_PIEZAS;
    public static final String CSV_FILE = "../Files/Pieza.csv";
    public static int estadosBacktracking = 0;
    public static int candidatosGreedy = 0;

    // <editor-fold desc="Lectura de Archivos">
    public static List<Maquina> leerArchivo(String rutaArchivo) {
        String linea;
        boolean primera = true;
        List<Maquina> maquinas = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            while ((linea = br.readLine()) != null) {
                if (primera) {
                    String aux = linea.trim();
                    if(linea.trim().substring(aux.length()-1).equals(",")){
                        CANTIDAD_PIEZAS = Integer.parseInt(linea.trim().substring(0,aux.length()-1));
                    }
                    primera = false;
                } else {
                    String[] valores = linea.split(",");
                    maquinas.add(new Maquina(valores[0].trim(), Integer.parseInt(valores[1].trim())));
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }

        return maquinas;
    }
    // </editor-fold>

    // <editor-fold desc="GREEDY">
    public static List<Maquina> greedy(List<Maquina> maquinas) {
        List<Maquina> solucion = new ArrayList<>();
        PriorityQueue<Maquina> candidatos = new PriorityQueue<>((a, b) -> b.getCantidad_piezas() - a.getCantidad_piezas());
        candidatos.addAll(maquinas);
        int contador = 0;

        while (!candidatos.isEmpty() && contador < CANTIDAD_PIEZAS) {
            Maquina m = candidatos.poll();
            candidatosGreedy++;

            while (esFactible(m, contador)) {
                solucion.add(m);
                contador += m.getCantidad_piezas();
            }
        }

        if (esSolucion(contador)) return solucion;
        else return null;
    }

    private static boolean esFactible(Maquina m, int contador) {
        return m.getCantidad_piezas() + contador <= CANTIDAD_PIEZAS;
    }

    private static boolean esSolucion(int cant) {
        return cant == CANTIDAD_PIEZAS;
    }
    // </editor-fold>

    // <editor-fold desc="BACKTRACKING">
    private static void back_recursivo(List<Maquina> maquinas, List<Maquina> sol_parcial,
                                       List<Maquina> sol_final, int piezasActuales) {
        estadosBacktracking++;

        if (piezasActuales == CANTIDAD_PIEZAS && (sol_final.isEmpty() || sol_parcial.size() < sol_final.size())) {
            sol_final.clear();
            sol_final.addAll(sol_parcial);
        } else {
            for (Maquina m : maquinas) {
                if (piezasActuales + m.getCantidad_piezas() <= CANTIDAD_PIEZAS) {
                    sol_parcial.add(m);
                    back_recursivo(maquinas, sol_parcial, sol_final, piezasActuales + m.getCantidad_piezas());
                    sol_parcial.remove(sol_parcial.size() - 1);
                }
            }
        }
    }

    public static List<Maquina> back(List<Maquina> maquinas) {
        List<Maquina> sol_parcial = new ArrayList<>();
        List<Maquina> sol_final = new ArrayList<>();
        back_recursivo(maquinas, sol_parcial, sol_final, 0);
        return sol_final;
    }
    // </editor-fold>

    public static void mostrarResultado(String titulo, List<Maquina> solucion, int piezas, int puestas, int costo) {
        System.out.println("\n--- " + titulo + " ---");
        if (solucion == null || solucion.isEmpty()) {
            System.out.println("No se encontró solución.");
            return;
        }

        System.out.println("Secuencia: " + solucion);
        System.out.println("Piezas producidas: " + piezas);
        System.out.println("Puestas en funcionamiento: " + puestas);
        System.out.println("Costo: " + costo);
    }

    public static void main(String[] args) {
        List<Maquina> maquinas = leerArchivo(CSV_FILE);

        if (maquinas == null || maquinas.isEmpty()) {
            System.out.println("No se cargaron máquinas.");
            return;
        }

        List<Maquina> solGreedy = greedy(new ArrayList<>(maquinas));
        int piezasG = solGreedy != null ? solGreedy.stream().mapToInt(Maquina::getCantidad_piezas).sum() : 0;
        mostrarResultado("GREEDY", solGreedy, piezasG, solGreedy != null ? solGreedy.size() : 0, candidatosGreedy);

        List<Maquina> solBack = back(maquinas);
        int piezasB = solBack != null ? solBack.stream().mapToInt(Maquina::getCantidad_piezas).sum() : 0;
        mostrarResultado("BACKTRACKING", solBack, piezasB, solBack != null ? solBack.size() : 0, estadosBacktracking);
    }
}

