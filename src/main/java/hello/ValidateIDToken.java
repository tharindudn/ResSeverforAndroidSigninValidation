package hello;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;


/**
 * Created by tharindu on 8/3/17.
 */
public class ValidateIDToken {
    private final String CLIENT_ID="392223441152-r2nursricdn7mf1p47q2miqpdtig2l3k.apps.googleusercontent.com";

    public void sendRequest(String idtoken){

        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost("https://www.googleapis.com/oauth2/v3/tokeninfo?id_token="+idtoken);


        HttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int statusCode = response.getStatusLine().getStatusCode();
        if (response.getStatusLine().getStatusCode() == 200) {
            System.out.println("Validate successful...");
        }else{
            System.out.println("Validate fail...");
        }

    }
    public void validateUsingGoogleAPI(String idtoken) {
        JsonFactory jsonFactory = new JacksonFactory();
        NetHttpTransport transport = new NetHttpTransport();
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(Collections.singletonList(CLIENT_ID))
                // Or, if multiple clients access the backend:
                //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
                .build();


        GoogleIdToken idToken = null;
        try {
            idToken = verifier.verify(idtoken);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (idToken != null) {
            GoogleIdToken.Payload payload = idToken.getPayload();

            // Print user identifier
            String userId = payload.getSubject();
            System.out.println("User ID: " + userId);

            // Get profile information from payload
            String email = payload.getEmail();
            boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
            String name = (String) payload.get("name");
            String pictureUrl = (String) payload.get("picture");
            String locale = (String) payload.get("locale");
            String familyName = (String) payload.get("family_name");
            String givenName = (String) payload.get("given_name");

            // Use or store profile information
            // ...

        } else {
            System.out.println("Invalid ID token.");
        }
    }
}
