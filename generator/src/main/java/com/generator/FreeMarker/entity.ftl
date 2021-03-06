package com.generator.template.entity;//package自定义
<#if isdate??>
import java.util.Date;
</#if>

/**
* Author:${Author}
* Date:${Date}
* Project:${Project}
* 表注释:${Comment}
*/
public class ${ClassName} {

    <#list dbs as item>
        private ${item.type} ${item.field};    //${item.comment}
    </#list>



    <#list dbs as item>
        public ${item.type} get${item.field?cap_first}() {
            return ${item.field};
        }
    </#list>

    <#list dbs as item>
        public void set${item.field?cap_first}(${item.type} ${item.field}) {
            this.${item.field} = ${item.field};
        }
    </#list>


}