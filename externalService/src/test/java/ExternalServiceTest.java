import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.junit.Test;

import com.dominikdorn.rest.utils.Utilities;

public class ExternalServiceTest {

    private static final Logger LOG = Logger.getLogger(ExternalServiceTest.class);

    private static final String EXTERNAL_SERVICE_ADDR = "86.112.115.1:8080";

    private static final String XML_CONTENT = "application/xml";

    private static final String JSON_CONTENT = "application/json";

    @Test
    public void shallSearchAndFindNothingXML() {

        if (Utilities.ping(EXTERNAL_SERVICE_ADDR)) {
            try {
                String response = this.getResponse("notexistingcriteria", XML_CONTENT);

                if (response == null) {
                    Assert.fail("No response!");
                } else {
                    // no items in the list
                    Assert.assertEquals(-1, response.indexOf("com.dominikdorn.tuwien.evs.rest.domain.Item"));
                }
            } catch (IOException e) {
                Assert.fail(e.getMessage());
            }

        } else {
            ExternalServiceTest.LOG.info("External Service at " + EXTERNAL_SERVICE_ADDR + " is down, skipping test");
        }
    }

    @Test
    public void shallSearchAndFindNothingJSON() {
        if (Utilities.ping(EXTERNAL_SERVICE_ADDR)) {
            try {
                String response = this.getResponse("Atom", JSON_CONTENT);

                if (response == null) {
                    Assert.fail("No response from service!");
                } else {
                    System.out.println(response);
                    // no items in the list
                    Assert.assertEquals(-1, response.indexOf("com.dominikdorn.tuwien.evs.rest.domain.Item"));
                }

            } catch (IOException e) {
                Assert.fail(e.getMessage());
            }

        } else {
            ExternalServiceTest.LOG.info("External Service at " + EXTERNAL_SERVICE_ADDR + " is down, skipping test");
        }
    }

    private String getResponse(String criteria, String content) throws IOException {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("criteria", criteria));

        final HttpClient client = new DefaultHttpClient();
        final HttpPost post = new HttpPost("http://" + EXTERNAL_SERVICE_ADDR + "/search");
        post.setHeader("Accept", content);

        try {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
            post.setEntity(entity);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        HttpResponse response = client.execute(post);

        if (response != null) {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                final StringBuffer resp = new StringBuffer();
                final InputStream in = entity.getContent();
                final BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String line;

                while ((line = reader.readLine()) != null) {
                    resp.append(line);
                }

                reader.close();

                return resp.toString();
            }
        } else {
            return null;
        }

        return null;
    }
}
