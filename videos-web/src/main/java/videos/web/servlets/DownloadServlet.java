package videos.web.servlets;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.Normalizer;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import videos.ejb.VideoBean;
import videos.ejb.soap.client.Video;

@WebServlet(name = "DownloadServlet", urlPatterns = {"/DownloadServlet"})
public class DownloadServlet extends HttpServlet {

    @EJB
    private VideoBean videoBean;

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idString = request.getParameter("id");
        int id = Integer.parseInt(idString);
        Video video = videoBean.getVideo(id);
        String title = video.getTitle();
        String filename = Normalizer.
                normalize(title.replaceAll(" ", "_")
                        .toLowerCase().concat(".mp4"), Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment; filename=" + filename);
        byte[] bytes = video.getFile();
        InputStream in = new ByteArrayInputStream(bytes);
        try (OutputStream out = response.getOutputStream()) {
            byte[] buffer = new byte[1024];
            int numBytesRead;
            while ((numBytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, numBytesRead);
            }
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
