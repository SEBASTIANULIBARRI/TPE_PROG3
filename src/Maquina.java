public class Maquina implements Comparable {
    private String nombre;
    private int cantidad_piezas;

    public Maquina(String nombre, int cantidad_piezas) {
        this.nombre = nombre;
        this.cantidad_piezas = cantidad_piezas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad_piezas() {
        return cantidad_piezas;
    }

    public void setCantidad_piezas(int cantidad_piezas) {
        this.cantidad_piezas = cantidad_piezas;
    }

    @Override
    public int compareTo(Object o) {
        Maquina m1 = (Maquina) o;
        return Integer.compare(this.cantidad_piezas,m1.cantidad_piezas);
    }

    @Override
    public String toString() {
        return "Maquina{" +
                "nombre='" + nombre + '\'' +
                ", cantidad_piezas=" + cantidad_piezas +
                '}';
    }
}
