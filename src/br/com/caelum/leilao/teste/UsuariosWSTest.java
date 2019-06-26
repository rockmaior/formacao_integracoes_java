package br.com.caelum.leilao.teste;

import static com.jayway.restassured.RestAssured.given;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.path.xml.XmlPath;

import br.com.caelum.leilao.modelo.Leilao;
import br.com.caelum.leilao.modelo.Usuario;

public class UsuariosWSTest {
	
	private Usuario mauricio;
	private Usuario guilherme;
	
	@Before
	public void setUp() {
		mauricio = new Usuario(1L,"Mauricio Aniche","mauricio.aniche@caelum.com.br");
		guilherme = new Usuario(2L,"Guilherme Silveira","guilherme.silveira@caelum.com.br");
	}
	
	@Test
	public void deveRetornarListaDeUsuarios() {
		XmlPath path = given().header("Accept","application/xml")
				.get("/usuarios?_format=xml").andReturn().xmlPath();
		
        List<Usuario> usuarios = path.getList("list.usuario", Usuario.class);
		
        Assert.assertEquals(mauricio,usuarios.get(0));
		Assert.assertEquals(guilherme,usuarios.get(1));
	}
	
	@Test
	public void deveRetornarUsuarioPeloId() {
		JsonPath path = given()
				.parameter("usuario.id", 1)
				.header("Accept","application/json")
				.get("/usuarios/show")
				.andReturn()
				.jsonPath();
		
		Usuario usuario = path.getObject("usuario" , Usuario.class);

		Assert.assertEquals(mauricio, usuario);

	}
	
	@Test
	public void deveRetornarLeilaoPeloId() {
		
		JsonPath path = given()
				.parameter("leilao.id", 1)
				.header("Accept","application/json")
				.get("/leiloes/show")
				.andReturn()
				.jsonPath();
		
		Leilao leilao = path.getObject("leilao", Leilao.class);
		
		Assert.assertEquals("Geladeira", leilao.getNome());
		
		Leilao esperado = new Leilao(1L, "Geladeira", 800.0, mauricio, false);
		Assert.assertEquals(esperado, leilao);
	}
}

//public class UsuariosWSTest {
//	@Test
//	public void deveRetornarListaDeUsuarios() {
//		RestAssured.baseURI = "http://localhost:8080";
//		RequestSpecification httpRequest = RestAssured.given();
//
//		XmlPath path = httpRequest.given().header("Accept", "application/xml").get("/usuarios").andReturn().xmlPath();
//
//		List<Usuario> usuarios = path.getList("list.usuario", Usuario.class);
//
//
//		Usuario usuario1 = path.getObject("list.usuario[0]", Usuario.class);
//		Usuario usuario2 = path.getObject("list.usuario[1]", Usuario.class);
//
//		Usuario esperado1 = new Usuario(1L, "Mauricio Aniche", "mauricio.aniche@caelum.com.br");
//		Usuario esperado2 = new Usuario(2L, "Guilherme Silveira", "guilherme.silveira@caelum.com.br");
//
//		Assert.assertEquals(esperado1, usuario1);
//		Assert.assertEquals(esperado2, usuario2);
//
//	}
//}
