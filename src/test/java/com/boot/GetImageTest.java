
package com.boot;

import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

/**
 * @说明 从网络获取图片到本地
 * @author 崔素强
 * @version 1.0
 * @since
 */
public class GetImageTest {
    /**
     * 测试
     * @param args
     */
    public static void main(String[] args) {
        //String url = "http://www.baidu.com/img/baidu_sylogo1.gif";
        //String url = "https://www.gravatar.com/avatar/00000000000000000000000000000000?d=identicon&f=y";
        String url = "http://123.56.228.112:35678/a0364d31-6a82-4f3e-beed-b5c76e2cd288.png";
        byte[] btImg = getImageFromNetByUrl(url);
        if(null != btImg && btImg.length > 0){
            System.out.println("读取到：" + btImg.length + " 字节");
            String fileName = "百度.png";
            writeImageToDisk(btImg, fileName);
        }else{
            System.out.println("没有从该连接获得内容");
        }
        //base64编码
        /*BASE64Encoder encoder = new BASE64Encoder();
        String encodedText = encoder.encode(btImg);*/

        final Base64.Encoder encoder = Base64.getEncoder();
        String encodedText = encoder.encodeToString(btImg);

        System.out.println(encodedText);
    }

    /**
     * 根据地址获得数据的字节流
     * @param strUrl 网络连接地址
     * @return
     */
    public static byte[] getImageFromNetByUrl(String strUrl){
        try {
            URL url = new URL(strUrl);
            //InputStream inStream = url.openStream();
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5 * 1000);
            InputStream inStream = conn.getInputStream();//通过输入流获取图片数据

            byte[] btImg = readInputStream(inStream);//得到图片的二进制数据
            return btImg;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从输入流中获取数据
     * @param inStream 输入流
     * @return
     * @throws Exception
     */
    public static byte[] readInputStream(InputStream inStream) throws Exception{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while( (len=inStream.read(buffer)) != -1 ){
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }

    /**
     * 将图片写入到磁盘
     * @param img 图片数据流
     * @param fileName 文件保存时的名称
     */
    public static void writeImageToDisk(byte[] img, String fileName){
        try {
            File file = new File("C:\\" + fileName);
            FileOutputStream fops = new FileOutputStream(file);
            fops.write(img);
            fops.flush();
            fops.close();
            System.out.println("图片已经写入到C盘");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void test() throws IOException {
        File file = new File("D://2.png");
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while( (len=in.read(buffer)) != -1 ){
            outStream.write(buffer, 0, len);
        }
        in.close();
        byte[] bytes = outStream.toByteArray();

        final Base64.Encoder encoder = Base64.getEncoder();
        String encodedText = encoder.encodeToString(bytes);
        System.out.println(encodedText);

        final Base64.Decoder decoder = Base64.getDecoder();
        bytes = decoder.decode(new String(encodedText).getBytes());
        ByteArrayInputStream bin = new ByteArrayInputStream(bytes);

        //压缩文件
        // 构造Image对象;
        String tempFilePath = System.getProperty("java.io.tmpdir");
        if (tempFilePath != null) {
            File tempFile = new File(tempFilePath);
            if (!tempFile.exists()) {
                tempFile.mkdirs();
            }
        }
        Image img = ImageIO.read(bin);
        // 得到源图宽
        int width = img.getWidth(null);
        // 得到源图长
        int height = img.getHeight(null);
        System.out.println(width);
        System.out.println(height);
    }
}