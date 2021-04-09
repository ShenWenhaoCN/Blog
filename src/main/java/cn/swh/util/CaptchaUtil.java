package cn.swh.util;

import org.thymeleaf.standard.expression.IStandardConversionService;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @ClassName CaptchaUtil
 * @Description: 验证码工具类
 * @Author: shenwenhao
 * @Date: 2020/11/17 20:47
 * @Version: 1.0
 **/
public class CaptchaUtil {

    /*private static Random random = new Random();

    *//**
     * @Title  getRandColor
     * @Author  shenwenhao
     * @Description: 获得随机颜色
     * @Date: 21:06 2020/11/17
     * @Param: [fc, bc]
     * @return: java.awt.Color
     **//*
    private static Color getRandColor(int fc, int bc) {
        fc = Math.min(fc, 255);
        bc = Math.min(fc, 255);
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    *//**
     * @Title  getRandomRgb
     * @Author  shenwenhao
     * @Description: 返回rgb颜色
     * @Date: 21:08 2020/11/17
     * @Param: []
     * @return: int[]
     **//*
    private static int[] getRandomRgb() {
        int[] rgb = new int[3];
        for(int i = 0; i < 3; i++) {
            rgb[i] = random.nextInt(255);
        }
        return rgb;
    }

    *//**
     * @Title  getRandomIntColor
     * @Author  获得随机的单个颜色
     * @Description: TODO
     * @Date: 21:34 2020/11/17
     * @Param: []
     * @return: int
     **//*
    private static int getRandomIntColor() {
        int[] rgb = getRandomRgb();
        int color = 0;
        for(int c : rgb) {
            color = color << 8;
            color = color | c;
        }
        return color;
    }

    *//**
     * @Title  shear
     * @Author  图像变形
     * @Description: TODO
     * @Date: 9:24 2020/11/18
     * @Param: [g, w1, h1, color]
     * @return: void
     **//*
    private static void shear(Graphics g, int w1, int h1, Color color) {
        shearX(g, w1, h1, color);
        shearY(g, w1, h1, color);
    }

    *//**
     * @Title  shearX
     * @Author  shenwenhao
     * @Description: 图像x方向变形
     * @Date: 9:35 2020/11/18
     * @Param: [g, w1, h1, color]
     * @return: void
     **//*
    private static void shearX(Graphics g, int w1, int h1, Color color) {

        int period = random.nextInt(2);

        boolean borderGap = true;
        int frames = 1;
        int phase = random.nextInt(2);

        for(int i = 0; i < h1; i++) {
            double d = (double) (period >> 1)
                    * Math.sin((double) i / (double) period + (6.2831853071795862D * (double) phase) / (double) frames);
            g.copyArea(0, i, w1, 1, (int) d, 0);
            if (borderGap) {
                g.setColor(color);
                g.drawLine((int) d, i, 0, i);
                g.drawLine((int) d + w1, i, w1, i);
            }
        }
    }

    *//**
     * @Title  shearY
     * @Author  shenwenhao
     * @Description: 图像y方向变形
     * @Date: 9:37 2020/11/18
     * @Param: [g, w1, h1, color]
     * @return: void
     **//*
    private static void shearY(Graphics g, int w1, int h1, Color color) {

        int period = random.nextInt(40) + 10; // 50;

        boolean borderGap = true;
        int frames = 20;
        int phase = 7;
        for (int i = 0; i < w1; i++) {
            double d = (double) (period >> 1)
                    * Math.sin((double) i / (double) period + (6.2831853071795862D * (double) phase) / (double) frames);
            g.copyArea(i, 0, 1, h1, 0, (int) d);
            if (borderGap) {
                g.setColor(color);
                g.drawLine(i, (int) d, i, 0);
                g.drawLine(i, (int) d + h1, i, h1);
            }
        }
    }

    *//**
     * @Title  getStringRandom
     * @Author  生成指定长度的随机数字和字母
     * @Description: TODO
     * @Date: 9:54 2020/11/18
     * @Param: [length]
     * @return: java.lang.String
     **//*
    public static String getStringRandom(int length) {
        String val = "";

        Random random = new Random();
        String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";

        switch (charOrNum) {
            case "char" :
                int tmp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char)(random.nextInt(26) + tmp);
                break;
            case "num" :
                val += String.valueOf(random.nextInt(10));
                break;
        }

        return val;
    }

    *//**
     * @Title  imageToBase64
     * @Author  shenwenhao
     * @Description: Base64编码的验证码图片
     * @Date: 9:55 2020/11/18
     * @Param: [w, h, code]
     * @return: java.lang.String
     **//*
    public static String imageToBase64(int w, int h, String code) throws Exception {
        int verifySize = code.length();
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Random rand = new Random();
        Graphics2D g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Color[] colors = new Color[5];
        Color[] colorSpaces = new Color[] { Color.WHITE, Color.CYAN, Color.GRAY, Color.LIGHT_GRAY, Color.MAGENTA,
                Color.ORANGE, Color.PINK, Color.YELLOW };
        float[] fractions = new float[colors.length];
        for (int i = 0; i < colors.length; i++) {
            colors[i] = colorSpaces[rand.nextInt(colorSpaces.length)];
            fractions[i] = rand.nextFloat();
        }
        Arrays.sort(fractions);

        g2.setColor(Color.GRAY);// 设置边框色
        g2.fillRect(0, 0, w, h);

        Color c = getRandColor(200, 250);
        g2.setColor(c);// 设置背景色
        g2.fillRect(0, 2, w, h - 4);

        // 绘制干扰线
        Random random = new Random();
        g2.setColor(getRandColor(160, 200));// 设置线条的颜色
        for (int i = 0; i < 20; i++) {
            int x = random.nextInt(w - 1);
            int y = random.nextInt(h - 1);
            int xl = random.nextInt(6) + 1;
            int yl = random.nextInt(12) + 1;
            g2.drawLine(x, y, x + xl + 40, y + yl + 20);
        }

        // 添加噪点
        float yawpRate = 0.05f;// 噪声率
        int area = (int) (yawpRate * w * h);
        for (int i = 0; i < area; i++) {
            int x = random.nextInt(w);
            int y = random.nextInt(h);
            int rgb = getRandomIntColor();
            image.setRGB(x, y, rgb);
        }

        shear(g2, w, h, c);// 使图片扭曲

        g2.setColor(getRandColor(100, 160));
        int fontSize = h - 4;
        Font font = new Font("Arial", Font.ITALIC, fontSize);
        g2.setFont(font);
        char[] chars = code.toCharArray();
        for (int i = 0; i < verifySize; i++) {
            AffineTransform affine = new AffineTransform();
            affine.setToRotation(Math.PI / 4 * rand.nextDouble() * (rand.nextBoolean() ? 1 : -1),
                    (w / verifySize) * i + fontSize / 2, h / 2);
            g2.setTransform(affine);
            g2.drawChars(chars, i, 1, ((w - 10) / verifySize) * i + 5, h / 2 + fontSize / 2 - 10);
        }
        g2.dispose();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", baos);
        return new BASE64Encoder().encode(baos.toByteArray());
    }

    *//**
     * @Title  base64ToImage
     * @Author  shenwenhao
     * @Description: 将Base64位编码的图片进行解码，并保存到指定目录
     * @Date: 9:57 2020/11/18
     * @Param: [base64]
     * @return: void
     **//*
    public static void base64ToImage(String base64) {
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            File file = new File("D:/test.jpg");
            FileOutputStream write = new FileOutputStream(file);
            byte[] decoderBytes = decoder.decodeBuffer(base64);
            write.write(decoderBytes);
            write.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    *//**
     * @Title  getCaptcha
     * @Author  shenwenhao
     * @Description: 获得验证码
     * @Date: 9:58 2020/11/18
     * @Param: [length]
     * @return: java.util.Map<java.lang.String,java.lang.String>
     **//*
    public static Map<String,String> getCaptcha(Integer length)throws Exception{
        Map<String,String> returnMap = new HashMap<>();
        String str = getStringRandom(length);
        returnMap.put("str" ,str);
        returnMap.put("image" , imageToBase64(120, 40,str) );
        return returnMap;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(imageToBase64(120, 40, getStringRandom(4)));
        base64ToImage(imageToBase64(120, 40, getStringRandom(4)));
    }*/


