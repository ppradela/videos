package videos.ejb;

import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.xml.ws.WebServiceRef;
import videos.ejb.soap.client.Video;
import videos.ejb.soap.client.VideoService;
import videos.ejb.soap.client.VideoWebService;

@Stateless
@LocalBean
public class VideoBean {

    @WebServiceRef(wsdlLocation = "http://localhost:8080/videos-soap/VideoService?wsdl")
    private VideoService service;

    public void deleteVideo(int id) {
        try { // Call Web Service Operation
            VideoWebService port = service.getVideoWebServicePort();
            port.deleteVideo(id);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void updateVideo(String title, byte[] file) {
        try { // Call Web Service Operation
            VideoWebService port = service.getVideoWebServicePort();
            Video video = new Video();
            if (!title.isEmpty() || !title.equals(video.getTitle())) {
                video.setTitle(title);
                port.updateVideo(video);
            }
            if (file != null) {
                video.setFile(file);
                port.updateVideo(video);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public Video getVideo(int id) {
        Video video = null;
        try { // Call Web Service Operation
            VideoWebService port = service.getVideoWebServicePort();
            Video result = port.getVideo(id);
            video = result;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return video;
    }

    public void addVideo(String title, byte[] file) {
        try { // Call Web Service Operation
            VideoWebService port = service.getVideoWebServicePort();
            Video video = new Video();
            video.setTitle(title);
            video.setFile(file);
            port.addVideo(video);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public List<Video> getVideos() {
        List<Video> videos = null;
        try { // Call Web Service Operation
            VideoWebService port = service.getVideoWebServicePort();
            List<Video> result = port.getVideos();
            videos = result;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return videos;
    }
}
