package BoulderKing.googleapi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import BoulderKing.Enum.TipoEnte;
import BoulderKing.Enum.TipoUser;
import BoulderKing.entities.users.User;
import BoulderKing.entities.users.UsersRepository;

@Service
public class GoogleMapsService {
    private final UsersRepository usersRepo;

    @Autowired
    public GoogleMapsService(UsersRepository usersRepo) {
        this.usersRepo = usersRepo;
    }

    public void fetchClimbingGymsFromGoogleMaps() {
        try {
            // URL dell'endpoint Google Maps
			String apiKey = "AIzaSyCJ572aOuiFdh1FQcIsFTojM8xjKSh3cKg"; // Sostituisci con la tua chiave API
            String apiUrl = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=climbing%2Bgym&location=42.504154%2C12.646361&radius=50000&key=" + apiKey;
            String nextPageToken = "";

            do {
                // Crea un oggetto URL
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

                    // Stampiamo la risposta sulla console
					// System.out.println(response.toString());

                    // Converti la risposta JSON in un oggetto JSONObject
                    JSONObject jsonResponse = new JSONObject(response.toString());

                    // Accedi all'array "results" nell'oggetto JSON
                    JSONArray resultsArray = jsonResponse.getJSONArray("results");


                    // Ora puoi iterare sugli elementi dell'array "results"
                    for (int i = 0; i < resultsArray.length(); i++) {
						// Creo un oggetto user che uso per fare i salvataggi nel DB
						User user = new User();
                        JSONObject resultObject = resultsArray.getJSONObject(i);
                        JSONObject geometryObject = resultObject.getJSONObject("geometry");
                        JSONObject locationObject = geometryObject.getJSONObject("location");
                        // Estrarre l'array "photos" e l'URL della prima foto, se presente
						if (resultObject.has("photos")) {
							JSONArray photosArray = resultObject.getJSONArray("photos");
							if (photosArray.length() > 0) {
								JSONObject photoObject = photosArray.getJSONObject(0);
								String photoReference = photoObject.getString("photo_reference");

								// Costruire l'URL completo dell'immagine
								String photoUrl = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference="
										+ photoReference + "&key=" + apiKey;

								// Fai qualcosa con l'URL dell'immagine
								user.setFoto(photoUrl);
							}
						} else {
							String photoUrl = "https://img.freepik.com/free-photo/thoughtful-frightened-looking-brown-white-dog-black-cotton-hoodie-with-hood-up_346278-419.jpg";
							user.setFoto(photoUrl);
                        }

                        // Puoi accedere alle proprietà specifiche di ciascun elemento qui
                        String name = resultObject.getString("name");
                        String formattedAddress = resultObject.getString("formatted_address");
						double lat = locationObject.getDouble("lat");
						double lng = locationObject.getDouble("lng");
						System.out.println(name);
						System.out.println(formattedAddress);
						System.out.println(lat);
						System.out.println(lng);

                        // Fai qualcosa con queste informazioni
                        user.setNomeEnte(name);
                        user.setIndirizzo(formattedAddress);
                        user.setTipoEnte(TipoEnte.PALESTRA);
                        user.setTipoUser(TipoUser.ENTE);
                        user.setLatitudine(lat);
                        user.setLongitudine(lng);
						// System.out.println(user);
//						usersRepo.save(user);

                        // Estraggo il next_page_token dalla risposta
                        nextPageToken = jsonResponse.optString("next_page_token", "");
					}
                } else {
                    // Gestisci errori o scelte appropriate per la gestione degli errori
                    // Ad esempio, log o eccezioni
                    System.err.println("Errore nella richiesta HTTP. Codice di risposta: " + responseCode);
                }
            } while (!nextPageToken.isEmpty()); // Continua finché c'è un next_page_token
        } catch (Exception e) {
            // Gestisci eccezioni in caso di problemi durante la chiamata HTTP
            e.printStackTrace();
        }
    }
}