    private static Random random = new Random();

    /**
     * @Title getRandColor
     * @Author Allan Deng
     * @Description  获得随机颜色
     * @Date 12:57 2020/2/10
     * @Param [fc, bc]
     * @return java.awt.Color
     **/
    private static Color getRandColor(int fc, int bc) {
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    /**
     * @Title getRandomIntColor
     * @Author Allan Deng
     * @Description  获得随机的单个颜色
     * @Date 12:57 2020/2/10
     * @Param []
     * @return int
     **/
    private static int getRandomIntColor() {
        int[] rgb = getRandomRgb();
        int color = 0;
        for (int c : rgb) {
            color = color << 8;
            color = color | c;
        }
        return color;
    }

    /**
     * @Title getRandomRgb
     * @Author Allan Deng
     * @Description  返回rgb颜色
     * @Date 12:58 2020/2/10
     * @Param []
     * @return int[]
     **/
    private static int[] getRandomRgb() {
        int[] rgb = new int[3];
        for (int i = 0; i < 3; i++) {
            rgb[i] = random.nextInt(255);
        }
        return rgb;
    }

    /**
     * @Title shear
     * @Author Allan Deng
     * @Description  图像变形
     * @Date 12:58 2020/2/10
     * @Param [g, w1, h1, color]
     * @return void
     **/
    private static void shear(Graphics g, int w1, int h1, Color color) {
        shearX(g, w1, h1, color);
        shearY(g, w1, h1, color);
    }

    /**
     * @Title shearX
     * @Author Allan Deng
     * @Description  图像x方向变形
     * @Date 12:58 2020/2/10
     * @Param [g, w1, h1, color]
     * @return void
     **/
    private static void shearX(Graphics g, int w1, int h1, Color color) {

        int period = random.nextInt(2);

        boolean borderGap = true;
        int frames = 1;
        int phase = random.nextInt(2);

        for (int i = 0; i < h1; i++) {
            double d = (double) (period >> 1)
                    * Math.sin((double) i / (double) period + (6.2831853071795862D * (double) phase) / (double) frames);
            g.copyArea(0, i, w1, 1, (int) d, 0);
            if (borderGap) {
                g.setColor(color);
                g.drawLine((int) d, i, 0, i);
                g.drawLine((int) d + w1, i, w1, i);
            }
        }

    }

    /**
     * @Title shearY
     * @Author Allan Deng
     * @Description 图像y方向变形
     * @Date 12:58 2020/2/10
     * @Param [g, w1, h1, color]
     * @return void
     **/
    private static void shearY(Graphics g, int w1, int h1, Color color) {

        int period = random.nextInt(40) + 10; // 50;

        boolean borderGap = true;
        int frames = 20;
        int phase = 7;
        for (int i = 0; i < w1; i++) {
            double d = (double) (period >> 1)
                    * Math.sin((double) i / (double) period + (6.2831853071795862D * (double) phase) / (double) frames);
            g.copyArea(i, 0, 1, h1, 0, (int) d);
            if (borderGap) {
                g.setColor(color);
                g.drawLine(i, (int) d, i, 0);
                g.drawLine(i, (int) d + h1, i, h1);
            }

        }
    }

    /**
     * @Title getStringRandom
     * @Author Allan Deng
     * @Description 生成指定长度的随机数字和字母
     * @Date 12:59 2020/2/10
     * @Param [length]
     * @return java.lang.String
     **/
    public static String getStringRandom(int length) {
        String val = "";
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            switch (charOrNum) {
                case "char":
                    int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                    val += (char) (random.nextInt(26) + temp);
                    break;
                case "num":
                    val += String.valueOf(random.nextInt(10));
                    break;
            }
        }
        return val;
    }

