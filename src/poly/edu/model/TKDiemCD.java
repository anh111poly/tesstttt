
package poly.edu.model;

public class TKDiemCD {
    private String tenCD;
    private int soLuong;
    private float diemTN;
    private float diemCN;
    private float diemTB;

    public TKDiemCD() {
    }

    public TKDiemCD(String tenCD, int soLuong, float diemTN, float diemCN, float diemTB) {
        this.tenCD = tenCD;
        this.soLuong = soLuong;
        this.diemTN = diemTN;
        this.diemCN = diemCN;
        this.diemTB = diemTB;
    }

    public String getTenCD() {
        return tenCD;
    }

    public void setTenCD(String tenCD) {
        this.tenCD = tenCD;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public float getDiemTN() {
        return diemTN;
    }

    public void setDiemTN(float diemTN) {
        this.diemTN = diemTN;
    }

    public float getDiemCN() {
        return diemCN;
    }

    public void setDiemCN(float diemCN) {
        this.diemCN = diemCN;
    }

    public float getDiemTB() {
        return diemTB;
    }

    public void setDiemTB(float diemTB) {
        this.diemTB = diemTB;
    }

    
    
    
    
}
