public class Book {
   private int maSach;
   private String tenSach;
   private String tacGia;
   private double donGia;

   public Book(int var1, String var2, String var3, double var4) {
      this.maSach = var1;
      this.tenSach = var2;
      this.tacGia = var3;
      this.donGia = var4;
   }

   public int getMaSach() {
      return this.maSach;
   }

   public String getTenSach() {
      return this.tenSach;
   }

   public String getTacGia() {
      return this.tacGia;
   }

   public double getDonGia() {
      return this.donGia;
   }

   public void setTenSach(String var1) {
      this.tenSach = var1;
   }

   public void setTacGia(String var1) {
      this.tacGia = var1;
   }

   public void setDonGia(double var1) {
      this.donGia = var1;
   }

   public void hienThi() {
      System.out.println("Mã sách: " + this.maSach + " | Tên sách: " + this.tenSach + " | Tác giả: " + this.tacGia + " | Đơn giá: " + this.donGia);
   }
}
