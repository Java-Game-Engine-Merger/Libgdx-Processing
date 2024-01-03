package l42111996.threadPool.disruptor;

import com.lmax.disruptor.EventFactory;

public class DistriptorEventFactory implements EventFactory<DistriptorHandler>
{

	@Override
    public DistriptorHandler newInstance() {
		return new DistriptorHandler();
	}

}
