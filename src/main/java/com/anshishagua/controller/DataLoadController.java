package com.anshishagua.controller;

import com.anshishagua.compute.Task;
import com.anshishagua.object.Result;
import com.anshishagua.object.Table;
import com.anshishagua.service.HiveService;
import com.anshishagua.service.TableService;
import com.anshishagua.service.TaskDependencyService;
import com.anshishagua.service.TaskExecutionService;
import com.anshishagua.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

/**
 * User: lixiao
 * Date: 2018/5/17
 * Time: 上午10:13
 */

@Controller
@RequestMapping("/dataLoad")
public class DataLoadController {
    private static final Logger LOG = LoggerFactory.getLogger(DataLoadController.class);

    @Autowired
    private TableService tableService;
    @Autowired
    private HiveService hiveService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private TaskDependencyService taskDependencyService;
    @Autowired
    private TaskExecutionService taskExecutionService;

    @Value("${hive.data.upload.file.path}")
    private String uploadDirectoryName;

    @RequestMapping("")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();

        List<Table> tables = tableService.getAllTables();

        modelAndView.addObject("tables", tables);
        modelAndView.setViewName("dataLoad/index");

        return modelAndView;
    }

    @RequestMapping("/upload")
    @ResponseBody
    public Result upload(@RequestParam(value = "tableName", defaultValue = "") String tableName,
                         @RequestParam(value = "date", defaultValue = "1111") String date,
                         @RequestParam(value = "fileEncoding", defaultValue = "UTF-8") String fileEncoding,
                         @RequestParam(value = "uploadFile") MultipartFile uploadFile) {
        if (!uploadDirectoryName.endsWith("/")) {
            uploadDirectoryName = uploadDirectoryName + "/";
        }

        String outputFileName = uploadDirectoryName + UUID.randomUUID().toString().replace("-", "") + ".txt";

        File target = new File(outputFileName);

        try {
            uploadFile.transferTo(target);
        } catch (IOException ex) {
            return Result.error(String.format("保存文件失败:%s", ex.getMessage()));
        }

        try {
            hiveService.load(outputFileName, tableName);
        } catch (IOException ex) {
            return Result.error(String.format("加载数据失败:%s", ex.getMessage()));
        }

        Table table = tableService.getByName(tableName);
        Task task = taskService.getByObjectId(table.getId());

        List<Long> taskIds = taskDependencyService.getDownStreamTaskIds(task.getId());

        taskExecutionService.executeTasks(taskIds, date, true);

        target.delete();

        String sql = "SELECT COUNT(*) FROM " + tableName;

        long count = 0;

        try {
            count = hiveService.count(sql);
        } catch (SQLException ex) {
            LOG.error("Failed to execute sql {}", sql, ex);

            return Result.error(String.format("加载数据成功,执行查询失败"));
        }

        Result result = Result.ok();
        result.setInfo(String.format("共加载%d条数据", count));

        return result;
    }
}