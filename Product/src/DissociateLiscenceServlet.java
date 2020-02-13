

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Servlet implementation class DissociateLiscenceServlet
 */
@WebServlet("/DissociateLiscenceServlet")
public class DissociateLiscenceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DissociateLiscenceServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());

		String appid=request.getParameter("appid");
		String serialNumber=request.getParameter("serialNumber");
		String fullUrl=buildUrl(appid,serialNumber);
		PrintWriter pw=response.getWriter();
		pw.println("success");
		try {
			if(thereinDb(appid,serialNumber)!=0){
				parseUtil(fullUrl);
			}
			else{
				System.out.println("cannot dissociate user, since the user is not associated with any appid");
			}
				
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public String buildUrl(String appid,String serialNumber){
		String token="eyJleHBEYXRlIjoiMjAyMC0wOS0xNVQyMzoxNToxNi0wNzAwIiwidG9rZW4iOiI5c3RtRTcvbWFuckxJZExqcU1DeXpjaXM2S1BxZ0p3blVha1JMditVN0swdlF1RTQvWDIwdkNYeXd2U3pwZXpZQk05d3B0M0Z0bVYrSExXYldlcVRWdUhmaWxzL050ajZ1OTgzdktPckFjbkNBOHlvN0VDV09IQ1o3bm1kSDFMK09zVzdJeThUVlZ5MkNWS0JXZGVOZEE9PSIsIm9yZ05hbWUiOiJOb3ZlbGwifQ==";
	
		String baseUrl="https://vpp.itunes.apple.com/mdm/manageVPPLicensesByAdamIdSrv?sToken="+token;
		
		String fullUrl=baseUrl+"&disassociateSerialNumbers="+serialNumber+"&adamIdStr="+appid+"&pricingParam=STDQ".trim();
		
		return fullUrl;
	}
	public void parseUtil(String fullUrl) throws Exception{
		
	CloseableHttpClient httpclient = HttpClients.createDefault();
	    try {
	        HttpGet httpget = new HttpGet(fullUrl);
	        System.out.println("Executing request " + httpget.getRequestLine());

	        // Create a custom response handler
	        ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

	            public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
	                int status = response.getStatusLine().getStatusCode();
	                if (status >= 200 && status < 300) {
	                    HttpEntity entity = response.getEntity();
	                    //System.out.println(entity);

	                    return entity != null ? EntityUtils.toString(entity) : null;
	                } else {
	                    throw new ClientProtocolException("Unexpected response status: " + status);
	                }
	            }

	        };
	        String responseBody = httpclient.execute(httpget, responseHandler);
	        System.out.println("----------------------------------------");
	        System.out.println(responseBody);
	        System.out.println("-----------------");
	        //calling the function to parse the response
			changeInDatabase(parse(new JSONObject(responseBody)));
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
	        httpclient.close();
		}
	}
	
	public DatabaseMapper parse(JSONObject responseBody) throws JSONException{
		String appid=responseBody.getString("adamIdStr");
		int isAssociated=responseBody.getInt("status");
		JSONArray associations=responseBody.getJSONArray("associations");
		JSONObject pair=associations.getJSONObject(0);
		String serialNumber=pair.getString("serialNumber");
		return new DatabaseMapper(appid,serialNumber,isAssociated);
	}
	public int thereinDb(String appId,String serialNumber) throws Exception{
		int countRows=0;
		Class.forName("org.postgresql.Driver");
        Connection con= DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/jso", "postgres", "vki31d97");
        if(con!=null){
        	
        	System.out.println("CONNECTED");
        	System.out.println("getting query ready");
        	PreparedStatement selection=con.prepareStatement("SELECT * FROM appusers WHERE \"appId\"= ? and \"serialNumber\"=?");
        	selection.setString(1,appId);
        	selection.setString(2,serialNumber);
        	ResultSet rs=selection.executeQuery();
        	while(rs.next()){
        		countRows++;
        	}
        }
        else{
        	System.out.println("unable to connect");
        }
        con.close();
        return countRows;
	}
	public void changeInDatabase(DatabaseMapper data) throws Exception{
		Class.forName("org.postgresql.Driver");
        Connection con= DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/jso", "postgres", "vki31d97");
        if(con!=null){
        	System.out.println("CONNECTED");
        	System.out.println("getting query ready");
        	if(thereinDb(data.getAppId(),data.getSerialNumber())!=0){
        	PreparedStatement change=con.prepareStatement("UPDATE appusers SET \"isAssociated\"=? WHERE \"isAssociated\"=?");
        	change.setInt(1, -1);
        	change.setInt(2, 0);
        	System.out.println(change.executeUpdate()+"rows affected");
            System.out.println("user associated to appid");
            }
        	else{
        		System.out.println("Already dissociated");
        	}
        }
        else{
        	System.out.println("unable to connect");
        }
        con.close();
	}



}
