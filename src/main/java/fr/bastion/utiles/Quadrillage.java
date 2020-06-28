package fr.bastion.utiles;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class Quadrillage {
	
	private final String fileName;
	double[][] square;
	List<Double> ordo;
	List<Double> absc;

	private final double degreLat = 0.009;
	private final double degreLong = 0.0143;
	
	public Quadrillage(String fileName) {
		this.fileName = fileName;
	}

	public static double distance(double lat1, double lat2, double lon1, double lon2) {
		lon1 = Math.toRadians(lon1);
		lon2 = Math.toRadians(lon2);
		lat1 = Math.toRadians(lat1);
		lat2 = Math.toRadians(lat2);

		// formule Haversine
		double dlon = lon2 - lon1;
		double dlat = lat2 - lat1;
		double a = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlon / 2), 2);
		double c = 2 * Math.asin(Math.sqrt(a));

		// Rayon de la terre en km
		double r = 6371;
		return (c * r);
	}
	
	public void drawSquare(double latCenter, double longCenter, int diametreKm, int cutting) {

		if (cutting >= diametreKm) {
			throw new IllegalArgumentException("L'espacement est trop grande par rapport au rayon");
		}
		
		/* [0][] = latitude;
		 * [1][] = longitude deplacement horizontal;
		 * [][0] = centre;
		 * [][1] = Nord.Ouest
		 * [][2] = N.Est
		 * [][3] = Sud.E
		 * [][4] = S.O
		 */
		square = new double[2][5];

		//Centre
		square[0][0] = latCenter;
		square[1][0] = longCenter;
		
		//NO
		square[0][1] = latCenter + (diametreKm/2)*degreLat;
		square[1][1] = longCenter - (diametreKm/2)*degreLong;
		
		//NE
		square[0][2] = latCenter + (diametreKm/2)*degreLat;
		square[1][2] = longCenter + (diametreKm/2)*degreLong;

		//SE
		square[0][3] = latCenter - (diametreKm/2)*degreLat;
		square[1][3] = longCenter + (diametreKm/2)*degreLong;
		
		//SO
		square[0][4] = latCenter - (diametreKm/2)*degreLat;
		square[1][4] = longCenter - (diametreKm/2)*degreLong;
		
		scale(diametreKm ,cutting);
	}

	public void scale(int diametreKm, int cutting) {
		
		int div = ( (diametreKm / cutting));

		double startLat = square[0][3]; //Latitude la plus au sud.
		double endLat = square[0][1]; //Latitude la plus au nord.
		double startLong = square[1][1]; //Longitude la plus à l'ouest.
		double endLong = square[1][2]; //Longitude la plus à l'est.

		double incrementLat = (endLat-startLat)/div;
		double incrementLong = (endLong-startLong)/div;
		
		this.ordo = abscOrdo(startLat, endLat, incrementLat);
		this.absc = abscOrdo(startLong, endLong, incrementLong);
		
		//writeDots(absc, ordo);

	}

	private List<Double> abscOrdo(double start, double end, double increment) {
		List<Double> list = new ArrayList<Double>();
		while (start <= end) {
			list.add(start);
			start = start+ increment;			
		}
		return list;
	}	

	private void writeDots(List<Double> absc, List<Double> ordo) {
		String content = "latitude ; longitude\n";
		try {
			Files.deleteIfExists(Paths.get(fileName));
			Files.writeString(Paths.get(fileName), content, StandardCharsets.UTF_8, StandardOpenOption.CREATE);
			
			for (Double a : absc) {
				for (Double o : ordo) {
					content = o+" ; "+a+"\n";
						Files.writeString(Paths.get(fileName), content, StandardCharsets.UTF_8, StandardOpenOption.APPEND);
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public List<Double> getOrdo() {
		return ordo;
	}

	public List<Double> getAbsc() {
		return absc;
	}

}
