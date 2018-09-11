package framework;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.*;
import org.apache.http.impl.client.*;
import java.io.*;
import java.net.*;

public class VkApiUtils {

    private HttpURLConnection connection;
    private int responseCode = 0;
    private final static String GET_REQUEST_TYPE = "GET";
    private final static String CONTENT_TYPE_NAME = "Content-Type";
    private final static String PHOTO_CONTENT_TYPE_NAME = "image/jpeg";
    private final static String PHOTO_BINARY_BODY_NAME = "photo";
    private final static String INPUT_STREAM_ENCODING = "UTF-8";
    private final static String GET_CONTENT_TYPE = "multipart/form-data";
    private final static String VK_API_REQUEST_VERSION = "5.80";
    private final static String REPLACEMENT = "";
    private final static String VK_API_REQUEST_URL = "https://api.vk.com/method/";
    private CloseableHttpResponse response = null;
    private CloseableHttpClient httpClient = HttpClients.createDefault();
    private InputStream inputStream = null;
    private String results = null;

    public String sendGetRequest(String requestUrl){
        try {
            URL url = new URL(requestUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(GET_REQUEST_TYPE);
            connection.setRequestProperty(CONTENT_TYPE_NAME, GET_CONTENT_TYPE);
            connection.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return obtainGetResponse(connection);
    }

    public String sendPostPhotoRequest(String url, String filePath) {
            HttpPost httpPost = new HttpPost(url);
            File fileToUpload = new File(filePath);
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addBinaryBody(PHOTO_BINARY_BODY_NAME, fileToUpload, ContentType.create(PHOTO_CONTENT_TYPE_NAME), filePath);
            HttpEntity reqEntity = builder.build();
            httpPost.setEntity(reqEntity);
            getPostResponse(httpPost);
            return results;
    }

    private String obtainGetResponse(HttpURLConnection connection){
        StringBuffer response = new StringBuffer();
        try {
            responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                while ((inputLine = bufferedReader.readLine()) != null) {
                    response.append(inputLine);
                }
                bufferedReader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response.toString();
    }

    private void getPostResponse(HttpPost httppost){
        try {
            response = httpClient.execute(httppost);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                inputStream = entity.getContent();
                StringWriter writer = new StringWriter();
                IOUtils.copy(inputStream, writer, INPUT_STREAM_ENCODING);
                results = writer.toString();
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (response != null) {
                    response.close();
                }
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getVkApiRequestVersion() {
        return VK_API_REQUEST_VERSION;
    }

    public static String getVkApiRequestUrl() {
        return VK_API_REQUEST_URL;
    }

    public static String getReplacement() { return REPLACEMENT; }
}






