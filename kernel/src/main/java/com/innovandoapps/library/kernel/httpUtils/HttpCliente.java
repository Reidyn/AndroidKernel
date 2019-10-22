package com.innovandoapps.library.kernel.httpUtils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
 * @author Marcos Ramirez
 * Clase Singleton para realiza request http atravez del objeto HttpURLConnection
 */
public class HttpCliente {

    private URL url;
    private HttpURLConnection connection;
    private static HttpCliente mInstance;

    /**
     * Constructor
     */
    public HttpCliente() {}

    /**
     * Creador de instancia
     * @return Instancia HttpCliente
     */
    public static HttpCliente getInstance() {
        if (mInstance == null) {
            mInstance = new HttpCliente();
        }
        return mInstance;
    }

    /**
     * Realizar Request Http
     * @param url   URL del webservice
     * @param sendData Objeto para envio de datos
     */
    /**
     * Realizar Request Http
     * @param url   URL del webservice
     * @return String resultado del request
     */
    public String sendDataPost(String url,Map<String, String> parametros){
        String respuesta = httpConnetar(parametros,url,0);
        return respuesta;
    }

    public String sendDataGet(String url,Map<String, String> parametros){
        String respuesta = httpConnetar(parametros,url,1);
        return respuesta;
    }

    public String sendDataPut(String url,Map<String, String> parametros){
        String respuesta = httpConnetar(parametros,url,2);
        return respuesta;
    }

    /**
     * Request http
     * @param parametros   estructura clave/valor de parametros de envio
     * @param urlWebserver URL a realizar el request
     * @param metodo       Metod de envio GET/POST
     * @return String resultante
     */
    private String httpConnetar(Map<String, String> parametros, String urlWebserver,int metodo){
        String response="";
        try {
            url = new URL(urlWebserver);
            connection = (HttpURLConnection)url.openConnection();
            switch (metodo) {
                case 0:
                    connection.setRequestMethod("POST");
                    break;
                case 1:
                    connection.setRequestMethod("GET");
                    break;
                case 2:
                    connection.setRequestMethod("PUT");
                break;
            }

            connection.setUseCaches(false);
            connection.setReadTimeout(15000);
            connection.setConnectTimeout(30000);

            if(parametros != null){
                connection.setDoInput(true);
                String paramString = urlEncodeUTF8(parametros);
                connection.setRequestProperty("Content-Length", "" + Integer.toString(paramString.getBytes().length));
                DataOutputStream wr = new DataOutputStream (connection.getOutputStream ());
                wr.writeBytes (paramString);
                wr.flush ();
                wr.close ();
            }


            InputStream is = connection.getInputStream();
            response=parseResponse(is);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public String httpUpFile(Map<String, String> parametros, String urlWebserver,File file){
        String response="";
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        try {
            url = new URL(urlWebserver);
            connection = (HttpURLConnection)url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);
            connection.setRequestProperty("uploaded_file",file.getName());

            DataOutputStream dos = new DataOutputStream( connection.getOutputStream() );
            for(Map.Entry<String,String>entry:parametros.entrySet()){
                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\""+entry.getKey()+"\"" + lineEnd);
                dos.writeBytes("Content-Type: text/plain; charset=UTF-8" + lineEnd);
                dos.writeBytes("Content-Length: " + entry.getValue().length() + lineEnd);
                dos.writeBytes(lineEnd);
                dos.writeBytes(entry.getValue());
                dos.writeBytes(lineEnd);
            }
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"archivo\";filename=\"" + file.getName() +"\"" + lineEnd);
            dos.writeBytes("Content-Length: " + file.length() + lineEnd);
            dos.writeBytes(lineEnd);

            FileInputStream fileInputStream = new FileInputStream(file);
            int bytesAvailable = fileInputStream.available();
            int maxBufferSize = 3072;
            int bufferSize = Math.min(bytesAvailable, maxBufferSize);
            byte[] buffer = new byte[bufferSize];
            int bytesRead = fileInputStream.read(buffer, 0, bufferSize);

            while (bytesRead > 0){
                dos.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            }

            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            fileInputStream.close();
            dos.flush();

            InputStream is = connection.getInputStream();
            response=parseResponse(is);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }


    /**
     * Encode de los parametros de envio
     * @param parametros parametros
     * @return Cadena en formato url UTF-8
     */
    private String urlEncodeUTF8(Map<String, String>parametros){
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<?,?> entry : parametros.entrySet()) {
            if (sb.length() > 0) {
                sb.append("&");
            }
            try {
                sb.append(String.format("%s=%s",
                        URLEncoder.encode(entry.getKey().toString(), "UTF-8"),
                        URLEncoder.encode(entry.getValue().toString(), "UTF-8")
                ));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (NullPointerException e){
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    /**
     * Extrae resultado de un InputStream
     * @param in Input de datos
     * @return String resultante
     */
    private String parseResponse(InputStream in){
        StringBuilder sb = new StringBuilder();;
        try {
            BufferedReader reader=new BufferedReader(new InputStreamReader(in,"iso-8859-1"),8);
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            in.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }
}
