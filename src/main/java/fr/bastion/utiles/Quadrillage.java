package fr.bastion.utiles;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Quadrillage {
	
	//Constants.
	private final double degreLat = 0.009;
	private final double degreLong = 0.0143;
	
	//Constructor's parameters.
	private Path fileName = null;
	private double latitudeCenter;
	private double longitudeCenter;
	private int rayonKm;
	private int decoupageKm;
	
	//Arrays.
	private double[][] square;
    private double[][] tableAllCoordinates;
	
	public Quadrillage(String fileName, double latitudeCenter, double longitudeCenter, int rayonKm, int decoupageKm) {
		this.fileName = (fileName!=null) ? Paths.get(fileName) : null;
		deleteIfFileExist();
		this.latitudeCenter = latitudeCenter;
		this.longitudeCenter = longitudeCenter;
		this.rayonKm = (rayonKm<200) ? rayonKm : null;
		this.decoupageKm = (decoupageKm >= (rayonKm*2)) ? null : decoupageKm;
	}
	

	/**
	 * This method calculate exactly the distance between two coordinates points.
	 * @param lat1 latitude of point A.
	 * @param lat2 latitude of point B.
	 * @param lon1 longitude of point A.
	 * @param lon2 longitude of point B.
	 * @return
	 */
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
	
	public double[][] getCoordinates(){
		drawSquare();
		return tableAllCoordinates;
	}
	
	private void drawSquare() {
		/* [0][] = Latitude (vertical);
		 * [1][] = Longitude (horizontal);
		 * [][0] = Center;
		 * [][1] = N.O
		 * [][2] = N.E
		 * [][3] = S.E
		 * [][4] = S.O */
		square = new double[2][5];

		//Center
		square[0][0] = latitudeCenter; 
		square[1][0] = longitudeCenter;
		System.out.println("m"+square[0][1]+";"+square[1][0]);
		//NO
		square[0][1] = latitudeCenter + (rayonKm)*degreLat;
		square[1][1] = longitudeCenter - (rayonKm)*degreLong;
		System.out.println("m"+square[0][1]+";"+square[1][1]);
		//NE
		square[0][2] = latitudeCenter + (rayonKm)*degreLat;
		square[1][2] = longitudeCenter + (rayonKm)*degreLong;

		//SE
		square[0][3] = latitudeCenter - (rayonKm)*degreLat;
		square[1][3] = longitudeCenter + (rayonKm)*degreLong;
		
		//SO
		square[0][4] = latitudeCenter - (rayonKm)*degreLat;
		square[1][4] = longitudeCenter - (rayonKm)*degreLong;
		
		generateMatrice((rayonKm*2));
	}

	private void generateMatrice(int diametreKm) {
		
		double startLat = square[0][3]; //Southernmost.Latitude. x
		double endLat = square[0][1]; //Northernmost.Latitude. x
		
		double startLong = square[1][1]; //Easternmost.Longitude. y
		double endLong = square[1][2]; //westernmost.Longitude. y
		
		//Latitude:
		double[] tableauLatitudes = calculateAbcOrd(latitudeCenter-decoupageKm*degreLat,startLat , latitudeCenter,endLat , (decoupageKm*degreLat));
		//Longitude:
		double[] tableauLongitude = calculateAbcOrd(longitudeCenter-(decoupageKm*degreLong), startLong, longitudeCenter, endLong, (decoupageKm*degreLong));  
		
		calculateAllCombinations(tableauLatitudes, tableauLongitude);
	}
	
	private double[] calculateAbcOrd(double nStart,double nEnd, double pStart,double pEnd, double incrementation) {
		double[] listCoordonnees = new double[((rayonKm/decoupageKm)*2)+1];
		int increment=0;

		for (double i = nStart; i >= nEnd; i=i-incrementation) {
			listCoordonnees[increment] = i;
			increment++;
		}
		
		for (double j = pStart; j <= pEnd; j=j+incrementation) {
			listCoordonnees[increment] = j;
			increment++;
		}
		return listCoordonnees;
	}
	
	private void calculateAllCombinations(double[] tableauLatitudes, double[] tableauLongitude) {
        tableAllCoordinates = new double[2][(int) Math.pow(((rayonKm/decoupageKm)*2)+1, 2)];
		String content;
        int i = 0;
        
        for (double lt : tableauLatitudes) {
        	
        	for (double lg : tableauLongitude) {
        		tableAllCoordinates[0][i] = lt;
        		tableAllCoordinates[1][i] = lg;
        		content = i+": [latitude "+tableAllCoordinates[0][i]+" ; longitude "+tableAllCoordinates[1][i]+"]\n";
				System.out.print(content);
				write(content);
				i++;
			}
		}
	}		
	
	private void deleteIfFileExist() {
		if (fileName!=null) {
			try {
				Files.deleteIfExists(fileName);
				Files.writeString(fileName, "latitude ; longitude\n", StandardCharsets.UTF_8, StandardOpenOption.CREATE);

				} catch (IOException e) {
					e.printStackTrace();
					}
			}
		}
	
	private void write(String content) {
		try {
			if (fileName!=null && Files.exists(fileName)) 
				Files.writeString(fileName, content, StandardCharsets.UTF_8, StandardOpenOption.APPEND);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
