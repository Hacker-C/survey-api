package com.cg.controller;

import com.cg.pojo.vo.OptionVo;
import com.cg.pojo.vo.OptionVo2;
import com.cg.result.Result;
import com.cg.service.OptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("option")
public class OptionalController {
    @Autowired
    private OptionService optionService;

    @PostMapping
    public Result saveOption(@RequestBody OptionVo optionVo) {
        return optionService.saveOption(optionVo);
    }
    @PutMapping
    public Result saveOption(@RequestBody OptionVo2 optionVo2) {
        return optionService.updateOption(optionVo2);
    }

    @DeleteMapping("{id}")
    public Result deleteOption(@PathVariable Integer id) {
        return optionService.deleteOption(id);
    }

    @GetMapping
    public Result listOption(Integer pageNum, Integer pageSize, Integer questionId) {
        return optionService.listOption(pageNum, pageSize, questionId);
    }
}
