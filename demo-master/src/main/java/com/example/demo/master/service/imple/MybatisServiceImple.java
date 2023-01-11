package com.example.demo.master.service.imple;

import com.example.demo.master.entity.SBaffleConfig;
import com.example.demo.master.entity.SDictionary;
import com.example.demo.master.mapper.SBaffleConfigMapper;
import com.example.demo.master.mapper.SDictionaryMapper;
import com.example.demo.master.service.IMybatisService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * <h2>简述</h2>
 * 	   <ol>无</ol>
 *   <h2>功能描述</h2>
 * 	   <ol>无</ol>
 *   <h2>修改历史</h2>
 *     <ol>无</ol>
 * </p>
 *
 * @author wangjisen
 * @version 1.0
 * @date 2022/10/17 10:39
 */
@Service("mybatisService")
public class MybatisServiceImple implements IMybatisService {

    //logback日志
    private static final Logger LOGGER_LOGBACK = LoggerFactory.getLogger(MybatisServiceImple.class);

    @Resource
    private SBaffleConfigMapper sBaffleConfigMapper;

    @Resource
    private SDictionaryMapper sDictionaryMapper;

    public SDictionaryMapper getsDictionaryMapper() {
        return sDictionaryMapper;
    }

    public SBaffleConfigMapper getsBaffleConfigMapper() {
        return sBaffleConfigMapper;
    }

    @Override
    public PageInfo<SBaffleConfig> selectToPageHelper(int pageNum, int pageSize) throws Exception {
        PageHelper.startPage(pageNum, pageSize);
        List<SBaffleConfig> sBaffleConfigList = sBaffleConfigMapper.selectAll();
        //字典项翻译
        for (SBaffleConfig sBaffleConfig : sBaffleConfigList) {
            SDictionary sDictionary = sDictionaryMapper.selectByPrimaryKey("BAFFLE_OPEN", sBaffleConfig.getBaffleOpen());
            sBaffleConfig.setBaffleOpen(sDictionary.getDictCodeDesc());
        }
        PageInfo pageInfo = new PageInfo(sBaffleConfigList);
        LOGGER_LOGBACK.info("PageHelper 总页数：" + pageInfo.getPages());
        LOGGER_LOGBACK.info("PageHelper 总记录数：" + pageInfo.getTotal());
        LOGGER_LOGBACK.info("PageHelper 当前页码：" + pageInfo.getPageNum());
        LOGGER_LOGBACK.info("PageHelper 开始行号：" + pageInfo.getStartRow());
        LOGGER_LOGBACK.info("PageHelper 结束行号：" + pageInfo.getEndRow());
        return pageInfo;
    }
}
