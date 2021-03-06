
package me.u6k.narou_analyze.narou_crawler.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import me.u6k.narou_analyze.narou_crawler.service.CrawlerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CrawlerController {

    private static final Logger L = LoggerFactory.getLogger(CrawlerController.class);

    @Autowired
    private CrawlerService service;

    @RequestMapping(value = "/api/novels/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public UpdateNovelIndexResult updateNovelIndex(@RequestBody UpdateNovelIndexParam param) {
        L.debug("#updateNovelIndex: param={}", param);

        long count = this.service.updateNovelIndex(param.getSearchDate());

        UpdateNovelIndexResult result = new UpdateNovelIndexResult();
        result.setCount(count);

        return result;
    }

    @RequestMapping(value = "/api/novels/{ncode}/meta/download", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void downloadNovelMeta(@PathVariable("ncode") String ncode) {
        L.debug("#downloadNovelMeta: ncode={}", ncode);
        try {
            this.service.downloadNovelMeta(ncode);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(value = "/api/novels/{ncode}/meta/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void analyzeNovelMeta(@PathVariable("ncode") String ncode) throws ParseException {
        L.debug("#analyzeNovelMeta: ncode={}", ncode);
        try {
            this.service.analyzeNovelMeta(ncode);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(value = "/api/ncodes/{searchDate}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<String> findNCodes(@PathVariable("searchDate") String searchDateText) {
        L.debug("#findNCodes: searchDateText={}", searchDateText);
        try {
            Date searchDate = new SimpleDateFormat("yyyy-MM-dd").parse(searchDateText);

            List<String> ncodes = this.service.findNovelIndex(searchDate);

            return ncodes;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

}
