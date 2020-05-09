//package自定义


import java.util.List;
import java.util.Map;


/**
* Author:${Author}
* Date:${Date}
* 表注释:${Comment}
*/
public interface ${ClassName}Mapper {

<#--    <#list dbs as item>-->
<#--        private ${item.type} ${item.field};    //${item.comment}-->
<#--    </#list>-->
    /**
    * 插入${ClassName}(非空)
    * @param bean
    * @return
    */
    public int insert${ClassName}(${ClassName} bean);

    /**
    * 删除${ClassName}
    *
    * @param bean
    * @return
    */
    public int delete${ClassName}(${ClassName} bean);

    /**
    * 修改信息
    *
    * @param bean
    * @return
    */
    public int update${ClassName}(${ClassName} bean);

    /**
    * 查询信息
    *
    * @param params
    * @return
    */
    public List<${ClassName}> select${ClassName}ByParams(Map params);



}