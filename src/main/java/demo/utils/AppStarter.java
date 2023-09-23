package demo.utils;
import jakarta.ws.rs.core.UriBuilder;
import org.glassfish.jersey.netty.httpserver.NettyHttpContainerProvider;
import org.glassfish.jersey.server.ResourceConfig;
import java.net.URI;

public class AppStarter {

    public static void runThisApp(){
        URI uri = UriBuilder.fromUri(Constants.URL)
                .port(Constants.PORT)
                .build();
        ResourceConfig config = new ResourceConfig().packages(Constants.PACKS);
        NettyHttpContainerProvider.createServer(uri,config,false);
        System.out.println("Server starts work...");
    }
}
