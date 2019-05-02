package scripts;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;

import org.tribot.api.Clicking;
import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api.util.abc.ABCProperties;
import org.tribot.api.util.abc.ABCUtil;
import org.tribot.api2007.GroundItems;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.Walking;
import org.tribot.api2007.types.RSGroundItem;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;

@ScriptManifest(authors = { "Miracle" }, category = "Hunter", name = "Trap Star", version = 0.1)
public class HunterHelper extends Script {

	private ABCUtil ABCUtil = new ABCUtil();
	private RSTile lootLoc = null;
	public Salamander lizardType = null;
	List<Integer> waitTimes = new ArrayList<>();
	public RSTile startPos = null;

	@Override
	public void run() {
		HunterUI frame = new HunterUI(this);
		frame.setVisible(true);
		startPos = Player.getPosition();
		General.useAntiBanCompliance(true);
		while (true) {
			if (lizardType != null) {

				if (startPos.distanceTo(Player.getPosition()) > 10) {
					Walking.generateStraightPath(startPos);
				}

				if (trapBroke()) {
					lootEquipment();
					// pickupEquipment();

				} else {

					int inv = Inventory.find(lizardType.getItemId()).length;
					if (Inventory.find(lizardType.getItemId()).length > 1) {
						Clicking.click("Release", Inventory.find(lizardType.getItemId()));
						Timing.waitCondition(new BooleanSupplier() {
							@Override
							public boolean getAsBoolean() {
								return Player.getAnimation() < 0 && !Player.isMoving()
										&& Inventory.getCount(lizardType.getItemId()) < inv;
							}
						}, General.random(208, 932));
					}

					if (Objects.findNearest(50, "Net trap").length > lizardType.getMaxTraps() - 1) {
						if (ABCUtil.shouldCheckTabs())
							ABCUtil.checkTabs();
						if (ABCUtil.shouldMoveMouse())
							ABCUtil.moveMouse();
						if (ABCUtil.shouldCheckXP())
							ABCUtil.checkXP();
						if (ABCUtil.shouldCheckTabs())
							ABCUtil.checkTabs();
						if (ABCUtil.shouldExamineEntity())
							ABCUtil.examineEntity();
						if (ABCUtil.shouldRotateCamera())
							ABCUtil.rotateCamera();
						if (ABCUtil.shouldPickupMouse())
							ABCUtil.pickupMouse();
						if (ABCUtil.shouldLeaveGame())
							ABCUtil.leaveGame();
						if (ABCUtil.shouldRightClick())
							ABCUtil.rightClick();
					}
					if (isTrapFull())
						checkTrap();
					else if (hasEquipment() && Objects.findNearest(50, "Net trap").length < lizardType.getMaxTraps()
							&& !trapBroke())
						setTrap();
				}
			}
			sleep(10);
		}
	}

	public Salamander determineType(final String type) {
		if (type.equalsIgnoreCase("orange"))
			return Salamander.ORANGE;
		else if (type.equalsIgnoreCase("red"))
			return Salamander.RED;
		else if (type.equalsIgnoreCase("black"))
			return Salamander.BLACK;
		return null;
	}

	public boolean trapBroke() {
		return GroundItems.find(303, 954).length > 0;

	}

	public void buildReaction(final long ms) {
		final int waiting_time = (int) (ms);
		final boolean menu_open = ABCUtil.shouldOpenMenu() && ABCUtil.shouldHover();
		final boolean hovering = ABCUtil.shouldHover();
		final long hover_option = hovering ? ABCUtil.OPTION_HOVERING : 0;
		final long menu_open_option = menu_open ? ABCUtil.OPTION_MENU_OPEN : 0;
		final int reaction_time = ABCUtil
				.generateReactionTime(ABCUtil.generateBitFlags(waiting_time, hover_option, menu_open_option));
		try {
			println(waiting_time);
			println(reaction_time + "TTTTTTT");
			ABCUtil.sleep(reaction_time);
		} catch (final InterruptedException e) {

		}
		ABCUtil.generateTrackers(ms);
	}

	public boolean lootEquipment() {
		final RSGroundItem[] items = GroundItems.findNearest(954, 303);
		if (items.length < 1)
			return false;
		final RSGroundItem toPickup = items[0];
		if (!toPickup.isOnScreen())
			toPickup.adjustCameraTo();
		final int invCnt = Inventory.getCount(303, 954);
		if (DynamicClicking.clickRSGroundItem(toPickup, "Take")) {
			start = System.currentTimeMillis();
			while (Player.isMoving()) {
				// sleep(20, 30);
			}
			buildReaction(System.currentTimeMillis() - start);
		}
		return false;
	}

	private RSObject nextTree = null;
	long start, finish, time;

	public boolean setTrap() {
		final RSObject[] youngTree = Objects.findNearest(7, lizardType.getTreeId());
		if (youngTree.length < 1)
			return false;
		nextTree = youngTree[0];
		if (nextTree != null) {
			if (!nextTree.isOnScreen())
				nextTree.adjustCameraTo();
			final int equipCnt = Inventory.find(303, 954).length;
			if (DynamicClicking.clickRSObject(nextTree, "Set-trap")) {
				ABCUtil.shouldHover();
				start = System.currentTimeMillis();
				while (Player.getAnimation() > -1 || Player.isMoving()) {

				}
				buildReaction(System.currentTimeMillis() - start);
				println("TIME" + time);
			}
		}
		return Player.getAnimation() < 0 && !Player.isMoving();
	}

	public boolean checkTrap() {
		final RSObject[] fullNet = Objects.findNearest(50, lizardType.getNetId());
		if (fullNet.length < 0)
			return false;
		final RSObject targetNet = (RSObject) ABCUtil.selectNextTarget(fullNet);
		if (!targetNet.isOnScreen() && targetNet != null)
			targetNet.adjustCameraTo();
		if (DynamicClicking.clickRSObject(targetNet, "Check")) {
			start = System.currentTimeMillis();
			while (Player.getAnimation() > -1 || Player.isMoving()) {

			}
			buildReaction(System.currentTimeMillis() - start);
		}
		return Player.getAnimation() < 0 && Inventory.find(lizardType.getItemId()).length > 0 && !Player.isMoving();
	}

	private boolean hasEquipment() {
		return Inventory.find("Rope").length > 0 && Inventory.find("Small fishing net").length > 0;
	}

	private boolean isTrapFull() {
		return Objects.findNearest(50, lizardType.getNetId()).length > 0;
	}

}
