package webservice;

import ejb.stateless.VideoBeanLocal;
import entities.Video;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(serviceName = "VideoWebService", portName = "VideoWebServicePort")
@Stateless()
public class VideoWebService {

    @EJB
    private VideoBeanLocal ejbRef;

    @WebMethod(operationName = "addVideo")
    @Oneway
    public void addVideo(@WebParam(name = "video") Video video) {
        ejbRef.addVideo(video);
    }

    @WebMethod(operationName = "deleteVideo")
    @Oneway
    public void deleteVideo(@WebParam(name = "id") int id) {
        ejbRef.deleteVideo(id);
    }

    @WebMethod(operationName = "updateVideo")
    @Oneway
    public void updateVideo(@WebParam(name = "video") Video video) {
        ejbRef.updateVideo(video);
    }

    @WebMethod(operationName = "getVideo")
    public Video getVideo(@WebParam(name = "id") int id) {
        return ejbRef.getVideo(id);
    }

    @WebMethod(operationName = "getVideos")
    public List<Video> getVideos() {
        return ejbRef.getVideos();
    }

}
