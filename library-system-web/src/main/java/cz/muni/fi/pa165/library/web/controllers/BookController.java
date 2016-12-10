package cz.muni.fi.pa165.library.web.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Lenka (433591)
 * @version 10.12.2016
 */
@Controller
@RequestMapping("/books")
public class BookController {

    final static Logger log = LoggerFactory.getLogger(BookController.class);

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        return "book/list";
    }

}
