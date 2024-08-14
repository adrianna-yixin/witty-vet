package net.yixin.witty_vet.service.photo;

import lombok.RequiredArgsConstructor;
import net.yixin.witty_vet.exception.ResourceNotFoundException;
import net.yixin.witty_vet.model.Photo;
import net.yixin.witty_vet.model.User;
import net.yixin.witty_vet.repository.PhotoRepository;
import net.yixin.witty_vet.repository.UserRepository;
import net.yixin.witty_vet.utils.FeedbackMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import javax.swing.text.html.Option;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PhotoServiceImpl implements PhotoService {
    private final PhotoRepository photoRepository;
    private final UserRepository userRepository;

    @Override
    public Photo savePhoto(MultipartFile file, Long userId) throws IOException, SQLException {
        Optional<User> user = userRepository.findById(userId);
        Photo photo = new Photo();
        if (file != null && !file.isEmpty()) {
            byte[] photoBytes = file.getBytes();
            Blob photoBlob = new SerialBlob(photoBytes);
            photo.setImage(photoBlob);
            photo.setFileName(file.getOriginalFilename());
            photo.setFileType(file.getContentType());
        }
        Photo savedPhoto = photoRepository.save(photo);
        user.ifPresent(u -> u.setPhoto(savedPhoto));
        userRepository.save(user.get());
        return savedPhoto;
    }

    @Override
    public Photo getPhotoById(Long photoId) {
        return photoRepository.findById(photoId)
                .orElseThrow(() -> new ResourceNotFoundException(FeedbackMessage.RESOURCE_NOT_FOUND));
    }

    @Override
    public Photo updatePhoto(Long photoId, MultipartFile file) throws SQLException, IOException {
        Photo existingPhoto = getPhotoById(photoId);
        if (existingPhoto != null) {
            byte[] photoBytes = file.getBytes();
            Blob photoBlob = new SerialBlob(photoBytes);
            existingPhoto.setImage(photoBlob);
            existingPhoto.setFileName(file.getOriginalFilename());
            existingPhoto.setFileType(file.getContentType());
            return photoRepository.save(existingPhoto);
        }
        throw new ResourceNotFoundException(FeedbackMessage.RESOURCE_NOT_FOUND);
    }

    @Transactional
    @Override
    public void deletePhoto(Long photoId, Long userId) {
        userRepository.findById(userId).ifPresentOrElse(User::removeUserPhoto, () -> {
            throw new ResourceNotFoundException(FeedbackMessage.RESOURCE_NOT_FOUND);
        });
        photoRepository.findById(photoId).ifPresentOrElse(photoRepository::delete, () -> {
            throw new ResourceNotFoundException(FeedbackMessage.RESOURCE_NOT_FOUND);
        });
    }

    @Override
    public byte[] getImageData(Long photoId) throws SQLException{
        Photo photo = getPhotoById(photoId);
        Blob photoBlob = photo.getImage();
        int blobLength = (int) photoBlob.length();
        return new byte[blobLength];
    }
}
