package sa02;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.databene.contiperf.junit.ContiPerfRule;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import categories.IntegrationTest;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jdo.Producto;
import jdo.Usuario;
import jdo.VentaProducto;

@Category(IntegrationTest.class)
public class ventaProductosResource {
	
	@Rule public ContiPerfRule rule = new ContiPerfRule();
	private HttpServer server;
    private WebTarget appTarget;
    private Client c;
    
    @Before
    public void setUp() throws Exception {
    	
    	server = Main.startServer();
        // create the client
        Client c = ClientBuilder.newClient();
        // uncomment the following line if you want to enable
        // support for JSON in the client (you also have to uncomment
        // dependency on jersey-media-json module in pom.xml and Main.startServer())
        // --
        // c.configuration().enable(new org.glassfish.jersey.media.json.JsonJaxbFeature());
        appTarget = c.target(Main.BASE_URI);
    }
    
    @After
    public void tearDown() throws Exception {
        server.stop();
    }
    
    @Test
    public void testgetVentaProducto() {
    	WebTarget ventaProductosTarget = appTarget.path("ventasproductos");
    	WebTarget ventaProductosUsuarioTarget = ventaProductosTarget.path("usuario").queryParam("usuario", "unai");
    	
    	VentaProducto vp1 = new VentaProducto("Manzana", "unai", 2);
    	
    	GenericType<List<VentaProducto>> genericType = new GenericType<List<VentaProducto>>() {};
		List<VentaProducto> producto = ventaProductosUsuarioTarget.request(MediaType.APPLICATION_JSON).get(genericType);
    	
    	assertEquals("Manzana", vp1.getProducto());
    }
    
//    @Test
//    public void testGetProductosNom() {
//    	WebTarget ventaProductosTarget = appTarget.path("ventasproductos");
//    	WebTarget ventaProductosCodTarget = ventaProductosTarget.path("cod").queryParam("codigo", "1");
//    	
//    	Producto prod1 = new Producto("Lechuga", "Muy sana", 2.4, "unai", 6);
//    	
//    	 GenericType<List<VentaProducto>> genericType = new GenericType<List<VentaProducto>>() {};
//		 List<VentaProducto> producto = ventaProductosCodTarget.request(MediaType.APPLICATION_JSON).get(genericType);
//		 
//		 assertEquals(prod1.getNombre(), producto.get(0).getProducto());
//    }
    
    @Test
    public void testGetProductosUser() {
    	WebTarget ventaProductosTarget = appTarget.path("ventasproductos");
    	WebTarget ventaProductosUsuarioTarget = ventaProductosTarget.path("usuario").queryParam("usuario", "unai");
    	
    	VentaProducto vp1 = new VentaProducto("Manzana", "unai", 2);
    	
    	GenericType<List<VentaProducto>> genericType = new GenericType<List<VentaProducto>>() {};
		List<VentaProducto> producto = ventaProductosUsuarioTarget.request(MediaType.APPLICATION_JSON).get(genericType);

    	assertEquals(vp1.getUsuario(), producto.get(0).getUsuario());
    }
    
    @Test
    public void testEliminarProducto() {
    	WebTarget ventaProductosTarget = appTarget.path("ventasproductos");
    	WebTarget ventaProductosElimTarget = ventaProductosTarget.path("elim");
    	List<String> listVentaProd = Arrays.asList("unai");
    	ventaProductosElimTarget.request().post(Entity.entity(listVentaProd, MediaType.APPLICATION_JSON));
    	
    	WebTarget ventaProductosUsuarioTarget = ventaProductosTarget.path("usuario").queryParam("usuario", "unai");
    	GenericType<List<VentaProducto>> genericType = new GenericType<List<VentaProducto>>() {};
		List<VentaProducto> producto = ventaProductosUsuarioTarget.request(MediaType.APPLICATION_JSON).get(genericType);
		
		assertTrue(producto.isEmpty());
    }
//    @Test
//    public void testInsertarProducto() {
//    	WebTarget ventaProductosTarget = appTarget.path("ventasproductos");
//    	WebTarget ventaProductosInsTarget = ventaProductosTarget.path("ins");
//    	List<String> listVentaProd = Arrays.asList("Lechuga", "unai", "4");
//    	ventaProductosInsTarget.request().post(Entity.entity(listVentaProd, MediaType.APPLICATION_JSON));
//    	
//    	WebTarget ventaProductosUsuarioTarget = ventaProductosTarget.path("usuario").queryParam("usuario", "unai");
//    	GenericType<List<VentaProducto>> genericType = new GenericType<List<VentaProducto>>() {};
//		List<VentaProducto> producto = ventaProductosUsuarioTarget.request(MediaType.APPLICATION_JSON).get(genericType);
//		
//		assertEquals(listVentaProd.get(0), producto.get(0).getProducto());
//    }
    
	/*
	 * @Test public void testsetCantidad() { VentaProducto vp1 = new
	 * VentaProducto(); WebTarget ventaProductosTarget =
	 * appTarget.path("ventasproductos"); WebTarget ventaProductosElimTarget =
	 * ventaProductosTarget.path("elim"); List<String> listVentaProd =
	 * Arrays.asList("unai");
	 * ventaProductosElimTarget.request().post(Entity.entity(listVentaProd,
	 * MediaType.APPLICATION_JSON));
	 * 
	 * WebTarget ventaProductosUsuarioTarget =
	 * ventaProductosTarget.path("usuario").queryParam("usuario", "unai");
	 * GenericType<List<VentaProducto>> genericType = new
	 * GenericType<List<VentaProducto>>() {}; List<VentaProducto> producto =
	 * ventaProductosUsuarioTarget.request(MediaType.APPLICATION_JSON).get(
	 * genericType);
	 * 
	 * assertEquals(5, vp1.getCantidad()); }
	 */
}
