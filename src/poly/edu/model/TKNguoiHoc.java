
package poly.edu.model;

import java.util.Date;

public class TKNguoiHoc {
    
    private int Nam;
    private int soLuongNH ;
    private Date dauTien;
    private Date cuoiCung;

    public TKNguoiHoc() {
    }

    public TKNguoiHoc(int Nam, int soLuongNH, Date dauTien, Date cuoiCung) {
        this.Nam = Nam;
        this.soLuongNH = soLuongNH;
        this.dauTien = dauTien;
        this.cuoiCung = cuoiCung;
    }

    public int getNam() {
        return Nam;
    }

    public void setNam(int Nam) {
        this.Nam = Nam;
    }

    public int getSoLuongNH() {
        return soLuongNH;
    }

    public void setSoLuongNH(int soLuongNH) {
        this.soLuongNH = soLuongNH;
    }

    public Date getDauTien() {
        return dauTien;
    }

    public void setDauTien(Date dauTien) {
        this.dauTien = dauTien;
    }

    public Date getCuoiCung() {
        return cuoiCung;
    }

    public void setCuoiCung(Date cuoiCung) {
        this.cuoiCung = cuoiCung;
    }
        
    
}
