package com.innovandoapps.library.kernel.utils;
/***************************************************************************************************************
 * autor : Marcos Ramirez
 * Created by desarrollo on 19/04/16.                                                                       **
 * ***********************************************************************************************************
 *************************************************************************************************************/
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Clase utilitario con funciones de ficheros e imagenes
 * @author Marcos Ramirez
 */
public class ImageFileUtil {

    ///Path para almacenar imagenes scaneadas
    public static final String PATH_GALLERY_SCAN="/DCIM/ssm_gallery_scan/";
    public static final String PATH_GALLERY = "/DCIM/ssm_gallery/";
    ///Extension de las imagenes
    private static final String IMG_EXT = ".jpg";

    /**
     * Convierte una imagen en BASE64.
     * realiza la compresion de la imagen
     * @param imagenpath URI de la imagen en el telefono
     * @return String en BASE64
     */
    public static String getStringImage(String imagenpath){

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inDither = true;


        Bitmap bitmap = BitmapFactory.decodeFile(imagenpath,options);
        Log.i("Largo",bitmap.getWidth()+"");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        if(bitmap.getWidth()>1280){
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        }else{
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, baos);
        }

        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    /**
     * Convierte un fichero en BASE64
     * @param fileName URI del Fichero
     * @return String en BASE64
     */
    public static String encodeFileToBase64Binary(String fileName)  {
        File file = new File(fileName);
        byte[] bytes = fileToByte(file);
        String encodedString = Base64.encodeToString(bytes, Base64.DEFAULT);
        return encodedString;
    }

    /**
     * Convierte un fichero a un array de bytes
     * @param file URI del fichero
     * @return Array de byte[]
     */
    private static byte[] fileToByte(File file){
        FileInputStream fileInputStream=null;
        byte[] bFile = new byte[(int) file.length()];
        try {
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bFile);
            fileInputStream.close();
            return bFile;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  null;
    }

    /**
     * funcion Boleana para determinar si un fichero existe
     * @param filepath URI del Fichero
     * @return TRUE si el archivo existe; FALSE caso contrario
     */
    public static boolean fileExist(String filepath){
        File file = new File(filepath);
        return file.exists();
    }

    /**
     * Genera un .zip apartir de una lista de ficheros
     * @param files  Array de ficheros a comprimir
     * @param zipFile nombre de zip a enviar
     */
    public static void comprimir(String[] files, String zipFile){
        try {
            BufferedInputStream origin = null;
            ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFile)));
            byte data[] = new byte[1024];
            for (int i = 0; i < files.length; i++) {
                FileInputStream fi = new FileInputStream(files[i]);
                origin = new BufferedInputStream(fi, 1024);
                ZipEntry entry = new ZipEntry(files[i].substring(files[i].lastIndexOf("/") + 1));
                out.putNextEntry(entry);
                int count;
                while ((count = origin.read(data, 0, 1024)) != -1) {
                    out.write(data, 0, count);
                }
            }
            out.close();
            origin.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo crea un archivo fisico de imagen apartir de bitmap
     * @param bitmap  Bitmap a tranformar
     * @param cedula  Numero de cedula para generar el nombre
     * @param nro     orden de indice de la imagen
     * @return String path del archivo generado
     */
    public static String bitmapToFile(Bitmap bitmap,String cedula,int nro){
        File folder=new File(Environment.getExternalStorageDirectory()+PATH_GALLERY_SCAN);
        if(!folder.exists()){
            folder.mkdirs();
        }
        File imagen =new File(folder, System.currentTimeMillis()+"SCAN"+IMG_EXT);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        byte[] bitmapdata = bos.toByteArray();

        try {
            FileOutputStream fos = new FileOutputStream(imagen);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
            return imagen.getAbsolutePath();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * extrae el nombre de imagen del path
     * @param path String de la direccion de la imagen
     * @return Cadena nombre de la imagen
     */
    public static String getNameImagen(String path){
        int indice=path.lastIndexOf("/");
        return path.substring(indice + 1);
    }

    /**
     * Metodo copia una imagen de la galeria a la galeria del ssm
     * @param pathimagen String de la imagen original
     * @return String de la imagen resultado
     */
    public static String copyImagen(String pathimagen){
        File source = new File(pathimagen);
        File folder=new File(Environment.getExternalStorageDirectory()+PATH_GALLERY);
        if(!folder.exists()){
            folder.mkdirs();
        }
        File destino =new File(folder,System.currentTimeMillis()+IMG_EXT);
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {
            inChannel=new FileInputStream(source).getChannel();
            outChannel=new FileOutputStream(destino).getChannel();
            inChannel.transferTo(0, inChannel.size(), outChannel);
            inChannel.close();
            outChannel.close();
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return destino.getAbsolutePath();
    }

    /**
     * Recupear el path de una imagen apartir de un Uri del dispositivo movil
     * @param context    Contexto del Activity
     * @param contentUri Uri del recurso
     * @return String path del recurso
     */
    public static String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            if(cursor.moveToFirst()){
                String result =  cursor.getString(column_index);
                if(result == null){
                    result = contentUri.getPath();
                    String id = result.split(":")[1];
                    String sel = MediaStore.Images.Media._ID + "=?";
                    cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, proj, sel, new String[]{ id }, null);
                    column_index = cursor.getColumnIndex(proj[0]);
                    if (cursor.moveToFirst()) {
                        result = cursor.getString(column_index);
                    }
                }
                return result;
            }else{
                return "";
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    /**
     * Obtien el hash de un archivo
     * @param file archivo a obtener su hash
     * @return String del hash del archivo
     */
    public static String getHashOfFile(File file){
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            FileInputStream fis = new FileInputStream(file);
            byte[] byteArray = new byte[1024];
            int bytesCount = 0;
            while ((bytesCount = fis.read(byteArray)) != -1) {
                messageDigest.update(byteArray, 0, bytesCount);
            }
            fis.close();
            byte[] bytes = messageDigest.digest();
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++){
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static List<String> getListOfFile(String path){
        List<String> arr_files = new ArrayList<>();

        File folder = new File(path);
        if(folder.exists()){
            File[] listOfFiles = folder.listFiles();
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    arr_files.add(listOfFiles[i].getAbsolutePath());
                }
            }
        }

        return arr_files;
    }

    public static String getDateOfFile(File file){
        Date lastModDate = new Date(file.lastModified());
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return df.format(lastModDate);
    }
}