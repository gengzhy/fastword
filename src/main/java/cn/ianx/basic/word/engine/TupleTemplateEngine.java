package cn.ianx.basic.word.engine;

import com.deepoove.poi.XWPFTemplate;

import java.io.File;

/**
 * 原始文档模板引擎基类
 * @author gengz
 */
public interface TupleTemplateEngine {

    /**
     * 构建文档模板引擎
     *
     * @param wordAbsolutePath Word模板文件绝对路径
     * @return
     */
    XWPFTemplate buildWordTemplate(String wordAbsolutePath);

    /**
     * 构建文档模板引擎
     *
     * @param wordFile Word模板文件
     * @return
     */
    XWPFTemplate buildWordTemplate(File wordFile);
}
