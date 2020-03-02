package videos.soap;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import videos.soap.entities.Video;

@WebService(serviceName = "VideoService")
public class VideoWebService {

    private final EntityManager em;

    public VideoWebService() {
        this.em = Persistence.createEntityManagerFactory("videosPU").createEntityManager();
    }

    @WebMethod(operationName = "addVideo")
    @Oneway
    public void addVideo(@WebParam(name = "video") Video video) {
        em.getTransaction().begin();
        em.persist(video);
        em.getTransaction().commit();
    }

    @WebMethod(operationName = "deleteVideo")
    @Oneway
    public void deleteVideo(@WebParam(name = "id") int id) {
        Video video = (Video) em.createNamedQuery("Video.findById").setParameter("id", id).getSingleResult();
        if (video != null) {
            em.getTransaction().begin();
            em.remove(video);
            em.getTransaction().commit();
        }
    }

    @WebMethod(operationName = "updateVideo")
    @Oneway
    public void updateVideo(@WebParam(name = "video") Video video) {
        em.getTransaction().begin();
        em.merge(video);
        em.getTransaction().commit();
    }

    @WebMethod(operationName = "getVideo")
    public Video getVideo(@WebParam(name = "id") int id) {
        Video video = (Video) em.createNamedQuery("Video.findById").setParameter("id", id).getSingleResult();
        if (video == null) {
            throw new EntityNotFoundException("Nie można znaleźć video o id: " + id);
        }
        byte[] file = video.getFile();
        String base64 = Base64.getEncoder().encodeToString(file);
        video.setFileBase64(base64);
        return video;
    }

    @WebMethod(operationName = "getVideos")
    public List<Video> getVideos() {
        List<Video> videosTmp = em.createNamedQuery("Video.findAll").getResultList();
        List<Video> videos = new ArrayList<>(videosTmp);
        videosTmp.forEach((video) -> {
            byte[] file = video.getFile();
            String base64 = Base64.getEncoder().encodeToString(file);
            video.setFileBase64(base64);
        });
        return videos;
    }
}
