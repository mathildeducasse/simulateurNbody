package polytech.gateway.ctrl;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

import java.util.ArrayList;
import java.util.List;

@Path("/")
public class ParkingCtrl {

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Voiture> getDataVoteByCommune(@QueryParam("etage") String etage) {

        return new ArrayList<>();
    }

    @GET
    @Path("one")
    @Produces(MediaType.APPLICATION_JSON)
    public Voiture getDataVoteByIris(@QueryParam("immat") String immat) {
        return new Voiture();

    }
}
