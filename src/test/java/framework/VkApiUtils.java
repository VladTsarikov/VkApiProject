package framework;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.sikuli.basics.FileManager.convertStreamToString;

public class VkApiUtils {
    private HttpURLConnection connection;
    private String requestTemplate = "https://api.vk.com/method/%s&v=%s";

    public HttpURLConnection getConnection(String requess, String requesstType){
        try {
            URL url = new URL(requess);

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(requesstType);
            connection.setRequestProperty("Content-Type", "multipart/form-data");
            //connection.setRequestProperty("Content-Length", String.valueOf(10000));
            connection.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return connection;

    }

    public String getRequestTemplate() {
        return requestTemplate;
    }

    public String getResponse(HttpURLConnection connection){
        InputStream in = null;
        int status = 0;
        try {
            status = connection.getResponseCode();
            if (status != HttpURLConnection.HTTP_OK) {
                in = connection.getErrorStream();
            } else {
                in = connection.getInputStream();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return convertStreamToString(in);
    }

}
