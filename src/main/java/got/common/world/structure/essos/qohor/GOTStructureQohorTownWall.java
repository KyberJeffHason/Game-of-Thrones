package got.common.world.structure.essos.qohor;

public class GOTStructureQohorTownWall extends GOTStructureEssosTownWall {
	public GOTStructureQohorTownWall(boolean flag) {
		super(flag);
		isQohor = true;
	}

	public static class Extra extends GOTStructureEssosTownWall.Extra {
		public Extra(boolean flag) {
			super(flag);
			isQohor = true;
		}
	}

	public static class Long extends GOTStructureEssosTownWall.Long {
		public Long(boolean flag) {
			super(flag);
			isQohor = true;
		}
	}

	public static class Short extends GOTStructureEssosTownWall.Short {
		public Short(boolean flag) {
			super(flag);
			isQohor = true;
		}
	}

	public static class SideMid extends GOTStructureEssosTownWall.SideMid {
		public SideMid(boolean flag) {
			super(flag);
			isQohor = true;
		}
	}
}