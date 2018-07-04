package pojo;

import util.MonitorStatus;

public class MonitorResponse<T> {

    private final long id;
    private final MonitorStatus monitorStatus;
    private final String message;
    private final T payload;

    public MonitorResponse(long id, MonitorStatus monitorStatus, String message, T payload) {
        this.id = id;
        this.monitorStatus = monitorStatus;
        this.message = message;
        this.payload = payload;
    }
}
