package org.lucifer.abchat.controller;

import org.lucifer.abchat.domain.BugTrack;
import org.lucifer.abchat.service.BugTrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "/bugTrack")
public class BugTrackController {
    @Autowired
    private BugTrackService bugTrackService;

    @RequestMapping(value = "/page/{page}", method = RequestMethod.GET)
    public
    @ResponseBody
    List<BugTrack> mlPage(@PathVariable(value = "page")Long page) {
        return bugTrackService.getPage(page);
    }

    @RequestMapping(value = "/page/count", method = RequestMethod.GET)
    public
    @ResponseBody
    Long pagesCount() {
        return bugTrackService.countPages();
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public
    @ResponseBody
    Long mlCount() {
        return bugTrackService.count();
    }

    @RequestMapping(method = RequestMethod.GET)
    public
    @ResponseBody
    List<BugTrack> getAll() {
        return bugTrackService.getAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    BugTrack get(@PathVariable(value = "id") Long id) {
        return bugTrackService.findById(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public
    @ResponseBody
    BugTrack delete(@PathVariable(value = "id") Long id) {
        return bugTrackService.delete(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public
    @ResponseBody
    BugTrack save(BugTrack bt) {
        return bugTrackService.save(bt);
    }
}
