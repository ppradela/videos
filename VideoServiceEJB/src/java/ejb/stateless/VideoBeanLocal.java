package ejb.stateless;

import entities.Video;
import java.util.List;
import javax.ejb.Local;

@Local
public interface VideoBeanLocal {

    void addVideo(Video video);

    void deleteVideo(int id);

    void updateVideo(Video video);

    Video getVideo(int id);

    List<Video> getVideos();
}
