package org.locationtech.spatial4j.io.jackson;

import java.io.IOException;

import org.junit.Test;
import org.locationtech.spatial4j.context.jts.JtsSpatialContext;
import org.locationtech.spatial4j.io.jackson.ShapesAsGeoJSONModule;
import org.locationtech.spatial4j.shape.RandomizedShapeTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import io.jeo.geom.GeomBuilder;

public class SimpleJacksonTest extends RandomizedShapeTest {

  public SimpleJacksonTest() {
    super(JtsSpatialContext.GEO);
  }
  
  @Test
  public void testReadWrite() throws IOException {
    
    GeomBuilder builder = new GeomBuilder();
    
    ObjectWithGeometry obj = new ObjectWithGeometry();
    obj.name = "Hello";
    obj.shape = randomPointIn(ctx.getWorldBounds());
    obj.geo = null; //
    
    ObjectMapper mapper = new ObjectMapper();
    mapper.enable(SerializationFeature.INDENT_OUTPUT);
    mapper.registerModule(new ShapesAsGeoJSONModule());
//    mapper.registerModule(new ShapesAsWKTModule());
    
    String json = mapper.writeValueAsString(obj);
    
    System.out.println( json );
    
    ObjectWithGeometry out = mapper.readValue(json, ObjectWithGeometry.class);

    System.out.println( ">> AFTER <<" );
    System.out.println( mapper.writeValueAsString(out) );
  }
}
