package io.helidon.productos;



import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.hash.Hashing;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ByteArrayEntity;
import org.tomitribe.auth.signatures.MissingRequiredHeaderException;
import org.tomitribe.auth.signatures.PEM;
import org.tomitribe.auth.signatures.Signature;
import org.tomitribe.auth.signatures.Signer;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Key;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.http.entity.StringEntity;

import java.util.Collections;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonException;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonArray;

import io.helidon.common.http.Http;
import io.helidon.config.Config;
import io.helidon.webserver.Routing;
import io.helidon.webserver.ServerRequest;
import io.helidon.webserver.ServerResponse;
import io.helidon.webserver.Service;

import java.io.*;
import java.net.*; 
/**
 * This example creates a {@link RequestSigner}, then prints out the Authorization header
 * that is inserted into the HttpGet object.
 *
 * 


 * apiKey is the identifier for a key uploaded through the console.
 * privateKeyFilename is the location of your private key (that matches the uploaded public key for apiKey).
 * 

 *
 * The signed HttpGet request is not executed, since instanceId does not map to a real instance.
 */
public class ProductosService implements Service
{
	public class ProductoData {
	  public String id;
	  public String nombre;
	  public double precio;
	  public String imagen;
	  
	  ProductoData() {
	  }
	}
	
    public ProductoData[] productos;
	
	public ProductosService(Config config) {
   //   productos = [{"id": "1", "nombre": "Producto 1", "imagen": "producto1.png", "precio": 100},
	//               {"id": "2", "nombre": "Producto 2", "imagen": "producto2.png", "precio": 50}];
    }
	
	/**
     * A service registers itself by updating the routine rules.
     * @param rules the routing rules.
     */
    @Override
    public void update(Routing.Rules rules) {
        rules
            .get("/catalogo", this::catalogoHandler)
			.get("/{id}", this::productoHandler)
			.post("/", this::crearProductoHandler);
    }
    private void catalogoHandler(ServerRequest request,
                                  ServerResponse response) {
        //JsonObject returnObject = atpInstancias();
	 	//response.send(returnObject);
		
    }
	private void productoHandler(ServerRequest request,
                                  ServerResponse response) {
        String id = request.path().param("id");
		int encontre = -1;
		
		for (int i=0; i < productos.length; i++) {
			if (productos[i].id.equals(id)) {
				encontre = i;
			}
		}	
		if (encontre == -1) {
			JsonObject jsonErrorObject = Json.createObjectBuilder()
                    .add("error", "No hay producto")
                    .build();
            response.status(Http.Status.BAD_REQUEST_400)
                    .send(jsonErrorObject);
        }
		else {
		 JsonObject returnObject = Json.createObjectBuilder()
								  .add("id", id)
								  .add("nombre", productos[encontre].nombre)
								  .add("imagen", productos[encontre].imagen)
								  .add("precio", productos[encontre].precio)
								  .build();								   
		 response.send(returnObject);
		}
    }
	 
	private void crearProductoHandler(ServerRequest request,
                                  ServerResponse response) {
		
		request.content().as(JsonObject.class)
            .thenAccept(jo -> crearProductoFromJson(jo, response))
            .exceptionally(ex -> { 
			      JsonObject jsonErrorObject = Json.createObjectBuilder()
                    .add("error", "No hay producto")
                    .build();
                  response.status(Http.Status.BAD_REQUEST_400)
                    .send(jsonErrorObject);
				 return null;	
			}); 

	 	//response.send(returnObject);		
    }	 

    private void crearProductoFromJson(JsonObject jo, ServerResponse response) {

        if (!jo.containsKey("producto")) {
            JsonObject jsonErrorObject = Json.createObjectBuilder()
                    .add("error", "No hay producto")
                    .build();
            response.status(Http.Status.BAD_REQUEST_400)
                    .send(jsonErrorObject);
            return;
        }

        //producto.set(jo.getString("producto"));
        response.status(Http.Status.NO_CONTENT_204).send();
    }

	
	
	public static void main(String[] args) throws UnsupportedEncodingException
    {
       	
    }
}