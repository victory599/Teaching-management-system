package com.liu.controller;

import com.liu.entity.Power;
import com.liu.entity.User;
import com.liu.service.PowerService;
import com.liu.service.SemesterService;
import com.liu.service.StudentService;
import com.liu.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Controller
public class PowerControllerPage {
    @Autowired
    PowerService powerService;
    @Autowired
    SemesterService semesterService;
    @Autowired
    StudentService studentService;
    @Autowired
    TeacherService teacherService;

    // 传参...
    private Integer tmpcno;
    private Integer tmpSemesterId;

    /**
     * 检查权限，是否管理员
     * @param request
     * @return
     */
    public boolean checkPower(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return false;
        }
        return user.getType() == 2;
    }

    @RequestMapping("/ExcelInsert")
    public String excelInsert(HttpServletRequest request) {
        return "StudentsFileAdd";
    }

    @RequestMapping("/GoChangePassword")
    public String goChangePassword(){
        return "changePassword";
    }

    @ResponseBody
    @RequestMapping("/ExcelAfterInsert")
    public String excelAfterInsert(HttpServletRequest request, @RequestParam("file") MultipartFile multfile) {
        if (checkPower(request) == false) {
            return "error";
        }
        // 获取文件名
        String fileName = multfile.getOriginalFilename();
        // 获取文件前缀
        String prefix = fileName.substring(fileName.lastIndexOf("."));
        // 用uuid作为文件名，防止生成的临时文件重复
        File file = null;
        try {
            file = File.createTempFile(getUUID(), prefix);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 转存文件
        try {
            multfile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        /* 测试数据 */
        tmpSemesterId = 1;
        tmpcno = 1;

        if (fileName == null && "".equals(fileName)) {
            return "文件名不能为空！";
        } else {
            if (fileName.endsWith("xls") || fileName.endsWith("xlsx")) {
                boolean flag = false;
                try {
                    flag = studentService.excel(file);
                } catch (Exception e) {
                    return "导入失败";
                }
                // 导入结束时，删除临时文件
                deleteFile(file);
                if (flag) {
                    return "导入成功！";
                } else {
                    return "导入失败！";
                }
            }
            return "文件格式错误！";
        }
    }

    @ResponseBody
    @RequestMapping("/ExcelAfterInsertForT")
    public String excelAfterInsertForT(HttpServletRequest request, @RequestParam("file") MultipartFile multipartFile) {
        if (checkPower(request) == false) {
            return "error";
        }
        // 获取文件名
        String fileName = multipartFile.getOriginalFilename();
        // 获取文件前缀
        String prefix = fileName.substring(fileName.lastIndexOf("."));
        // 用uuid作为文件名，防止生成的临时文件重复
        File file = null;
        try {
            file = File.createTempFile(getUUID(), prefix);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 转存文件
        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        /* 测试数据 */
        tmpSemesterId = 1;
        tmpcno = 1;

        if (fileName == null && "".equals(fileName)) {
            return "文件名不能为空！";
        } else {
            if (fileName.endsWith("xls") || fileName.endsWith("xlsx")) {
                boolean flag = false;
                try {
                    flag = teacherService.excel(file);
                } catch (Exception e) {
                    return "导入失败";
                }
                // 导入结束时，删除临时文件
                deleteFile(file);
                if (flag) {
                    return "导入成功！";
                } else {
                    return "导入失败！";
                }
            }
            return "文件格式错误！";
        }
    }

    @RequestMapping("/GoHomePage")
    public String homePage(Map<String, Object> model, HttpServletRequest request) {
        if (checkPower(request) == false) {
            return "error";
        }
        Power status = powerService.getStatus();
        model.put("power", status);
        request.setAttribute("currentSemesterInfo", semesterService.getCurrentSemesterInfo());
        return "HomePage";
    }

    @RequestMapping("/PowerManage")
    public String powerControll(Map<String, Object> model, HttpServletRequest request) {
        if (checkPower(request) == false) {
            return "error";
        }
        request.setAttribute("score", powerService.getScore());
        request.setAttribute("abnormal", powerService.getAbnormal());
        request.setAttribute("selectCourse", powerService.getSelectCourse());
        return "PowerManage";
    }

    @PostMapping("/OpenSelectCourse")
    public String openSelectCourse(Map<String, Object> model, HttpServletRequest request) {
        if (checkPower(request) == false) {
            return "error";
        }
        powerService.openSelectCourse();
        return "redirect:/PowerManage";
    }

    @PostMapping("/OpenScore")
    public String openScore(Map<String, Object> model, HttpServletRequest request) {
        if (checkPower(request) == false) {
            return "error";
        }
        powerService.openScore();
        return "redirect:/PowerManage";
    }

    @PostMapping("/OpenAbnormal")
    public String openAbnormal(Map<String, Object> model, HttpServletRequest request) {
        if (checkPower(request) == false) {
            return "error";
        }
        powerService.openAbnormal();
        return "redirect:/PowerManage";
    }

    @PostMapping("/CloseAbnormal")
    public String closeAbnormal(Map<String, Object> model, HttpServletRequest request) {
        if (checkPower(request) == false) {
            return "error";
        }
        powerService.closeAbnormal();
        return "redirect:/PowerManage";
    }

    @PostMapping("/CloseScore")
    public String closeScore(Map<String, Object> model, HttpServletRequest request) {
        if (checkPower(request) == false) {
            return "error";
        }
        powerService.closeScore();
        return "redirect:/PowerManage";
    }

    @PostMapping("/CloseSelectCourse")
    public String closeSelectCourse(Map<String, Object> model, HttpServletRequest request) {
        if (checkPower(request) == false) {
            return "error";
        }
        powerService.closeSelectCourse();
        return "redirect:/PowerManage";
    }

    /**
     * 获取32位UUID字符串 临时文件名
     * @return
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 删除临时文件
     * @param files
     */
    private void deleteFile(File... files) {
        for (File file : files) {
            if (file.exists()) {
                file.delete();
            }
        }
    }
}
