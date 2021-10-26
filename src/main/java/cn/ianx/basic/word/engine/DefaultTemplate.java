package cn.ianx.basic.word.engine;

import cn.ianx.basic.exception.FastWordException;
import cn.ianx.basic.holder.ContextHolder;
import cn.ianx.basic.word.annotation.WordField;
import cn.ianx.basic.word.annotation.WordType;
import cn.ianx.util.ClassUtils;
import com.deepoove.poi.XWPFTemplate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 默认文档模板引擎基类
 * @author gengz
 */
@Component
public class DefaultTemplate implements TupleTemplateEngine {

    /**
     * Word支持文档后缀
     */
    private final static Pattern WORD_SUFFIX = Pattern.compile("^.*.(doc|docx|DOC|DOCX)$");

    /**
     * 构建文档模板引擎
     *
     * @param wordAbsolutePath Word模板文件绝对路径
     * @return
     */
    @Override
    public XWPFTemplate buildWordTemplate(String wordAbsolutePath) {
        return XWPFTemplate.compile(wordAbsolutePath);
    }

    /**
     * 构建文档模板引擎
     *
     * @param wordFile Word模板文件
     * @return
     */
    @Override
    public XWPFTemplate buildWordTemplate(File wordFile) {
        return XWPFTemplate.compile(wordFile);
    }

    /**
     * 构建文档模板引擎
     *
     * @param wordFile Word模板文件
     * @return
     */
    public XWPFTemplate buildWordTemplate(InputStream wordFile) {
        return XWPFTemplate.compile(wordFile);
    }

    /**
     *
     * @param wordTemplate word模板文件
     * @param newFileName 输出文件路径
     * @param dataEntity 数据实体
     * @param <T>
     */
    public <T extends Serializable> void writeWord(File wordTemplate, String newFileName, T dataEntity) throws IOException {
        InputStream inputStream = new FileInputStream(wordTemplate);
        this.writeWord(inputStream, newFileName, dataEntity);
    }

    /**
     *
     * @param wordTemplate word模板文件
     * @param newFileName 输出文件路径
     * @param dataEntity 数据实体
     * @param <T>
     */
    public <T extends Serializable> void writeWord(InputStream wordTemplate, String newFileName, T dataEntity) throws IOException {
        if (!WORD_SUFFIX.matcher(newFileName).matches()) {
            throw new FastWordException("暂不支持该文档类型.");
        }
        ConcurrentHashMap<String, Object> datas = new ConcurrentHashMap<>();
        if (dataEntity instanceof Map<?, ?>) {
            datas.putAll((Map<? extends String, ?>) dataEntity);
        } else if (dataEntity instanceof Collection<?>) {
            throw new FastWordException("暂不支持多文件集合.");
        } else {
            this.beforeWriteValidate(dataEntity);
            // 拿到数据实体注解
            Class<? extends Serializable> entityClass = dataEntity.getClass();
            WordType wordType = entityClass.getAnnotation(WordType.class);
            newFileName = wordType.value().concat(newFileName);
            // 解析数据实体属性
            List<Field> fields = ClassUtils.getAllFields(entityClass, WordField.class, null, false);
            Set<Field> dataFields = fields.stream().filter(field -> !field.getAnnotation(WordField.class).ignore()).collect(Collectors.toSet());

            dataFields.forEach(field -> {
                try {
                    field.setAccessible(true);
                    Object value = field.get(dataEntity);
                    if (value != null) {
                        WordField wordField = field.getAnnotation(WordField.class);
                        if (StringUtils.isBlank(wordField.fieldName())) {
                            datas.put(field.getName(), value);
                        } else {
                            datas.put(wordField.fieldName(), value);
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            });
        }
        // 渲染及写数据
        XWPFTemplate template = this.buildWordTemplate(wordTemplate).render(datas);
        this.writeAndClose(template, newFileName);
    }

    /**
     * 写数据
     *
     * @param template 模板
     * @param fileName 生成文件名
     * @throws IOException
     */
    private void writeAndClose(XWPFTemplate template, String fileName) throws IOException {
        // 设置强制下载不打开（生成word到设置浏览默认下载地址）
        HttpServletResponse response = ContextHolder.get().getResponse();
        response.setContentType("application/force-download");
        // 设置文件名
        response.addHeader("Content-Disposition", "attachment;fileName=" + new String(fileName.getBytes("gb2312"), "ISO8859-1"));
        template.writeAndClose(response.getOutputStream());
    }

    /**
     * 写数据前校验
     *
     * @param dataEntity
     * @param <T>
     */
    private <T extends Serializable> void beforeWriteValidate(T dataEntity) {
        if (dataEntity == null) {
            throw new FastWordException("数据为空，无法完成写数据操作");
        }
        if (!dataEntity.getClass().isAnnotationPresent(WordType.class)) {
            throw new FastWordException("当前数据实体类[%S]未使用注解[]标注，无法完成写数据操作.",
                    dataEntity.getClass().getName(), WordType.class.getName());
        }
    }
}
