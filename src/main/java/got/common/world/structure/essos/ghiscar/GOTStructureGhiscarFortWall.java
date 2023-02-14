package got.common.world.structure.essos.ghiscar;

public class GOTStructureGhiscarFortWall extends GOTStructureEssosFortWall {
	public GOTStructureGhiscarFortWall(boolean flag) {
		super(flag);
		isGhiscar = true;
	}

	public static class Long extends GOTStructureEssosFortWall.Long {
		public Long(boolean flag) {
			super(flag);
			isLong = true;
			isGhiscar = true;
		}
	}

	public static class Short extends GOTStructureEssosFortWall.Short {
		public Short(boolean flag) {
			super(flag);
			isLong = false;
			isGhiscar = true;
		}
	}
}