package com.generator.template.service;//package自定义
import com.generator.template.entity.${ClassName};

import java.util.List;
import java.util.Map;

/**
* Author:${Author}
* Date:${Date}
* 表注释:${Comment}
*/
public interface ${ClassName}Service {

    /**
    * 插入${ClassName}(非空)
    * @param bean
    * @return
    */
    public int add${ClassName}(${ClassName} bean);

    /**
    * 删除${ClassName}
    *
    * @param bean
    * @return
    */
    public int  remove${ClassName}(${ClassName} bean);

    /**
    * 修改${ClassName}
    *
    * @param bean
    * @return
    */
    public int  modify${ClassName}(${ClassName} bean);

    /**
    * 查询${ClassName}
    *
    * @param params
    * @return
    */
    public List<${ClassName}> query${ClassName}ByParams(Map params);




}