package BoulderKing.entities.caricaImmagini;


import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

//		eventoId.ifPresent(id -> {
//			Evento evento = eventoServ.findById(id);
//			if (evento != null) {
//				image.setEventoImmagine(evento);
//			}
//		});

		imageRepository.save(image);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ImageUploadResponse("Image uploaded successfully: " +
                        file.getOriginalFilename()));
    }

    @GetMapping(path = {"/get/image/info/{name}"})
	public Image getImageDetails(@PathVariable("name") String name) throws IOException {

        final Optional<Image> dbImage = imageRepository.findByName(name);

		return Image.builder().name(dbImage.get().getName()).type(dbImage.get().getType())
				.image(ImageUtility.decompressImage(dbImage.get().getImage())).build();
    }

    @GetMapping(path = {"/get/image/{name}"})
	public ResponseEntity<byte[]> getImage(@PathVariable("name") String name) throws IOException {

        final Optional<Image> dbImage = imageRepository.findByName(name);

		return ResponseEntity.ok().contentType(MediaType.valueOf(dbImage.get().getType()))
				.body(ImageUtility.decompressImage(dbImage.get().getImage()));
    }
}