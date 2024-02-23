package iotserver;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Date;

public class IoTServer {
    public void sendToServer(String url, String site, String sensor, String value) {
        try {

            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", "IoTDevices");

            String jsonObject = "{" +
                    "\"sensor\":[ {" +
                    "\"siteID\": \"" + site + "\"," +
                    "\"sensorID\": \"" + sensor + "\"," +
                    "\"timestamp\" : \"" + System.currentTimeMillis() + "\"," +
                    "\"value\": \"" + value + "\"" +
                    "}" +
                    "]}";

            // For POST only - START
            con.setDoOutput(true);
            OutputStream os = con.getOutputStream();
            os.write(jsonObject.getBytes());
            os.flush();
            os.close();
            // For POST only - END

            int responseCode = con.getResponseCode();
            System.out.println("POST Response Code :: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) { // success
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // print result
                System.out.println(response.toString());
            } else {
                System.out.println("POST request did not work.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }
    }
}
