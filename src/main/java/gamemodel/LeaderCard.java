package gamemodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import gamemodel.command.GameError;
import gamemodel.command.GameException;
import gamemodel.effects.CouncilPrivileges;
import gamemodel.effects.IstantEffect;
import gamemodel.permanenteffect.PermanentEffect;
import gameview.gui.LeaderCardAction;

public class LeaderCard implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Requirement requirement;
	private PermanentEffect pe;
	private List<IstantEffect> oncePerRound=new ArrayList<>();
	private String name;
	private int id;
	private Player owner;
	
	private LeaderState state = LeaderState.NOT_PLAYED;
	
	public LeaderCard(int id,String name,Requirement req2, PermanentEffect pe2) {
		this.name=name;
		this.id=id;
		this.requirement = req2;
		this.pe = pe2;
	}

	public LeaderCard(int id,String name,Requirement req2, List<IstantEffect> opr2) {
		this.name=name;
		this.id=id;
		this.requirement = req2;
		this.oncePerRound = opr2;
	}
	
	void activateOPT() throws GameException {
		if (oncePerRound == null) {
			System.out.println("Why did you call activate??? This card has only a permanent effect");
			return;
		}
		
		if (state == LeaderState.USED_OPR)
			throw new GameException(GameError.LEADER_CARD_USED);
		else if (state == LeaderState.NOT_PLAYED)
			throw new GameException(GameError.LEADER_CARD_STILL_NOT_PLAYED);
			
		for(IstantEffect e:oncePerRound)
			e.activate(owner);
	}

	void discard() {
		new CouncilPrivileges(1).activate(owner);
	}
	
	void play() throws GameException {
		if (requirement.isSatisfiedBy(owner)) {
			state = LeaderState.PLAYED;
		} else {
			throw new GameException(GameError.LEADER_CARD_NOT_ENOUGH_MONEY);
		}
	}
	
	void prepareForNewRound() {
		state = LeaderState.PLAYED;
	}

	public PermanentEffect getPe() {
		return pe;
	}

	public List<IstantEffect> getOncePerRound() {
		return oncePerRound;
	}

	@Override
	public String toString() {
		String s = "" + name + " Requirements: " + requirement + " State: " + state;
		if (hasPE()) {
			s += " PermEff: " + pe;
		} else if (hasOPR()) {
			s += " OPR: " + oncePerRound;
		}
		return s;
	}

	public String getName() {
		return name;
	}
	public Requirement getRequirement() {
		return requirement;
	}

	public void setOwner(Player player) {
		owner = player;
	}

	public boolean getPlayedOPR() {
		return state == LeaderState.USED_OPR;
	}

	public boolean getPlayed() {
		return state == LeaderState.PLAYED;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LeaderState getState() {
		return state;
	}
	
	public boolean hasOPR() {
		return !hasPE();
	}
	
	
	public boolean hasPE() {
		return pe != null;
	}

	public List<LeaderCardAction> getPossibleActions() {
		List<LeaderCardAction> lst = new ArrayList<>();
		switch (this.state) {
		case NOT_PLAYED:
			lst.add(LeaderCardAction.DISCARD);
			lst.add(LeaderCardAction.PLAY);
			break;
		case PLAYED:
			if (this.hasOPR())
				lst.add(LeaderCardAction.ACTIVATE);
			break;
		case USED_OPR:
		default:
			break;
		
		}
		return lst;
	}
}
