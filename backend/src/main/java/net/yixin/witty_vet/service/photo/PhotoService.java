package net.yixin.witty_vet.service.photo;

import net.yixin.witty_vet.model.Photo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;

public interface PhotoService {
    Photo savePhoto(MultipartFile file, Long userId) throws IOException, SQLException;
    Photo getPhotoById(Long photoId);
    Photo updatePhoto(Long photoId, MultipartFile file) throws SQLException, IOException;
    void deletePhoto(Long photoId, Long userId) throws SQLException;
    byte[] getImageData(Long photoId) throws SQLException;
}
