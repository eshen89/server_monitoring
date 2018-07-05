package contoller.monitor;

import exception.BadRequestException;
import exception.ServiceBusyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pojo.MonitorReport;
import util.MonitorStatus;
import util.StringUtil;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/monitor")
public class MonitorController {

    private static final Logger logger = LoggerFactory.getLogger(MonitorController.class);

    private static MonitorStatus currentStatus = MonitorStatus.IDLING;

    @RequestMapping(value = "/start", method = RequestMethod.GET)
    public String start(@RequestParam(value = "interval", defaultValue = "1") String interval,
                                         @RequestParam(value = "url") String url){

        if (currentStatus != MonitorStatus.IDLING) {
            throw new ServiceBusyException();
        }

        try {
            logger.debug(String.format("Service endpoint /start invoked.: interval=%s, url=%s", interval, url));
            int period = StringUtil.convertStringToInt(interval);

            url = StringUtil.convertStringToUrl(url);
            MonitorService.start(period, url);
            currentStatus = MonitorStatus.RUNNING;

        } catch (Exception e) {
            logger.debug(e.getMessage());
            throw new BadRequestException();
        }

        return "Service is running now.";

    }

    @RequestMapping(value = "/stop", method = RequestMethod.GET)
    public String stop(){
        logger.debug("Service endpoint /stop invoked.");

        if(currentStatus != MonitorStatus.IDLING && MonitorService.stop()){
            currentStatus = MonitorStatus.IDLING;
        }
        logger.debug("Stop service request processed.");
        return "Service is stopped.";
    }

    @RequestMapping(value = "/overview", method = RequestMethod.GET)
    public List<MonitorReport> getReports(){
        logger.debug("Service endpoint /overview invoked.");
        return MonitorService.getReports();
    }

}
