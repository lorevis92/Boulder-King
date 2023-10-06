package BoulderKing.entities.caricaImmagini;


import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import BoulderKing.entities.evento.EventoService;
import BoulderKing.entities.users.User;
import BoulderKing.entities.users.UsersService;

@RestController
//@CrossOrigin(origins = "http://localhost:8082") open for specific port
@CrossOrigin() // open for all ports
public class ImageController {

    @Autowired
    ImageRepository imageRepository;

	@Autowired
	UsersService userServ;

	@Autowired
	EventoService eventoServ;

    @PostMapping("/upload/image")
	public ResponseEntity<ImageUploadResponse> uploadImage(@RequestParam("userId") Optional<UUID> userId,
			@RequestParam("eventoId") Optional<UUID> eventoId,
			@RequestParam("image") MultipartFile file) throws IOException {

		Image image = Image.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
				.image(ImageUtility.compressImage(file.getBytes())).build();

		userId.ifPresent(id -> {
			User user = userServ.findById(id);
			if (user != null) {
				image.setUserImmagine(user);
			}
		});

		imageRepository.save(image);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ImageUploadResponse("Image uploaded successfully: " +
                        file.getOriginalFilename()));
    }


	@GetMapping("/get/image/{id}")
	public ResponseEntity<byte[]> getImageById(@PathVariable UUID id) throws IOException {
		Optional<Image> dbImage = imageRepository.findById(id);
		if (dbImage.isPresent()) {
			return ResponseEntity.ok().contentType(MediaType.valueOf(dbImage.get().getType()))
					.body(ImageUtility.decompressImage(dbImage.get().getImage()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

	@PutMapping("/upload/image/{id}")
	public ResponseEntity<ImageUploadResponse> changeImageById(@PathVariable UUID id,
			@RequestParam("image") MultipartFile file) throws IOException {
		System.out.println("PUT endpoint called with ID: " + id); // Log per debugging
		   
		Optional<Image> dbImageOptional = imageRepository.findById(id);
		if (!dbImageOptional.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		Image dbImage = dbImageOptional.get();
			dbImage.setName(file.getOriginalFilename());
			dbImage.setType(file.getContentType());
			dbImage.setImage(ImageUtility.compressImage(file.getBytes()));
			imageRepository.save(dbImage);
			return ResponseEntity.status(HttpStatus.OK).body(new ImageUploadResponse("Image updated successfully"));
		}

		@DeleteMapping("/delete/image/{id}")
		public ResponseEntity<?> deleteImageById(@PathVariable UUID id) {
			System.out.println("Attempting to delete image with ID: " + id); // Aggiungi log per debugging
			Optional<Image> dbImageOptional = imageRepository.findById(id);
			if (!dbImageOptional.isPresent()) {
				System.out.println("Image with ID: " + id + " not found!"); // Aggiungi log per debugging
				return ResponseEntity.notFound().build();
			}

			try {
				imageRepository.deleteById(id);
				System.out.println("Image with ID: " + id + " successfully deleted!"); // Aggiungi log per debugging
				return ResponseEntity.ok().body(new ImageUploadResponse("Image deleted successfully"));
			} catch (Exception e) {
				System.out.println("Error deleting image with ID: " + id + " - " + e.getMessage()); // Aggiungi log per
																									// debugging
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}
		}


}