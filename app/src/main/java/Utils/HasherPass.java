//package Utils;
//
//import android.util.Log;
//
//import org.apache.commons.codec.digest.DigestUtils;
//
//import java.nio.charset.StandardCharsets;
//import java.security.MessageDigest;
//
//public class HasherPass {
//
//    public static String calcularSHA256(String input) {
//        try {
//            MessageDigest digest = MessageDigest.getInstance("SHA-256");
//            byte[] hashBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));
//            StringBuilder hexString = new StringBuilder();
//            for (byte b : hashBytes) {
//                String hex = Integer.toHexString(0xff & b);
//                if (hex.length() == 1) {
//                    hexString.append('0');
//                }
//                hexString.append(hex);
//            }
//            return hexString.toString();
//        } catch (Exception e) {
//            Log.e("Hashing", "Error al calcular el hash: " + e.getMessage());
//            return null;
//        }
//    }
//}
