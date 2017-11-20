package com.idata365.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TestController {
	private final static Logger LOG = LoggerFactory.getLogger(TestController.class);
	@RequestMapping("test")
    public String name() {
        LOG.info("a info logger...");
        LOG.error("a error logger...");
        return "welcome to the FYFT index page.I'm shen.";
    }
}
