package ${package};

<#if isdate??>
import java.util.Date;
</#if>

/**
* Author:${Author}
* Date:${Date}
* Project:${Project}
* package:${package}
*/
public interface ${ClassName} {

    <#list dbs as item>
        private ${item.type} ${item.field};    //${item.comment}
    </#list>





}