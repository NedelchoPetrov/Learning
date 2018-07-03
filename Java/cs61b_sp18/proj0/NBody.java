public class NBody{
	

	public static double readRadius(String fileName){
		In in = new In(fileName);
		int numOfPlanets = in.readInt();
		double radius = in.readDouble();
		return radius;
	}


	public static Planet[] readPlanets(String fileName){
		In in = new In(fileName);
		int numOfPlanets = in.readInt();
		double radius = in.readDouble();

		Planet[] arrayOfPlanets = new Planet[5];
		for(int i = 0;i < numOfPlanets; i++){
			double xxPos = in.readDouble();
			double yyPos = in.readDouble();
			double xxVel = in.readDouble();
			double yyVel = in.readDouble();
			double mass = in.readDouble();
			String img = in.readString();

			arrayOfPlanets[i] = new Planet(xxPos, yyPos, xxVel, yyVel, mass, img);
		}
		return arrayOfPlanets;
	}


	/**java NBody 157788000.0 25000.0 data/planets.txt*/

	public static void main(String[] args){
		Double t = Double.parseDouble(args[0]);
		Double dt = Double.parseDouble(args[1]);
		String filename = args[2];

		double radius = readRadius(filename);
		Planet[] planets = readPlanets(filename);
		int numOfPlanets = planets.length;



		StdDraw.setScale(-radius, radius);
		StdDraw.picture(0, 0, "images/starfield.jpg");


		for(Planet p : planets){
			p.draw();
		}
		StdDraw.enableDoubleBuffering();


		for(int time = 0; time < t; time+=dt){
			
			double[] xForces = new double[numOfPlanets];
			double[] yForces = new double[numOfPlanets];

			//Calculate the net force for each planet in 'planets' and update().
			for(int j = 0; j < numOfPlanets; j++){
				xForces[j] = planets[j].calcNetForceExertedByX(planets);
				yForces[j] = planets[j].calcNetForceExertedByY(planets);
				System.out.print(yForces[j]);
			}


			//Update each planet's attributes
			for(int j = 0; j < numOfPlanets; j ++){
				planets[j].update(dt, xForces[j], yForces[j]);
			}

			//Redraw the canvas.
			StdDraw.clear();			
			StdDraw.picture(0, 0, "images/starfield.jpg");

			for(Planet p : planets){
				p.draw();
			}
			
			StdDraw.show();
			StdDraw.pause(10);

		}
	}
}