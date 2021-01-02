package report;

import core.DTNHost;
import core.Message;

public class EmMessageDeliveryReport extends MessageDeliveryReport {

	public static String HEADER="# time  created  delivered  delivered/created";
	private int created;
	private int delivered;

	/**
	 * Constructor.
	 */
	public EmMessageDeliveryReport() {
		init();
	}

	@Override
	public void init() {
		super.init();
		created = 0;
		delivered = 0;
		write(HEADER);
	}

	public void messageTransferred(Message m, DTNHost from, DTNHost to,
			boolean firstDelivery) {
	
		if(m.getId().contains("EM")) {
			if (firstDelivery && !isWarmup() && !isWarmupID(m.getId())) {		
		
				delivered++;
				reportValues();
			}
		}
	}

	public void newMessage(Message m) {
		if (isWarmup()) {
			addWarmupID(m.getId());
			return;
		}
		if(m.getId().contains("EM")) {
			created++;
			reportValues();
		}
	}

	/**
	 * Writes the current values to report file
	 */
	private void reportValues() {
		double prob = (1.0 * delivered) / created;
		write(format(getSimTime()) + " " + created + " " + delivered +
				" " + format(prob));
	}

	// nothing to implement for the rest
	public void messageDeleted(Message m, DTNHost where, boolean dropped) {}
	public void messageTransferAborted(Message m, DTNHost from, DTNHost to) {}
	public void messageTransferStarted(Message m, DTNHost from, DTNHost to) {}

	@Override
	public void done() {
		super.done();
	}
}
