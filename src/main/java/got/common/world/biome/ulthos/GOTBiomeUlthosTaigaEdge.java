package got.common.world.biome.ulthos;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;

public class GOTBiomeUlthosTaigaEdge extends GOTBiomeUlthosTaiga {
	public GOTBiomeUlthosTaigaEdge(int i, boolean major) {
		super(i, major);
		decorator.treesPerChunk = 2;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.ULTHOS.getSubregion("ulthosTaigaEdge");
	}
}