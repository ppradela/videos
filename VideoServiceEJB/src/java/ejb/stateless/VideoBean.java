package ejb.stateless;

import entities.Video;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

@Stateless
public class VideoBean implements VideoBeanLocal {

    @PersistenceContext(unitName = "VideoServiceEJBPU")
    EntityManager em;

    @Override
    public void addVideo(Video video) {
        em.persist(video);
    }

    @Override
    public void deleteVideo(int id) {
        Video video = (Video) em.createNamedQuery("Video.findById").setParameter("id", id).getSingleResult();
        if (video != null) {
            em.remove(video);
        }
    }

    @Override
    public void updateVideo(Video video) {
        em.merge(video);
    }

    @Override
    public Video getVideo(int id) {
        Video video = (Video) em.createNamedQuery("Video.findById").setParameter("id", id).getSingleResult();
        if (video == null) {
            throw new EntityNotFoundException("Nie można znaleźć video o id: " + id);
        }
        byte[] file = video.getFile();
        String base64 = Base64.getEncoder().encodeToString(file);
        video.setFileBase64(base64);
        return video;
    }

    @Override
    public List<Video> getVideos() {
        List<Video> videosTmp = em.createNamedQuery("Video.findAll").getResultList();
        List<Video> videos = new ArrayList<>();
        for (Video video : videosTmp) {
            byte[] file = video.getFile();
            String base64 = Base64.getEncoder().encodeToString(file);
            video.setFileBase64(base64);
            videos.add(video);
        }
        return videos;
    }
}
