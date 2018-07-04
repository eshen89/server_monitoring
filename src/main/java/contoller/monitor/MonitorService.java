package contoller.monitor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pojo.MonitorReport;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

class MonitorService {

    private static final Logger logger = LoggerFactory.getLogger(MonitorController.class);

    private static ScheduledExecutorService scheduledExecutorService;
    private static Future<?> futureSchedule;
    private static final AtomicLong counter = new AtomicLong();
    private static List<MonitorReport> reports;

    static void start(int period, String url) {
        logger.debug("Monitoring service started...");
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        //Initiate a repeating job based on the time interval.
        futureSchedule = scheduledExecutorService.scheduleAtFixedRate(() -> checkAndLogRemoteServerStatus(url),
                0, period, TimeUnit.MINUTES);
    }

    static boolean stop(){
        if(futureSchedule.cancel(true)) {
            scheduledExecutorService.shutdown();
        }
        logger.debug("Monitoring service stopped...");
        return true;
    }

    static List<MonitorReport> getReports() {
        return reports;
    }

    //Fetch remote server status and wrap result into MonitorReport class, add it to report list.
    private static void checkAndLogRemoteServerStatus(String url) {
        updateRecord(url, callRemote(url));
        logger.debug(String.format("Report list updated, currently have %s records", reports.size()));
    }

    private static void initReportList(){
        if(null == reports){
            reports = new ArrayList<>();
        }
    }

    private static void updateRecord(String url, Response response){
        initReportList();
        MonitorReport report = new MonitorReport(
                counter.incrementAndGet(),
                url,
                response.readEntity(String.class),
                new Timestamp(System.currentTimeMillis()));
        reports.add(report);
    }

    private static Response callRemote(String url){
        logger.debug(String.format("Remote host %s is called on %s", url, new Timestamp(System.currentTimeMillis())));
        Client client = ClientBuilder.newClient();
        Response response = client.target(url)
                .request(MediaType.APPLICATION_JSON)
                .get();

        logger.debug(String.format("Remote host answer with status code: %s", response.getStatus()));
        return response;
    }
}
