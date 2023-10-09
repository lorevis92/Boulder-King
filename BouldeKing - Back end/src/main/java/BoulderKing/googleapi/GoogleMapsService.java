package BoulderKing.googleapi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import BoulderKing.Enum.TipoEnte;
import BoulderKing.Enum.TipoUser;
import BoulderKing.Enum.ZonaItalia;
import BoulderKing.entities.users.Role;
import BoulderKing.entities.users.User;
import BoulderKing.entities.users.UsersRepository;

@Service
public class GoogleMapsService {
    private final UsersRepository usersRepo;

	// Chiave API di Google Maps
	@Value("${google.maps.api.key}")
	private String apiKey;
    @Autowired
    public GoogleMapsService(UsersRepository usersRepo) {
        this.usersRepo = usersRepo;
    }

    public void fetchClimbingGymsFromGoogleMaps() {
        try {
            // Chiave API di Google Maps

			String apiUrl = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=climbing+gym+in+Lombardia&region=it&key="
					+ apiKey;
			String nextPageToken = "ATJ83zjFSvYO3tlPZjK9WD-hx49IMJ9nLLSuX8dMltdU0r-FkqxtOae5olxye8f4fRyJ97Pdd_28Lr7-YOFeVNG4VYKFDycMjEWf-xoobhNV-pHKtPg4QVDiLUClbWUoNDX-_VDgBNVaY6eacTjkoWy-SOEeLMy-seQUkxz_8MqOpOFdvga5NWxOoEm4yL0RhCFlpJa7ikplTcdcNpcqLKKjePHLNQ6hJvBVRHIfzuGkFEIOMJpS6RW__eZOWtTb5ox_UtB03KZ-iPn-jqAR6pIugvfMbIPqt0U1KXIFG6vfyKlSWIlBxQo0BFzoIgCVIV5BvlvptFAlC47joslhPehVsgbBlBHp4erlcRN7EZ85aJsF0b6YnLuZPbzGxK9nvj1Kcg1UpYO8crCFS4MZBDV3OSo8_s8B3ayu9tlrA2d5ZTisx2v2R2CSlTpadwT2sI1tq4oyKabc9_z5Fo7C6ReI8SZjUws132vmTFEROFdaPdvPnYd-0okzR5ubncaEY6dsK0JmTqXvd3uwA4jZ48v5rJGVM2Ab90za-UKU5nYzixehWqnqc4J_9HBMFAe_JjxyQw6wxZNGGMY0g4Z0SFG1qYgwek7LChKjSr7kHyS72_9sEei6qzpfeQYEI9w";
                URL url = new URL(apiUrl + "&pagetoken=" + nextPageToken);

                // Apre una connessione HTTP
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                // Imposta il metodo di richiesta come GET
                connection.setRequestMethod("GET");

                // Ottiene la risposta
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Legge la risposta
                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String inputLine;
                    StringBuffer response = new StringBuffer();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    // Converti la risposta JSON in un oggetto JSONObject
                    JSONObject jsonResponse = new JSONObject(response.toString());
					System.out.println(jsonResponse);
                    // Accedi all'array "results" nell'oggetto JSON
                    JSONArray resultsArray = jsonResponse.getJSONArray("results");

                    // Ora puoi iterare sugli elementi dell'array "results"
                    for (int i = 0; i < resultsArray.length(); i++) {
                        // Creo un oggetto user che uso per fare i salvataggi nel DB
                        User user = new User();
                        JSONObject resultObject = resultsArray.getJSONObject(i);
                        JSONObject geometryObject = resultObject.getJSONObject("geometry");
                        JSONObject locationObject = geometryObject.getJSONObject("location");

                        // Estrai l'array "photos" e l'URL della prima foto, se presente
                        if (resultObject.has("photos")) {
                            JSONArray photosArray = resultObject.getJSONArray("photos");
                            if (photosArray.length() > 0) {
                                JSONObject photoObject = photosArray.getJSONObject(0);
                                String photoReference = photoObject.getString("photo_reference");

                                // Costruisci l'URL completo dell'immagine
                                String photoUrl = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference="
                                        + photoReference + "&key=" + apiKey;

                                // Fai qualcosa con l'URL dell'immagine
                                user.setFoto(photoUrl);
                            }
                        } else {
							String photoUrl = "https://us.123rf.com/450wm/kstudija/kstudija1603/kstudija160300018/52849548-i-bambini-ragazza-sagome-sul-muro-di-arrampicata-in-attivo-e-sano-sport-background-illustrazione.jpg?ver=6";
                            user.setFoto(photoUrl);
                        }

                        // Puoi accedere alle proprietà specifiche di ciascun elemento qui
                        String name = resultObject.getString("name");
                        String formattedAddress = resultObject.getString("formatted_address");
						double rating = resultObject.getDouble("rating");
                        double lat = locationObject.getDouble("lat");
                        double lng = locationObject.getDouble("lng");

						// Imposto i valori
                        user.setNomeEnte(name);
                        user.setIndirizzo(formattedAddress);
						user.setTipoEnte(TipoEnte.FALESIA);
                        user.setTipoUser(TipoUser.ENTE);
                        user.setRole(Role.USER);
						user.setZonaItalia(ZonaItalia.NORD);
						user.setRegione("Lombardia");
                        user.setLatitudine(lat);
                        user.setLongitudine(lng);
						user.setRating(rating);

                        // Salva l'utente nel repository (rimuovi il commento quando sei pronto a salvarli)
//						usersRepo.save(user);


                    }
                } else {
                    // Gestisci errori o scelte appropriate per la gestione degli errori
                    // Ad esempio, log o eccezioni
                    System.err.println("Errore nella richiesta HTTP. Codice di risposta: " + responseCode);
                }

        } catch (Exception e) {
            // Gestisci eccezioni in caso di problemi durante la chiamata HTTP
            e.printStackTrace();
        }
    }
}
