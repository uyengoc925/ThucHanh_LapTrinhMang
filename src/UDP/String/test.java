package UDP.String;

import java.io.IOException;

public class test {
    public static long chuyenNP(String a){
        StringBuilder k = new StringBuilder(a);
        k.reverse();

        int n = k.length();
        long res = 0;
        for(int i =0; i<n; i++){
            if(k.charAt(i) == '1'){
                res+= 1L<<i;
            }
        }
        return res;
    }
    public static void main(String[] args)throws IOException{
        String b1 ="0100011111001101";
        String b2 ="1101000111110101";
        System.out.println(chuyenNP(b1) + chuyenNP(b2));
        Double a = 44654873.44546547835454;
        System.out.println(Math.round(a*100)/100);
        System.out.printf("%.3f%n", a);
    }

}
