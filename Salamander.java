package scripts;

public enum Salamander {

	GREEN(29, -1, -1, -1, -1), ORANGE(47, 8732, 8734, 10146, 3), RED(59, 8990, 8986, 10147, 4), BLACK(67, 9000, 8996, 10148, 5);

	private final int lvlReq;
	private final int treeId;
	private final int netId;
	private final int itemId;
	private final int maxTraps;

	Salamander(final int lvlReq, final int treeId, final int netId, final int itemId, final int maxTraps) {
		this.lvlReq = lvlReq;
		this.treeId = treeId;
		this.netId = netId;
		this.itemId = itemId;
		this.maxTraps = maxTraps;
	}

	public int getLvlReq() {
		return lvlReq;
	}

	public int getTreeId() {
		return treeId;
	}

	public int getNetId() {
		return netId;
	}

	public int getItemId() {
		return itemId;
	}

	public int getMaxTraps() {
		return maxTraps;
	}

}
