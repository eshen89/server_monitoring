package pojo;

import java.sql.Timestamp;

public class MonitorReport {

    private final long id;
    private final String host_url;
    private final String server_status;
    private final Timestamp last_checked;


    public MonitorReport(long id, String host_url, String server_status, Timestamp last_checked) {
        this.id = id;
        this.host_url = host_url;
        this.server_status = server_status;
        this.last_checked = last_checked;
    }

    public long getId() {
        return id;
    }

    public String getHost_url() {
        return host_url;
    }

    public String getServer_status() {
        return server_status;
    }

    public Timestamp getLast_checked() {
        return last_checked;
    }
}
