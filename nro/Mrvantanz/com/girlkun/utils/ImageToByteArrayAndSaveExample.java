
package com.girlkun.utils;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ImageToByteArrayAndSaveExample {
    public static void main(int id) {
        String imagePath = "D:\\CBRO2_1.3_2\\CBRO2_1.3\\CBRO2\\data\\girlkun\\effect\\x2\\img\\ImgEffect_" + id +".png"; // Đường dẫn đến tệp ảnh của bạn

        try {
            byte[] imageData = convertImageToByteArray(imagePath);
            String outputPath = "D:\\CBRO2_1.3_2\\CBRO2_1.3\\CBRO2\\data\\girlkun\\effdata\\x2\\"+id; // Tên tệp đầu ra (không có đuôi định dạng)

            saveByteArrayToFile(imageData, outputPath);
            System.out.println("Đã lưu tệp thành công.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static byte[] convertImageToByteArray(String imagePath) throws IOException {
        Path path = Paths.get(imagePath);
        return Files.readAllBytes(path);
    }

    private static void saveByteArrayToFile(byte[] data, String outputPath) throws IOException {
        FileOutputStream fos = new FileOutputStream(outputPath);
        fos.write(data);
        fos.close();
    }
}
