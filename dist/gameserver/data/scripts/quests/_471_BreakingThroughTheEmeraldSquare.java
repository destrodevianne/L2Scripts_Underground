package quests;

import l2s.gameserver.model.Player;
import l2s.gameserver.model.instances.NpcInstance;
import l2s.gameserver.model.quest.Quest;
import l2s.gameserver.model.quest.QuestState;
import l2s.gameserver.scripts.ScriptFile;

public class _471_BreakingThroughTheEmeraldSquare extends Quest implements ScriptFile
{
	//npc
	public static final int FIOREN = 33044;
	
	//mobs
	public static final int EMABIFI = 25881;

	@Override
	public void onLoad()
	{}

	@Override
	public void onReload()
	{}

	@Override
	public void onShutdown()
	{}

	public _471_BreakingThroughTheEmeraldSquare()
	{
		super(false);
		addStartNpc(FIOREN);
		addKillId(EMABIFI);
		addLevelCheck(97);
	}

	@Override
	public String onEvent(String event, QuestState st, NpcInstance npc)
	{
		Player player = st.getPlayer();
		String htmltext = event;
		if(event.equalsIgnoreCase("33044-3.htm"))
		{
			st.setCond(1);
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("33044-6.htm"))
		{
			st.giveItems(30387,2); // hell proof
			st.unset("cond");
			st.playSound(SOUND_FINISH);
			st.exitCurrentQuest(this);
		}
		return event;
	}

	@Override
	public String onTalk(NpcInstance npc, QuestState st)
	{
		Player player = st.getPlayer();
		int npcId = npc.getNpcId();
		int state = st.getState();
		int cond = st.getCond();
		if(npcId == FIOREN)
		{
			if(state == 1)
			{
				if(player.getLevel() < 97)
					return "33044-lvl.htm";

				if(!st.isNowAvailable())
					return "33044-comp.htm";
				return "33044.htm";
			}
			else if(state == 2)
			{
				if(cond == 1)
					return "33044-4.htm";
				else if(cond == 2)
					return "33044-5.htm";
			}
		}
		return "noquest";
	}
	@Override
	public String onKill(NpcInstance npc, QuestState st)
	{
		int cond = st.getCond();
		if(cond != 1 || npc == null)
			return null;
		st.setCond(2);
		return null;
	}	
}