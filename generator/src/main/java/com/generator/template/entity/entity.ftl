package ${package};


/**
* Author:${Author}
* Date:${Date}
* Project:${Project}
* package:${package}
*/

public class ${ClassName} {

    <#list db as property>
        private ${property.Type} ${property.Field};//${property.Comment}
    </#list>
<#--    <#list Map?keys as key>-->
<#--        private ${Map[key]}-->
<#--    </#list>-->

    <#list db as property>
        public ${property.Type} get${property.Field?cap_first}() {
        return ${property.Field};
        }

        public void set${property.Field?cap_first}(${property.Type} ${property.Field}) {
        this.${property.Field} = ${property.Field};
        }
    </#list>

}