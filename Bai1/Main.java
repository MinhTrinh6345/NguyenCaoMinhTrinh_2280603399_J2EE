import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Main {
   static Scanner sc;
   static ArrayList<Book> danhSach;

   public Main() {
   }

   public static void main(String[] var0) {
      int var1;
      do {
         menu();
         var1 = Integer.parseInt(sc.nextLine());
         switch (var1) {
            case 0:
               System.out.println("Kết thúc chương trình.");
               break;
            case 1:
               themSach();
               break;
            case 2:
               xoaSach();
               break;
            case 3:
               suaSach();
               break;
            case 4:
               xuatDanhSach();
               break;
            case 5:
               timSachLapTrinh();
               break;
            case 6:
               laySachTheoGia();
               break;
            case 7:
               timSachTheoTacGia();
               break;
            default:
               System.out.println("Chọn sai!");
         }
      } while(var1 != 0);

   }

   static void menu() {
      System.out.println("\n===== BÀI 1: QUẢN LÝ SÁCH =====");
      System.out.println("1. Thêm 1 cuốn sách");
      System.out.println("2. Xóa 1 cuốn sách");
      System.out.println("3. Thay đổi cuốn sách");
      System.out.println("4. Xuất thông tin tất cả sách");
      System.out.println("5. Tìm sách có tiêu đề chứa 'Lập trình'");
      System.out.println("6. Lấy tối đa K sách có giá <= P");
      System.out.println("7. Nhập danh sách tác giả và xuất sách");
      System.out.println("0. Thoát");
      System.out.print("Chọn: ");
   }

   static void themSach() {
      System.out.print("Mã sách: ");
      int var0 = Integer.parseInt(sc.nextLine());
      System.out.print("Tên sách: ");
      String var1 = sc.nextLine();
      System.out.print("Tác giả: ");
      String var2 = sc.nextLine();
      System.out.print("Đơn giá: ");
      double var3 = Double.parseDouble(sc.nextLine());
      danhSach.add(new Book(var0, var1, var2, var3));
      System.out.println("Đã thêm sách!");
   }

   static void xoaSach() {
      System.out.print("Nhập mã sách cần xóa: ");
      int var0 = Integer.parseInt(sc.nextLine());
      danhSach.removeIf((var1) -> {
         return var1.getMaSach() == var0;
      });
      System.out.println("Đã xóa (nếu tồn tại).");
   }

   static void suaSach() {
      System.out.print("Nhập mã sách cần sửa: ");
      int var0 = Integer.parseInt(sc.nextLine());
      Iterator var1 = danhSach.iterator();

      Book var2;
      do {
         if (!var1.hasNext()) {
            System.out.println("Không tìm thấy sách!");
            return;
         }

         var2 = (Book)var1.next();
      } while(var2.getMaSach() != var0);

      System.out.print("Tên mới: ");
      var2.setTenSach(sc.nextLine());
      System.out.print("Tác giả mới: ");
      var2.setTacGia(sc.nextLine());
      System.out.print("Giá mới: ");
      var2.setDonGia(Double.parseDouble(sc.nextLine()));
      System.out.println("Đã cập nhật!");
   }

   static void xuatDanhSach() {
      if (danhSach.isEmpty()) {
         System.out.println("Danh sách rỗng!");
      } else {
         danhSach.forEach(Book::hienThi);
      }
   }

   static void timSachLapTrinh() {
      Iterator var0 = danhSach.iterator();

      while(var0.hasNext()) {
         Book var1 = (Book)var0.next();
         if (var1.getTenSach().toLowerCase().contains("lập trình")) {
            var1.hienThi();
         }
      }

   }

   static void laySachTheoGia() {
      System.out.print("Nhập K: ");
      int var0 = Integer.parseInt(sc.nextLine());
      System.out.print("Nhập giá P: ");
      double var1 = Double.parseDouble(sc.nextLine());
      int var3 = 0;
      Iterator var4 = danhSach.iterator();

      while(var4.hasNext()) {
         Book var5 = (Book)var4.next();
         if (var5.getDonGia() <= var1) {
            var5.hienThi();
            ++var3;
            if (var3 == var0) {
               break;
            }
         }
      }

   }

   static void timSachTheoTacGia() {
      System.out.print("Nhập danh sách tác giả (cách nhau dấu phẩy): ");
      String[] var0 = sc.nextLine().split(",");
      Iterator var1 = danhSach.iterator();

      while(var1.hasNext()) {
         Book var2 = (Book)var1.next();
         String[] var3 = var0;
         int var4 = var0.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            String var6 = var3[var5];
            if (var2.getTacGia().equalsIgnoreCase(var6.trim())) {
               var2.hienThi();
            }
         }
      }

   }

   static {
      sc = new Scanner(System.in);
      danhSach = new ArrayList();
   }
}
