//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import java.io.*;
import java.util.*;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static Integer CANTIDAD_PIEZAS;
    public static final String CSV_FILE = ".//Piezas.csv";

    // <editor-fold desc="Lectura de Archivos">
    public static List<Maquina> leerArchivo(String rutaArchivo) {
        boolean primera = true;
        String linea;
        List<Maquina> maquinas = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            while ((linea = br.readLine()) != null) {
                if (primera) {
                    String[] linea_1 = linea.split(",");
                    CANTIDAD_PIEZAS = Integer.parseInt(linea_1[0]);
                    primera = false;
                } else {
                    String[] valores = linea.split(",");
                    maquinas.add(new Maquina(valores[0], Integer.parseInt(valores[1])));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (maquinas.size() > 0)
            return maquinas;
        return null;
    }

    // </editor-fold>

    // <editor-fold desc="GREEDY">

    public static List<Maquina> greedy(List<Maquina> maquinas) {
        List<Maquina> solucion = new ArrayList<>();
        List<Maquina> candidatos = maquinas;
        candidatos.sort(Comparator.reverseOrder());
        int contador = 0;

        while (!candidatos.isEmpty() && !esSolucion(contador) && contador < CANTIDAD_PIEZAS) {
            //Seleccionamos el candidato. (Primero por orden de cantidad)
            Maquina m = candidatos.removeFirst();
            while (esFactible(m, contador)) {
                solucion.add(m);
                contador += m.getCantidad_piezas();
            }
        }

        if (esSolucion(contador)) {
            return solucion;
        } else return null;
    }


    private static boolean esFactible(Maquina m, int contador) {
        return m.getCantidad_piezas() + contador <= CANTIDAD_PIEZAS;
    }

    private static boolean esSolucion(int cant) {
        return cant == CANTIDAD_PIEZAS;
    }

    // </editor-fold>

    // <editor-fold desc="BACKTRACKING">
    // Tu código acá
    // </editor-fold>


    public static void main(String[] args) {
        List<Maquina> maquinas = leerArchivo("Files/Pieza.csv");
        greedy(maquinas);
    }
}