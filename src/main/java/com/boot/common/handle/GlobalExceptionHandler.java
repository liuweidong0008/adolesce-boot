package com.boot.common.handle;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author lwd
 * 设置一个@ControllerAdvice用来监控Multipart上传的文件大小是否受限，当出现此异常时在前端页面给出提示。
 * 利用@ControllerAdvice可以做很多东西，比如全局的统一异常处理等.
 *
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MultipartException.class)
    public String handleError1(MultipartException e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("message", e.getCause().getMessage());
        return "redirect:/uploadStatus";
    }

    /**
     * 统一拦截系统异常
     *
     * @param e
     * @return
     */
    /*@ExceptionHandler(Exception.class)
    public void handlerException(Exception e, HttpServletResponse response) {
        log.error("mvc 拦截器 : 系统异常 >> ", e);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter printWriter;
        RetData ret = RetData.fail("系统异常");
        if (e instanceof HttpMessageNotReadableException) {
            ret.setMsg("参数不合法");
        }
        if (e instanceof HttpRequestMethodNotSupportedException) {
            ret.setMsg(e.getMessage());
        }
        if(ex instanceof MissingServletRequestParameterException){
            ret.setMsg("缺少必需参数："+((MissingServletRequestParameterException) ex).getParameterName());
        }
        try {
            printWriter = response.getWriter();
            printWriter.write(JSON.toJSONString(ret));
        } catch (Exception ee) {
            log.error("", ee);
        }
    }*/

    /*@ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Map errorHandler(Exception ex) {
        Map map = new HashMap();
        map.put("code", 400);
        map.put("msg","这是异常");
        return map;
    }*/

   /* @ExceptionHandler(value = MyException.class)
    public ModelAndView myErrorHandler(MyException ex) {
        ModelAndView modelAndView = new ModelAndView();
        //指定错误页面的模板页
        modelAndView.setViewName("error");
        modelAndView.addObject("code", ex.getCode());
        modelAndView.addObject("msg", ex.getMsg());
        return modelAndView;
    }*/


}
