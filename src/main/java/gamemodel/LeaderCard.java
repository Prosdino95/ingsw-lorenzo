package gamemodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import gamemodel.command.GameError;
import gamemodel.command.GameException;
import gamemodel.effects.CouncilPrivileges;
import gamemodel.effects.IstantEffect;
import gamemodel.permanenteffect.PermanentEffect;

public class LeaderCard implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Requirement requirement;
	
	private PermanentEffect pe;
	private List<IstantEffect> oncePerRound=new ArrayList<>();
	
	private String name;
	private int id;
	private boolean usedOPR = false;
	private boolean played = false;	
	private Player owner;
	
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
	
	public void activateOPT() throws GameException {
		if (oncePerRound == null) {
			System.out.println("Why did you call activate??? This card has only a permanent effect");
			return;
		}
		
		if (usedOPR)
			throw new GameException(GameError.LEADER_CARD_USED);
		for(IstantEffect e:oncePerRound)
			e.activate(owner);
	}

	public void discard() {
		new CouncilPrivileges(1).activate(owner);
	}
	
	public void play() throws GameException {
		if (requirement.isSatisfiedBy(owner)) {
			played = true;
		} else {
			throw new GameException(GameError.LEADER_CARD_NOT_ENOUGH_MONEY);
		}
	}
	
	public void prepareForNewRound() {
		usedOPR = true;
	}

	public PermanentEffect getPermanentEffect() {
		return pe;
	}
	
	

	@Override
	public String toString() {
		return "LeaderCard [name=" + name + ", id=" + id + ", requirement=" + requirement + ", pe=" + pe
				+ ", oncePerRound=" + oncePerRound + ", usedOPR=" + usedOPR + ", played=" + played + "]";
	}

	public void setOwner(Player player) {
		owner = player;
	}

	public boolean getPlayedOPR() {
		// TODO Auto-generated method stub
		return usedOPR;
	}

	public boolean getPlayed() {
		// TODO Auto-generated method stub
		return played;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
