package poly.edu.model;

public class HocVien {

    private int maHV;
    private KhoaHoc KhoaHoc;
    private NguoiHoc NguoiHoc;
    private float diem;

    public HocVien() {
    }

    public HocVien(int maHV, float diem) {
        this.maHV = maHV;
        this.diem = diem;
    }

    public HocVien(int maHV, KhoaHoc KhoaHoc, NguoiHoc NguoiHoc, float diem) {
        this.maHV = maHV;
        this.KhoaHoc = KhoaHoc;
        this.NguoiHoc = NguoiHoc;
        this.diem = diem;
    }

    public int getMaHV() {
        return maHV;
    }

    public void setMaHV(int maHV) {
        this.maHV = maHV;
    }

    public KhoaHoc getKhoaHoc() {
        return KhoaHoc;
    }

    public void setKhoaHoc(KhoaHoc KhoaHoc) {
        this.KhoaHoc = KhoaHoc;
    }

    public NguoiHoc getNguoiHoc() {
        return NguoiHoc;
    }

    public void setNguoiHoc(NguoiHoc NguoiHoc) {
        this.NguoiHoc = NguoiHoc;
    }

    public float getDiem() {
        return diem;
    }

    public void setDiem(float diem) {
        this.diem = diem;
    }

    public String xepLoai() {
        String xepLoai = "";
        if (diem >= 9 && diem <= 10) {
            xepLoai = "Xuất xắc";
        } else if (diem >= 8) {
            xepLoai = "Giỏi";
        } else if (diem >= 6.5) {
            xepLoai = "Khá";
        } else if (diem >= 5) {
            xepLoai = "Trung bình";
        } else if (diem >= 3) {
            xepLoai = "Yếu";
        } else {
            xepLoai = "Kém";
        }

        return xepLoai;
    }

}