    /**
     * @Title imageToBase64
     * @Author Allan Deng
     * @Description Base64编码的验证码图片
     * @Date 12:59 2020/2/10
     * @Param [w, h, code]
     * @return java.lang.String
     **/
    public static String imageToBase64(int w, int h, String code) throws Exception {
        int verifySize = code.length();
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Random rand = new Random();
        Graphics2D g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Color[] colors = new Color[5];
        Color[] colorSpaces = new Color[] { Color.WHITE, Color.CYAN, Color.GRAY, Color.LIGHT_GRAY, Color.MAGENTA,
                Color.ORANGE, Color.PINK, Color.YELLOW };
        float[] fractions = new float[colors.length];
        for (int i = 0; i < colors.length; i++) {
            colors[i] = colorSpaces[rand.nextInt(colorSpaces.length)];
            fractions[i] = rand.nextFloat();
        }
        Arrays.sort(fractions);

        g2.setColor(Color.GRAY);// 设置边框色
        g2.fillRect(0, 0, w, h);

        Color c = getRandColor(200, 250);
        g2.setColor(c);// 设置背景色
        g2.fillRect(0, 2, w, h - 4);

        // 绘制干扰线
        Random random = new Random();
        g2.setColor(getRandColor(160, 200));// 设置线条的颜色
        for (int i = 0; i < 20; i++) {
            int x = random.nextInt(w - 1);
            int y = random.nextInt(h - 1);
            int xl = random.nextInt(6) + 1;
            int yl = random.nextInt(12) + 1;
            g2.drawLine(x, y, x + xl + 40, y + yl + 20);
        }

        // 添加噪点
        float yawpRate = 0.05f;// 噪声率
        int area = (int) (yawpRate * w * h);
        for (int i = 0; i < area; i++) {
            int x = random.nextInt(w);
            int y = random.nextInt(h);
            int rgb = getRandomIntColor();
            image.setRGB(x, y, rgb);
        }

        shear(g2, w, h, c);// 使图片扭曲

        g2.setColor(getRandColor(100, 160));
        int fontSize = h - 4;
        Font font = new Font("Arial", Font.ITALIC, fontSize);
        g2.setFont(font);
        char[] chars = code.toCharArray();
        for (int i = 0; i < verifySize; i++) {
            AffineTransform affine = new AffineTransform();
            affine.setToRotation(Math.PI / 4 * rand.nextDouble() * (rand.nextBoolean() ? 1 : -1),
                    (w / verifySize) * i + fontSize / 2, h / 2);
            g2.setTransform(affine);
            g2.drawChars(chars, i, 1, ((w - 10) / verifySize) * i + 5, h / 2 + fontSize / 2 - 10);
        }
        g2.dispose();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", baos);
        return new BASE64Encoder().encode(baos.toByteArray());
    }

    /**
     * @Title base64ToImage
     * @Author Allan Deng
     * @Description 将Base64位编码的图片进行解码，并保存到指定目录
     * @Date 13:00 2020/2/10
     * @Param [base64]
     * @return void
     **/
    public static void base64ToImage(String base64) {
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            File file = new File("D:/test.jpg");
            FileOutputStream write = new FileOutputStream(file);
            byte[] decoderBytes = decoder.decodeBuffer(base64);
            write.write(decoderBytes);
            write.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Title getCaptcha
     * @Author Allan Deng
     * @Description  获得验证码
     * @Date 13:00 2020/2/10
     * @Param [length]
     * @return java.util.Map<java.lang.String,java.lang.String>
     **/
    public static Map<String,String> getCaptcha(Integer length)throws Exception{
        Map<String,String> returnMap = new HashMap<>();
        String str = getStringRandom(length);
        returnMap.put("str" ,str);
        returnMap.put("image" , imageToBase64(120, 40,str) );
        return returnMap;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(imageToBase64(120, 40, getStringRandom(4)));
        base64ToImage(imageToBase64(120, 40, getStringRandom(4)));
    }

}
