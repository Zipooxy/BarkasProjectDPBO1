/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import Exception.InvalidRegistrationException;
import Exception.LoginException;
import Exception.UserNotFoundException;
import Model.User;
import java.util.HashMap;
import java.util.Map;
import Utils.AppUtils;

public class UserService {
    private Map<Integer, User> userMap = new HashMap<>();
    private User activeUser; // User yang sedang login/aktif
    private boolean isLoggedIn; // Flag untuk status login

    public UserService() {
        this.isLoggedIn = false;
    }

    public User getActiveUser() {
        return activeUser;
    }

    public boolean isIsLoggedIn() {
        return isLoggedIn;
    }
    
    public void registerUser(User user) {
        userMap.put(user.getId(), user);
    }

    public User loginUser(int userId, String username, String password) throws UserNotFoundException, LoginException {
        User user = userMap.get(userId);
        if (user == null) {
            throw new UserNotFoundException("User dengan ID " + userId + " tidak ditemukan.");
        } else if ((!username.equals(user.getUsername()) || (!password.equals(user.getPassword())))) {
                throw new LoginException("Verifikasi username atau password gagal. Login ditolak.");
            }
        return user;
    }

    public void logout() {
        if (isLoggedIn) {
            System.out.println("Logout berhasil untuk user: " + activeUser.getUsername());
            activeUser = null;
            isLoggedIn = false;
        } else {
            System.out.println("Tidak ada user yang sedang login.");
        }
    }

    // Method registrasi dengan exception handling
    public User inputDataRegistrasi() throws InvalidRegistrationException {
        // Input ID User
        int id;
        while (true) {
            System.out.print("Masukkan ID Pengguna Baru (Harus Unik): ");
            id = AppUtils.getIntInput();
            // Cek apakah ID sudah digunakan
            if (!userMap.containsKey(id)) {
                break;
            }
            System.out.println("ID sudah digunakan. Silakan masukkan ID lain.");
        }

        // Input Nama dengan validasi
        String username;
        while (true) {
            System.out.print("Masukkan username Pengguna Baru: ");
            username = AppUtils.getStringInput().trim();
            if (username.length() >= 3) {
                break;
            }
            System.out.println("Username harus minimal 3 karakter!");
        }
        
        String password;
        while (true) {
            System.out.print("Masukkan Password Pengguna Baru: ");
            password = AppUtils.getStringInput().trim();
            if (password.length() >= 4) {
                break;
            }
            System.out.println("Password harus minimal 4 karakter!");
        }

        // Input Saldo dengan validasi
        double saldo;
        while (true) {
            System.out.print("Masukkan Saldo Awal (minimal Rp 10.000): ");
            saldo = AppUtils.getDoubleInput();

            if (saldo >= 10000) {
                break;
            }
            System.out.println("Saldo minimal Rp 10.000!");
        }

        // Validasi final
        if (username.isEmpty() || saldo < 10000) {
            throw new InvalidRegistrationException("Registrasi gagal. Periksa kembali data Anda.");
        }

        // Membuat user baru
        return new User(id, username, password, saldo);
    }

    // Method login dengan exception handling
    public User inputLogin() throws LoginException {
        try {
            int userId = AppUtils.inputInt("Masukkan ID User untuk login: ");
            // Konfirmasi login tambahan jika diperlukan
            System.out.println("Konfirmasi Login");
            System.out.print("\nUsername: ");
            String usernameVerifikasi = AppUtils.getStringInput();
            System.out.print("\nPassword: ");
            String passwordVerifikasi = AppUtils.getStringInput();
            User userLogin = loginUser(userId, usernameVerifikasi, passwordVerifikasi);
            
            isLoggedIn = true;
            activeUser = userLogin;
            return userLogin;

        } catch (UserNotFoundException e) {
            throw new LoginException(e.getMessage());
        }
    }

    // Method untuk menampilkan menu utama
    public void displayMenuMasukUser() {
        while (true) {
            System.out.println("\n===== MENU UTAMA =====");
            System.out.println("1. Registrasi User Baru");
            System.out.println("2. Login");
            System.out.println("3. Keluar Aplikasi");
            
            try {
                int pilihan = AppUtils.inputInt("Pilih menu (1-3): ");

                switch (pilihan) {
                    case 1:
                        try {
                            User newUser = inputDataRegistrasi();
                            registerUser(newUser);
                            System.out.println("Registrasi berhasil!");
                        } catch (InvalidRegistrationException e) {
                            System.out.println("Registrasi gagal: " + e.getMessage());
                        }
                        break;
                    case 2:
                        try {
                            User userLogin = inputLogin();
                            System.out.println("Login berhasil!");
                            displayUserMenu();
                        } catch (LoginException e) {
                            System.out.println("Login gagal: " + e.getMessage());
                        }
                        break;
                    case 3:
                        System.out.println("Terima kasih. Sampai jumpa!");
                        return;
                    default:
                        System.out.println("Pilihan tidak valid. Silakan coba lagi.");
                }
            } catch (Exception e) {
                System.out.println("Terjadi kesalahan: " + e.getMessage());
            }
        }
    }

    // Method untuk menampilkan menu user
    public void displayUserMenu() {
        System.out.println("\n===== MENU USER =====");
        System.out.println("1. Daftar Menjadi Member");
        System.out.println("2. Tambah Saldo");
        System.out.println("3. Logout");
            
        try {
            int pilihan = AppUtils.inputInt("Pilih menu (1-3): ");

            switch (pilihan) {
                case 1:
                    daftarMember();
                    break;
                case 2:
                    tambahSaldo();
                    break;
                case 3:
                    logout();
                    break;
                default:
                    System.out.println("Pilihan tidak valid. Silakan coba lagi.");
                }
            } catch (Exception e) {
                System.out.println("Terjadi kesalahan: " + e.getMessage());
            }
    }

    // Method untuk menambah saldo
    public void tambahSaldo() {
        if (!isLoggedIn || activeUser == null) {
            System.out.println("Tidak ada user aktif.");
            return;
        }

        try {
            System.out.println("Masukkan jumlah saldo yang ingin ditambahkan: ");
            double jumlah = AppUtils.getDoubleInput();

            activeUser.tambahSaldo(jumlah);
            System.out.println("Saldo berhasil ditambahkan.");
            System.out.printf("Saldo baru Anda: Rp %.0f", activeUser.getSaldo());
        } catch (Exception e) {
            System.out.println("Gagal menambah saldo: " + e.getMessage());
        }
    }

    public void daftarMember() {
        if (activeUser != null) {
            activeUser.menjadiMember();
        } else {
            System.out.println("Tidak ada user aktif.");
        }
    }
}

