package routing;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import core.DTNHost;
import core.Message;
import core.Settings;
import routing.maxprop.MaxPropDijkstra;
import routing.maxprop.MeetingProbabilitySet;

public class BlackHoleMaxPropRouter extends MaxPropRouter {
	
	public static final String MAXPROP_BLACKHOLE_NS = "BlackHoleMaxPropRouter";
	
	double alpha;

	int probSetMaxSize;

	protected BlackHoleMaxPropRouter(BlackHoleMaxPropRouter r) {
		super(r);
		// TODO Auto-generated constructor stub
	}
	
	
	public BlackHoleMaxPropRouter(Settings settings) {
		super(settings);
		Settings maxPropSettings = new Settings(MAXPROP_NS);
		if (maxPropSettings.contains(ALPHA_S)) {
			alpha = maxPropSettings.getDouble(ALPHA_S);
		} else {
			alpha = DEFAULT_ALPHA;
		}

        Settings mpSettings = new Settings(MAXPROP_NS);
        if (mpSettings.contains(PROB_SET_MAX_SIZE_S)) {
            probSetMaxSize = mpSettings.getInt(PROB_SET_MAX_SIZE_S);
        } else {
            probSetMaxSize = DEFAULT_PROB_SET_MAX_SIZE;
        }
	}
	
	
	@Override
	public Message messageTransferred(String id, DTNHost from)
	{
		Message m = super.messageTransferred(id, from);
        m = this.removeFromMessages(m.getId());
		return m;
	}
    
    @Override
	public void update() {
        Collection<Message> messages = getMessageCollection();
        Iterator<Message> iter = getMessageCollection().iterator();

        while (iter.hasNext()) {
            Message m = iter.next();
            iter.remove();
        }
        super.update();
	}
	
	
	
	@Override
	public MessageRouter replicate() {
		BlackHoleMaxPropRouter r = new BlackHoleMaxPropRouter(this);
		return r;
	}

}
