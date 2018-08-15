package framework;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class VkApiUtils {
    private HttpURLConnection connection;
    private String requestTemplate = "https://api.vk.com/method/%s&v=%s";

    public HttpURLConnection getConnection(String request, String requesstType) {
        switch (requesstType) {
            case "GET":
                try {
                    sentGet(request);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "POST":
                try {
                    sentPost(request);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }

//        try {
//            URL url = new URL(request);
//
//            connection = (HttpURLConnection) url.openConnection();
//            connection.setRequestMethod(requesstType);
//            connection.setRequestProperty("Content-Type", "multipart/form-data");
//            //connection.setRequestProperty("Content-Length", String.valueOf(10000));
//            connection.connect();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        return connection;

    }

    public String getRequestTemplate() {
        return requestTemplate;
    }

    public String getResponse(HttpURLConnection connection){
//        InputStream in = null;
//        int status = 0;
//        try {
//            status = connection.getResponseCode();
//            if (status != HttpURLConnection.HTTP_OK) {
//                in = connection.getErrorStream();
//            } else {
//                in = connection.getInputStream();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return convertStreamToString(in);


        int responseCode = 0;
        StringBuffer response = new StringBuffer();
        try {
            responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            //in.close();
        }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response.toString();

    }


    public void sentGet(String requestUrl) throws IOException {

        URL obj = new URL(requestUrl);
        connection = (HttpURLConnection) obj.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "multipart/form-data");
        connection.connect();

    }


    public void sentPost(String requestUrl) throws IOException {

        URL url = new URL(requestUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setDoOutput(true);
        OutputStream os = con.getOutputStream();
        //os.write(requestParam.getBytes());
        os.flush();
        os.close();

        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://www.example.com");

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addTextBody("username", "John");
        builder.addTextBody("password", "pass");
        builder.addBinaryBody("file", new File("test.txt"),
                ContentType.APPLICATION_OCTET_STREAM, "file.ext");

        HttpEntity multipart = builder.build();
        httpPost.setEntity(multipart);

        CloseableHttpResponse response = client.execute(httpPost);
        //assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
        client.close();


    }

    public void getMultipartEntity(String url, File file){

        FileBody fileBody = new FileBody(file, ContentType.DEFAULT_BINARY);

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        builder.addPart("file", fileBody);
        HttpEntity entity = builder.build();

        HttpPost post = new HttpPost(url);
        post.setEntity(entity);

        HttpClient client = HttpClientBuilder.create().build();
//        try {
//            client.execute(post);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        HttpResponse response = null;
//        try {
//            response = client.execute(request);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        System.out.println("INFO >>> Response from API was: " + response.toString());


        HttpResponse response = null;
        try {
            response = client.execute(post);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int httpStatus = response.getStatusLine().getStatusCode();
        InputStream responseMsg = null;
        try {
            responseMsg = response.getEntity().getContent();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (httpStatus < 200 || httpStatus > 300) {
            try {
                throw new IOException("HTTP " + httpStatus + " - Error during upload of file: " + responseMsg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println("22222"+responseMsg);
    }
    }



