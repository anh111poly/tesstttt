package poly.edu.untinity;

import poly.edu.model.NhanVien;

public class Auth {

    public static NhanVien user = null;

    public static void logout() {
        Auth.user = null;
    }

    public static boolean isLogin() {
        return Auth.user != null;
    }

    public static boolean isManager() {
        return Auth.isLogin() && user.isVaiTro();
    }

}
