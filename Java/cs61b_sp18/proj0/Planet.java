public class Planet{

	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	public String imgFileName;
	//public double fNetX;
	//public double fNetY;


	/**Gravitational constant*/
	static final double G  = 6.67*(Math.pow(10, -11));


	/**Constructor*/
	
	public Planet(double xP, double yP, double xV, double yV, double m, String img){
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;
	}

	/**Planet copy constructor*/
	public Planet(Planet p){
		xxPos = p.xxPos;
		yyPos = p.yyPos;
		xxVel = p.xxVel;
		yyVel = p.yyVel;
		mass = p.mass;
		imgFileName = p.imgFileName;
	}

	/**Calculate the distance between two planets.*/
	public double calcDistance(Planet p){
			double dx = this.xxPos - p.xxPos;
			double dy = this.yyPos - p.yyPos;
		return Math.sqrt(dx*dx + dy*dy);
	}

	/**Calculate the force a planet exerts to another one.*/
	public double calcForceExertedBy(Planet p){
			double r = this.calcDistance(p);

		return (G*this.mass*p.mass)/(r*r);
	}

	/**Force on the x Axis*/
	public double calcForceExertedByX(Planet p){
			double f = this.calcForceExertedBy(p);
			double r = this.calcDistance(p);
			double dx = p.xxPos - this.xxPos;
		return f*dx/r;
	}

	/**Force on the y Axis*/
	public double calcForceExertedByY(Planet p){
			double f = this.calcForceExertedBy(p);
			double r = this.calcDistance(p);
			double dy = p.yyPos - this.yyPos;
		return f*dy/r;
	}

	/**Calc the net force from all planets*/
	public double calcNetForceExertedByX(Planet[] allPlanets){
		double sumOfForcesX = 0;

			for(Planet p : allPlanets){
				if(this.equals(p)){
					continue;
				}else{
					sumOfForcesX += this.calcForceExertedByX(p);
				}
			}
		return sumOfForcesX;
	}

		public double calcNetForceExertedByY(Planet[] allPlanets){
		double sumOfForcesY = 0;

			for(Planet p : allPlanets){
				if(this.equals(p)){
					continue;
				}else{
					sumOfForcesY += this.calcForceExertedByY(p);
				}
			}
		return sumOfForcesY;
	}

	/**Calculate acceleration, velocity change and new position per unit time dt for a planet*/
	public void update(double dt, double fX, double fY){

		double aX = fX/mass;
		double aY = fY/mass;

		double xxNewVel = dt*aX;
		double yyNewVel = dt*aY;


		xxVel = xxVel + xxNewVel;
		yyVel = yyVel + yyNewVel;

		xxPos += dt*xxNewVel;
		yyPos += dt*yyNewVel;
	}

	public void draw(){
		StdDraw.picture(xxPos, yyPos, "images/" + imgFileName);
	}
}