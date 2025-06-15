class Maquina {
    private final String nombre;
    private final int cantidad_piezas;

    public Maquina(String nombre, int cantidad_piezas) {
        this.nombre = nombre;
        this.cantidad_piezas = cantidad_piezas;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCantidad_piezas() {
        return cantidad_piezas;
    }

    @Override
    public String toString() {
        return nombre + "(" + cantidad_piezas + ")";
    }
}