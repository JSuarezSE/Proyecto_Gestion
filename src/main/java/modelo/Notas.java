package modelo;

public class Notas {
    private int idNota;
    private int idUsuario;
    private int idCurso;
    private double insumo1;
    private double insumo2;
    private double insumo3;
    private double total;

    public Notas() {}

    public Notas(int idNota, int idUsuario, int idCurso, double insumo1, double insumo2, double insumo3) {
        this.idNota = idNota;
        this.idUsuario = idUsuario;
        this.idCurso = idCurso;
        this.insumo1 = insumo1;
        this.insumo2 = insumo2;
        this.insumo3 = insumo3;
        this.total = (insumo1 + insumo2 + insumo3) / 3; // Cálculo del total
    }

    public int getIdNota() {
        return idNota;
    }

    public void setIdNota(int idNota) {
        this.idNota = idNota;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }

    public double getInsumo1() {
        return insumo1;
    }

    public void setInsumo1(double insumo1) {
        this.insumo1 = insumo1;
        calcularTotal();
    }

    public double getInsumo2() {
        return insumo2;
    }

    public void setInsumo2(double insumo2) {
        this.insumo2 = insumo2;
        calcularTotal();
    }

    public double getInsumo3() {
        return insumo3;
    }

    public void setInsumo3(double insumo3) {
        this.insumo3 = insumo3;
        calcularTotal();
    }

    public double getTotal() {
        return total;
    }

    private void calcularTotal() {
        this.total = (this.insumo1 + this.insumo2 + this.insumo3) / 3;
    }
}
